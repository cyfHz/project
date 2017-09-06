package com.kingmon.project.psam.jzw.model;

import java.io.Serializable;


public class JzwfjChangeBizConfig implements Serializable{

	
    private String id;
    
    /**
     * 业务逻辑bizName
     */
    private String bizName;
    /**
     * 业务逻辑bizCode
     */
    private String bizCode;
    
    private String tableName;
    

    
    private String fieldName;//业务表中 依赖外键房间ID 字段名
    
//    private String refJzwFjFieldName;//业务表中 依赖房间标的字段名，一般为--id
    
    private String updateQueryFieldName;//业务表中 更新外键房间ID ，update where field= 字段名
    
  //--------------------以下字段暂不用-----@20160622----------  
    
    
    private String field2Name; //jzwfw---房屋名称字段----->a+b+c+-fjmc
    
    private String refJzwFjField2Name;//jzwfj---fjmc
    
    private String ref2replceMode;//替换模式 1：直接替换，2： 字符替换
    
//--------------------以下字段暂不用----------------------------  
    private String field3Name;
    
    private String refJzwFjField3Name;
    
    private String ref3replceMode;//替换模式 1：直接替换，2： 字符替换

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id =id==null? null : id.trim();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName =tableName==null? null : tableName.trim();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName==null? null : fieldName.trim();
	}

//	public String getRefJzwFjFieldName() {
//		return refJzwFjFieldName;
//	}
//
//	public void setRefJzwFjFieldName(String refJzwFjFieldName) {
//		this.refJzwFjFieldName =  refJzwFjFieldName==null? null : refJzwFjFieldName.trim();
//	}

	public String getField3Name() {
		return field3Name;
	}

	public void setField3Name(String field3Name) {
		this.field3Name = field3Name==null? null : field3Name.trim();
	}

	public String getRefJzwFjField3Name() {
		return refJzwFjField3Name;
	}

	public void setRefJzwFjField3Name(String refJzwFjField3Name) {
		this.refJzwFjField3Name = refJzwFjField3Name==null? null : refJzwFjField3Name.trim();
	}

	public String getField2Name() {
		return field2Name;
	}

	public void setField2Name(String field2Name) {
		this.field2Name = field2Name==null? null : field2Name.trim();
	}

	public String getRefJzwFjField2Name() {
		return refJzwFjField2Name;
	}

	public void setRefJzwFjField2Name(String refJzwFjField2Name) {
		this.refJzwFjField2Name = refJzwFjField2Name==null? null : refJzwFjField2Name.trim();
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName =  bizName==null? null : bizName.trim();
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode==null? null : bizCode.trim();
	}

	public String getUpdateQueryFieldName() {
		return updateQueryFieldName;
	}

	public void setUpdateQueryFieldName(String updateQueryFieldName) {
		this.updateQueryFieldName =  updateQueryFieldName==null? null : updateQueryFieldName.trim();
	}

	public String getRef2replceMode() {
		return ref2replceMode;
	}

	public void setRef2replceMode(String ref2replceMode) {
		this.ref2replceMode = ref2replceMode;
	}

	public String getRef3replceMode() {
		return ref3replceMode;
	}

	public void setRef3replceMode(String ref3replceMode) {
		this.ref3replceMode = ref3replceMode;
	}


    
   
}