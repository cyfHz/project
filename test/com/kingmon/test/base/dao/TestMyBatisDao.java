package com.kingmon.test.base.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;
import com.kingmon.project.demo.mapper.UserMapper;
import com.kingmon.project.demo.model.User;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class TestMyBatisDao {
	private static final Logger logger = LoggerFactory.getLogger(TestMyBatisDao.class);

	@Inject
	private UserMapper userMapper;
	
	
	@Test
	public void test_getById() {
		logger.info(String.valueOf(userMapper.getUserById("cc3")));
	}
	//--------------------------------------------------------------------
	@Inject
	private com.kingmon.base.dao.MyBatisDao myBatisDao;
	
	String statement_key="";
	@Test
	public void test1_batisInsert() {
		User u=new User();
		u.setSex("1");
		u.setIdnum("02");
		u.setUserId("cc3");
		u.setUsername("kingmong");
		myBatisDao.batisInsert("com.kingmon.project.demo.mapper.UserMapper.addUser", u);
	
	}
	@Test
	public void test2_batisUpdate() {
		User u=new User();
		u.setUserId("cc3");
		
		u.setSex("1X");
		u.setIdnum("02X");
		u.setUsername("kingmongX");
		myBatisDao.batisUpdate("com.kingmon.project.demo.mapper.UserMapper.updateUser", u);
	
	}
	@Test
	public void test3_batisDelete() {
		User u=new User();
		u.setUserId("cc3");
		myBatisDao.batisDelete("com.kingmon.project.demo.mapper.UserMapper.deleteUser", u);
	
	}
	@Test
	public void test4_batisSelectList() {
		Map<String, Object> _params =Maps.newHashMap();
		_params.put("username", "kingmongX");
		List<?> list=myBatisDao.batisSelectList("com.kingmon.project.demo.mapper.UserMapper.queryList", _params);
		logger.info(""+list);
	}
	@Test
	public void test5_batisSelectListA() {
		List<?> list=myBatisDao.batisSelectList("com.kingmon.project.demo.mapper.UserMapper.queryList", "kingmongX");
		logger.info(""+list);
	}
	@Test
	public void test6_batisSelectOne() {
		User u=(User) myBatisDao.batisSelectOne("com.kingmon.project.demo.mapper.UserMapper.getUserById", "cc3");
		logger.info(""+u);
	}

	
	
	
}
