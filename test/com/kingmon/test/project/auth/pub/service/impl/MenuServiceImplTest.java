package com.kingmon.test.project.auth.pub.service.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.kingmon.project.auth.pub.service.IMenuService;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class MenuServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImplTest.class);

	
	@Autowired
	private IMenuService menuService;
	
	
	@Test
	public void testLoadMenu() {
		menuService.loadMenu();
	}

}
