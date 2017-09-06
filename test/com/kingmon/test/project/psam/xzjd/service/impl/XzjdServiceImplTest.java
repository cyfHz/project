package com.kingmon.test.project.psam.xzjd.service.impl;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.project.psam.xzjd.service.IXzjdService;
import com.kingmon.project.psam.xzqhdw.service.IXzqhDwService;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class XzjdServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);

	@Autowired
	private IXzjdService xzjdService;
	
	
	@Test
	public void testAddXzjd() {
		
		//xzjdService.addXzjd(record);
		
		fail("Not yet implemented");
		logger.info("");
	}

	@Test
	public void testGetXzjdById() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateXzjd() {
		fail("Not yet implemented");
	}

	@Test
	public void testRevokeXzjd() {
		fail("Not yet implemented");
	}

	@Test
	public void testActivateXzjd() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadXzjdDataSet() {
		fail("Not yet implemented");
	}
	@Test
	public  void testSelectDetailByPrimaryKey(){
		Map map=xzjdService.selectDetailByPrimaryKey("f7769b17-17da-43b0-8d6b-26509409f373");
		logger.info(""+map);
	}

}
