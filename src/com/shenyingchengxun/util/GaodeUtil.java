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

public class GaodeUtil {
	private static String key = "525b96fd7b9b3dfc946251d1d287ca06";
	private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	public static Map<String,String> GetCommAndStreetByLnglat(String lnglat){
		Map<String,String> map = new HashMap<String,String>();
		String lng = lnglat.split(",")[0];
		String lat = lnglat.split(",")[1];
		Pattern p1 = Pattern.compile("\"pois\":\\[\\{\"(.*?)\"\\}\\],\"roads\"");
		Pattern p2 = Pattern.compile("\"name\":\"(.*?)\",\"type\"");
		Pattern p3 = Pattern.compile("\"address\":\"(.*?)\",\"poiweight\"");
		String poisStr = "",name = "",address="";
		String url = String.format("http://restapi.amap.com/v3/geocode/regeo?poitype=120301|120302|120303|120304&extensions=all&key=%s&location=%s",key,lnglat);
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
					System.out.println(line);
					Matcher m1 = p1.matcher(line);
				    while(m1.find()) {  
				    	poisStr = m1.group(1);
				    }
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
				}}
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("name",name);
		map.put("address",address);
		Object[] obj = convertBaidu(Double.parseDouble(lng),Double.parseDouble(lat));
		map.put("lng", obj[0]+"");
		map.put("lat", obj[1]+"");
		return map;
	}
	public static Map<String,String> getAreaStreetByCityCommName(String city,String comm){
		Pattern p0 = Pattern.compile("\"district\":\"(.*?)\",\"township\"");
		Pattern p1 = Pattern.compile("\"location\":\"(.*?)\",\"level\"");
		Pattern p2 = Pattern.compile("\"district\":\"(.*?)\",\"adcode\"");
		Pattern p3 = Pattern.compile("\"township\":\"(.*?)\",\"towncode\"");
		Map<String,String> map = new HashMap<String,String>();
		String zone ="",street ="",location="";
		String url = String.format("http://restapi.amap.com/v3/geocode/geo?address=%s&key=%s",city+comm,key);
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
					System.out.println(line);
					Matcher m0 = p0.matcher(line);
					while(m0.find()) {  
						Matcher m1 = p1.matcher(line);
						while(m1.find()) {  
					    	location = m1.group(1);
					    	url = String.format("http://restapi.amap.com/v3/geocode/regeo?key=%s&location=%s",key,location);
					    	myUrl = new URL(url);
							httpsConn = (URLConnection)myUrl.openConnection();
							if(httpsConn != null){
								input = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");
								reader = new BufferedReader(input);
								line = null;
								while((line=reader.readLine())!=null){
									Matcher m2 = p2.matcher(line);
									while(m2.find()) {  
								    	zone = m2.group(1);
								    }
									Matcher m3 = p3.matcher(line);
									while(m3.find()) {
										street = m3.group(1);
								    }
								}
							}
					    }						
					}
				}}
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("zone", zone);
		map.put("street", street);
		return map;
	}
	public static Object[] convertBaidu(Double lng, Double lat) {
		double x = lng, y = lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		double bd_lng = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return new Object[]{bd_lng,bd_lat};
	}
	
	public static void main(String[] args) {
		System.out.println(GetCommAndStreetByLnglat("116.32401,39.958893"));
		Map<String,String> map = getAreaStreetByCityCommName("北京","万润家园");
		System.out.println(map);
	}
}
