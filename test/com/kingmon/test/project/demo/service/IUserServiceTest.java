package com.kingmon.test.project.demo.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.project.demo.service.IUserService;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class IUserServiceTest {
	@Autowired
	private IUserService userService;
	
	@Test
	public void testFindByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserById() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByID_batis() {
		fail("Not yet implemented");
	}

	@Test
	public void testBatchDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadUserDataSet() {
		fail("Not yet implemented");
	}

}
