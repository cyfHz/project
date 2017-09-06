package com.kingmon.test.base.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.demo.model.User;
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class TestJdbcDao {
	private static final Logger logger = LoggerFactory.getLogger(TestJdbcDao.class);

	@Inject
	private com.kingmon.base.dao.JdbcBaseDao jdbcBaseDao;
	@Test
	public void test1_loadDataSet() {
		ParamObject po=ParamObject.new_P_C();
		if(true){
			po.addSQLParam("userId", "1");
		}
		po.setPage(1);
		po.setRows(20);
		DataSet list=jdbcBaseDao.jdbcLoadDataSet("select * @from t_user where userId=:userId ", po);
		logger.info(list.toString());
	}
	@Test
	public void test2_findMapList() {
		ParamObject po=new ParamObject();
		if(true){
			po.addSQLParam("userId", "1");
		}
		String sqlX="select t.xzjddm  from DZ_XZJD t where t.sjxzqy_dzbm=:sjxzqy_dzbm ";
		List<?> listMap=(List<?>) jdbcBaseDao.jdbcQueryMapList(sqlX, ParamObject.new_NP_NC().addSQLParam("sjxzqy_dzbm", "371081"));
		
		//List<?> list=jdbcBaseDao.jdbcQueryMapList("select * from t_user where userId=:userId ", po);
		System.out.println(listMap); 
	}
	@Test
	public void test3_findObject() {
		Object list=jdbcBaseDao.jdbcQueryObject("select * from t_user where userId=:userId ", 
				new ParamObject().addSQLParam("userId", "001"),
				User.class);
		System.out.println(list); 
	}
	@Test
	public void test4_insert() {
		String sql="INSERT INTO t_user(userid,username,idnum,sex) VALUES(:userid,:username,:idnum,:sex)";
		ParamObject po=new ParamObject();
		po.addSQLParam("username", "t1")
		  .addSQLParam("sex", "3")
		  .addSQLParam("idnum", "2")
		  .addSQLParam("userid", "001");
		int count=jdbcBaseDao.jdbcInsert(sql, po);
		System.out.println(count); 
	}
	@Test
	public void test5_batchInsert() {
		String sql="INSERT INTO t_user(userid,username,idnum,sex) VALUES(:userid,:username,:idnum,:sex)";
		int ba=10000*2;
		ParamObject[] mapArray = new ParamObject[ba];  
		for(int i=0;i<ba;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("username", "t1")
			  .addSQLParam("sex", "3")
			  .addSQLParam("idnum", "2")
			  .addSQLParam("userid", "001");
			mapArray[i]=(po);
		}
		System.out.println("kaishi -------- "); 
		long a=System.currentTimeMillis();
		int[] count=jdbcBaseDao.jdbcBatchInsert(sql, mapArray);
		long b=System.currentTimeMillis();
		System.out.println("耗时： "+(b-a)+" "+count); 
	}
	@Test
	public void test6_update() {
		//update stu set s_name=:sname,s_sex=:ssex,s_brith=:sbrith where s_id=:sid
		String sql="update t_user set userid=:userid,username=:username,idnum=:idnum,sex=:sex where 1=1";
		ParamObject po=new ParamObject();
		po.addSQLParam("username", "t1XXA")
			.addSQLParam("sex", "x")
			.addSQLParam("idnum", "2XX")
			.addSQLParam("userid", "001XX");
		int count=jdbcBaseDao.jdbcUpdate(sql, po);
		System.out.println(count); 
	}
//	@Test
//	public void test7_batchUpdate() {
//		DataSet list=jdbcBaseDao.loadDataSet("select * from t_user where userId=:userId ", new ParamObject().addParam("userId", "1"));
//		System.out.println(list); 
//	}
	@Test
	public void test8_delete() {
		int list=jdbcBaseDao.jdbcDelete("delete from t_user where userId=:userId ", 
				new ParamObject().addSQLParam("userId", "001"));
		System.out.println(list); 
	}
	@Test
	public void test9_batchDelete() {
		int ba=10000*2;
		ParamObject[] mapArray = new ParamObject[ba];  
		for(int i=0;i<ba;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("userIds", "001");
			mapArray[i]=(po);
		}
		int[] list=jdbcBaseDao.jdbcBatchDelete("delete from t_user where userId in(:userIds)", mapArray);
		System.out.println(list); 
	}
}
