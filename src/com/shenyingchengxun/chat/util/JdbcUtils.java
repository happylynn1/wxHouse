package com.shenyingchengxun.chat.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
	
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static String driverName = null;
	
	private JdbcUtils(){}
	
	static {
		InputStream in = null;
		try {
			String path = JdbcUtils.class.getResource("/").getPath().toString();
			path = path.replace("classes", "resource") + "db.properties";
			in = new FileInputStream(path);
			Properties properties = new Properties();  
			properties.load(in);
			url = properties.getProperty("jdbc.url");
			driverName = properties.getProperty("jdbc.driver");
			username = properties.getProperty("jdbc.username");
			password = properties.getProperty("jdbc.password");
			Class.forName(driverName);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, username, password);
	}
	
	public static void main(String[] args) throws IOException{
		System.out.println(url);
	}
}
