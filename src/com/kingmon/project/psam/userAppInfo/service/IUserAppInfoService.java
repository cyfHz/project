package com.kingmon.project.psam.userAppInfo.service;
import java.util.Map;

import com.kingmon.project.psam.appVersonUser.model.AppVersonUser;
public interface IUserAppInfoService {
   /**
    * 添加用户下载APP记录信息
    * @param userAppInfo
    */
	void saveUserAppINfo(Map userAppInfo);

}
