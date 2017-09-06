package com.kingmon.project.psam.appVersonUser.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.appVersonUser.model.AppVersonUser;
@KMapper
public interface AppVersonUserMapper {
    int insert(AppVersonUser record);

    int insertSelective(AppVersonUser record);
   /**
    * 根据用户Id查询用户信息
    * @param appuser_id
    * @return
    */
	AppVersonUser selectAppVersionUser(String appuser_id);
    /**
     * 跟新用户记录信息
     * @param appVersonUser
     */
    void updatAppUserById(AppVersonUser appVersonUser);
}