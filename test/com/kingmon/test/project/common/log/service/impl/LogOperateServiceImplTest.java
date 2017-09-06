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
import com.kingmon.project.common.log.model.Log;
import com.kingmon.project.common.log.model.LogOperate;
import com.kingmon.project.common.log.service.ILogOperateService;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })

public class LogOperateServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);
	@Autowired
	private ILogOperateService operateService;
	
	@Test
	public void testAddLogOperate() {
		LogOperate logOperate = new LogOperate();
		logOperate.setId(UUIDUtil.uuid());
		logOperate.setLog_text("1");
		logOperate.setOperate_def("2");
		logOperate.setOperate_time(new Date());
		logOperate.setOperate_type("3");
		logOperate.setOrgna_id("4");
		logOperate.setTable_name("5");
		logOperate.setUser_id("6");
		logOperate.setUser_loginname("7");
		logOperate.setUser_name("8");
		
		//operateService.addLogOperate(logOperate);
	}

	@Test
	public void testLoadLogOperateDataSet() {
		Map<String, String> params=new HashMap<String, String>();
		PaginationUtil.initPageAndSort(params);
		DataSet data=operateService.loadLogOperateDataSet(params);
		logger.info(data.toString());
	}

}
