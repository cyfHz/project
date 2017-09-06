package com.kingmon.test.project.common.log.service.impl;

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
import com.kingmon.project.common.log.service.ILogService;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })

public class LogServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);
	@Autowired
	private ILogService LogService;

	@Test
	public void testAddLog() {
		Log log = new Log();
		log.setId(UUIDUtil.uuid());
		log.setArea_code("123bb");
		log.setLog_content("456bb");
		log.setLog_object("789bb");
		log.setUser_name("xxxbb");
		log.setLog_type("操作日志");
		log.setOpratetime(new Date());
		//LogService.addLog(log);
	}

	@Test
	public void testLoadLogDataSet() {
		Map<String, String> params=new HashMap<String, String>();
		PaginationUtil.initPageAndSort(params);
		DataSet data=LogService.loadLogDataSet(params);
		logger.info(data.toString());
	}

}
