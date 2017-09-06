package com.kingmon.project.elastic.model;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.alibaba.fastjson.JSON;
import com.kingmon.project.elastic.util.ElasticTypes;

public class SyncTask {
	private String taskId;
	private String taskName;
	private ElasticTypes type;
	private Date start;
	private Date end;
	/**
	 * 0 未开始
	 * 1 正在执行
	 * 2正常结束
	 * 3异常结束
	 */
	private int status;
	private long successCnt;
	private long totalCnt;
	private long failCnt;
	private String errorMsg;
	private boolean notIndexedOnly;
	private boolean stopOnError;
	private int current;
	private ConcurrentLinkedQueue<String> currentMsg;
	private boolean forceStop;
	
	public SyncTask() {
		this.currentMsg = new ConcurrentLinkedQueue<String>();
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getSuccessCnt() {
		return successCnt;
	}

	public void setSuccessCnt(long successCnt) {
		this.successCnt = successCnt;
	}

	public long getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(long failCnt) {
		this.failCnt = failCnt;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isNotIndexedOnly() {
		return notIndexedOnly;
	}

	public void setNotIndexedOnly(boolean notIndexedOnly) {
		this.notIndexedOnly = notIndexedOnly;
	}

	public boolean isStopOnError() {
		return stopOnError;
	}

	public void setStopOnError(boolean stopOnError) {
		this.stopOnError = stopOnError;
	}

	public ElasticTypes getType() {
		return type;
	}

	public void setType(ElasticTypes type) {
		this.type = type;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public long getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(long totalCnt) {
		this.totalCnt = totalCnt;
	}

	public String getCurrentMsg() {
		return JSON.toJSONString(currentMsg.toArray());
	}

	public void setCurrentMsg(String currentMsg) {
		System.out.println(currentMsg);
		try {
			if(this.currentMsg.size()>=50){
				this.currentMsg.poll();
			}
			this.currentMsg.add("["+(new Date())+"] "+currentMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isForceStop() {
		return forceStop;
	}

	public void setForceStop(boolean forceStop) {
		this.forceStop = forceStop;
	}

}
