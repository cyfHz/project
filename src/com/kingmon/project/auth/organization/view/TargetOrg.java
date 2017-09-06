package com.kingmon.project.auth.organization.view;

import java.io.Serializable;

public class TargetOrg  implements Serializable{
	//{fromOrgChildOrg=[{"ORGNA_PID":"370101001000","ORGNA_CODE":"370101001001","ORGNA_NAME":"派出所1-警务区1","ORGNA_ID":"3d387b6c-7b0e-4887-8f85-76039ffe4ca6"},{"ORGNA_PID":"370101002000","ORGNA_CODE":"370101001002","ORGNA_NAME":"派出所1-警务区2","ORGNA_ID":"619384e2-1236-4393-bbfe-7186a402882c"}], 
	//targetOrgIds=[{"T_ORGNA_JC":"派出所1","T_ORGNA_ID":"370101001000","T_PORGNA_ID":"370101000000","T_ORGNA_CODE":"370101001000","T_ORGNA_NAME":"派出所1"},{"T_ORGNA_JC":"派出所2","T_ORGNA_ID":"370101002000","T_PORGNA_ID":"370101000000","T_ORGNA_CODE":"370101002000","T_ORGNA_NAME":"派出所2"}],
	//type=pcs, fromId=370101001000}
	private String T_ORGNA_JC;
	private String T_ORGNA_ID;
	private String T_PORGNA_ID;
	private String T_ORGNA_CODE;
	private String T_ORGNA_NAME;
	public String getT_ORGNA_JC() {
		return T_ORGNA_JC;
	}
	public void setT_ORGNA_JC(String t_ORGNA_JC) {
		T_ORGNA_JC = t_ORGNA_JC;
	}
	public String getT_ORGNA_ID() {
		return T_ORGNA_ID;
	}
	public void setT_ORGNA_ID(String t_ORGNA_ID) {
		T_ORGNA_ID = t_ORGNA_ID;
	}
	public String getT_PORGNA_ID() {
		return T_PORGNA_ID;
	}
	public void setT_PORGNA_ID(String t_PORGNA_ID) {
		T_PORGNA_ID = t_PORGNA_ID;
	}
	public String getT_ORGNA_CODE() {
		return T_ORGNA_CODE;
	}
	public void setT_ORGNA_CODE(String t_ORGNA_CODE) {
		T_ORGNA_CODE = t_ORGNA_CODE;
	}
	public String getT_ORGNA_NAME() {
		return T_ORGNA_NAME;
	}
	public void setT_ORGNA_NAME(String t_ORGNA_NAME) {
		T_ORGNA_NAME = t_ORGNA_NAME;
	}
	
	
}
