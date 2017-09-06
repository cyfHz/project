package com.kingmon.project.webservice.common.token;

import java.io.Serializable;

public class AuthToken implements Serializable{
	
	public AuthToken() {
		super();
	}
	public AuthToken(String tokenId,String systemid, String username, String sfsh, String bdip, String sfsd, String deltag) {
		super();
		this.tokenId = tokenId;
		this.systemid =systemid;
		this.username = username;
		this.sfsh = sfsh;
		this.bdip = bdip;
		this.sfsd = sfsd;
		this.deltag = deltag;
		
	}
	
	public AuthToken(String tokenId, String systemid, String username, String sfsh, String bdip, String sfsd,
			String deltag, long lastAccessTime, long count) {
		super();
		this.tokenId = tokenId;
		this.systemid = systemid;
		this.username = username;
		this.sfsh = sfsh;
		this.bdip = bdip;
		this.sfsd = sfsd;
		this.deltag = deltag;
		this.lastAccessTime = lastAccessTime;
		this.count = count;
	}
	
	private String tokenId;
	
	private String systemid;
	
    /**
     * 用户名
     */
    private String username;
    /**
     * 是否审核
     */
    private String sfsh;
    /**
     * 绑定IP
     */
    private String bdip;

    /**
     * 是否锁定
     */
    private String sfsd;
    /**
     * 删除标记
     */
    private String deltag;
    
    /**
     * 上次访问时间
     */
    private long lastAccessTime;
    
    /**
     * 每分钟访问次数
     */
    private long count;
    /**
     * 每分钟最大次数
     */
    private long rejectCount;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSfsh() {
		return sfsh;
	}
	public void setSfsh(String sfsh) {
		this.sfsh = sfsh;
	}
	public String getBdip() {
		return bdip;
	}
	public void setBdip(String bdip) {
		this.bdip = bdip;
	}
	public String getSfsd() {
		return sfsd;
	}
	public void setSfsd(String sfsd) {
		this.sfsd = sfsd;
	}
	public String getDeltag() {
		return deltag;
	}
	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public long getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getRejectCount() {
		return rejectCount;
	}
	public void setRejectCount(long rejectCount) {
		this.rejectCount = rejectCount;
	}
    
    
}
