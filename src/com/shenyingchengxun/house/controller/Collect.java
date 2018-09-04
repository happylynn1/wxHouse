package com.shenyingchengxun.house.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.house.service.HouseSourceService;

@Controller
@RequestMapping(value = "/collect")
public class Collect {
	
	@Resource HouseSourceService houseSourceServiceImp;
	/**
	 * 获取我的收藏列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyCollect")
	public void getMyCollect(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page =  request.getParameter("page");
		String limit =  request.getParameter("limit");
		String openid =  request.getParameter("openid");
		String type =  request.getParameter("type");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		List<Map<String,Object>>  hsList = houseSourceServiceImp.getMyCollect(page,limit,openid,type);
		JSONArray json = JSONArray.fromObject(hsList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 添加到我的收藏
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/addMyCollect")
	public void addMyCollect(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String houseid = request.getParameter("houseid");
		String type = request.getParameter("type");
		houseSourceServiceImp.addMyCollect(openid, houseid,type);
	}
	/**
	 * 删除我的收藏
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delMyCollect")
	public void delMyCollect(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String houseid = request.getParameter("houseid");
		houseSourceServiceImp.delMyCollect(openid, houseid);
	}
}
