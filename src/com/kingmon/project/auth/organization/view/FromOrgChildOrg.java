package com.kingmon.project.auth.organization.view;

import java.io.Serializable;

public class FromOrgChildOrg implements Serializable{
	//{fromOrgChildOrg=[{"ORGNA_PID":"370101001000","ORGNA_CODE":"370101001001","ORGNA_NAME":"派出所1-警务区1","ORGNA_ID":"3d387b6c-7b0e-4887-8f85-76039ffe4ca6"},{"ORGNA_PID":"370101002000","ORGNA_CODE":"370101001002","ORGNA_NAME":"派出所1-警务区2","ORGNA_ID":"619384e2-1236-4393-bbfe-7186a402882c"}], 
		//targetOrgIds=[{"T_ORGNA_JC":"派出所1","T_ORGNA_ID":"370101001000","T_PORGNA_ID":"370101000000","T_ORGNA_CODE":"370101001000","T_ORGNA_NAME":"派出所1"},{"T_ORGNA_JC":"派出所2","T_ORGNA_ID":"370101002000","T_PORGNA_ID":"370101000000","T_ORGNA_CODE":"370101002000","T_ORGNA_NAME":"派出所2"}],
		//type=pcs, fromId=370101001000}
	private String ORGNA_PID;
	private String ORGNA_CODE;
	private String ORGNA_NAME;
	private String ORGNA_ID;
	public String getORGNA_PID() {
		return ORGNA_PID;
	}
	public void setORGNA_PID(String oRGNA_PID) {
		ORGNA_PID = oRGNA_PID;
	}
	public String getORGNA_CODE() {
		return ORGNA_CODE;
	}
	public void setORGNA_CODE(String oRGNA_CODE) {
		ORGNA_CODE = oRGNA_CODE;
	}
	public String getORGNA_NAME() {
		return ORGNA_NAME;
	}
	public void setORGNA_NAME(String oRGNA_NAME) {
		ORGNA_NAME = oRGNA_NAME;
	}
	public String getORGNA_ID() {
		return ORGNA_ID;
	}
	public void setORGNA_ID(String oRGNA_ID) {
		ORGNA_ID = oRGNA_ID;
	}
	
	
	
}
