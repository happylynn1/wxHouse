package com.shenyingchengxun.action.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.Client;
import com.shenyingchengxun.action.service.AgentService;
import com.shenyingchengxun.action.service.ClientService;
import com.shenyingchengxun.action.service.ContactService;

@Controller
@RequestMapping(value = "/contact")
public class Contact {
	
	@Resource ClientService clientServiceImp;
	@Resource AgentService agentServiceImp;
	@Resource ContactService contactServiceImp;
	
	@RequestMapping(value = "/getMyAgent")
	public void getMyAgent(HttpServletRequest request,
			HttpServletResponse response) throws IOException, DocumentException {
		String openid = request.getParameter("openid"); 
		String storeId = request.getParameter("storeId"); 
		if(openid==null||openid.equals("")||openid.equals("null")){
			 openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		System.out.println("************************agentList*****************************");
		List<Agent> agentList = agentServiceImp.getAgentListByClientOpenid(openid,storeId);
		System.out.println("************************agentList2*****************************");
		List<Agent> agentList2 = agentServiceImp.getAgentListByChatClient(openid,storeId);
		System.out.println("************************filterList*****************************");
		List<Agent> filterList = contactServiceImp.getFilterAgentByOpenid(openid);
		for (int i = 0; i < agentList.size(); i++) {
			String openid1 = agentList.get(i).getOpenid();
			boolean flag = true;
			for (int j = 0; j < agentList2.size(); j++) {
				String openid2 = agentList2.get(j).getOpenid();
				if(openid2.equals(openid1)){
					flag = false;
					break;
				}
			}
			if(flag){
				agentList2.add(agentList.get(i));
			}
		}
		for (int i = 0; i < filterList.size(); i++) {
			String openidf = filterList.get(i).getOpenid();
			for (int j = 0; j < agentList2.size(); j++) {
				String openidl = agentList2.get(j).getOpenid();
				if(openidf.equals(openidl)){
					agentList2.remove(j);
					break;
				}
			}
		}
		JSONArray json = JSONArray.fromObject(agentList2);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	@RequestMapping(value = "/getMyClient")
	public void getMyClient(HttpServletRequest request,
			HttpServletResponse response) throws IOException, DocumentException {
		String openid = request.getParameter("openid"); 
		String storeId = request.getParameter("storeId"); 
		if(openid==null||openid.equals("")||openid.equals("null")){
			 openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		System.out.println("************************clientList*****************************");
		List<Client> clientList = clientServiceImp.getClientListByAgentOpenid(openid,storeId);
		System.out.println("************************clientList2*****************************");
		List<Client> clientList2 = clientServiceImp.getClientListByChatAgent(openid,storeId);
		System.out.println("************************filterList*****************************");
		List<Client> filterList = contactServiceImp.getFilterClientByOpenid(openid);
		for (int i = 0; i < clientList.size(); i++) {
			String openid1 = clientList.get(i).getOpenid();
			boolean flag = true;
			for (int j = 0; j < clientList2.size(); j++) {
				String openid2 = clientList2.get(j).getOpenid();
				if(openid2.equals(openid1)){
					flag = false;
					break;
				}
			}
			if(flag){
				clientList2.add(clientList.get(i));
			}
		}
		for (int i = 0; i < filterList.size(); i++) {
			String openidf = filterList.get(i).getOpenid();
			for (int j = 0; j < clientList2.size(); j++) {
				String openidl = clientList2.get(j).getOpenid();
				if(openidf.equals(openidl)){
					clientList2.remove(j);
					break;
				}
			}
		}
		JSONArray json = JSONArray.fromObject(clientList2);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	@RequestMapping(value = "/getAgentListByStoreId")
	public void getAgentListByStoreId(HttpServletRequest request,
			HttpServletResponse response) throws IOException, DocumentException {
		String openid = request.getParameter("openid"); 
		if(openid==null||openid.equals("")||openid.equals("null")){
			 openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String storeId= request.getParameter("storeId");
		String keys= request.getParameter("keys");
		List<Agent> agentList = agentServiceImp.getAgentListByStoreId(storeId,keys);
		JSONArray json = JSONArray.fromObject(agentList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	@RequestMapping(value = "/addContactFilter")
	public void addContactFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, DocumentException {
		String openid = request.getParameter("openid"); 
		String toOpenid = request.getParameter("toOpenid"); 
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		int num = contactServiceImp.addContactFilter(openid, toOpenid);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(num);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
