package com.kingmon.test.project.demo.dao;

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
import com.kingmon.project.demo.dao.IUserDao;
import com.kingmon.project.demo.model.User;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class TestUserDao {
	private static final Logger logger = LoggerFactory.getLogger(TestUserDao.class);

	@Autowired
	@Qualifier("userDaoImpl")
	private IUserDao userDao;
	
	
	@Test
	public void test0_findByID() {
		User u=userDao.findByID("cc3X");
		logger.info(""+u);
	}
	@Test
	public void test1_addUser() {
		User u=new User();
		u.setSex("1");
		u.setIdnum("02X");
		u.setUserId("cc3X");
		u.setUsername("kingmongXXA");
		userDao.addUser(u);
	}
	@Test
	public void test2_updateUser() {
		User u=new User();
		u.setUserId("cc3");
		
		u.setSex("1X");
		u.setIdnum("02X");
		u.setUsername("kingmongX");
		userDao.updateUser(u);
	
	}
	@Test
	public void test3_deleteUser() {
		userDao.deleteUser("cc3");	
	}
	@Test
	public void test4_queryList() {
		List<?> list=userDao.queryList("kingmongXXA");
		logger.info(""+list);
	}
	@Test
	public void test5_getAll() {
		List<?> list=userDao.getAll();
		logger.info(""+list);
	}

	
	
	
}
