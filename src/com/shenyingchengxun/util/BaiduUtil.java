package com.shenyingchengxun.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiduUtil {
	public static void main(String[] args) {
		String lnglat = "116.335038,40.321147";
		System.out.println(GetCommAndStreetByLnglat(500,lnglat));
		System.out.println(GetCommAndStreetByCityComm("北京","万润"));
	}
	private static String key = "NolUmsKobzWH9XEu1yY20uxbfREhslrW";
	public static String GetCommAndStreetByLnglat(int radius,String lnglat){
		String lng = lnglat.split(",")[0];
		String lat = lnglat.split(",")[1];
		Pattern p2 = Pattern.compile("\"name\":\"(.*?)\",\"location\"");
		Pattern p3 = Pattern.compile("\"address\":\"(.*?)\",\"province\"");
		String url = "http://api.map.baidu.com/place/v2/search?query=小区$住宅&radius_limit=true&location="+lat+","+lng+"&radius="+radius+"&output=json&ak="+key;
		String poisStr = "",name="",address ="";
		URL myUrl = null;
		URLConnection httpsConn = null;
		InputStreamReader input = null;
		BufferedReader reader = null;
		try {
			myUrl = new URL(url);
			httpsConn = (URLConnection)myUrl.openConnection();
			if(httpsConn != null){
				input = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");
				reader = new BufferedReader(input);
				String line = null;
				while((line=reader.readLine())!=null){
					poisStr+=line.trim();
				}}
				System.out.println(poisStr);
				Matcher m2 = p2.matcher(poisStr);
				while(m2.find()) {
					name = m2.group(1);
					break;
				}
				Matcher m3 = p3.matcher(poisStr);
				while(m3.find()) {
					address = m3.group(1);
					break;
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!"".equals(name)){
			return name+":"+address;
		}else if(radius<5000){
			return GetCommAndStreetByLnglat(radius+500, lnglat);
		}else{
			return "";
		}
	}
	public static Map<String,String> GetCommAndStreetByCityComm(String city,String comm){
		Map<String,String> map = new HashMap<String,String>();
		Pattern p2 = Pattern.compile("\"name\":\"(.*?)\",\"location\"");
		Pattern p3 = Pattern.compile("\"address\":\"(.*?)\",\"province\"");
		Pattern p4 = Pattern.compile("\"lat\":(.*?),\"lng\"");
		Pattern p5 = Pattern.compile("\"lng\":(.*?)},\"address\"");
		String url = "http://api.map.baidu.com/place/v2/search?query="+comm+"&tag=小区$住宅&region="+city+"&output=json&ak="+key;
		String poisStr = "",name="",address ="",lng="",lat="";
		URL myUrl = null;
		URLConnection httpsConn = null;
		InputStreamReader input = null;
		BufferedReader reader = null;
		try {
			myUrl = new URL(url);
			httpsConn = (URLConnection)myUrl.openConnection();
			if(httpsConn != null){
				input = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");
				reader = new BufferedReader(input);
				String line = null;
				while((line=reader.readLine())!=null){
					poisStr+=line.trim();
				}}
				System.out.println(poisStr);
				Matcher m2 = p2.matcher(poisStr);
				while(m2.find()) {
					name = m2.group(1);
					break;
				}
				Matcher m3 = p3.matcher(poisStr);
				while(m3.find()) {
					address = m3.group(1);
					break;
				}
				Matcher m4 = p4.matcher(poisStr);
				while(m4.find()) {
					lat = m4.group(1);
					break;
				}
				Matcher m5 = p5.matcher(poisStr);
				while(m5.find()) {
					lng = m5.group(1);
					break;
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("name", name);
		map.put("address", address);
		map.put("lng", lng);
		map.put("lat", lat);
		return map;
	}
}
