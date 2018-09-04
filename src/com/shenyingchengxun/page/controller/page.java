package com.shenyingchengxun.page.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.Client;
import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.service.AgentService;
import com.shenyingchengxun.action.service.ClientService;
import com.shenyingchengxun.action.service.CoreService;
import com.shenyingchengxun.action.service.StoreInfoService;
import com.shenyingchengxun.chat.service.ChatcService;
import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.service.HouseSourceService;

@Controller
@RequestMapping(value = "/page")
public class page {
	
	@Resource CoreService coreServiceImp;
	@Resource AgentService agentServiceImp;
	@Resource ChatcService chatcServiceImp;
	@Resource ClientService clientServiceImp;
	@Resource StoreInfoService storeInfoServiceImp;
	@Resource HouseSourceService houseSourceServiceImp;
	/**
	 * 房源录入页面入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toSelect")
	public String toSelect(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/select";
	}
	/**
	 * 判断openid类别，进入对应的页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/sellr")
	public String sellr(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String type = request.getParameter("type");
		request.setAttribute("type",type);
		Agent ag = agentServiceImp.getAgentByOpenid(openid);
		String url = "error";
		if(ag!=null){
			url =  "house/agentHousePub1";
		}else{
			Client cl = clientServiceImp.getClientByOpenid(openid);
			if(cl!=null){
				url = "house/agentHousePub1";
			}
		}
		return url;
	}
	/**
	 * 经纪人录入房源第二个页面(上传图片页面)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/agentHousePub2")
	public String agentHousePub2(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		request.setAttribute("openid", openid);
		return "house/agentHousePub2";
	}
	/**
	 * 设置封面图片
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/agentHousePub3")
	public String agentHousePub3(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String type = request.getParameter("type");
		String picPath = request.getParameter("picPath");
		request.setAttribute("type", type);
		request.setAttribute("openid", openid);
		request.setAttribute("picPath", picPath);
		return "house/agentHousePub3";
	}
	/**
	 * 经纪人录入房源第三个页面(房源详情录入页面)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/agentHousePub4")
	public String agentHousePub4(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String houseid = request.getParameter("houseid");
		String storeId = request.getParameter("storeId");
		if(storeId != null){
			request.setAttribute("storeId", storeId);
		}
		request.setAttribute("houseid", houseid);
		request.setAttribute("openid", openid);
		return "house/agentHousePub4";
	}
	/**
	 * 客户录入房源选择委托经纪人页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/clientHousePub4")
	public String clientHousePub4(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String houseid = request.getParameter("houseid");
		request.setAttribute("houseid", houseid);
		request.setAttribute("openid", openid);
		return "house/clientHousePub4";
	}
	/**
	 * 业主录入房源第二个页面(上传图片录入页面)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/clientHousePub2")
	public String clientHousePub2(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String houseid = request.getParameter("houseid");
		request.setAttribute("houseid", houseid);
		request.setAttribute("openid", openid);
		return "house/clientHousePub2";
	}
	/**
	 * 查看房源详情页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/houseSourceInfo")
	public String houseSourceInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String storeId = request.getParameter("storeId");
		if(storeId==null) storeId = (String) request.getSession().getAttribute("storeId");
		String houseid = request.getParameter("houseid");
		return "redirect:/action/redirect.do?storeId="+storeId+"&path=page/houseSourceInfo2.do?houseid="+houseid;
	}
	@RequestMapping(value = "/houseSourceInfo2")
	public String houseSourceInfo2(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		String houseid = request.getParameter("houseid");
		if(houseid==null||"".equals(houseid))
			return "forward:errorHouse.do";
		HouseSource hs = houseSourceServiceImp.getHouseSourceByHouseid(houseid);
		if(hs==null)
			return "forward:errorHouse.do";
		request.setAttribute("houseid", houseid);
		return "house/houseSourceInfo";
	}
	/**
	 * 小区列表菜单入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toComm")
	public String toComm(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/commList";
	}
	/**
	 * 出租房源列表菜单入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toRent")
	public String toRent(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/rentHouse";
	}
	/**
	 * 微名片入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toWxCard")
	public String toWxCard(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/wxCard";
	}
	/**
	 * 出售房源列表菜单入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toSale")
	public String toSale(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String genre= request.getParameter("genre");
		request.setAttribute("openid", openid);
		request.setAttribute("genre", genre);
		return "house/saleHouse";
	}
	/**
	 * 我的收藏菜单入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toMyCollect")
	public String toMyCollect(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/myCollect";
	}
	/**
	 * 聊天记录菜单入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toMyAgent")
	public String toMyAgent(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		Agent a = agentServiceImp.getAgentByOpenid(openid);
		if(a==null){
			return "house/myAgent";
		}else{
			return "house/myClient";
		}
	}
	/**
	 * 推荐房源菜单入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toCommend")
	public String toCommend(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/commend";
	}
	/**
	 * 我的房源
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toMyHouse")
	public String toMyHouse(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		Agent a = agentServiceImp.getAgentByOpenid(openid);
		if(a==null){
			return "house/myCollect";
		}else{
			return "house/myHouse";
		}
	}
	/**
	 * 店铺热点菜单入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toStoreHot")
	public String toStoreHot(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/storeHot";
	}
	/**
	 * 店员信息入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toAgentList")
	public String toAgentList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/agentList";
	}
	/**
	 * 房源详情错误页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/errorHouse")
	public String errorHouse(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		return "house/errorHouse";
	}
	/**
	 * 求购登记页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toBuy")
	public String toBuy(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/toBuy1";
	}
	/**
	 * 求租登记页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toRenting")
	public String toRenting(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
//		return "house/toRenting";
		return "house/toRent1";
	}
	/**
	 * 租售房源
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toRentSale")
	public String toRentSale(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		request.setAttribute("focus", request.getParameter("focus"));
		return "house/toRentSale";
	}
	/**
	 * 精品二手房
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toSecondH")
	public String toSecondH(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		request.setAttribute("openid", openid);
		return "house/toSecondH";
	}
	
	/**
	 * 调整提示信息
	 * @return
	 */
	@RequestMapping(value = "/toMessage")
	public String toMessage(){
		return "message";
	}
	
