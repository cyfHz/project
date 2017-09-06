package com.kingmon.test.project.auth.organization.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class OrganizationServiceImplTest {
private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);
	
	@Autowired
	private IOrganizationService organizationService;

	@Test
	public void testFindOrganizationByUserId() {
		Organization o=organizationService.findOrgByUserId("6c6a3b22-06d7-479f-abe9-de8b384a99ce");
		logger.info(o.toString());
	}

	@Test
	public void testLoadChildOrgans() {
		DataSet ds=organizationService.loadChildOrgans("371322000000");
		logger.info(ds.toString());
	}

}
