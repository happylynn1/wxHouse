package com.shenyingchengxun.chat.service;

import java.util.List;
import java.util.Map;

import com.shenyingchengxun.chat.bean.ChatLog;

public interface ChatcService {
	List<ChatLog> getUnreadMsgByOpenid(ChatLog cl);

	void updateUnreadToRead(ChatLog cl);
	
	List<Map<String,String>> getChatUserList(String openid,String toOpenid);

	void addChatList(String openid,String toOpenid);
	
	void upRequire(String openid,String toOpenid,String require);

	void delChatList(String openid,String toOpenid);

	String getRequire(String openid,String toOpenid);
	
	int getAgentClient(String openid,String toOpenid);
	
	List<ChatLog> getOldMsg(String openid,String toOpenid,String index,String pageNum);
}
