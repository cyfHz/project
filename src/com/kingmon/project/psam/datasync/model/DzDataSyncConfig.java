package com.kingmon.project.psam.datasync.model;

import java.io.Serializable;

public class DzDataSyncConfig implements Serializable{
	
	 public DzDataSyncConfig() {
			super();
		}
    public DzDataSyncConfig(String id, String bizTable, String bizFiled, String referTable, String referFiled,String referUpdateWhereField,
			String enabled, String bizId, String isSyncSearch,
			String searchType, String searchField, String describe) {
		super();
		this.id = id;
		this.bizTable = bizTable;
		this.bizFiled = bizFiled;
		this.referTable = referTable;
		this.referFiled = referFiled;
		this.enabled = enabled;
		this.bizId = bizId;
		this.isSyncSearch = isSyncSearch;
		this.searchType = searchType;
		this.searchField = searchField;
		this.describe = describe;
		this.referUpdateWhereField = referUpdateWhereField;
	}

	private String id;

	/**
	 * 业务表
	 */
    private String bizTable;
    
    /**
	 * 业务表字段
	 */
    private String bizFiled;
    
    /**
	 * 涉及表
	 */
    private String referTable;
    
    /**
	 * 涉及表字段
	 */
    private String referFiled;
    
    /**
	 * 
	 */
    private String referUpdateWhereField;
    
    /**
     * 1:启用
     * 0：禁用
     */
    private String enabled;

//    private Date endtime;

 //   private Date createtime;

 //   private String createuser;
    
    /**
	 * 涉及业务点
	 */
    private String bizId;
    
    /**
	 * 是否同步ES
	 */
    private String isSyncSearch;
    
    /**
	 *  ES Type
	 */
    private String searchType;
    
    /**
	 * ES 字段
	 */
    private String searchField;
    
    /**
	 * 描述
	 */
    private String describe;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBizTable() {
        return bizTable;
    }

    public void setBizTable(String bizTable) {
        this.bizTable = bizTable == null ? null : bizTable.trim();
    }

    public String getBizFiled() {
        return bizFiled;
    }

    public void setBizFiled(String bizFiled) {
        this.bizFiled = bizFiled == null ? null : bizFiled.trim();
    }

    public String getReferTable() {
        return referTable;
    }

    public void setReferTable(String referTable) {
        this.referTable = referTable == null ? null : referTable.trim();
    }

    public String getReferFiled() {
        return referFiled;
    }

    public void setReferFiled(String referFiled) {
        this.referFiled = referFiled == null ? null : referFiled.trim();
    }


    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

//    public Date getEndtime() {
//        return endtime;
//    }
//
//    public void setEndtime(Date endtime) {
//        this.endtime = endtime == null ? null :endtime;
//    }

//    public Date getCreatetime() {
//        return createtime;
//    }
//
//    public void setCreatetime(Date createtime) {
//        this.createtime = createtime;
//    }
//
//    public String getCreateuser() {
//        return createuser;
//    }
//
//    public void setCreateuser(String createuser) {
//        this.createuser = createuser == null ? null : createuser.trim();
//    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId == null ? null : bizId.trim();
    }

    public String getIsSyncSearch() {
        return isSyncSearch;
    }

    public void setIsSyncSearch(String isSyncSearch) {
        this.isSyncSearch = isSyncSearch == null ? null : isSyncSearch.trim();
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType == null ? null : searchType.trim();
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField == null ? null : searchField.trim();
    }

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe == null ? null : describe.trim();
	}
	public String getReferUpdateWhereField() {
		return referUpdateWhereField;
	}
	public void setReferUpdateWhereField(String referUpdateWhereField) {
		this.referUpdateWhereField = referUpdateWhereField == null ? null :referUpdateWhereField.trim();
	}
    
    
}