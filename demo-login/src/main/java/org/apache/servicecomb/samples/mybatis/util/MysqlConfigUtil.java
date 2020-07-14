package org.apache.servicecomb.samples.mybatis.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class MysqlConfigUtil {
    public static String MYSQL_URL;
    public static String MYSQL_USERNAME;
    public static String MYSQL_PASSWORD;
    
	public static void getConfigMap() {
		String path = "/home/mysqlconfig/";
		String urlfile = path + "DB_URL";
		String usernamefile = path + "DB_USERNAME";
		String passwordfile = path + "DB_PASSWORD";
		try {
		BufferedReader urlReader = new BufferedReader(new FileReader(urlfile));
		BufferedReader unReader = new BufferedReader(new FileReader(usernamefile));
		BufferedReader pwReader = new BufferedReader(new FileReader(passwordfile));
		MYSQL_URL=urlReader.readLine();
		MYSQL_USERNAME=unReader.readLine();
		MYSQL_PASSWORD=pwReader.readLine();	
		urlReader.close();
		unReader.close();
		pwReader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		PropertiesUtil.init();
	        //获取所有
		PropertiesUtil.list();
		PropertiesUtil.update("url",MYSQL_URL);
		PropertiesUtil.update("username",MYSQL_USERNAME);
		PropertiesUtil.update("password",MYSQL_PASSWORD);
	        //获取所有
		PropertiesUtil.list();
	}

}
