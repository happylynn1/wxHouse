package com.shenyingchengxun.house.util;

public class MessageUtil {
	  public static String getBatchSendInfo(String condition,String time,String url) {
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append("消息提醒").append("\n"); 
	    	buffer.append("\n");
	    	buffer.append("恭喜!有套新房源符合您的找房条件。").append("\n"); 
	    	buffer.append("找房条件：").append(condition).append("\n");
	    	buffer.append("提醒时间：").append(time).append("\n");
			buffer.append("<a href='"+url+"' >点击查看房源详情</a>").append("\n"); 
	    	return buffer.toString();
	    }
	  public static String getUnreadMsgInfo(String title,String name,String time,String url) {
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append("聊天信息").append("\n"); 
	    	buffer.append("\n");
	    	buffer.append(title).append("\n"); 
	    	buffer.append("发送人：").append(name).append("\n"); 
	    	buffer.append("发送时间：").append(time).append("\n");
			buffer.append("<a href='"+url+"' >点此查看详情</a>").append("\n"); 
	    	return buffer.toString();
	    }
}
