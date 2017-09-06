package com.kingmon.project.psam.appVersonUser.service;
import com.kingmon.base.data.DataSet;
import com.kingmon.project.psam.appVersonUser.model.AppVersonUser;
public interface IAppVersonUserService {
  /**
   * 根据用户Id加载APP版本信息
   * @param appuser_id
   * @return
   */
	AppVersonUser selectAppVersion(String appuser_id);
    /**
     * 添加用户记录信息
     * @param appVersonUser
     */
   void insertAppUser(AppVersonUser appVersonUser);
   /**
    * 更新用户记录信息
    * @param appVersonUser
    */
	void updateAppUser(AppVersonUser appVersonUser);


  
	
	
}
