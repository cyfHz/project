package com.kingmon.project.auth.resource.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.auth.resource.model.Resource;
@KMapper
public interface ResourceMapper {
	
    int insertSelective(Resource record);
    
	public List<Resource> selectResourceListByByUserId(String userId);
	
	public List<String> selectResourceCodeListByByUserId(String userId);
	
	//tree
	public  List<Map<String, Object>>  selectResTreeNodeByRescode(String orgna_id);
	public List<Map<String, Object>> selectResChildList(String pid);
	public Long selectResChildCount(String pid);
	
}