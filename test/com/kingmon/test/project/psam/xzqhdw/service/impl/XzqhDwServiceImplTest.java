package com.kingmon.test.project.psam.xzqhdw.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.project.psam.xzqhdw.service.IXzqhDwService;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })

public class XzqhDwServiceImplTest {
	@Autowired
	private IXzqhDwService xzqhDwService;
	
	@Test
	public void testSelectXzqhdmByDwid() {
		xzqhDwService.selectXzqhdmByDwid("");
	}

}
