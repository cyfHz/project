package com.kingmon.test.project.auth.organizationuser.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class OrganizationUserServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationUserServiceImplTest.class);
	
	@Autowired
	private IOrganizationUserService organizationUserService;
	
	@Test
	public void testFindOrganizationUserByUserId() {
		OrganizationUser user=organizationUserService.findByUserId("6c6a3b22-06d7-479f-abe9-de8b384a99ce");
		logger.info(user.toString());
	}
	
	@Test
	public void testFindOrganizationUserBySfzh() {
		OrganizationUser user=organizationUserService.findBySfzh("371323198912074911");
		logger.info(user.toString());
	}
	
	@Test
	public void testFindOrganizationUserIdByUsernameAndPws() {
		OrganizationUser user=organizationUserService.findByLoginnameAndPws("admin","admin");
		logger.info(user.toString());
	}

}
