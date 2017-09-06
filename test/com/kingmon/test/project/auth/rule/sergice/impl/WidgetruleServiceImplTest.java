package com.kingmon.test.project.auth.rule.sergice.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.base.util.SubSpringBeanUtil;
import com.kingmon.project.auth.rule.model.Widgetrule;
import com.kingmon.project.auth.rule.service.IWidgetruleService;
import com.kingmon.project.auth.rule.service.impl.WidgetruleServiceImpl;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })

public class WidgetruleServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(WidgetruleServiceImplTest.class);

	
	@Autowired
	private IWidgetruleService widgetruleService;
	@Test
	public void testSelectWidgetruleListByByUserId() {
		List<Widgetrule> rules=widgetruleService.loadWidgetRuleListByUserId("6c6a3b22-06d7-479f-abe9-de8b384a99ce");
		logger.info(""+rules);
	}
	@Test
	public void testSelectWidgetruleCodeListByByUserId() {
		List<String> rules=widgetruleService.loadWidgetRuleCodeListByUserId("6c6a3b22-06d7-479f-abe9-de8b384a99ce");
		logger.info(""+rules);
	}
	
	@Test
	public void testScanWidgetrule() {
		widgetruleService.scanWidgetrule("com.kingmon");
	}
	
	public static void main(String[] args){
		SpringBeanFacUtil.getBean(WidgetruleServiceImpl.class).scanWidgetrule("com.kingmon");
	}
}
