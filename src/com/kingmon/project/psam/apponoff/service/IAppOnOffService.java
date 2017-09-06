package com.kingmon.project.psam.apponoff.service;

import com.kingmon.project.psam.apponoff.model.AppOnOff;

public interface IAppOnOffService {

	AppOnOff findStatusByAreaCode(String areaCode);


	/**
	 * 移动端登录时 验证IMEI号
	 */
	public Long countImei(String imei,String sfzh);
}
