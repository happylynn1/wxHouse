package com.shenyingchengxun.chat.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shenyingchengxun.chat.bean.ChatLog;
import com.shenyingchengxun.chat.dao.ChatcDao;

@Service("chatcServiceImp")
public class ChatcServcieImp implements ChatcService{

	@Resource ChatcDao chatcDao;
	public List<ChatLog> getUnreadMsgByOpenid(ChatLog cl) {
		return chatcDao.getUnreadMsgByOpenid(cl);
	}
	
	@Override
	public void updateUnreadToRead(ChatLog cl) {
		chatcDao.updateMsgSendToRead(cl);
		chatcDao.updateUnreadToRead(cl);
	}

	@Override
	public List<Map<String, String>> getChatUserList(String openid,String toOpenid) {
		// TODO Auto-generated method stub
		return chatcDao.getChatUserList(openid,toOpenid);
	}

	@Override
	public void upRequire(String openid, String toOpenid, String require) {
		// TODO Auto-generated method stub
		chatcDao.upRequire(openid,toOpenid,require);
	}

	@Override
	public String getRequire(String openid, String toOpenid) {
		// TODO Auto-generated method stub
		return chatcDao.getRequire(openid, toOpenid);
	}

	@Override
	public int getAgentClient(String openid, String toOpenid) {
		// TODO Auto-generated method stub
		return chatcDao.getAgentClient(openid, toOpenid);
	}

	@Override
	public List<ChatLog> getOldMsg(String openid, String toOpenid, String index,String pageNum) {
		// TODO Auto-generated method stub
		int i = 0,j=0;
		if(index!=null&&!"".equals(index)){
			i = Integer.parseInt(index);
		}
		if(pageNum!=null&&!"".equals(pageNum)){
			j = Integer.parseInt(pageNum);
		}
		return chatcDao.getOldMsg(openid,toOpenid,i,j);
	}

	@Override
	public void delChatList(String openid, String toOpenid) {
		// TODO 自动生成的方法存根
		chatcDao.delChatList(openid, toOpenid);
	}

	@Override
	public void addChatList(String openid, String toOpenid) {
		// TODO 自动生成的方法存根
		chatcDao.addChatList(openid, toOpenid);
	}
}
