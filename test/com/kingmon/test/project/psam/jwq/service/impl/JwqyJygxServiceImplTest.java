package com.kingmon.test.project.psam.jwq.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.project.psam.jwq.service.IJwqyJygxService;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class JwqyJygxServiceImplTest {

	@Autowired
	private IJwqyJygxService jwqyJygxService;
	
	@Test
	public void testLoadOrgJyDataSet() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadJwqyJygxDataSet() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddJyToJwq() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveJyFromJwq() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisableJwqyJygx() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnableJwqyJygx() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindJwqIdByAppuserId() {
		List<String> list=jwqyJygxService.findJwqIdByAppuserId("6c6a3b22-06d7-479f-abe9-de8b384a99ce", "1");
	System.out.println(list);
	}

}
