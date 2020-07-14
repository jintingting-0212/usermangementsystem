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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class PropertiesUtil {
		 
		public static final Properties p = new Properties();
		public static final String path = "jdbc.properties";
		//public static final String pathout = "src/main/resources/jdbc.properties";	
		 /**
		 * 通过类装载器 初始化Properties
		 */
		public static void init() {
			//转换成流

		    InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
			try {
				//从输入流中读取属性列表（键和元素对）
				p.load(inputStream);
				
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	    /**
		 * 通过key获取value
		 * @param key
		 * @return
		 */
		public static String get(String key) {
			return p.getProperty(key);
		}
	    /**
		 * 修改或者新增key
		 * @param key
		 * @param value
		 */
		public static void update(String key, String value) {
			p.setProperty(key, value);
			FileOutputStream oFile = null;
			try {
				oFile = new FileOutputStream(path);
				//将Properties中的属性列表（键和元素对）写入输出流
				p.store(oFile, "");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	    /**
		 * 通过key删除value
		 * @param key
		 */
		public static void delete(String key) {
			p.remove(key);
			FileOutputStream oFile = null;
			try {
				oFile = new FileOutputStream(path);
				p.store(oFile, "");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	    /**
		 * 循环所有key value
		 */
		public static void list() {
			Enumeration en = p.propertyNames(); //得到配置文件的名字
			while(en.hasMoreElements()) {
				String strKey = (String) en.nextElement();
				String strValue = p.getProperty(strKey);
				System.out.println(strKey + "=" + strValue);
			}
		}
		
		
		public static void main(String[] args) {
			PropertiesUtil.init();
		        //获取所有
			PropertiesUtil.list();		 
			//修改
			PropertiesUtil.update("password","ffffff");
			System.out.println(PropertiesUtil.get("password"));
			
		        //获取所有
			PropertiesUtil.list();
		}
		
		
		
}
