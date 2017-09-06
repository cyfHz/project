package com.kingmon.project.auth.organization.view;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class OrgChaiFenPostView implements Serializable{
	//{fromOrgChildOrgs=[{"ORGNA_PID":"370101001000","ORGNA_CODE":"370101001001","ORGNA_NAME":"派出所1-警务区1","ORGNA_ID":"3d387b6c-7b0e-4887-8f85-76039ffe4ca6"},{"ORGNA_PID":"370101002000","ORGNA_CODE":"370101001002","ORGNA_NAME":"派出所1-警务区2","ORGNA_ID":"619384e2-1236-4393-bbfe-7186a402882c"}], 
	//targetOrgs=[{"T_ORGNA_JC":"派出所1","T_ORGNA_ID":"370101001000","T_PORGNA_ID":"370101000000","T_ORGNA_CODE":"370101001000","T_ORGNA_NAME":"派出所1"},{"T_ORGNA_JC":"派出所2","T_ORGNA_ID":"370101002000","T_PORGNA_ID":"370101000000","T_ORGNA_CODE":"370101002000","T_ORGNA_NAME":"派出所2"}],
	//type=pcs, fromId=370101001000}
	
	private List<FromOrgChildOrg> fromOrgChildOrgs;
	private List<TargetOrg> targetOrgs;
	private String type;
	private String fromId;
	
	public List<String> genToOrgIds(){
		if(targetOrgs==null||targetOrgs.isEmpty()){
			return	null;
		}
		List<String> toIds=Lists.newArrayList();
		for(TargetOrg target:targetOrgs){
			toIds.add(target.getT_ORGNA_ID());
		}
		return toIds;
	}
	
	public List<FromOrgChildOrg> getFromOrgChildOrgs() {
		return fromOrgChildOrgs;
	}
	public void setFromOrgChildOrgs(List<FromOrgChildOrg> fromOrgChildOrgs) {
		this.fromOrgChildOrgs = fromOrgChildOrgs;
	}
	public List<TargetOrg> getTargetOrgs() {
		return targetOrgs;
	}
	public void setTargetOrgs(List<TargetOrg> targetOrgs) {
		this.targetOrgs = targetOrgs;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	
	
}
