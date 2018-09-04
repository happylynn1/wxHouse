package com.shenyingchengxun.action.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.AgentClient;
import com.shenyingchengxun.action.bean.Client;
import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.dao.AgentClientDao;
import com.shenyingchengxun.action.dao.AgentDao;
import com.shenyingchengxun.action.dao.ClickLogDao;
import com.shenyingchengxun.action.dao.ClientDao;
import com.shenyingchengxun.action.dao.ContactDao;
import com.shenyingchengxun.action.message.resp.TextMessage;
import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.action.util.MessageUtil;
import com.shenyingchengxun.chat.dao.ChatcDao;
/**
 * 消息分发业务
 * @author niufeihu
 *
 */
@Service("MsgDistributeImp")
public class MsgDistributeImp implements MsgDistribute{
	
	
	private static Logger logger = Logger.getLogger(MsgDistributeImp.class);
	
	@Resource ChatcDao chatcDao;	
	@Resource ContactDao contactDao;	
	@Autowired AgentDao agentDao;	
	@Autowired ClientDao clientDao;	
	@Autowired ClickLogDao clickLogDao;	
	@Autowired AgentClientDao agentClientDao;	
	
	public  String respMessage = null;
	public  String respContent  = null;
	
	public  String forText(Map<String, String> requestMap){
		String openid = requestMap.get("FromUserName");
		String requestStr = requestMap.get("Content"),content="";
		Agent agent = agentDao.getAgentByOpenid(openid);
		if(agent!=null){
			if(requestStr.contains("我的")||requestStr.contains("名片")||requestStr.contains("信息")){
				content = "<a href=\""+Config.Base+"page/toWxCard.do?storeId="+agent.getStoreId()+"&openid="+openid+"\" >我的微名片:点击进入</a>";
				TextMessage textMessage = MessageUtil.getTextMessage(requestMap);
				textMessage.setContent(content);
		        respMessage = MessageUtil.textMessageToXml(textMessage);
		        return respMessage;
			}
		}
		return null;
	}
	public  String forImage(Map<String, String> requestMap){
		return null;
	}
	public  String forLocation(Map<String, String> requestMap){
		return null;
	}
	public  String forTLink(Map<String, String> requestMap){
		return null;
	}
	public  String forVoice(Map<String, String> requestMap){
		return null;
	}
	public  String forEvent(StoreInfo si,Map<String, String> requestMap){
		logger.debug("**********************事件消息*******************************");
		logger.debug(requestMap);
        String openid = requestMap.get("FromUserName");
		String eventType = requestMap.get("Event");
        String eventKey = requestMap.get("EventKey");
        Agent agent = agentDao.getAgentByOpenid(openid);
        //关注
        if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
        	logger.debug("**********************关注-->事件*******************************");
    		logger.debug(requestMap);
        	//如果不是“ 经纪人”,ignore(动态)添加到client表中
            //如果是 “经纪人”,不做任何处理
        	if(agent==null){
        		Client client = clientDao.getClientByOpenid(openid);
        		if(client==null){
        			clientDao.addClient(Config.getUserInfo(si.getStoreId(),openid,si.getAppId(),si.getAppSecret()));
        		}
        	}
        	//扫描二维码关注
        	//判断是不是经纪人的二维码
        	if (eventKey!=null&&eventKey.contains("qrscene")) {
        		logger.debug("**********************扫描-->关注-->事件*******************************");
        		logger.debug(requestMap);
        		//判断二维码类型
        		//(门店二维码-经纪人扫描用)
        		if(eventKey.contains("storeqrcode")){
        			if(agent==null){
        				clientDao.delClient(openid);
        				agentDao.addAgent(Config.getAgentInfo(si.getStoreId(),openid,si.getAppId(),si.getAppSecret()));
        			}
        		//(经纪人二维码-用户扫描用)
        		}else{
        			//如果不是“ 经纪人”,添加到agentClient表中
        			if(agent==null){
        				Map<String,String> map = agentClientDao.getAgentClient(new AgentClient(eventKey.replace("qrscene_", ""),openid));
        				if(map==null){
        					agentClientDao.addAgentClient(new AgentClient(eventKey.replace("qrscene_", ""),openid));
        				}
        			}
        			//如果是 “经纪人”,不做任何处理
        		}
			}
        	respMessage = MessageUtil.getHelloNews(si,requestMap);
        	return respMessage;
        //扫描
        }else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
        	logger.debug("**********************扫描-->事件*******************************");
    		logger.debug(requestMap);
        	//判断二维码类型
    		//(门店二维码-经纪人扫描用)
        	if(eventKey.contains("storeqrcode")){
        		if(agent==null){
        			clientDao.delClient(openid);
        			agentClientDao.delAgentClient(openid);
        			agentDao.addAgent(Config.getAgentInfo(si.getStoreId(),openid,si.getAppId(),si.getAppSecret()));
        		}
        	//(经纪人二维码-用户扫描用)	
        	}else{
        		//如果不是“ 经纪人”,ignore(动态)添加到client表中,
        		if(agent==null){
        			clientDao.addClient(Config.getUserInfo(si.getStoreId(),openid,si.getAppId(),si.getAppSecret()));
        			//如果扫描的二维码是经纪人二维码,添加关系到agentClient表中
        			if(eventKey!=null){
        				String agentOpenid = eventKey.replace("qrscene_", "");
        				contactDao.delContactFilter(openid, agentOpenid);
        				Map<String,String> map = agentClientDao.getAgentClient(new AgentClient(agentOpenid,openid));
        				System.out.println(map);
        				if(map==null){
        					agentClientDao.addAgentClient(new AgentClient(agentOpenid,openid));
        				}
        			}
        		}
        		//如果是 “经纪人”,不做任何处理
        	}
        	respMessage = MessageUtil.getHelloNews(si,requestMap);
        	return respMessage;
        // 取消订阅
        }else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
            // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
        	Client client = clientDao.getClientByOpenid(openid);
    		if(client!=null){
    			clientDao.delClient(openid);
    			chatcDao.delChatLog(openid);
    			chatcDao.delChatListByOpenid(openid);
    			agentClientDao.delAgentClient(openid);
    		}
        }else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
        	//记录用户点击菜单动作
        }else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
        	//记录用户点击链接动作
        	clickLogDao.addClickLog(openid, eventKey);
        }
        return null;
	}
}
