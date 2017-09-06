package com.kingmon.test.base.dao;

import java.util.List;
import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.kingmon.project.demo.model.User;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class TestMyBatisXMLDao {
	private static final Logger logger = LoggerFactory.getLogger(TestMyBatisXMLDao.class);
	//--------------------------------------------------------------------
	@Inject
	private com.kingmon.base.dao.MyBatisDao myBatisDao;
	@Test
	public void test5_batisSelectListA() {
		List<?> list=myBatisDao.batisSelectList("com.kingmon.project.demo.mapper.UserMapper.queryList", "kingmongXXA");
		logger.info(""+list);
	}
	@Test
	public void test6_selectUserById() {
		User u=(User) myBatisDao.batisSelectOne("com.kingmon.project.demo.mapper.UserMapper.selectUserById", "cc3X");
		logger.info(""+u);
	}

	
	
	
}
