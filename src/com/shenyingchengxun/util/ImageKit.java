package com.shenyingchengxun.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImageKit {
	
	private final static String[] badStrArr = "request|.getfolder|.createfolder|.deletefolder|.createdirectory|.deletedirectory|.saveas|wscript.shell|script.encode|server.|.createobject|execute|activexobject|language=|javascript|html".split("\\|");
	private final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
	private final static String imgType = "jpg|png|gif|mp4|flv";
	static{
		getAllFileType();
	}
	public static Map<String, Object> checkAll(String path) throws IOException{
		Map<String, Object> map = checkHeader(path);
		boolean success = (Boolean) map.get("success");
		if(success){
			map = checkBadStrArr(path);
		}
		return map;
	}
	private static void getAllFileType(){    
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)       
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png"); //PNG (png)       
        FILE_TYPE_MAP.put("47494638396126026f01", "gif"); //GIF (gif)       
        FILE_TYPE_MAP.put("49492a00227105008037", "tif"); //TIFF (tif)       
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp"); //16色位图(bmp)       
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp"); //24位位图(bmp)       
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp"); //256色位图(bmp)       
        FILE_TYPE_MAP.put("41433130313500000000", "dwg"); //CAD (dwg)       
        FILE_TYPE_MAP.put("3c21444f435459504520", "html"); //HTML (html)  
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm"); //HTM (htm)  
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css"); //css  
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js"); //js  
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf)       
        FILE_TYPE_MAP.put("38425053000100000000", "psd"); //Photoshop (psd)       
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml)         
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样       
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd"); //Visio 绘图       
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb"); //MS Access (mdb)        
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");       
        FILE_TYPE_MAP.put("255044462d312e360d25", "pdf"); //Adobe Acrobat (pdf)     
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); //rmvb/rm相同    
        FILE_TYPE_MAP.put("464c5601050000000900", "flv"); //flv与f4v相同    
        FILE_TYPE_MAP.put("00000020667479706973", "mp4");   
        FILE_TYPE_MAP.put("49443303000000000f76", "mp3");   
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg"); //       
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); //wmv与asf相同      
        FILE_TYPE_MAP.put("524946464694c9015741", "wav"); //Wave (wav)    
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");    
        FILE_TYPE_MAP.put("4d546864000000060001", "mid"); //MIDI (mid)     
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");      
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");     
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");     
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");   
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");//可执行文件  
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");//jsp文件  
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");//MF文件  
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");//xml文件  
        FILE_TYPE_MAP.put("efbbbf2f2a0d0a53514c", "sql");//xml文件  
        FILE_TYPE_MAP.put("7061636b616765207765", "java");//java文件  
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");//bat文件  
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");//gz文件  
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");//bat文件  
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");//bat文件  
        FILE_TYPE_MAP.put("49545346030000006000", "chm");//bat文件  
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");//bat文件  
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");//docx文件  
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");//WPS文字wps、表格et、演示dps都是一样的  
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");  
        FILE_TYPE_MAP.put("494d4b48010100000200", "264");  
        FILE_TYPE_MAP.put("6D6F6F76", "mov"); //Quicktime (mov)    
        FILE_TYPE_MAP.put("FF575043", "wpd"); //WordPerfect (wpd)     
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)       
        FILE_TYPE_MAP.put("2142444E", "pst"); //Outlook (pst)        
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf"); //Quicken (qdf)       
        FILE_TYPE_MAP.put("E3828596", "pwl"); //Windows Password (pwl)           
        FILE_TYPE_MAP.put("2E7261FD", "ram"); //Real Audio (ram)   
    }  
	private static FileInputStream getFileInputStream(String path) throws FileNotFoundException{
		return new FileInputStream(new File(path));  
	}
	
	private static BufferedReader getBufferedReader(String path) throws FileNotFoundException{
		return new BufferedReader(new FileReader(path));
	}
	private static String bytesToHexString(byte[] src){      
        StringBuilder stringBuilder = new StringBuilder();      
        if(null==src || src.length <= 0)    
            return null;      
        for(int i = 0; i < src.length; i++){      
            int v = src[i] & 0xFF;      
            String hv = Integer.toHexString(v);      
            if(hv.length() < 2)    
                stringBuilder.append(0);      
            stringBuilder.append(hv);      
        }      
        return stringBuilder.toString();    
    }  
	public static Map<String,Object> checkBadStrArr(String path) throws IOException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		BufferedReader bufferedReader= getBufferedReader(path);
		String line = null;
		boolean success = true;
		String code = "包含恶意代码";
		while (((line = bufferedReader.readLine()) != null)&&success) {
			for (int i = 0; i < badStrArr.length; i++) {
				if(line.contains(badStrArr[i])){
					System.out.println("异常部分-->"+line);
					success = false;
					break;
				}
			}
		}
		bufferedReader.close();
		if(success){
			code =  "正常文件";
		}else{
			System.out.println("path-->"+path);
			File file = new File(path);
			if(file.exists()&&file.isFile()) System.out.println(file.delete());
		}
		resultMap.put("success", success);
		resultMap.put("code", code);
		return resultMap;
	}
	public static Map<String,Object> checkHeader(String path) throws IOException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		boolean success = false;
		String code = "文件类型错误";
		String res = null;  
        FileInputStream fis  = getFileInputStream(path);
        //获取文件头的前六位  
        byte[] b = new byte[3];    
        fis.read(b, 0, b.length);    
        String fileCode = bytesToHexString(b);   
        Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();    
        while(keyIter.hasNext()){    
            String key = keyIter.next();          
             //比较前几位是否相同就可以判断文件格式（相同格式文件文件头后面几位会有所变化）  
            if(key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())){    
                res = FILE_TYPE_MAP.get(key);
                if(imgType.contains(res)){
                	success = true;
                	code  = "正常文件";
                }
                break;    
            }    
        }
        fis.close();
        if(!success){
        	File file = new File(path);
			if(file.exists()) file.delete();
        }
        System.out.println("文件头:"+fileCode+"-----文件类型:"+res);  
        fis.close();
        resultMap.put("success", success);
        resultMap.put("code", code);
        return resultMap;    
	}
}
