package com.kingmon.test.project.psam.fw.service.impl;


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
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.base.dao.JdbcBaseDao;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.sy.service.ISyFwjbxxService;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.test.project.auth.organization.service.impl.OrganizationServiceImplTest;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class SYFwjbxxServiceImplTest {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImplTest.class);

	@Autowired
	private ISyFwjbxxService fwjbxxService;
	@Autowired
	private MlphMapper mlphMapper;
	@Autowired
	private XzqhMapper xzqhMapper;
	
	@Test
	public void testLoadSYFwjbxxDataSet() {
		 Map<String, String> params=new HashMap<String, String>();
		 PaginationUtil.initPageAndSort(params);
		DataSet data=fwjbxxService.loadSYFwjbxxDataSet(params);
		logger.info(data.toString());
	}
	
	@Test 
	@Transactional(readOnly=true)
	public void test() throws Throwable{
		Map<String,String> params = new HashMap<String, String>();
		params.put("pagestart", 1+"");
		params.put("pageend", 2+"");
		params.put("sort", "YWLSH");
		params.put("order", "ASC");
		List<Map<String, Object>> mlphList = mlphMapper.mlphList(params);
		System.out.println(mlphList);
	}
	
	@Test 
	public void test2() throws Throwable{
	Map<String,String> params = new HashMap<String, String>();
	params.put("pagestart", 1+"");
	params.put("pageend", 2+"");
	params.put("sort", "ywlsh");
	params.put("order", "ASC");
//	List<Map<String, Object>> mlphList = mlphMapper.mlphList(params);
	List<?> mlphList=MyBatisDao.batisSelectList("com.kingmon.project.psam.mlph.dao.MlphMapper.mlphList", params);
//	DataSet sd = mlphService.mlphList(params);
	System.out.println(mlphList);
	
}
	@Autowired
	protected JdbcBaseDao jdbcBaseDao;
	@Autowired
	protected MlphService mlphService;
	@Autowired
	protected  com.kingmon.base.dao.MyBatisDao MyBatisDao;
	@Test 
	public void test3() throws Throwable{
		Map<String,String> params = new HashMap<String, String>();
		params.put("pagestart", 1+"");
		params.put("pageend", 2+"");
		params.put("sort", "DZBM");
		params.put("order", "ASC");
		
		//jdbcBaseDao.jdbcQueryMapList(querySQL, paramObject);
		ParamObject po=ParamObject.new_P_C();
		po.setOrder("ASC");
		po.setSort("DZBM");
		po.setPage(1);
		po.setRows(10);
		
		String sqssl="  SELECT YWLSH, DZBM, DZYSLXDM, DZMC, SSXQDM, QHNXXDZ, SSJLXXQ_JLXXQMC, SSJLXXQ_DZBM, SSJZW_DZBM, MLPH, LSMLPBS, BLDW_GAJGJGDM, BLDW_GAJGMC, BLR_XM, BLSJ, SJGSDWDM, SJGSDWMC, MOVESIGN, DJR, DJDW, DJSJ, XGR, XGDW, GXSJ, SQRID, CHILDCOUNT, ZXDHZB, ZXDZZB, "
				+ "DELTAG, DELUSER, DELTIME, SSZDYJXZQY_DZBM, JZWMC, JWQBH, JWQMC, FROMBY, SBID, GPSX, GPSY, MLPHLXID,"
				+ " MLPHLXMC, DRSJ, SSPCS, SSFJ, SSSJ,SPZT,SHPZDM,IS_INDEXED,CREATE_TIME @FROM DZ_MLPH"
				+ " WHERE 1=1  order by ywlsh asc  ";
		//List<?> list=jdbcBaseDao.jdbcQueryMapList(sqssl,po);
		DataSet ds=jdbcBaseDao.jdbcLoadDataSet(sqssl, po);
		
//		List<Map<String, Object>> list = xzqhMapper.xzqhList(params);
		System.out.println(ds);
	
	}
}
