package com.kingmon.project.psam.datasync.view;

import java.io.Serializable;


public class SyncParamItem implements Serializable{
	
	private Object sourceValue;
	private Object targetValue;
	private Object updateWhereValue;
	
	
	public SyncParamItem(Object sourceValue, Object targetValue,Object updateWhereValue) {
		super();
		this.sourceValue = sourceValue;
		this.targetValue = targetValue;
		this.updateWhereValue = updateWhereValue;
	}
	public Object getSourceValue() {
		return sourceValue;
	}
	public void setSourceValue(Object sourceValue) {
		this.sourceValue = sourceValue;
	}
	public Object getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(Object targetValue) {
		this.targetValue = targetValue;
	}
	public Object getUpdateWhereValue() {
		return updateWhereValue;
	}
	public void setUpdateWhereValue(Object updateWhereValue) {
		this.updateWhereValue = updateWhereValue;
	}
	
	
	
	
}
