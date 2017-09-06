package com.kingmon.test.common.session.repository.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.session.SessionUser;
import com.kingmon.common.session.repository.ISessionUserRepository;
import com.kingmon.project.elastic.service.ElasticService;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class RedisSessionUserRepositoryTest {
	
	@Autowired
	private ISessionUserRepository sessionUserRepository;
	
	@Test
	public void test1SaveSessionUser() {
		String sad="12345679zht";
		SessionUser user=new SessionUser();
		user.setLoginname(sad);
		user.setSessionId(sad);
		sessionUserRepository.saveSessionUser(user);
	}

	@Test
	public void test2DeleteSessionUser() {
		fail("Not yet implemented");
	}

	@Test
	public void test3GetSessionUser() {
		String sad="12345679zht";
		SessionUser su=sessionUserRepository.getSessionUser(sad);
		System.out.println(su);
	}

	@Test
	public void test4GetJedisManager() {
		fail("Not yet implemented");
	}

	@Test
	public void test5SetJedisManager() {
		fail("Not yet implemented");
	}

}
