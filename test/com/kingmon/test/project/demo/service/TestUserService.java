package com.kingmon.test.project.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;
import com.kingmon.project.demo.model.User;
import com.kingmon.project.demo.service.IUserService;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class TestUserService {
	private static final Logger logger = LoggerFactory.getLogger(TestUserService.class);

	@Autowired
	private IUserService userService;
	
	
	@Test
	public void test0_findByID() {
		User u=userService.findByID("cc3X");
		logger.info(""+u);
	}
	@Test
	public void test1_addUser() {
		User u=new User();
		u.setSex("1");
		u.setIdnum("02X");
		u.setUsername("k");
		userService.addUser(u);
	}
	@Test
	public void test2_updateUser() {
		User u=new User();
		u.setUserId("cc3");
		
		u.setSex("1X");
		u.setIdnum("02X");
		u.setUsername("kingmongX");
		userService.updateUser(u);
	
	}
	@Test
	public void test3_deleteUser() {
		userService.deleteUser("cc3");	
	}
	@Test
	public void test4_queryList() {
		List<?> list=userService.queryList("kingmongXXA");
		logger.info(""+list);
	}
	@Test
	public void test5_getAll() {
		List<?> list=userService.getAll();
		logger.info(""+list);
	}

	@Test
	public void test6_findByID_batis() {
		User u=userService.findByID_batis("cc3X");
		logger.info(""+u);
	}
	
	@Test
	public void test7_batchDeleteUser() {
		List<String> ls = new ArrayList<String>();
	    for(int i = 4;i < 8;i++){
	        ls.add(""+i);
	    }
		userService.batchDeleteUser(ls);
		
	}
}
