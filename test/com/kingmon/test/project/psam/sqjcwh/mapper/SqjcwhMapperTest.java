package com.kingmon.test.project.psam.sqjcwh.mapper;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kingmon.project.psam.sqjcwh.service.ISqjcwhService;
import com.kingmon.project.psam.xzjd.service.IXzjdService;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;

public class SqjcwhMapperTest {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);

	@Autowired
	private ISqjcwhService sqjcwhService;

	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByPrimaryKeyL() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testRevokeSqjcwh() {
		fail("Not yet implemented");
	}

	@Test
	public void testActivateSqjcwh() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryCount() {
		fail("Not yet implemented");
	}
	@Test
	public void testSelectDetailByPrimaryKey(){
		Map map=sqjcwhService.selectDetailByPrimaryKey("b397ce53-f033-44b5-8cfc-9838c5a33ca4");
		fail("Not yet implemented");
		
	}

}
