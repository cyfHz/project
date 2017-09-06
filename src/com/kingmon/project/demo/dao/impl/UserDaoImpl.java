package com.kingmon.project.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.project.demo.dao.IUserDao;
import com.kingmon.project.demo.mapper.UserMapper;
import com.kingmon.project.demo.model.User;

@Repository
public class UserDaoImpl implements IUserDao{

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
	 
}
