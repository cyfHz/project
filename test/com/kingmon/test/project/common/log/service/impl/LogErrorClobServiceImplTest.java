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
import com.kingmon.project.common.log.model.LogErrorClob;
import com.kingmon.project.common.log.service.ILogErrorClobService;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })

public class LogErrorClobServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);
	@Autowired
	ILogErrorClobService clobService;
	
	@Test
	public void testAddLogErrorClob() {
		LogErrorClob clob = new LogErrorClob();
		clob.setId(UUIDUtil.uuid());
		clob.setCreatetime(new Date());
		clob.setMessage("测试");
		//clobService.addLogErrorClob(clob);
	}

	@Test
	public void testLoadLogErrorClobDataSet() {
		Map<String, String> params=new HashMap<String, String>();
		PaginationUtil.initPageAndSort(params);
		DataSet data=clobService.loadLogErrorClobDataSet(params);
		logger.info(data.toString());
	}

}
