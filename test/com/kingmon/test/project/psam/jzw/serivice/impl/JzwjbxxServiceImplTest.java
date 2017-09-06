package com.kingmon.test.project.psam.jzw.serivice.impl;

import static org.junit.Assert.*;

import java.util.HashMap;
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

import com.google.common.collect.Maps;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class JzwjbxxServiceImplTest {
private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);
	@Autowired
	private XzqhMapper  xzqhMapper;
	@Autowired
	private IJzwjbxxService jzwjbxxService;
	@Test
	public void testLoadJzwjbxxDataSet() {
		 Map<String, String> params=new HashMap<String, String>();
		 PaginationUtil.initPageAndSort(params);
		DataSet data=jzwjbxxService.loadJzwjbxxDataSet(params);
		logger.info(data.toString());
	}
	@Test
	public void testx() {
		String sd=xzqhMapper.selectXzqhdmById("20a7bcc0-ca61-4269-b7ca-83242d216941");
		
		logger.info(sd.toString());
	}
}
