package com.shenyingchengxun.chat.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import com.shenyingchengxun.chat.util.JdbcUtils;
import com.shenyingchengxun.house.util.WxPostUtil;

@ServerEndpoint("/chat/{openid}/{toOpenid}")
public class MyWebSocket {		

	
	public static Map<String,MyWebSocket> webSocketMap = new HashMap<String,MyWebSocket>();

	public Session session;
	public String openid;
	public String toOpenid;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 * @throws IOException 
	 *
	 */
	@OnOpen()
	public void onOpen(@PathParam("openid") String openid,@PathParam("toOpenid") String toOpenid,Session session) throws IOException {
		this.session = session;
		this.openid = openid;
		this.toOpenid = toOpenid;
		webSocketMap.put(this.openid, this);
//		System.out.println(this.openid + " 到 " + this.toOpenid + " 的连接加入！");
	}
	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketMap.remove(this.openid);
//		System.out.println(this.openid + " 到 " + this.toOpenid + " 的连接关闭！");
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 * @throws IOException 
	 */
	@OnMessage
	public void onsMessage(String message, Session session) throws IOException {
		System.out.println("来自客户端的消息:" + message);
		if(message.equals("heartbeat")){
			System.out.println(" heartbeat -- openid:" + this.openid + ", message:" + message);
			MyWebSocket item = webSocketMap.get(this.openid);
			item.sendMessage(message);
		} else {
			System.out.println("openid:" + this.openid + ", message:" + message);
			if(webSocketMap.get(this.toOpenid) != null){
				MyWebSocket item = webSocketMap.get(this.toOpenid);
				if(this.openid.equals(item.toOpenid)){
					System.out.println(this.toOpenid + "在线，并且他建立的连接面向：" + item.toOpenid);
					item.sendMessage(message);
					chatLogIntoMysql(this.openid, this.toOpenid, message, "1");
					delContactFilter(this.openid, this.toOpenid);
				} else {
					System.out.println(this.toOpenid + "在线，但是他建立的连接面向：" + item.toOpenid);
					item.sendMessage("-" + this.openid);
					chatLogIntoMysql(this.openid, this.toOpenid, message, "0");
					delContactFilter(this.openid, this.toOpenid);
				}
			} else {
				System.out.println(this.toOpenid + "下线。");
				chatLogIntoMysql(this.openid, this.toOpenid, message, "0");
				delContactFilter(this.openid, this.toOpenid);
				// 下线用户需要微信推送一条消息进行提醒
				System.out.println("---------------------------------检查条件是否符合-------------------------------------");
				if(isSend(this.openid, this.toOpenid)){
					boolean isAgent = isAgent(this.toOpenid);
					String title = isAgent?"您有新的房源消息，请查收。":"您有新的经纪人消息，请查收。";
					String content = isAgent?"咨询房产相关事宜。":"房产咨询回复。";
					Map<String,String> storeMap = getStoreByOpenid(this.openid,this.toOpenid);
					String storeId = storeMap.get("storeId");
					String accessToken = storeMap.get("accessToken");
					if(accessToken!=null){
						String name = getName(isAgent(this.openid),this.openid);
						String url="http://wxmp.jjr580.com/wxHouse/page/toChat.do?storeId="+storeId+"&openid="+this.toOpenid+"&toOpenid="+this.openid;
						JSONObject json = WxPostUtil.creatNewMsgTemplate(storeId, title, name,content, this.toOpenid, url);
						String code = WxPostUtil.sendTemplate(accessToken,json);	
						System.out.println("-----------------------------"+(code.equals("0")?"发送成功":"发送失败")+":"+code+"----------------------------------");
						if(code.equals("0")){
							addMsgSend(this.openid, this.toOpenid);
						}
					}
				}
			}
		}
	}
	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 * 
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getAsyncRemote().sendText(message);
	}
	
	public void chatLogIntoMysql(String fromUser, String toUser, String msg, String unread){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String chat_time = sdf.format(new Date());
		String insert = "INSERT INTO wx_chatlog(senderid,receiverid,content,`type`,chat_time,unread) VALUES ( ?, ?, ?, 'text', ?, ?) ";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.prepareStatement(insert);
			stmt.setString(1, fromUser);
			stmt.setString(2, toUser);
			stmt.setString(3, msg);
			stmt.setString(4, chat_time);
			stmt.setString(5, unread);
			stmt.executeUpdate();
			chatListIntoMysql(fromUser,toUser);
			chatListIntoMysql(toUser,fromUser);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt, conn);
		}
	}
	public void delContactFilter(String fromUser, String toUser){
		String sql = " delete from wx_contact_filter where (openid = ? and toOpenid = ?) or (openid = ? and toOpenid = ?) ";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, toUser);
			stmt.setString(2, fromUser);
			stmt.setString(3, fromUser);
			stmt.setString(4, toUser);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt, conn);
		}
	}
	public void chatListIntoMysql(String fromUser, String toUser){
		String insert = " insert into wx_chatlist (openid,toOpenid,times) values (?,?,now()) on DUPLICATE KEY update times = now()  ";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.prepareStatement(insert);
			stmt.setString(1, fromUser);
			stmt.setString(2, toUser);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt, conn);
		}
	}
	public static String getName(boolean isAgent,String openid){
		String selAgent = "select if(name IS NULL or name='',nickname,name) a from wx_agentinfo where agent_openid = ?";
		String selUser = "select if(nickname IS NULL or nickname = '','未知',nickname) c from wx_client where client_openid = ?";
		String name = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.prepareStatement(isAgent?selAgent:selUser);
			stmt.setString(1, openid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt, conn);
		}
		System.out.println(name);
		return name;
	}
	public static boolean isAgent(String openid){
		String sel = "select id from wx_agentinfo where agent_openid = ? ";
		int num = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.prepareStatement(sel);
			stmt.setString(1, openid);
			ResultSet rs = stmt.executeQuery();
			rs.last();
			num = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt, conn);
		}
		return num>0;
	}
	public static Map<String,String> getStoreByOpenid(String openid,String toOpenid){
		System.out.println("-------------------------------------获取门店信息---------------------------------------------");
		Map<String,String> map = new HashMap<String,String>();
		String sel = "select storeid from wx_agentinfo where agent_openid = ? or agent_openid = ? limit 1";
		String sel2 = "select accessToken from wx_storeinfo where storeid = ? limit 1";
		String storeId= "";
		String accessToken = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.prepareStatement(sel);
			stmt.setString(1, openid);
			stmt.setString(2, toOpenid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				storeId = rs.getString(1);
				map.put("storeId", storeId);
				stmt = conn.prepareStatement(sel2);
				stmt.setString(1, storeId);
				rs = stmt.executeQuery();
				if(rs.next()){
					accessToken = rs.getString(1);
					map.put("accessToken", accessToken);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt, conn);
		}
		System.out.println(map);
		return map;
	}
	public static boolean isSend(String openid,String toOpenid){
		/*String sel = "select id from wx_msg_send where openid = ? and toOpenid = ? and status = '未读' ";
		int num = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.prepareStatement(sel);
			stmt.setString(1, openid);
			stmt.setString(2, toOpenid);
			ResultSet rs = stmt.executeQuery();
			rs.last();
			num = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt, conn);
		}
		System.out.println("---------------------------------"+(num==0)+"-------------------------------------");*/
		return 0==0;
	}
	public void addMsgSend(String openid,String toOpenid){
		System.out.println("-------------------------------------插入数据到wx_msg_send-------------------------------------------------");
		System.out.println("openid----->"+openid);
		System.out.println("toOpenid----->"+toOpenid);
		String sql = "insert into  `wx_msg_send` (openid,toOpenid,status,times) values ( ?,?,'未读',now()) ";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, openid);
			stmt.setString(2, toOpenid);
			int a = stmt.executeUpdate();
			System.out.println("-------------------------------------插入"+a+"条数据成功-------------------------------------------------");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			close(stmt, conn);
		}
	}
	public static void close(PreparedStatement stmt,Connection conn){
		try {
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}