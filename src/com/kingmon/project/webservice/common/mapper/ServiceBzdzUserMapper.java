package com.kingmon.project.webservice.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.webservice.common.model.ServiceBzdzUser;
import com.kingmon.project.webservice.common.model.ServiceBzdzUserjs;
@KMapper
public interface ServiceBzdzUserMapper {

    int insertSelective(ServiceBzdzUser record);

    ServiceBzdzUser selectByPrimaryKey(String systemid);

    int updateByPrimaryKeySelective(ServiceBzdzUser record);

    ServiceBzdzUser selectByNameAndPwd(@Param("username")String username,@Param("userpassword")String userpassword);


    /**
     * 根据主键id查询信息
     * @param systemid
     * @return
     */
//    ServiceBzdzUser selectByPrimaryKey(String systemid);

    /**
     * 加载全部信息
     * @param params
     * @return
     */
    List<Map<String, Object>> userList(Map<String, String> params);
    
    Long userListCount(Map<String, String> params);
    
    /**
     * 添加用户信息
     * @param serviceBzdzUser
     * @return
     */
//    public void addBzdzUser(ServiceBzdzUser serviceBzdzUser);
    
    /**
     * 修改用户信息
     * @param serviceBzdzUser
     * @return
     */
//    public void saveBzdzUser(ServiceBzdzUser serviceBzdzUser);
    
    /**
     * 删除用户信息
     * @param serviceBzdzUser
     */
    public void delBzdzUser(ServiceBzdzUser serviceBzdzUser);
    
    public void reviewUser(Map<String, Object> params);
    
    /**
	 * 解锁
	 * @param params
	 */
	public void unlockedUser(Map<String, Object> params);
	
	/**
	 * 锁定
	 * @param params
	 */
	public void lockedUser(Map<String, Object> params);
	
	/**
	 * WEB接口 登录 存储tokenId
	 * @param systemid
	 * @param tokenId
	 */
	public int updateTokenId(@Param("systemid")String systemid,@Param("tokenId")String tokenId);
    /**
     * 根据用户名和Ip查询用户是否存在
     * @param bdip
     * @param username
     * @return
     */
	List<ServiceBzdzUser> selectByUserNameAndIp(String bdip, String username);
}