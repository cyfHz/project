package com.kingmon.project.demo.service;

import java.util.List;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.demo.model.User;

public interface IUserService {
	
	public User findByID(String id);
	
	public List<User> queryList(String username);
	 
    public List<User> getAll();
                                                                                                                                                                                                                                   
    public User getUserById(String id);
                                                                                                                                                                                                                                   
    public void addUser(User user);
                                                                                                                                                                                                                                   
    public void deleteUser(String id);
    
    public void updateUser(User user);
    
    //-----------------------\
	public User findByID_batis(String id);
	
	public void batchDeleteUser( List<String>  ids);
	
	
	public DataSet loadUserDataSet(ParamObject paramObject);
}
