package com.kingmon.test.project.common.log.service.impl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.project.common.log.model.LogLogin;
import com.kingmon.project.common.log.service.ILogLoginService;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })

public class LogLoginServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);
	@Autowired
	private ILogLoginService loginService;
	
	@Test
	public void testAddLogLogin() {
		LogLogin logLogin = new LogLogin();
		logLogin.setId(UUIDUtil.uuid());
		logLogin.setGps_x("1");
		logLogin.setGps_y("2");
		logLogin.setIp("3");
		logLogin.setLoginclient("4");
		logLogin.setLogintime(new Date());
		logLogin.setLogintime1("5");
		logLogin.setLogintype("6");
		logLogin.setLogouttime(new Date());
		logLogin.setLogouttime1("7");
		logLogin.setMemo("8");
		logLogin.setMovesign("9");
		logLogin.setOrgna_id("10");
		logLogin.setSbid("11");
		logLogin.setUser_id("12");
		logLogin.setUser_name("13");
		//logLogin.setUser_type();
		//loginService.addLogLogin(logLogin);
	}

	@Test
	public void testLoadLogLoginDataSet() {
		Map<String, String> params=new HashMap<String, String>();
		PaginationUtil.initPageAndSort(params);
		DataSet data=loginService.loadLogLoginDataSet(params);
		logger.info(data.toString());
	}

}
