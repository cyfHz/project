package com.kingmon.project.psam.appMapUrl.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.KAssert;
import com.kingmon.project.psam.appMapUrl.mapper.mapurlMapper;
import com.kingmon.project.psam.appMapUrl.model.mapurl;
import com.kingmon.project.psam.appMapUrl.service.ImapUrlService;
@Service
public class mapUrlServiceImpl extends BaseService implements ImapUrlService {
	@Autowired
	private mapurlMapper  mapUrlMapper;
	@Override
	public List<mapurl> findMapUrlByAreaCode(String areaCode) {
		// TODO Auto-generated method stub
	//	KAssert.hasText(areaCode,"地域划分代码不能为空");
		List<mapurl> mapUrl=mapUrlMapper.selectMapUrlByCode(areaCode);
	//	KAssert.hasText(mapUrl,"地图信息不能为空");
		return mapUrl;
	}

	
	

}
