package com.kingmon.project.auth.rule.service;

import java.util.List;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.auth.rule.model.Widgetrule;

public interface IWidgetruleService {

	public List<Widgetrule> loadWidgetRuleListByUserId(String appuser_id);
	
	public List<String> loadWidgetRuleCodeListByUserId(String appuser_id);
	
//	public List<String> loadWidgetRuleValueListByUserId(String appuser_id);
	
	
	public void scanWidgetrule(String basepackageName);
	
	public DataSet loadWidgetRuleDataset(ParamObject po);
	
}
