package com.shenyingchengxun.house.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.house.service.LookHouseService;

@Controller
@RequestMapping(value = "/lookHouse")
public class LookHouse {
	
	@Resource LookHouseService lookHouseServiceImp;
	/**
	 * 添加我的预约看房
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/addLookHouse")
	public void addLookHouse(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String storeId =  request.getParameter("storeId");
		String openid =  request.getParameter("openid");
		String name =  request.getParameter("name");
		String phone =  request.getParameter("phone");
		String time =  request.getParameter("time");
		String houseid =  request.getParameter("houseid");
		Map<String,String> map = lookHouseServiceImp.addLookHouse(storeId,openid,name,phone,time,houseid);
		JSONObject json = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取我的预约看房
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLookHouseList")
	public void getLookHouseList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String limit = request.getParameter("limit");
		String openid =  request.getParameter("openid");
		Map<String,Object> result= lookHouseServiceImp.getLookHouseList(page,limit,openid);
		JSONObject json = JSONObject.fromObject(result);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 删除我的预约看房
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delLookHouse")
	public void delLookHouse(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		String isAgent =  request.getParameter("isAgent");
		int num = lookHouseServiceImp.delLookHouse(id,isAgent);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(num);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 删除我的预约看房
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelLookHouse")
	public void cancelLookHouse(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		String openid =  request.getParameter("openid");
		String reason =  request.getParameter("reason");
		String isAgent =  request.getParameter("isAgent");
		String storeId =  request.getParameter("storeId");
		String toOpenid =  request.getParameter("toOpenid");
		int num = lookHouseServiceImp.cancelLookHouse(id,storeId,openid,toOpenid,isAgent,reason);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(num);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取我的预约看房详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLookHouseInfo")
	public void getLookHouseInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		String openid =  request.getParameter("openid");
		String storeId =  request.getParameter("storeId");
		Map<String,String> map = lookHouseServiceImp.getLookHouseInfo(id,openid,storeId);
		System.out.println(map);
		JSONObject json = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
