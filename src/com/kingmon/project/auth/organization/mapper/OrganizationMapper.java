package com.kingmon.project.auth.organization.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.auth.organization.model.Organization;
@KMapper
public interface OrganizationMapper {
//
    int insertSelective(Organization record);
    int updateByPrimaryKeySelective(Organization record);
    
    Organization selectOrgById(String orgna_id);
    Organization selectOrgByCode(String orgna_code);
    Organization selectOrgByUserId(String appuser_id);
    
	public List<Map<String, Object>> selectChildList(String id);
	public Long selectChildCount(String id);
	public Map<String, Object> selectChildListType(String id);
	/**
	 * list中只会返回一个节点
	 * @param orgna_id
	 * @return
	 */
	public  List<Map<String, Object>>  selectOrgTreeNodeById(String orgna_id);
	
	public  List<String>  selectOrgCodeByPorgId(String porg_id);
	public  List<Organization> selectOrgByPorgId(String porg_id);
	
	
	
	public  List<String> selectOrgCodeByIds(@Param("ids") List<String> ids);
	public  List<Organization> selectOrgByIds(@Param("ids") List<String> ids);
}