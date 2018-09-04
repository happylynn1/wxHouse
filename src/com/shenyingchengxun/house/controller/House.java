package com.shenyingchengxun.house.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.service.AgentService;
import com.shenyingchengxun.action.service.StoreInfoService;
import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.house.bean.ClientHouse;
import com.shenyingchengxun.house.bean.Comm;
import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.bean.Notes;
import com.shenyingchengxun.house.bean.Video;
import com.shenyingchengxun.house.bean.VrInfo;
import com.shenyingchengxun.house.dao.HouseTagDao;
import com.shenyingchengxun.house.service.HouseSourceService;
import com.shenyingchengxun.house.service.HouseTagService;
import com.shenyingchengxun.house.service.LogService;
import com.shenyingchengxun.house.service.NotesService;
import com.shenyingchengxun.house.service.PictureService;
import com.shenyingchengxun.house.service.VrInfoService;
import com.shenyingchengxun.house.util.WxPostUtil;
import com.shenyingchengxun.util.BaiduUtil;
import com.shenyingchengxun.util.GaodeUtil;

@Controller
@RequestMapping(value = "/house")
public class House {
	Logger logger = Logger.getLogger(House.class);
	
	@Autowired HouseTagDao houseTagDao;
	@Resource LogService logServiceImp;
	@Resource NotesService notesServiceImp;
	@Resource AgentService agentServiceImp;
	@Resource VrInfoService vrInfoServiceImp;
	@Resource PictureService pictureServiceImp;
	@Resource HouseTagService houseTagServiceImp;
	@Resource HouseSourceService houseSourceServiceImp;
	@Resource StoreInfoService storeInfoServiceImp;
	
