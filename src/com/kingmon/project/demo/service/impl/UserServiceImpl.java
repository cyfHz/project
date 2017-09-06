package com.kingmon.project.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.demo.mapper.UserMapper;
import com.kingmon.project.demo.model.User;
import com.kingmon.project.demo.service.IUserService;

@Service
public class UserServiceImpl extends BaseService implements IUserService{

	@Autowired
	private UserMapper userMapper;
	
	
	 @Cacheable(value="defaultQueryResultCache",key="'UserService_findByID_'+#id")
	 public User findByID(String id){
	    return userMapper.getUserById(id);
	 }

	@Override
	public List<User> queryList(String username) {
		return userMapper.queryList(username);
	}

	@Override
	public List<User> getAll() {
		return userMapper.getAll();
	}

	@Override
	public User getUserById(String id) {
		return userMapper.getUserById(id);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addUser(User user) {
		
		KAssert.hasText(user.getUsername(),"用户名不能为空");

		//查询方式1
		User u2=(User)jdbcBaseDao.jdbcQueryObject("select u.userId from t_user u where u.username=:username ", 
				 ParamObject.new_NP_NC().addSQLParam("username",user.getUsername()), User.class);
		
		KAssert.isNull(u2, "该用户名用户已经存在，请重新填写");
		
//--------------------------------------------------------------
		//查询方式2
		List<?> userx=userMapper.queryList(user.getUsername());
		
		//判断方式1
		KAssert.empty(userx, "该用户名用户已经存在，请重新填写");
		
		//判断方式2
		if(userx!=null&&!userx.isEmpty()){
			//AlertSLEUtil 用来声明ServiceLogicalException 异常
			AlertSLEUtil.Error("该用户名用户已经存在，请重新填写");
		}
		//判断方式3
		if (userx != null && !userx.isEmpty()) {
			throw new ServiceLogicalException("该用户名用户已经存在，请重新填写");
		}
		user.setUserId(UUIDUtil.uuid());
		userMapper.addUser(user);
		
	}
	
	public void addUserDemo(User user) {
		KAssert.hasText(user.getUsername(),"用户名不能为空");
		//查询方式1
		User u2=(User)jdbcBaseDao.jdbcQueryObject("select u.userId from t_user u where u.username=:username ", 
				 ParamObject.new_NP_NC().addSQLParam("username",user.getUsername()), User.class);
		
		KAssert.isNull(u2, "该用户名用户已经存在，请重新填写");
		user.setUserId(UUIDUtil.uuid());
		userMapper.addUser(user);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteUser(String id) {
		userMapper.deleteUser(id);
		
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
		
	}

	@Override
	public User findByID_batis(String id) {
		User u=(User) myBatisDao.batisSelectOne("com.kingmon.project.demo.mapper.UserMapper.getUserById",id);
		return u;
	}


	@Override
	public void batchDeleteUser(List<String> ids) {
		userMapper.batchDelete(ids);
	}

	@Override
	public DataSet loadUserDataSet(ParamObject po) {
		SessionUser user=SecurityUtils.getSessionUser();
		//注意@from
		StringBuffer sql=new StringBuffer(" select username ,idnum,sex,userId @from t_user where 1=1  ");
		
		String username=(String) po.getWebParam("username");
		if(!SubApStrUtil.isEmptyAfterTrimE(username)){
			sql.append(" and username=:username ");
			po.addSQLParam("username", username);
		}
		String userId=(String) po.getWebParam("userId");
		if(!SubApStrUtil.isEmptyAfterTrimE(username)){
			sql.append(" and userId=:userId ");
			po.addSQLParam("userId", userId);
		}
		if(po.hasOrder()){
//			private String order;//direction 
//			private String sort;//sort field
			sql.append(" order by ").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	 
}
