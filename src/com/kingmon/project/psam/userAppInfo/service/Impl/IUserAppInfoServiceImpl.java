package com.kingmon.project.psam.userAppInfo.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.DBTimeUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.project.psam.appMapUrl.mapper.mapurlMapper;
import com.kingmon.project.psam.appVersion.service.IAppVersionService;
import com.kingmon.project.psam.appVersonUser.mapper.AppVersonUserMapper;
import com.kingmon.project.psam.appVersonUser.model.AppVersonUser;
import com.kingmon.project.psam.appVersonUser.service.IAppVersonUserService;
import com.kingmon.project.psam.userAppInfo.mapper.UserAppInfoMapper;
import com.kingmon.project.psam.userAppInfo.model.UserAppInfo;
import com.kingmon.project.psam.userAppInfo.service.IUserAppInfoService;
@Service
public class IUserAppInfoServiceImpl extends BaseService implements IUserAppInfoService {
	@Autowired
	private UserAppInfoMapper  userAppInfoMapper;

	@Override
	public void saveUserAppINfo(Map userAppInfo) {
		// TODO Auto-generated method stub
		String userId=(String) userAppInfo.get("userId");
		KAssert.hasText(userId, "用户信息不能为空");
		String versonIfo=(String) userAppInfo.get("bbCode");
		KAssert.hasText(versonIfo, "版本信息不能为空");
		UserAppInfo userinfo=new UserAppInfo();
		userinfo.setId(UUIDUtil.uuid());
		userinfo.setUserid(userId);
		userinfo.setVersoninfo(versonIfo);
		userinfo.setUpdatetime(DBTimeUtil.getDBTime());
		userAppInfoMapper.insertSelective(userinfo);
	}

	
}
