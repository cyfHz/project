package com.kingmon.project.psam.datasync.model;

import java.io.Serializable;
import java.util.Date;

import com.kingmon.base.util.UUIDUtil;

public class DzDataSyncTask implements Serializable{
	public DzDataSyncTask() {
		super();
		
	}
	public static DzDataSyncTask newDefaultTask ( String configId, String originalValue, String targetValue,String updateWhereValue,String createuser,String describe) {
		return new DzDataSyncTask( 
						UUIDUtil.uuid(),
						configId,  
						originalValue,  
						targetValue, 
						updateWhereValue,
						"1",  
						"1",  
						new Date(),  
						createuser,
						 describe);
	} 
	
	public DzDataSyncTask(String id, String configId, String originalValue, String targetValue,String updateWhereValue,
			 /*String esOriginalValue,
				String esTargetValue,*/ 
				String enabled, String status, Date createtime, String createuser,
				String describe) {
			super();
			this.id = id;
			this.configId = configId;
			this.originalValue = originalValue;
			this.targetValue = targetValue;
			this.updateWhereValue=updateWhereValue;
//			this.esOriginalValue = esOriginalValue;
//			this.esTargetValue = esTargetValue;
			this.enabled = enabled;
			this.status = status;
			this.createtime = createtime;
			this.createuser = createuser;
			this.describe = describe;
		}   
    public DzDataSyncTask(String id, String configId, String originalValue, String targetValue,String updateWhereValue,
    		/* String esOriginalValue,
			String esTargetValue,*/
    		String enabled, String status, Date createtime, String createuser, Date endtime,
			String describe) {
		super();
		this.id = id;
		this.configId = configId;
		this.originalValue = originalValue;
		this.targetValue = targetValue;
		this.updateWhereValue=updateWhereValue;
/*		this.esOriginalValue = esOriginalValue;
		this.esTargetValue = esTargetValue;*/
		this.enabled = enabled;
		this.status = status;
		this.createtime = createtime;
		this.createuser = createuser;
		this.endtime = endtime;
		this.describe = describe;
	}

	private String id;
    private String configId;
    private String originalValue;
    private String targetValue;
    private String updateWhereValue;
/*    private String esOriginalValue;
    private String esTargetValue;*/
    
    /**
     * 1:启用
     * 0：禁用
     */
    private String enabled;
    /**
     *  [1： 新添加  ,2：运行中 ,3：运行成功 ,4：运行失败 ，5：DB成功，ES失败,6：ES成功，DB失败]
     */
    private String status;
    private Date createtime;
    private String createuser;
    private Date endtime;
	private String describe;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue == null ? null : originalValue.trim();
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue == null ? null : targetValue.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime == null ? null : endtime;
    }
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe == null ? null : describe.trim();
	}
	public String getUpdateWhereValue() {
		return updateWhereValue;
	}

	public void setUpdateWhereValue(String updateWhereValue) {
		this.updateWhereValue = updateWhereValue == null ? null : updateWhereValue.trim();
	}
/*	public String getEsOriginalValue() {
		return esOriginalValue;
	}
	public void setEsOriginalValue(String esOriginalValue) {
		this.esOriginalValue = esOriginalValue;
	}
	public String getEsTargetValue() {
		return esTargetValue;
	}
	public void setEsTargetValue(String esTargetValue) {
		this.esTargetValue = esTargetValue;
	}*/
	
	
}