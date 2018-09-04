package com.shenyingchengxun.house.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.service.StoreInfoService;
import com.shenyingchengxun.house.bean.ClientRecommend;
import com.shenyingchengxun.house.service.ClientRecommendService;

@Controller
@RequestMapping("/recommend")
public class IntelliRecommend {
	
	@Resource StoreInfoService storeInfoServiceImp;
	@Resource ClientRecommendService clientRecommendServiceImp;
	
	/**
	 * 根据用户openid获取推荐房源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClientRecommendListByOpenid")
	public void getClientRecommendListByOpenid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid = (String) request.getParameter("openid");
		String houseid = (String) request.getParameter("houseid");
		List<ClientRecommend> crList = clientRecommendServiceImp.getClientRecommendListByOpenid(houseid,openid);
		JSONArray json = JSONArray.fromObject(crList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 群发消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchSend")
	public void batchSend(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		String houseid =  request.getParameter("houseid");
		String openidStr= request.getParameter("openidArr");
		String[] openidArr = openidStr.split(",");
		String storeId =  request.getParameter("storeId");
		if(storeId==null){
			storeId =  (String) request.getSession().getAttribute("storeId");
		}
        StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
		if(openidArr.length>0){
			System.out.println("batchSend-->StoreInfo : " + si.toString());
			clientRecommendServiceImp.batchSend(si,openid,houseid,openidArr,true);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(true);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取用户智能推荐的房源列表
	 * 
	 * 推荐房源
	 * 规则：租房和买房分开推荐，分别根据用户最近浏览的10条记录（没有10条则取全部）为用户推荐相似的房源信息。
	 * 比如在相同的小区、相同的居室、价格上下浮动20%，满足其中一个条件就可以。
	 * 展示的先后顺序依据为：三个条件同时满足的在前，其次是满足两个条件、最后是满足一个条件。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRecommendListByClientOpenid")
	public void getRecommendListByClientOpenid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = (String) request.getParameter("page");
		String limit = (String) request.getParameter("limit");
		String openid = (String) request.getParameter("openid");
		String storeId = (String) request.getParameter("storeId");
		String type = (String) request.getParameter("type");
		List<Map<String,Object>> resultList = clientRecommendServiceImp.getRecommendListByClientOpenid(page,limit,storeId,openid,type);
		JSONArray json = JSONArray.fromObject(resultList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
