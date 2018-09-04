package com.shenyingchengxun.chat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.Client;
import com.shenyingchengxun.action.service.AgentService;
import com.shenyingchengxun.action.service.ClientService;
import com.shenyingchengxun.chat.bean.ChatLog;
import com.shenyingchengxun.chat.service.ChatcService;

/**
 * 聊天界面，左侧用户栏、未读信息查询
 * @author hadoop
 *
 */
@Controller
@RequestMapping(value = "/chatc")
public class Chatc {
	@Resource AgentService agentServiceImp;
	@Resource ClientService clientServiceImp;
	@Resource ChatcService chatcServiceImp;
	
	@RequestMapping(value = "/getChatNickName")
	public void getChatNickName(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String openid = request.getParameter("openid");
		String toOpenid = request.getParameter("toOpenid");
		Map<String, String> map = new HashMap<String, String>();
		Agent agent = null;
		Client client = null;
		agent = agentServiceImp.getAgentByOpenid(openid);
		if(agent != null){
			client = clientServiceImp.getClientByOpenid(toOpenid);
			String myname="我";
			if(agent.getNickname()!=null&&!"".equals(agent.getNickname())){
				myname=agent.getNickname();
			}
			map.put("fromNickname", myname);
			String otname = "";
			if(client!=null){
				otname = client.getNickname();
			}
			String require = chatcServiceImp.getRequire(openid, toOpenid);
			if(require!=null&&!"".equals(require)){
				otname = require;
			}
			if(otname==null||"".equals(otname)){
				otname = "客户";
			}
			map.put("toNickName", otname);
		} else {
			agent = agentServiceImp.getAgentByOpenid(toOpenid);
			client = clientServiceImp.getClientByOpenid(openid);
			String myname="我";
			if(client!=null&&client.getNickname()!=null&&!"".equals(client.getNickname())){
				myname = client.getNickname();
			}
			map.put("fromNickname", myname);
			String otname = "经纪人";
			if(agent!=null&&agent.getNickname()!=null&&!"".equals(agent.getNickname())){
				otname=agent.getNickname();
			}
			map.put("toNickName", otname);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonObject);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	@RequestMapping(value = "/getUser")
	public void leftUserList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String openid = request.getParameter("openid");
		String toOpenid = request.getParameter("toOpenid");
		List<Map<String,String>> resultList = chatcServiceImp.getChatUserList(openid,toOpenid);
		JSONArray json = JSONArray.fromObject(resultList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	@RequestMapping(value = "/getUnreadMsg")
	public void getUnreadMsg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String openid = request.getParameter("openid");
		String toOpenid = request.getParameter("toOpenid");
		ChatLog cl = new ChatLog();
		cl.setSenderid(openid);
		cl.setReceiverid(toOpenid);
		List<ChatLog> chats = chatcServiceImp.getUnreadMsgByOpenid(cl);
		chatcServiceImp.updateUnreadToRead(cl); 
		JSONArray json = JSONArray.fromObject(chats);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	@RequestMapping(value = "/upRequire")
	public void upRequire(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String openid = request.getParameter("openid");
		String toOpenid = request.getParameter("toOpenid");
		String require = request.getParameter("require");
		chatcServiceImp.upRequire(openid, toOpenid, require); 
	}
	@RequestMapping(value = "/delChatList")
	public void delChatList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String openid = request.getParameter("openid");
		String toOpenid = request.getParameter("toOpenid");
		chatcServiceImp.delChatList(openid, toOpenid); 
	}
	/**
	 * 是否是agent
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/isInAgentClient")
	public void isInAgentClient(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		String toOpenid =  request.getParameter("toOpenid");
		int num =  chatcServiceImp.getAgentClient(openid, toOpenid);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("flag", num>0);
		JSONObject json_result = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json_result);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取聊天记录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOldMsg")
	public void getOldMsg(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		String toOpenid =  request.getParameter("toOpenid");
		String index =  request.getParameter("index");
		String pageNum =  request.getParameter("pageNum");
		List<ChatLog> list = chatcServiceImp.getOldMsg(openid,toOpenid,index,pageNum);
		JSONArray json_result = JSONArray.fromObject(list);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json_result);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
