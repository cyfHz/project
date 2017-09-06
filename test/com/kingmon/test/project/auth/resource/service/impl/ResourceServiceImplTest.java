package com.kingmon.test.project.auth.resource.service.impl;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.kingmon.project.auth.resource.model.Resource;
import com.kingmon.project.auth.resource.service.IResourceService;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class ResourceServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImplTest.class);

	@Autowired
	private IResourceService resourceService;
	@Test
	public void testFindResourceListByByUserId() {
	List<Resource> resources=resourceService.findResListByUserId("6c6a3b22-06d7-479f-abe9-de8b384a99ce");
		logger.info(resources.toString());
	}

	@Test
	public void testFindResourceListByByRoleId() {
//		List<Resource> resources=resourceService.findResourceListByByRoleId("role_test_admin_01");
//		logger.info(resources.toString());
	}

}