	/**
	 * 根据经纬度获取小区名称(定位时使用)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCommByLnglatByWx")
	public void getCommByLnglatByWx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lnglat = request.getParameter("lnglat");
		Map<String,String> map = GaodeUtil.GetCommAndStreetByLnglat(lnglat);
		JSONObject json = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 根据经纬度获取小区名称(地图上选点时使用)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCommByLnglatByBaidu")
	public void getCommByLnglatByBaidu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lnglat = request.getParameter("lnglat");
		String commStreet = BaiduUtil.GetCommAndStreetByLnglat(50,lnglat);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(commStreet);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 根据经纬度获取小区名称(输入时使用)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCommByLnglatBySearch")
	public void getCommByLnglatBySearch(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String comm = request.getParameter("comm");
		String city = request.getParameter("city");
		Map<String,String> map = BaiduUtil.GetCommAndStreetByCityComm(city, comm);
		JSONObject json = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取房源标签(录入房源页面中的其他信息)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHouseTagList")
	public void getHouseTagList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		List<Map<String,Object>> tagList = houseTagServiceImp.getHouseTagList(type);
		JSONArray json = JSONArray.fromObject(tagList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取门店热点房源和新上房源数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getStoreHotHouseList")
	public void getStoreHotHouseList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page =  request.getParameter("page");
		String limit =  request.getParameter("limit");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String storeId = request.getParameter("storeId");
		List<Map<String, Object>> houseList = houseSourceServiceImp.getStoreHotHouseList(page,limit,openid,storeId);
		JSONArray json = JSONArray.fromObject(houseList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 搜索本人的VR列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchVrlist")
	public void searchVrlist(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String comm  = request.getParameter("comm");
		String area  = request.getParameter("area");
		String room  = request.getParameter("room");
		String towards  = request.getParameter("towards");
		Agent agent = agentServiceImp.getAgentByOpenid(openid);
		List<VrInfo> vrList = vrInfoServiceImp.getVrlist(agent.getPhone(),comm,area,room,towards);
		JSONArray json = JSONArray.fromObject(vrList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 搜索本人的VR
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyVr")
	public void getMyVr(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String vrno  = request.getParameter("vrno");
		VrInfo vr = vrInfoServiceImp.getMyVr(vrno);
		JSONObject json = JSONObject.fromObject(vr);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 录入房源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/submmit")
	public void submmit(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		String storeId = request.getParameter("storeId");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String comm = request.getParameter("comm");
		String tuiguangyu = request.getParameter("tuiguangyu");
		String address = request.getParameter("address");
		String lng = request.getParameter("lng");
		String lat = request.getParameter("lat");
		String buildingNo = request.getParameter("buildingNo");
		String unitno = request.getParameter("unitno");
		String doorno = request.getParameter("doorno");
		String houseno = request.getParameter("houseno");		
		String room = request.getParameter("room");
		String area = request.getParameter("area");
		String total = request.getParameter("total");
		String towards = request.getParameter("towards");
		String years = request.getParameter("years");
		String floor = request.getParameter("floor");
		String rentType = request.getParameter("rentType");
		String totalFloor = request.getParameter("totalFloor");
		String property_type = request.getParameter("property_type");
		String info = request.getParameter("info");
		String labels = request.getParameter("labels");
		String perPhone = request.getParameter("perPhone");
		String vrno = request.getParameter("vrno");
		String vr_path = request.getParameter("vr_path");
		String ispublic = request.getParameter("ispublic");
		String outdoor = request.getParameter("outdoor");
		String lift = request.getParameter("lift");
		String showStatus = request.getParameter("showStatus");
		String showTime = request.getParameter("showTime");
		String arrears = request.getParameter("arrears");
		String only = request.getParameter("only");
		String estateLicense = request.getParameter("estateLicense");
		String degree = request.getParameter("degree");
		String renovate = request.getParameter("renovate");
		String lastRenovate = request.getParameter("lastRenovate");
		String genre = request.getParameter("genre");
		String picPath = request.getParameter("picPath");
		HouseSource hs = new HouseSource(null, openid, comm,tuiguangyu, buildingNo, unitno, doorno, houseno, total, area, null, room, years, floor, totalFloor, 
				rentType, type, null, null, info, labels, property_type, outdoor, lift, showStatus, showTime, arrears, only, estateLicense, 
				degree, renovate, lastRenovate,towards, perPhone, ispublic, vrno, vr_path, null);
		hs.setGenre("sale".equals(type)?genre:null);
		hs.setPicPath(picPath);
		String role = houseSourceServiceImp.addHouseSource(storeId,hs,address,lng,lat);
		Map<String, String> map = new HashMap<String, String>();
		map.put("role", role);
		map.put("houseid", hs.getHouseid());
		JSONObject json = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 修改房源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHouseByHouseid")
	public void updateHouseByHouseid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		String storeId = request.getParameter("storeId");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String houseid = request.getParameter("houseid");
		String comm = request.getParameter("comm");
		String tuiguangyu = request.getParameter("tuiguangyu");
		String address = request.getParameter("address");
		String lng = request.getParameter("lng");
		String lat = request.getParameter("lat");
		String buildingNo = request.getParameter("buildingNo");
		String unitno = request.getParameter("unitno");
		String doorno = request.getParameter("doorno");
		String houseno = request.getParameter("houseno");
		String room = request.getParameter("room");
		String area = request.getParameter("area");
		String total = request.getParameter("total");
		String towards = request.getParameter("towards");
		String years = request.getParameter("years");
		String floor = request.getParameter("floor");
		String rentType = request.getParameter("rentType");
		String totalFloor = request.getParameter("totalFloor");
		String property_type = request.getParameter("property_type");
		String info = request.getParameter("info");
		String labels = request.getParameter("labels");
		String perPhone = request.getParameter("perPhone");
		String vrno = request.getParameter("vrno");
		String vr_path = request.getParameter("vr_path");
		String ispublic = request.getParameter("ispublic");
		String outdoor = request.getParameter("outdoor");
		String lift = request.getParameter("lift");
		String showStatus = request.getParameter("showStatus");
		String showTime = request.getParameter("showTime");
		String arrears = request.getParameter("arrears");
		String only = request.getParameter("only");
		String estateLicense = request.getParameter("estateLicense");
		String degree = request.getParameter("degree");
		String renovate = request.getParameter("renovate");
		String lastRenovate = request.getParameter("lastRenovate");
		String genre = request.getParameter("genre");
		String picPath = request.getParameter("picPath");
		HouseSource hs = new HouseSource(null, openid, comm,tuiguangyu, buildingNo, unitno, doorno, houseno, total, area, null, room, years, floor, totalFloor, 
				rentType, type, null, null, info, labels, property_type, outdoor, lift, showStatus, showTime, arrears, only, estateLicense, 
				degree, renovate, lastRenovate,towards, perPhone, ispublic, vrno, vr_path, null);
		hs.setGenre("sale".equals(type)?genre:null);
		hs.setPicPath(picPath);
		hs.setHouseid(houseid);
		houseSourceServiceImp.updateHouseSourceByHouseid(storeId,hs,address,lng,lat);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(hs.getHouseid());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 修改客户房源 并 将客户房源拷贝到经纪人房源表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClientHouseByHouseid")
	public void updateClientHouseByHouseid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		String storeId = request.getParameter("storeId");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String houseid = request.getParameter("houseid");
		String comm = request.getParameter("comm");
		String tuiguangyu = request.getParameter("tuiguangyu");
		String address = request.getParameter("address");
		String lng = request.getParameter("lng");
		String lat = request.getParameter("lat");
		String buildingNo = request.getParameter("buildingNo");
		String unitno = request.getParameter("unitno");
		String doorno = request.getParameter("doorno");
		String houseno = request.getParameter("houseno");
		String room = request.getParameter("room");
		String area = request.getParameter("area");
		String total = request.getParameter("total");
		String towards = request.getParameter("towards");
		String years = request.getParameter("years");
		String floor = request.getParameter("floor");
		String rentType = request.getParameter("rentType");
		String totalFloor = request.getParameter("totalFloor");
		String property_type = request.getParameter("property_type");
		String info = request.getParameter("info");
		String labels = request.getParameter("labels");
		String perPhone = request.getParameter("perPhone");
		String vrno = request.getParameter("vrno");
		String vr_path = request.getParameter("vr_path");
		String ispublic = request.getParameter("ispublic");
		String outdoor = request.getParameter("outdoor");
		String lift = request.getParameter("lift");
		String showStatus = request.getParameter("showStatus");
		String showTime = request.getParameter("showTime");
		String arrears = request.getParameter("arrears");
		String only = request.getParameter("only");
		String estateLicense = request.getParameter("estateLicense");
		String degree = request.getParameter("degree");
		String renovate = request.getParameter("renovate");
		String lastRenovate = request.getParameter("lastRenovate");
		String genre = request.getParameter("genre");
		String picPath = request.getParameter("picPath");
		HouseSource hs = new HouseSource(houseid, openid, comm,tuiguangyu, buildingNo, unitno, doorno, houseno, total, area, null, room, years, floor, totalFloor, 
				rentType, type, null, null, info, labels, property_type, outdoor, lift, showStatus, showTime, arrears, only, estateLicense, 
				degree, renovate, lastRenovate,towards, perPhone, ispublic, vrno, vr_path, null);
		hs.setGenre("sale".equals(type)?genre:null);
		hs.setPicPath(picPath);
		houseSourceServiceImp.updateClientHouseSourceByHouseid(storeId,hs,address,lng,lat);
		
		houseSourceServiceImp.copyClientHouseToAgentHouse(houseid);
		
		houseSourceServiceImp.updateClientHouseIsdeal(houseid);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(hs.getHouseid());
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 业主录入房源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/addClientHouse")
	public void addClientHouse(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String remark = request.getParameter("remark");
		String storeId = request.getParameter("storeId");
		ClientHouse ch = new ClientHouse(null, openid, type, name, phone, remark,storeId);
		houseSourceServiceImp.addClientHouse(ch);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(ch.getHouseid());
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取小区列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCommList")
	public void getCommList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page =  request.getParameter("page");
		String limit =  request.getParameter("limit");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String storeId = request.getParameter("storeId");
		String comm = request.getParameter("comm");
		List<Comm> resultList = houseSourceServiceImp.getCommList(page,limit,storeId,openid,comm);
		JSONArray json = JSONArray.fromObject(resultList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 根据小区获取其中的房源列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/commHouseList")
	public String commHouseList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String comm = request.getParameter("comm");
		String type = request.getParameter("type");
		request.setAttribute("comm", comm);
		request.setAttribute("type", type);
		return "house/commHouseList";
	}
	/**
	 * 根据房源id获取房源基本信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHouseSourceByHouseid")
	public void getHouseSourceByHouseid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String houseid = request.getParameter("houseid");
		String storeId = request.getParameter("storeId");
		Map<String,Object> resultMap = new	HashMap<String,Object>();
		HouseSource hs = houseSourceServiceImp.getHouseSourceByHouseid(houseid);
		if(hs!=null){
			resultMap.put("hs", hs);
			resultMap.put("flag", true);
			List<com.shenyingchengxun.house.bean.Picture> pList = pictureServiceImp.getPictureByHouseid(houseid);
			resultMap.put("pList", pList);
			String wechatname = "";
			if(storeId!=null) wechatname = houseSourceServiceImp.getWechatNameByStoreid(storeId);
			resultMap.put("wechatname", wechatname);
		}else{
			resultMap.put("flag", false);
		}
		JSONObject json = JSONObject.fromObject(resultMap);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 根据房源id获取房源详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHouseSourceInfoByHouseid")
	public void getHouseSourceInfoByHouseid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String houseid = request.getParameter("houseid");
		Map<String,Object> resultMap = new	HashMap<String,Object>();
		HouseSource hs = houseSourceServiceImp.getHouseSourceByHouseid(houseid);
		resultMap.put("hs", hs);
		if(hs!=null){
			List<Video> vlist = pictureServiceImp.getVideoByHouseid(houseid);
			resultMap.put("vlist", vlist);
			
			List<com.shenyingchengxun.house.bean.Picture> pList = pictureServiceImp.getPictureByHouseid(houseid);
			resultMap.put("pList", pList);
			List<String> tagList = houseTagDao.getLikeTagList();
			List<String> likeTags = houseTagServiceImp.getLikeTags(tagList,hs);
			resultMap.put("likeTags", likeTags);
			List<Notes> notes = notesServiceImp.getNotesListByHouseid(houseid);
			resultMap.put("notes", notes);
			Agent agent = agentServiceImp.getAgentByOpenid(hs.getOpenid());
			resultMap.put("agent", agent);
			boolean collect = houseSourceServiceImp.isMyCollect(openid,houseid);
			resultMap.put("collect", collect);
			logServiceImp.addVisitLog(openid,hs);
		}
		JSONObject json = JSONObject.fromObject(resultMap);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 根据房源id获取房源详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClientHouseByHouseid")
	public void getClientHouseByHouseid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String houseid = request.getParameter("houseid");
		Map<String,Object> resultMap = new	HashMap<String,Object>();
		HouseSource hs = houseSourceServiceImp.getClientHouseInfoByHouseid(houseid);
		resultMap.put("hs", hs);
		if(hs!=null){
			List<Video> vlist = pictureServiceImp.getVideoByHouseid(houseid);
			resultMap.put("vlist", vlist);
			
			List<com.shenyingchengxun.house.bean.Picture> pList = pictureServiceImp.getPictureByHouseid(houseid);
			resultMap.put("pList", pList);
			List<String> tagList = houseTagDao.getLikeTagList();
			List<String> likeTags = houseTagServiceImp.getLikeTags(tagList,hs);
			resultMap.put("likeTags", likeTags);
			List<Notes> notes = notesServiceImp.getNotesListByHouseid(houseid);
			resultMap.put("notes", notes);
			Agent agent = agentServiceImp.getAgentByOpenid(hs.getOpenid());
			resultMap.put("agent", agent);
			boolean collect = houseSourceServiceImp.isMyCollect(openid,houseid);
			resultMap.put("collect", collect);
			logServiceImp.addVisitLog(openid,hs);
		}
		JSONObject json = JSONObject.fromObject(resultMap);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 根据小区获取小区下的房源列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHouseListByCommType")
	public void getHouseListByCommType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String limit = request.getParameter("limit");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String type =  request.getParameter("type");
		String keys =  request.getParameter("keys");
		String storeId = request.getParameter("storeId");
		List<Map<String,Object>> resultList = houseSourceServiceImp.getHouseListByCommType(page,limit,storeId,openid,type,keys);
		JSONArray json = JSONArray.fromObject(resultList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 出租出售房源列表页面，获取房源数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHouseListByType")
	public void getHouseListByType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String limit = request.getParameter("limit");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String type =  request.getParameter("type");
		String keys =  request.getParameter("keys");
		String room =  request.getParameter("room");
		String price =  request.getParameter("price");
		String towards =  request.getParameter("towards");
		String storeId = request.getParameter("storeId");
		String genre = request.getParameter("genre");
		List<Map<String,Object>> resultList = houseSourceServiceImp.getHouseListByType(page,limit,storeId,openid,type,keys,room,price,towards,genre);
		JSONArray json = JSONArray.fromObject(resultList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 添加经纪人笔记
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNotes")
	public void addNotes(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String houseid = request.getParameter("houseid");
		String notes = request.getParameter("notes");
		String notesId = notesServiceImp.addNotes(houseid, notes);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(notesId);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取我的房源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyHouse")
	public void getMyHouse(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String limit = request.getParameter("limit");
		String type = request.getParameter("type");
		String openid = request.getParameter("openid");
		String storeId = request.getParameter("storeId");
		List<Map<String,Object>> resultList = houseSourceServiceImp.getMyHouseByType(page,limit,storeId,openid,type);
		JSONArray json = JSONArray.fromObject(resultList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/getDelPermission")
	public void getDelPermission(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String openid = request.getParameter("openid");
		String permission = agentServiceImp.getDelPermission(openid);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(permission);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 删除我的房源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delMyHouseById")
	public void delMyHouseById(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String houseid = request.getParameter("houseid");
		int num = houseSourceServiceImp.delMyHouseById(houseid);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(num);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 删除我的笔记
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delNotes")
	public void delNotes(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		notesServiceImp.delNotes(id);
	}
	/**
	 * 修改房源笔记信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/upOtherNotes")
	public void upOtherNotes(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String houseid = request.getParameter("houseid");
		String content = request.getParameter("content");
		String type = request.getParameter("type");
		houseSourceServiceImp.upOtherNotes(houseid,type,content);
	}
	
	/**
	 * 获取随机五个经纪人为客户房源委托
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getRandAgent")
	public void getRandAgent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String storeid = request.getParameter("storeid");
		List<Agent> agents = agentServiceImp.getRandAgent(storeid);
		JSONArray json = JSONArray.fromObject(agents);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 客户房源添加经纪人信息 并 发送模板消息给经纪人
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/updateClienthouseByAgentopenid")
	public void updateClienthouseByAgentopenid(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String houseid = request.getParameter("houseid");
		String agentopenid = request.getParameter("agentopenid");
		String storeid = request.getParameter("storeid");
		String clientopenid = request.getParameter("clientopenid");
		int flag = houseSourceServiceImp.updateClienthouseByAgentopenid(houseid,agentopenid);
		
		StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeid);
		
		HouseSource house = houseSourceServiceImp.getClientHouseByHouseid(houseid);
		String comm = house.getComm() != null ? house.getComm() : "";
		String address = house.getAddress() != null ? house.getAddress() : "";
		if(address.equals("")){
			String city = house.getCity() != null ? house.getCity() : "";
			String area = house.getArea() != null ? house.getArea() : "";
			String street = house.getStreet() != null ? house.getStreet() : "";
			if(!city.equals("")) address += city;
			if(!area.equals("")) address += area;
			if(!street.equals("")) address += street;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String genre = house.getTrading_type() != null ? house.getTrading_type() : "";
		if(genre.equals("rent")){
			genre = "出租";
		}
		if(genre.equals("sale")){
			genre = "出售";
		}
		String url=Config.Base+"page/entrustHouse1.do?houseid="+houseid+"&openid="+agentopenid+"&clientopenid="+clientopenid+"&storeId="+storeid;
		// 添加发送模板消息
		JSONObject json = WxPostUtil.createEntrustMsgTemplate(storeid, comm, address, time, genre, agentopenid, url);
		String code = WxPostUtil.sendTemplate(si.getAccessToken(),json);
		Map<String, String> map = new HashMap<String, String>();
		map.put("flag", String.valueOf(flag));
		map.put("code", code);
		JSONObject rjson = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(rjson);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
