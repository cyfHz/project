package com.kingmon.test.project.auth.rule.sergice.impl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.kingmon.project.auth.rule.service.IDataruleService;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })

public class DataruleServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(DataruleServiceImplTest.class);

	@Autowired
	private IDataruleService dataruleService;
	
//	@Test
//	public void testselectVlausByRuleCodeAndRoleId() {
//		List<String> list=dataruleService.loadValuesByRuleCodeAndRoleId("YHJB","5e677cda-35ff-4bf7-8362-a8a4c18f57c2");
//		logger.info(list.toString());
//	}

	@Test
	public void testselectVlausByRuleCodeAndUserId() {
		List<String> list=dataruleService.loadValuesByRuleCodeAndUserId("YHJB","6c6a3b22-06d7-479f-abe9-de8b384a99ce");
		logger.info(list.toString());
	}

}
