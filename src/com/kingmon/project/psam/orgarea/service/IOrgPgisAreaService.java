package com.kingmon.project.psam.orgarea.service;


import java.util.List;
import java.util.Map;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.orgarea.model.OrgPgisArea;

public interface IOrgPgisAreaService {

	 DataSet loadOrgPgisAreaDataSet(ParamObject po);
	
//	 int deleteByOrgnaId(String orgnaId);

	 int addOrgPisArea(OrgPgisArea record);

	 OrgPgisArea findByOrgnaId(String orgnaId);
	 
	 OrgPgisArea loadUserOrgPgisArea();
	 
	 public List<Map<String,Object>> loadUserOrgPgisBianjie(String app_userId);
	 
	 int updateOrgPisArea(OrgPgisArea record);

	 void saveOrgPgisArea(String orgid,String mapArea);
}
