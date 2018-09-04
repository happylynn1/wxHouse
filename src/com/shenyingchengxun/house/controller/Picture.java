package com.shenyingchengxun.house.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.service.StoreInfoService;
import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.house.bean.Video;
import com.shenyingchengxun.house.service.PictureService;
import com.shenyingchengxun.house.util.ImageBase64Util;
import com.shenyingchengxun.house.util.MediaUtil;
import com.shenyingchengxun.house.util.VideoThumbTaker;
import com.shenyingchengxun.util.ImageKit;

@Controller
@RequestMapping(value = "/picture")
public class Picture {
	Logger logger = Logger.getLogger(Picture.class);
	@Resource
	StoreInfoService storeInfoServiceImp;
	@Resource
	PictureService pictureServiceImp;
	/**
	 * 获取用户上传的图片
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPictureListByOpenid")
	public void getPictureListByOpenid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		List<com.shenyingchengxun.house.bean.Picture> list = pictureServiceImp.getPictureByOpenid(openid);
		JSONArray res_json = JSONArray.fromObject(list);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(res_json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	@RequestMapping(value = "/getPictureListByOpenidAndHouseId")
	public void getPictureListByOpenidAndHouseId(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String houseid =  request.getParameter("houseid");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		List<com.shenyingchengxun.house.bean.Picture> list = pictureServiceImp.getPictureByHouseid(houseid);
		JSONArray res_json = JSONArray.fromObject(list);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(res_json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 删除上传的图片
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delPicture")
	public void delPicture(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String filename = request.getParameter("filename");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		pictureServiceImp.delPicture(filename,openid);
	}
	@RequestMapping(value = "/delPictureByHouseid")
	public void delPictureByHouseid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String houseid = request.getParameter("houseid");
		String filename = request.getParameter("filename");
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		pictureServiceImp.delPicture(filename,openid,houseid);
	}
	/**
	 * 将上传的图片保存到服务器
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePicture")
	public void savePicture(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
        String mediaId = request.getParameter("mediaId");
        String storeId =  (String) request.getSession().getAttribute("storeId");
        StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
        String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
        String path = request.getSession().getServletContext().getRealPath("");
        String filename = MediaUtil.saveImageToDisk(path,si,mediaId);
        
        // 所有提交图片都根据原图分辨率缩小
        String imgBasePath = request.getSession().getServletContext().getRealPath("/housePicture");
        logger.info("---- imgBasePath:" + imgBasePath + "----");
        String img_path = imgBasePath + File.separator + filename;
        //验证
        Map<String,Object> checkMap = ImageKit.checkAll(img_path);
        boolean success = (boolean) checkMap.get("success");
        String msg = (String) checkMap.get("code");
        if(success){
        	logger.info("---- img_path:" + img_path + "----");
        	logger.info("---- Thumbnails ----");
        	Thumbnails.of(img_path).size(800, 1200).toFile(img_path);
        	logger.info("---- Thumbnails end ----");
        	// 图片加水印
        	// 水印图片base64编码
        	String wm_base64 = storeInfoServiceImp.getWaterMarkByStoreid(storeId);
        	logger.info("-- wm_base64 : " + wm_base64);
        	if(wm_base64 != null && !wm_base64.equals("")){
        		String wm_path = imgBasePath + File.separator + "_waterMark_img_" + storeId + ".png";
        		logger.info("---- wm_path:" + wm_path + "----");
        		File imgw = new File(wm_path);
        		logger.info("---- img_exists:" + imgw.exists() + "----");
        		if(imgw.exists()) imgw.delete();
        		ImageBase64Util.generateImage(wm_base64, wm_path);
        		logger.info("---- img_exists:" + imgw.exists() + "----");
        		Thumbnails.of(wm_path).size(120, 60).keepAspectRatio(false).toFile(wm_path);
        		logger.info("---- watermark ----");
        		Thumbnails.of(img_path).watermark(Positions.TOP_LEFT, ImageIO.read(new File(wm_path)), 0.8f).scale(1.0f).toFile(img_path);
        		logger.info("---- watermark end----");
        	}
        	com.shenyingchengxun.house.bean.Picture p = new com.shenyingchengxun.house.bean.Picture(filename,openid);
        	pictureServiceImp.addPictureByOpenid(p);
        	resultMap.put("picture", p);
        }
        resultMap.put("success", success);
        resultMap.put("msg", msg);
        JSONObject res_json = JSONObject.fromObject(resultMap);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(res_json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	@RequestMapping(value = "/savePictureByHouseid")
	public void savePictureByHouseid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String houseid = request.getParameter("houseid");
		String mediaId = request.getParameter("mediaId");
		String storeId =  (String) request.getSession().getAttribute("storeId");
		StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String path = request.getSession().getServletContext().getRealPath("");
		String filename = MediaUtil.saveImageToDisk(path,si,mediaId);
		
		// 所有提交图片都根据原图分辨率缩小
		String imgBasePath = request.getSession().getServletContext().getRealPath("/housePicture");
		logger.info("---- imgBasePath:" + imgBasePath + "----");
		String img_path = imgBasePath + File.separator + filename;
		//验证
        Map<String,Object> checkMap = ImageKit.checkAll(img_path);
        boolean success = (boolean) checkMap.get("success");
        String msg = (String) checkMap.get("code");
        if(success){
        	logger.info("---- img_path:" + img_path + "----");
        	logger.info("---- Thumbnails ----");
        	Thumbnails.of(img_path).size(800, 1200).toFile(img_path);
        	logger.info("---- Thumbnails end ----");
        	// 图片加水印
        	// 水印图片base64编码
        	String wm_base64 = storeInfoServiceImp.getWaterMarkByStoreid(storeId);
        	logger.info("-- wm_base64 : " + wm_base64);
        	if(wm_base64 != null && !wm_base64.equals("")){
        		String wm_path = imgBasePath + File.separator + "_waterMark_img_" + storeId + ".png";
        		logger.info("---- wm_path:" + wm_path + "----");
        		File imgw = new File(wm_path);
        		logger.info("---- img_exists:" + imgw.exists() + "----");
        		if(imgw.exists()) imgw.delete();
        		ImageBase64Util.generateImage(wm_base64, wm_path);
        		logger.info("---- img_exists:" + imgw.exists() + "----");
        		Thumbnails.of(wm_path).size(120, 60).keepAspectRatio(false).toFile(wm_path);
        		logger.info("---- watermark ----");
        		Thumbnails.of(img_path).watermark(Positions.TOP_LEFT, ImageIO.read(new File(wm_path)), 0.8f).scale(1.0f).toFile(img_path);
        		logger.info("---- watermark end----");
        	}
        	com.shenyingchengxun.house.bean.Picture p = new com.shenyingchengxun.house.bean.Picture(filename,openid);
        	p.setHouseid(houseid);
        	pictureServiceImp.addPictureByOpenid(p);
        	resultMap.put("picture", p);
        }
        resultMap.put("success", success);
        resultMap.put("msg", msg);
        JSONObject res_json = JSONObject.fromObject(resultMap);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(res_json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 将图片绑定到指定的房源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/upClientPicture")
	public void upClientPicture(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String openid =  request.getParameter("openid");
		if(openid==null||openid.equals("")||openid.equals("null")){
			openid = (String) request.getSession().getAttribute("openid");
		}else{
			request.getSession().setAttribute("openid",openid);
		}
		String houseid = request.getParameter("houseid");
		pictureServiceImp.upPictureByOpenid(new  com.shenyingchengxun.house.bean.Picture(houseid, null, null, openid));
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveVideo", method = RequestMethod.POST)
	public Map<String, Object> saveVideo(@RequestParam("file") MultipartFile file,HttpServletRequest request,
			  HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String openid = request.getParameter("openid");
		String storeId = request.getParameter("storeId");
		
		String filePath = request.getSession().getServletContext().getRealPath("/houseVideo");
		File uploadDir = new File(filePath);
		if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
		if(!file.isEmpty()){
			String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
		    Random random = new Random();   
		    String nonceStr = "";
		    for (int i = 0; i < 16; i++) {
		        int number = random.nextInt(base.length());   
		        nonceStr+=(base.charAt(number));
		    }
		    String filename = System.currentTimeMillis()+ nonceStr + "." 
		                      + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
		    map.put("filename", filename);
		    try {
			    filePath = filePath + File.separator + filename;
			    String urlpath = Config.Base + "houseVideo/" + filename;
			    Video video = new Video();
			    video.setFilename(filename);
			    video.setOpenid(openid);
			    video.setStoreid(storeId);
			    video.setVideopath(urlpath);
			    logger.debug("video.videopath : " + video.getVideopath());
			    file.transferTo(new File(filePath));
			    //验证
		        Map<String,Object> checkMap = ImageKit.checkAll(filePath);
		        boolean success = (boolean) checkMap.get("success");
		        String msg = (String) checkMap.get("code");
		        if(success){
		        	int flag = pictureServiceImp.addVideoInfo(video);
		        	map.put("flag", String.valueOf(flag));
		        	map.put("urlpath", urlpath);
		        	// 视频封面截图
		        	String videopic = filePath.substring(0,filePath.indexOf('.') + 1) + "png";
		        	logger.debug("videopic : " + videopic);
		        	VideoThumbTaker videoThumbTaker = new VideoThumbTaker("/usr/bin/ffmpeg");
		        	logger.debug("filePath : " + filePath);
		        	videoThumbTaker.getThumb(filePath, videopic, 400, 300, 0, 0, 1);
		        }
		        map.put("success", success);
		        map.put("msg", msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	@ResponseBody
	@RequestMapping(value = "/saveVideoByHouseid", method = RequestMethod.POST)
	public Map<String, Object> saveVideoByHouseid(@RequestParam("file") MultipartFile file,HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String houseid = request.getParameter("houseid");
		String openid = request.getParameter("openid");
		String storeId = request.getParameter("storeId");
		
		String filePath = request.getSession().getServletContext().getRealPath("/houseVideo");
		File uploadDir = new File(filePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		if(!file.isEmpty()){
			String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
			Random random = new Random();   
			String nonceStr = "";
			for (int i = 0; i < 16; i++) {
				int number = random.nextInt(base.length());   
				nonceStr+=(base.charAt(number));
			}
			String filename = System.currentTimeMillis()+ nonceStr + "." 
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
			map.put("filename", filename);
			try {
				filePath = filePath + File.separator + filename;
				String urlpath = Config.Base + "houseVideo/" + filename;
				Video video = new Video();
				video.setHouseid(houseid);
				video.setFilename(filename);
				video.setOpenid(openid);
				video.setStoreid(storeId);
				video.setVideopath(urlpath);
				logger.debug("video.videopath : " + video.getVideopath());
				file.transferTo(new File(filePath));
				//验证
		        Map<String,Object> checkMap = ImageKit.checkAll(filePath);
		        boolean success = (boolean) checkMap.get("success");
		        String msg = (String) checkMap.get("code");
		        if(success){
		        	int flag = pictureServiceImp.addVideoInfo(video);
		        	map.put("flag", String.valueOf(flag));
		        	map.put("urlpath", urlpath);
		        	
		        	// 视频封面截图
		        	String videopic = filePath.substring(0,filePath.indexOf('.') + 1) + "png";
		        	logger.debug("videopic : " + videopic);
		        	VideoThumbTaker videoThumbTaker = new VideoThumbTaker("/usr/bin/ffmpeg");
		        	logger.debug("filePath : " + filePath);
		        	videoThumbTaker.getThumb(filePath, videopic, 400, 300, 0, 0, 1);
		        }
		        map.put("success", success);
		        map.put("msg", msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	
	@RequestMapping(value = "/delVideoByOpenid")
	public void delVideoByOpenid(HttpServletRequest request,
			  HttpServletResponse response){
		String openid = request.getParameter("openid");
		String storeid = request.getParameter("storeId");
		List<String> filenames = pictureServiceImp.getFileNameByOpenid(openid,storeid);
		if(filenames != null && filenames.size() > 0){
			for(String filename : filenames){
				String folderPath = request.getSession().getServletContext().getRealPath("/houseVideo");
				String filePath = folderPath + File.separator + filename;
				File file = new File(filePath);
				if(file.exists()){
					file.delete();
				}
				String imgPath = filePath.substring(0,filePath.indexOf('.') + 1) + "png";
				File file1 = new File(imgPath);
				if(file1.exists()){
					file1.delete();
				}
			}
		}
		pictureServiceImp.delVideoByOpenid(openid,storeid);
	}
	@RequestMapping(value = "/getVideoByHouseid")
	public void getVideoByHouseid(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String houseid = request.getParameter("houseid");
		List<Video> list = pictureServiceImp.getVideoByHouseid(houseid);
		JSONArray res_json = JSONArray.fromObject(list);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(res_json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	@ResponseBody
	@RequestMapping(value = "/delVideoByFilename")
	public String delVideoByFilename(HttpServletRequest request,
			  HttpServletResponse response){
		String filename = request.getParameter("filename");
		String folderPath = request.getSession().getServletContext().getRealPath("/houseVideo");
		String filePath = folderPath + File.separator + filename;
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		String imgPath = filePath.substring(0,filePath.indexOf('.') + 1) + "png";
		File file1 = new File(imgPath);
		if(file1.exists()){
			file1.delete();
		}
		int flag = pictureServiceImp.delVideoByFilename(filename);
		
	    return String.valueOf(flag);
	}
	@ResponseBody
	@RequestMapping(value = "/delVideoByFilenameAndHouseid")
	public String delVideoByFilenameAndHouseid(HttpServletRequest request,
			HttpServletResponse response){
		String filename = request.getParameter("filename");
		String houseid = request.getParameter("houseid");
		String folderPath = request.getSession().getServletContext().getRealPath("/houseVideo");
		String filePath = folderPath + File.separator + filename;
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		String imgPath = filePath.substring(0,filePath.indexOf('.') + 1) + "png";
		File file1 = new File(imgPath);
		if(file1.exists()){
			file1.delete();
		}
		int flag = pictureServiceImp.delVideoByFilenameAndHouseid(filename,houseid);
		
		return String.valueOf(flag);
	}
	@ResponseBody
	@RequestMapping(value = "/upPictureByPicName")
	public String upPictureByPicName(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String houseid = request.getParameter("houseid");
		String picPath = request.getParameter("picPath");
		int flag = pictureServiceImp.upPictureByPicNameAndHouseid(houseid,picPath);
		return String.valueOf(flag);
	}
}
