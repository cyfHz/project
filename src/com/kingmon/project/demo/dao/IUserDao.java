package com.kingmon.project.demo.dao;

import java.util.List;

import com.kingmon.project.demo.model.User;

public interface IUserDao {
	
	public User findByID(String id);
	
	public List<User> queryList(String username);
	 
    public List<User> getAll();
                                                                                                                                                                                                                                   
    public User getUserById(String id);
                                                                                                                                                                                                                                   
                                                                                                                                                                                                                                   
    public void addUser(User user);
                                                                                                                                                                                                                                   
    public void deleteUser(String id);
                                                                                                                                                                                                                                   
    public void updateUser(User user);
}
