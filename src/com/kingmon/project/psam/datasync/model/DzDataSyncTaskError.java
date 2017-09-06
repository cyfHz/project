package com.kingmon.project.psam.datasync.model;

import java.io.Serializable;
import java.util.Date;

public class DzDataSyncTaskError implements Serializable{
	
	public DzDataSyncTaskError() {
		super();
	}

	public DzDataSyncTaskError(String id, String taskId, Date errortime, String describe) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.errortime = errortime;
		this.describe = describe;
	}

	private String id;

    private String taskId;

    private Date errortime;

	private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public Date getErrortime() {
        return errortime;
    }

    public void setErrortime(Date errortime) {
		this.errortime = errortime;
	}
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe == null ? null : describe.trim();
	}
}