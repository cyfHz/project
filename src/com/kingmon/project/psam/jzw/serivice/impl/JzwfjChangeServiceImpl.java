package com.kingmon.project.psam.jzw.serivice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.base.service.BaseService;
import com.kingmon.project.psam.jzw.mapper.JzwfjChangeMapper;
import com.kingmon.project.psam.jzw.model.JzwfjChange;
import com.kingmon.project.psam.jzw.serivice.IJzwfjChangeService;

@Service
public class JzwfjChangeServiceImpl extends BaseService implements IJzwfjChangeService{

	@Autowired
	private JzwfjChangeMapper jzwfjChangeMapper;
	
	@Transactional(rollbackFor = Exception.class)
	public void addChange(JzwfjChange record) {
		jzwfjChangeMapper.insertSelective(record);
	}
	
}
