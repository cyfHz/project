package com.kingmon.project.auth.rule.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kingmon.base.service.BaseService;
import com.kingmon.project.auth.rule.mapper.DataruleMapper;
import com.kingmon.project.auth.rule.service.IDataruleService;

@Service
public class DataruleServiceImpl extends BaseService implements IDataruleService {

	@Autowired
	private DataruleMapper dataruleMapper;

	@Cacheable(value="authTmpResultCache",key="'DataruleServiceImpl_selectVlausByRuleCodeAndUserId'+#appuser_id")
	@SuppressWarnings("unchecked")
	@Override
	public List<String> loadValuesByRuleCodeAndUserId(String rule_code,String appuser_id) {
		if (!StringUtils.hasText(appuser_id)) {
			return Collections.emptyList();
		}
		if (!StringUtils.hasText(rule_code)) {
			return Collections.emptyList();
		}
		List<String> list = dataruleMapper.selectVlausByRuleCodeAndUserId(rule_code,appuser_id);
		return (List<String>) (list == null ? Collections.emptyList() : list);
	}
	
//	@Cacheable(value="authTmpResultCache",key="'DataruleServiceImpl_selectVlausByRuleCodeAndRoleId'+#APPUSER_ID")
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<String> loadValuesByRuleCodeAndRoleId(String rule_code,String role_id) {
//		if (!StringUtils.hasText(role_id)) {
//			return Collections.emptyList();
//		}
//		if (!StringUtils.hasText(rule_code)) {
//			return Collections.emptyList();
//		}
//		List<String> list = dataruleMapper.selectVlausByRuleCodeAndRoleId(rule_code,role_id);
//		return (List<String>) (list == null ? Collections.emptyList() : list);
//	}
}
