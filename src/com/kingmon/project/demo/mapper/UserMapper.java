package com.kingmon.project.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.demo.model.User;

@KMapper
public interface UserMapper {
	
	@Select("select * from t_user where username=#{username}")
	public List<User> queryList(String username);
	 
    @Select("select * from t_user")
    public List<User> getAll();
                                                                                                                                                                                                                                   
    @Select("select * from t_user where userId=#{id}")
    public User getUserById(String id);
                                                                                                                                                                                                                                   
    @Insert("INSERT INTO t_user(username,idnum,sex,userId) VALUES(#{username},"
            + "#{idnum},#{sex},#{userId})")
    public void addUser(User user);
                                                                                                                                                                                                                                   
    @Delete("delete from t_user where userId=#{userId}")
    public void deleteUser(String id);
                                                                                                                                                                                                                                   
    @Update("update t_user set username=#{username},idnum=#{idnum}"
            + " where userId=#{userId}")
    public void updateUser(User user);
    
    //from UserMapper.xml
    public User selectUserById(String id);
  //from UserMapper.xml
    public void batchDelete( List<String>  ids);
    
    
}