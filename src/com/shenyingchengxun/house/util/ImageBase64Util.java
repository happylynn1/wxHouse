package com.shenyingchengxun.house.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class ImageBase64Util {
	/**
	 * 图片转换base64编码
	 */
	public static String getImageStr(String imgFile){
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(data));
	}
	
	/**
	 * 将base64编码字符串转换为图片
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	 */
	public static boolean generateImage(String imgStr,String path){
		if(imgStr == null)
			return false;
		try {
			// 解码
			byte[] b = Base64.decodeBase64(imgStr);
			// 调整异常数据
			for(int i = 0; i < b.length; i++){
				if(b[i] < 0){
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
            out.close();
            return true;	
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
