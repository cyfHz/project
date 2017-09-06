package com.kingmon.project.psam.datasync.view;

import java.io.Serializable;
import java.util.List;

public class SyncParam implements Serializable{
	
	private String craeteUserId;
	private List<SyncParamItem> syncParamItemList;
	
	
	public SyncParam() {
		super();
	}
	public static SyncParam newSP() {
		return new SyncParam();
	}
	
	
	public SyncParam(String craeteUserId, List<SyncParamItem> syncParamItemList) {
		super();
		this.craeteUserId = craeteUserId;
		this.syncParamItemList = syncParamItemList;
	}
	
	public String getCraeteUserId() {
		return craeteUserId;
	}
	public SyncParam setCraeteUserId(String craeteUserId) {
		this.craeteUserId = craeteUserId;
		return this;
	}
	public List<SyncParamItem> getSyncParamItemList() {
		return syncParamItemList;
	}
	public SyncParam setSyncParamItemList(List<SyncParamItem> syncParamItemList) {
		this.syncParamItemList = syncParamItemList;
		return this;
	}
	
}
