package com.shenyingchengxun.house.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.service.StoreInfoService;
import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.house.service.HouseSourceService;
import com.shenyingchengxun.house.util.WxPostUtil;
import com.shenyingchengxun.util.Md5;

@Controller
@RequestMapping(value = "/require")
public class Require {
	
	Logger logger = Logger.getLogger(Require.class);
	
	@Resource HouseSourceService houseSourceServiceImp;
	@Resource StoreInfoService storeInfoServiceImp;
	/**
	 * 添加到需求列表s
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRequire")
	public void addRequire(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String storeId = request.getParameter("storeId");
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String remark = request.getParameter("remark");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String street = request.getParameter("street");
		String usedto = request.getParameter("usedto");
		String decorate = request.getParameter("decorate");
		String rent_type = request.getParameter("rent_type");
		String price = request.getParameter("price");
		String room = request.getParameter("room");
		String area = request.getParameter("area");
		com.shenyingchengxun.house.bean.Require require = new com.shenyingchengxun.house.bean.Require(openid, null, storeId, type, name, phone, remark, city, district, street, usedto, decorate, rent_type, price, room, area, null, null);
		String requireid = null;
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
	    String houseidStr = "";
		Random random = new Random();   
	    for (int i = 0; i < 16; i++) {   
	        int number = random.nextInt(base.length());   
	        houseidStr+=(base.charAt(number));
	    }
	    requireid = Md5.string2MD5(houseidStr);
	    require.setId(requireid);
		int flag = houseSourceServiceImp.addRequire(require);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("flag", flag);
		map.put("requireid", requireid);
		JSONObject json = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	@RequestMapping(value="/sendRequireMsg")
	public void sendRequireMsg(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String requireid = request.getParameter("requireid");
		String storeid = request.getParameter("storeid");
		String agent_openid = request.getParameter("agentopenid");
		int flag = houseSourceServiceImp.updateRequireAgentByRequireid(requireid,agent_openid);
		com.shenyingchengxun.house.bean.Require require = houseSourceServiceImp.getRequireByRequireid(requireid);
		String title = "求购".equals(require.getType()) ? "您收到一条求购需求。" : "您收到一条求租需求。";
		String name = require.getName();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		logger.debug(" time : " + time);
		String msg = "求购".equals(require.getType()) ? "急需买房" : "急需租房";
		String address = require.getCity()+require.getDistrict()+require.getStreet();
		String url = "";
		if("求购".equals(require.getType())){
			url = Config.Base+"page/dealSaleRequire.do?requireid="+requireid+"&openid="+agent_openid+"&storeId="+storeid;
		} else if("求租".equals(require.getType())){
			url = Config.Base+"page/dealRentRequire.do?requireid="+requireid+"&openid="+agent_openid+"&storeId="+storeid;
		}
		
		StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeid);
		JSONObject json = WxPostUtil.createRequireMsgTemplate(title, storeid, name, time, msg, address, agent_openid, url);
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
	
	@RequestMapping(value="/getRequireByRequireid")
	public void getRequireByRequireid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String requireid = request.getParameter("requireid");
		com.shenyingchengxun.house.bean.Require require = houseSourceServiceImp.getRequireByRequireid(requireid);
		JSONObject json = JSONObject.fromObject(require);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
