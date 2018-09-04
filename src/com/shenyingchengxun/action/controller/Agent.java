package com.shenyingchengxun.action.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.service.AgentService;
import com.shenyingchengxun.action.service.StoreInfoService;
import com.shenyingchengxun.util.HttpUtil;

@Controller
@RequestMapping(value = "/agent")
public class Agent {
	@Resource AgentService agentServiceImp;
	@Resource StoreInfoService storeInfoServiceImp;
	/**
	 * 获取经纪人个人信息，及生成带参数的二维码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyWxCard")
	public void getMyWxCard(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Max-Age", "1800");
		String ticketUrl ="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
		String qrcodeUrl ="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String storeId =  request.getParameter("storeId");
		StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
		Map<String,Object> map =new HashMap<String,Object>();
		com.shenyingchengxun.action.bean.Agent agent = agentServiceImp.getAgentByOpenid(openid);
		map.put("agent",agent);
		
		Map<String,String> ticketMap1 = new HashMap<String,String>();
		Map<String,Object> ticketMap2 = new HashMap<String,Object>();
		Map<String,Object> ticketMap3 = new HashMap<String,Object>();
		ticketMap1.put("scene_str", openid);
		ticketMap2.put("scene", ticketMap1);
		ticketMap3.put("action_name", "QR_LIMIT_STR_SCENE");
		ticketMap3.put("action_info", ticketMap2);
		JSONObject ticketJson = HttpUtil.httpRequest(ticketUrl.replace("TOKEN", si.getAccessToken()), "POST",JSONObject.fromObject(ticketMap3));
		String ticket = ticketJson.getString("ticket");
		String qrcode = qrcodeUrl.replace("TICKET", ticket);
		map.put("qrcode",qrcode);
		JSONObject json_result = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json_result);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 获取带参数的二维码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getQrCodeUrl")
	public void getQrCodeUrl(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Max-Age", "1800");
		String ticketUrl ="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
		String qrcodeUrl ="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		String storeId =  request.getParameter("storeId");
		StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
		Map<String,Object> map =new HashMap<String,Object>();
		Map<String,String> ticketMap1 = new HashMap<String,String>();
		Map<String,Object> ticketMap2 = new HashMap<String,Object>();
		Map<String,Object> ticketMap3 = new HashMap<String,Object>();
		ticketMap1.put("scene_str", "storeqrcode");
		ticketMap2.put("scene", ticketMap1);
		ticketMap3.put("action_name", "QR_LIMIT_STR_SCENE");
		ticketMap3.put("action_info", ticketMap2);
		JSONObject ticketJson = HttpUtil.httpRequest(ticketUrl.replace("TOKEN", si.getAccessToken()), "POST",JSONObject.fromObject(ticketMap3));
		String ticket = ticketJson.getString("ticket");
		String qrcode = qrcodeUrl.replace("TICKET", ticket);
		map.put("qrcode",qrcode);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(qrcode);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
