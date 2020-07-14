package org.apache.servicecomb.samples.mybatis.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	

    public static String REDIS_HOST;
    public static int REDIS_PORT;
    public static String REDIS_PASSWORD;
	//单个连接
	public static Jedis cli_single() {

		String path = "/home/redisconfig/";
		String hostfile = path + "REDIS_HOST";
		String portfile = path + "REDIS_PORT";
		String passwordfile = path + "REDIS_PASSWORD";
		try {
		BufferedReader hostReader = new BufferedReader(new FileReader(hostfile));
		BufferedReader portReader = new BufferedReader(new FileReader(portfile));
		BufferedReader passwordReader = new BufferedReader(new FileReader(passwordfile));
		REDIS_HOST=hostReader.readLine();
		REDIS_PORT=Integer.parseInt(portReader.readLine());
		REDIS_PASSWORD=passwordReader.readLine();	
		hostReader.close();
		portReader.close();
		passwordReader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Jedis jedis=new Jedis(REDIS_HOST,REDIS_PORT);
		jedis.auth(REDIS_PASSWORD);
		return jedis;

	}
	
	
    public static void main(String[] args) {
    	String key= "wanglikun";
    	String value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";	
    	Jedis jedis = JedisUtil.cli_single();
    	jedis.connect();
    	jedis.set(key, value);
		System.out.println(jedis.get(key));
       
     }


}
