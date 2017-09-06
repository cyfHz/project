package com.kingmon.project.psam.appVersonUser.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.KAssert;
import com.kingmon.project.psam.appMapUrl.mapper.mapurlMapper;
import com.kingmon.project.psam.appVersion.service.IAppVersionService;
import com.kingmon.project.psam.appVersonUser.mapper.AppVersonUserMapper;
import com.kingmon.project.psam.appVersonUser.model.AppVersonUser;
import com.kingmon.project.psam.appVersonUser.service.IAppVersonUserService;
@Service
public class IAppVersonUserServiceImpl extends BaseService implements IAppVersonUserService {
	@Autowired
	private AppVersonUserMapper  appVersonUserMapper;
	@Override
	public AppVersonUser selectAppVersion(String appuser_id) {
		// TODO Auto-generated method stub
		KAssert.hasText(appuser_id, "用户信息不能为空");
		AppVersonUser appversionuser=appVersonUserMapper.selectAppVersionUser(appuser_id);
		return appversionuser;
	}
	@Override
	public void insertAppUser(AppVersonUser appVersonUser) {
		// TODO Auto-generated method stub
		KAssert.hasText(appVersonUser.getUserid(), "用户信息不能为空");
		KAssert.hasText(appVersonUser.getIsvalid(), "是否为最新版本不能为空");
		KAssert.hasText(appVersonUser.getVersoninfo(), "版本信息不能为空");
		appVersonUserMapper.insertSelective(appVersonUser);
		
	}
	@Override
	public void updateAppUser(AppVersonUser appVersonUser) {
		// TODO Auto-generated method stub
		KAssert.hasText(appVersonUser.getUserid(), "用户信息不能为空");
		KAssert.hasText(appVersonUser.getIsvalid(), "是否为最新版本不能为空");
		KAssert.hasText(appVersonUser.getVersoninfo(), "版本信息不能为空");
		appVersonUserMapper.updatAppUserById(appVersonUser);
	}

	
}
