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
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.samples.mybatis.dao.IUserDao;
import org.apache.servicecomb.samples.mybatis.entity.User;
import org.apache.servicecomb.samples.mybatis.util.PropertiesUtil;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * {@link RestEndpoint} provides the rest implementation of {@link Endpoint}.
 * The rest endpoint is accessed by /gc with HTTP POST.
 */
/*
这里的REST接口是以Spring MVC风格开发的。CSEJavaSDK支持的开 发风格有REST(JAX-RS、Spring MVC）和RPC，开发者可以自由选用。
 */
@RestSchema(schemaId = "RestEndpoint")
@RequestMapping(path = "/operation")
public class RestEndpoint implements Endpoint {
    private static AtomicInteger count = new AtomicInteger(0);//线程安全的计数变量 
	private final IUserDao userDao;

	@Autowired
	public RestEndpoint(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	
	@CrossOrigin(origins="*", maxAge = 3600)
	@GetMapping(path = "/findAllUsers")
	public List<User> findAllUsers(@RequestParam("userName")String userName) {
		User user1 = new User();
		user1.setUserName(userName);	
		System.out.println(user1.toString());
		List<User> users = userDao.findAllUsers(user1);
		return users;
	}
	
	@GetMapping(path = "/findUser/{userName}")
	public User findUser(@PathVariable String userName) {
		User user1 = new User();
		user1.setUserName(userName);
		User user2 = userDao.findUser(user1);
		return user2;
	}

	@Override
	@PostMapping(value = "/addUser")
	public int addUser(@RequestBody User user) {
		System.out.println(user.toString());
		int r = userDao.addUser(user);
		return r;
	}
	
	@Override
	@PostMapping(value = "/modifyUser")
	public int modifyUser(@RequestBody User user) {
		System.out.println(user.toString());
		int r = userDao.modifyUser(user);
		return r;
	}
	
//	@Override
//	@PostMapping(value = "/deleteUser")
//	public int deleteUser(@RequestBody User user) {
//		System.out.println(user.toString());
//		int r = userDao.deleteUser(user);
//		return r;
//	}
	
	@Override
	@DeleteMapping(value = "/deleteUser/{userName}")
	public int deleteUser(@PathVariable String userName) {
		count.incrementAndGet();
		if((count.get())%3==0) {
			System.out.println("----------------------"+count.get()+"----------------------");
			User user = new User();
			user.setUserName(userName);
			System.out.println(user.toString());
			int r = userDao.deleteUser(user);
			return r;
		}else {
			System.out.println("----------------------"+count.get()+"----------------------");
			throw new InvocationException(javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE, null);	
		}
	}
	
	
}