	/**
	 * 聊天室入口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/toChat")
	public String toChat(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request),toOpenid = request.getParameter("toOpenid");
		request.setAttribute("openid", openid);
		chatcServiceImp.addChatList(openid, toOpenid);
		request.setAttribute("toOpenid", toOpenid);
		request.setAttribute("content", request.getParameter("content"));
		return "chat/chat";
	}
	@RequestMapping(value = "/upMyHouseById")
	public String upMyHouseById(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String houseid = request.getParameter("houseid");
		request.setAttribute("openid", openid);
		request.setAttribute("houseid", houseid);
		return "house/upMyHouse";
	}
	@RequestMapping(value = "/changePic1")
	public String changePic1(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String houseid = request.getParameter("houseid");
		request.setAttribute("openid", openid);
		request.setAttribute("houseid", houseid);
		return "house/changePic1";
	}
	@RequestMapping(value = "/changePic2")
	public String changePic2(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String openid = setVars(request);
		String houseid = request.getParameter("houseid");
		request.setAttribute("openid", openid);
		request.setAttribute("houseid", houseid);
		return "house/changePic2";
	}
	@RequestMapping(value = "/entrustHouse1")
	public String entrustHouse1(HttpServletRequest request,
			HttpServletResponse response){
		String houseid = request.getParameter("houseid");
		String openid = request.getParameter("openid");
		String clientopenid = request.getParameter("clientopenid");
		String storeId = request.getParameter("storeId");
		request.setAttribute("openid", openid);
		request.setAttribute("houseid", houseid);
		request.setAttribute("clientopenid", clientopenid);
		request.setAttribute("storeId", storeId );
		return "house/entrustHouse1";
	}
	@RequestMapping(value = "/entrustHouse2")
	public String entrustHouse2(HttpServletRequest request,
			HttpServletResponse response){
		String houseid = request.getParameter("houseid");
		String openid = request.getParameter("openid");
		String clientopenid = request.getParameter("clientopenid");
		String storeId = request.getParameter("storeId");
		request.setAttribute("openid", openid);
		request.setAttribute("houseid", houseid);
		request.setAttribute("clientopenid", clientopenid);
		request.setAttribute("storeId", storeId );
		return "house/entrustHouse2";
	}
	@RequestMapping(value = "/entrustHouse3")
	public String entrustHouse3(HttpServletRequest request,
			HttpServletResponse response){
		String houseid = request.getParameter("houseid");
		String openid = request.getParameter("openid");
		String clientopenid = request.getParameter("clientopenid");
		String storeId = request.getParameter("storeId");
		request.setAttribute("openid", openid);
		request.setAttribute("houseid", houseid);
		request.setAttribute("clientopenid", clientopenid);
		request.setAttribute("storeId", storeId );
		return "house/entrustHouse3";
	}
	@RequestMapping(value="entrustSuccess")
	public String entrustSuccess(HttpServletRequest request,
			HttpServletResponse response){
		return "house/entrustSuccess";
	}
	@RequestMapping(value = "/requireEntrust")
	public String requireEntrust(HttpServletRequest request,
			HttpServletResponse response){
		String storeId = request.getParameter("storeId");
		String openid = request.getParameter("openid");
		String requireid = request.getParameter("requireid");
		request.setAttribute("storeId", storeId);
		request.setAttribute("openid", openid);
		request.setAttribute("requireid", requireid);
		return "house/requireEntrust";
	}
	@RequestMapping(value="requireSuccess")
	public String requireSuccess(HttpServletRequest request,
			HttpServletResponse response){
		return "house/requireSuccess";
	}
	@RequestMapping(value="dealRentRequire")
	public String dealRentRequire(HttpServletRequest request,
			HttpServletResponse response){
		String storeId = request.getParameter("storeId");
		String openid = request.getParameter("openid");
		String requireid = request.getParameter("requireid");
		request.setAttribute("storeId", storeId);
		request.setAttribute("openid", openid);
		request.setAttribute("requireid", requireid);
		return "house/dealRentRequire";
	}
	@RequestMapping(value="dealSaleRequire")
	public String dealSaleRequire(HttpServletRequest request,
			HttpServletResponse response){
		String storeId = request.getParameter("storeId");
		String openid = request.getParameter("openid");
		String requireid = request.getParameter("requireid");
		request.setAttribute("storeId", storeId);
		request.setAttribute("openid", openid);
		request.setAttribute("requireid", requireid);
		return "house/dealSaleRequire";
	}
	
	/**
	 * 缓存storeId、openid,并返回openid
	 * @param request
	 * @return String openid
	 */
	public String setVars(HttpServletRequest request){
		String storeId = (String) request.getParameter("storeId");
		String openid = null;
		if(storeId==null){
			storeId =  (String) request.getSession().getAttribute("storeId");
		}
		if(storeId!=null){
			request.getSession().setAttribute("storeId",storeId);
			StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
			request.getSession().setAttribute("store",si);
			openid = coreServiceImp.getOpenid(si,request);
		}
		return  openid;
	}
	
}