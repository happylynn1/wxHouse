package com.shenyingchengxun.chat.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.shenyingchengxun.chat.bean.ChatLog;

@Repository("chatDao")
public interface ChatcDao {
	List<ChatLog> getUnreadMsgByOpenid(ChatLog cl);

	void updateMsgSendToRead(ChatLog cl);
	
	void updateUnreadToRead(ChatLog cl);

	void addChatList(@Param("openid")String openid,@Param("toOpenid")String toOpenid);
	
	List<Map<String,String>> getChatUserList(@Param("openid")String openid,@Param("toOpenid")String toOpenid);
	
	void delChatLog(@Param("openid")String openid);

	void delChatListByOpenid(@Param("openid")String openid);

	void delChatList(@Param("openid")String openid,@Param("toOpenid")String toOpenid);
	
	void upEname(@Param("openid")String openid,@Param("toOpenid")String toOpenid,@Param("ename")String ename);

	void upRequire(@Param("openid")String openid,@Param("toOpenid")String toOpenid,@Param("require")String require);
	
	String getRequire(@Param("openid")String openid,@Param("toOpenid")String toOpenid);
	
	int getAgentClient(@Param("openid")String openid,@Param("toOpenid")String toOpenid);
	
	List<ChatLog> getOldMsg(@Param("openid")String openid,@Param("toOpenid")String toOpenid,@Param("index")int index,@Param("pageNum")int pageNum);
}
