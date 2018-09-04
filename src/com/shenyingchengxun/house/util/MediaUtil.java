package com.shenyingchengxun.house.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import com.shenyingchengxun.action.bean.StoreInfo;

public class MediaUtil {
	/**
	 * 获取临时素材
	 * @param si
	 * @param mediaId
	 * @return
	 */
	private static InputStream getMedia(StoreInfo si,String mediaId) {
	    String url = "https://api.weixin.qq.com/cgi-bin/media/get";
	    String access_token = si.getAccessToken();
	    String params = "access_token=" + access_token + "&media_id=" + mediaId;
	       InputStream is = null;
	       try {
	        String urlNameString = url + "?" + params;
	           URL urlGet = new URL(urlNameString);
	           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	           http.setRequestMethod("GET");
	           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           http.connect();
	           is = http.getInputStream();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	    return is;
	}
	/**
	 * 保存图片至服务器 
	 * @param mediaId
	 * @return 文件名
	 */
	public static String saveImageToDisk(String path,StoreInfo si,String mediaId){
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
	    Random random = new Random();   
	    String nonceStr = "";
	    for (int i = 0; i < 16; i++) {
	        int number = random.nextInt(base.length());   
	        nonceStr+=(base.charAt(number));
	    }  
	    String filename = "";
	    InputStream inputStream = getMedia(si,mediaId);
	    byte[] data = new byte[1024];
	    int len = 0;
	    FileOutputStream fileOutputStream = null;
	    try {
	        //服务器存图路径
	        filename = System.currentTimeMillis()+ nonceStr + ".jpg";
	        path += File.separator+"housePicture"+File.separator+filename;
	        fileOutputStream = new FileOutputStream(path);
	        while ((len = inputStream.read(data)) != -1) {
	            fileOutputStream.write(data, 0, len);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (inputStream != null) {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (fileOutputStream != null) {
	            try {
	                fileOutputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return filename;
	}
}
