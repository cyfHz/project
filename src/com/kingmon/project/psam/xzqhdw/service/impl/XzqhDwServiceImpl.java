package com.kingmon.project.psam.xzqhdw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.service.BaseService;
import com.kingmon.project.psam.xzqhdw.mapper.XzqhDwMapper;
import com.kingmon.project.psam.xzqhdw.service.IXzqhDwService;
@Service
public class XzqhDwServiceImpl extends BaseService implements IXzqhDwService {

	@Autowired
	private XzqhDwMapper xzqhDwMapper;
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Cacheable(value="defaultQueryResultCache",key="'XzqhDwServiceImpl_selectXzqhdmByDwid'+#dwid")
	@Override
	public String selectXzqhdmByDwid(String dwid) {
		if(StringUtils.hasText(dwid)){
			return xzqhDwMapper.selectXzqhdmByDwid(dwid);
		}
		return null;
	}

}
