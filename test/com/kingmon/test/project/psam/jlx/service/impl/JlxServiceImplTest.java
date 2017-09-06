package com.kingmon.test.project.psam.jlx.service.impl;


import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.project.psam.jlx.model.Jlx;
import com.kingmon.project.psam.jlx.service.IJlxService;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class JlxServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);

	@Autowired
	private IJlxService iJlxService;
	@Test
	public void testLoadJlxBMMCDataSet() {
		iJlxService.loadJlxBMMCDataSet(new HashMap<String, String>());
	}
	
	@Test
	public void testGetJlxById() {
		Jlx jlx=iJlxService.getJlxById("81b8b9e6-3134-42b9-9f0d-bf644de62ada");
		logger.info(jlx.toString());
	}
	@Test
	public void testGetSimpleJlxById() {
		Jlx jlx=iJlxService.getSimpleJlxById("81b8b9e6-3134-42b9-9f0d-bf644de62ada");
		logger.info(jlx.toString());
	}
	@Test
	public void testgetJlxSszdyjxzqyType() {
		String jlx=iJlxService.getJlxSszdyjxzqyType("17cs73394-a8b8-444d-96a1-5249852cdccd");
		logger.info(jlx);
	}
}
