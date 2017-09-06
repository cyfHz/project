package com.kingmon.project.psam.workload.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Maps;

import com.kingmon.project.auth.organization.OrgUtilX;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organizationuser.mapper.OrganizationUserMapper;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;

public class WorkLoadUtil {
	
	/**
	 * 			map.put("name", org.getOrgna_name());<br>
				map.put("code", org.getOrgna_code());<br>
				
				map.put("mlph_totle", 0);<br>
				map.put("mlph_nozb", 0);<br>
				map.put("mlph_nojwq", 0);<br>
				
				map.put("jzwjbxx_totle", 0);<br>
				map.put("jzwjbxx_nozb", 0);<br>
				map.put("jzwjbxx_nojwq", 0);<br>
	 * @param porg_code
	 * @param organizationMapper
	 * @param organizationUserMapper
	 * @return
	 */
	public static   List<Map<String,Object>> genDataRows(String porg_code,OrganizationMapper organizationMapper,OrganizationUserMapper organizationUserMapper,JwqMapper jwqMapper) {
		List<Map<String,Object>> rows=new ArrayList<Map<String,Object>>();
		int org_level=OrgUtilX.getOrgLevel(porg_code);
 		if(org_level<=6){//  >pcs
			List<Organization> OrgList= organizationMapper.selectOrgByPorgId(porg_code);
			for(Organization org:OrgList){
				Map<String,Object> map=Maps.newHashMap();
				map.put("name", org.getOrgna_name());
				map.put("code", org.getOrgna_code());
				
				map.put("mlph_totle", 0);
				map.put("mlph_nozb", 0);
				map.put("mlph_nojwq", 0);
				
				map.put("jzwjbxx_totle", 0);
				map.put("jzwjbxx_nozb", 0);
				map.put("jzwjbxx_nojwq", 0);
				
				map.put("jwq_totle", 0);
				map.put("jwq_nozb", 0);
				map.put("jwq_nojwq", 0);
				rows.add(map);
			}
		}else{//  <=pcs
//			List<OrganizationUser> orgUserList=organizationUserMapper.selectOrgUserByOrgCode(porg_code);
//			for(OrganizationUser user:orgUserList){
//				Map<String,Object> map=Maps.newHashMap();
//				map.put("name",user.getUser_name());
//				map.put("code", user.getAppuser_id());
//				
//				map.put("mlph_totle", 0);
//				map.put("mlph_nozb", 0);
//				map.put("mlph_nojwq", 0);
//				
//				map.put("jzwjbxx_totle", 0);
//				map.put("jzwjbxx_nozb", 0);
//				map.put("jzwjbxx_nojwq", 0);
//				rows.add(map);
//			}
			
			List<Jwq> jwqList= jwqMapper.selectJwqListByPcsId(porg_code);
			for(Jwq jwq:jwqList){
				Map<String,Object> map=Maps.newHashMap();
				map.put("name",jwq.getJwqmc());
				map.put("code", jwq.getJwqbh());
				
				map.put("mlph_totle", 0);
				map.put("mlph_nozb", 0);
				map.put("mlph_nojwq", 0);
				
				map.put("jzwjbxx_totle", 0);
				map.put("jzwjbxx_nozb", 0);
				map.put("jzwjbxx_nojwq", 0);
				
				map.put("jwq_totle", 0);
				map.put("jwq_nozb", 0);
				map.put("jwq_nojwq", 0);
				rows.add(map);
			}
		}
		return rows;
	}
}
