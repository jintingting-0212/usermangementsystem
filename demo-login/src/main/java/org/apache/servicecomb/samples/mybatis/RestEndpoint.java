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
package org.apache.servicecomb.samples.mybatis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.samples.mybatis.dao.IUserDao;
import org.apache.servicecomb.samples.mybatis.entity.User;
import org.apache.servicecomb.samples.mybatis.util.JedisUtil;
import org.apache.servicecomb.samples.mybatis.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import redis.clients.jedis.Jedis;

/**
 * {@link RestEndpoint} provides the rest implementation of {@link Endpoint}.
 * The rest endpoint is accessed by /gc with HTTP POST.
 */
@RestSchema(schemaId = "RestEndpoint")
@RequestMapping(path = "/")
public class RestEndpoint implements Endpoint {

    private static AtomicInteger count = new AtomicInteger(0);//线程安全的计数变量   
    private static int num = 0;  
	private final IUserDao userDao;
	
	@Autowired
	public RestEndpoint(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@GetMapping(value = "/sleep")
	public  String sleep() {
		count.incrementAndGet();
		if((count.get())%3==0) {
			System.out.println("----------------------"+count.get()+"----------------------");
			return "User Management System";
		}else {
		try {
			System.out.println("----------------------"+count.get()+"----------------------");
			Thread.sleep(1000*60);
		}catch (Exception e) {
			e.printStackTrace();
		}
			return "Sleep Stop";
		}
	}

	@Override
	@PostMapping(value = "/login")
	public boolean login(@RequestBody Map<String,String> map) {
	System.out.print("*********************************************************");
	
	
	User inputUser = new User();
	inputUser.setUserName(map.get("userName"));
	inputUser.setPassword(map.get("password")); 
//	inputPassword = map.get("password");
	
	User users = new User();
	users= userDao.findUser(inputUser);
	//System.out.println(users.getUserName())
	if(users==null)
		return false;
	else if(users.getUserName().equals(inputUser.getUserName())&&users.getPassword().equals(inputUser.getPassword())) {
		//生成token
		String token = JwtToken.token(inputUser.getUserName(), inputUser.getPassword());
		System.out.println(token);
		//连接redis，保存token
		Jedis jedis = JedisUtil.cli_single();
		jedis.connect();
		jedis.set(inputUser.getUserName(), token);
		System.out.println(jedis.get(inputUser.getUserName()));
		return true;			
	}
	
	return false;
	
}
	
	
	
//	public boolean login(@RequestBody User user) {
//		System.out.print("*********************************************************");
//		System.out.println(user.toString());
//		User users = new User();
//		String inputUserName = user.getUserName();
//		String inputPassword = user.getPassword();
//		users= userDao.findUser(user);
//		//System.out.println(users.getUserName())
//		if(users==null)
//			return false;
//		else if(users.getUserName().equals(inputUserName)&&users.getPassword().equals(inputPassword)) {
//			//生成token
//			String token = JwtToken.token(inputUserName, inputUserName);
//			System.out.println(token);
//			//连接redis，保存token
//			Jedis jedis = JedisUtil.cli_single();
//			jedis.connect();
//			jedis.set(inputUserName, token);
//			System.out.println(jedis.get(inputUserName));
//			return true;			
//		}
//		
//		return false;
//		
//	}
}
