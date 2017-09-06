package com.kingmon.test.project.common.dictionary.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.project.auth.role.mapper.RoleMapper;
import com.kingmon.project.common.dictionary.service.IDictionaryService;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class DictionaryServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImplTest.class);

	@Autowired
	private IDictionaryService dictionaryService;
	@Test
	public void testGetDictByCode() {
		String sdasd=dictionaryService.getDictByCode("DWLB");
		logger.info(sdasd);
	}

}
