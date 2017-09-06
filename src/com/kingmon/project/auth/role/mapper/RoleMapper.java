package com.kingmon.project.auth.role.mapper;

import java.util.List;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.auth.role.model.Role;
@KMapper
public interface RoleMapper {
//    int deleteByPrimaryKey(String ROLE_ID);
//
//    int insert(Role record);
//
//    int insertSelective(Role record);
//
//    Role selectByPrimaryKey(String ROLE_ID);
//
//    int updateByPrimaryKeySelective(Role record);
//
//    int updateByPrimaryKey(Role record);
    
    //------------
   /**
    * R.ROLE_ID, R.ROLE_NAME,R.ROLE_CODE, R.ORGANID <br>
    * @param APPUSER_ID
    * @return
    */
    List<Role> selectRoleListByByUserId(String APPUSER_ID );
}