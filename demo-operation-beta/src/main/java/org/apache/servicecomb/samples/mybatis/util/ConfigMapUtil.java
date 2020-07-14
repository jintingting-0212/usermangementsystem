/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicecomb.samples.mybatis.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ConfigMapUtil {
		 
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
