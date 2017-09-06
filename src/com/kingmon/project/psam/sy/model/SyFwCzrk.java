package com.kingmon.project.psam.sy.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 常住人口房屋关系表
* @ClassName :SyFwCzrk     
* @Description :   
* @createTime :2015年12月19日  下午5:54:54   
* @author ：zhaohuatai   
* @version :1.0
 */
public class SyFwCzrk implements Serializable{
	/**
	 * 主键 -VARCHAR2(36)
	 * 
	 */
    private String id;
	
	/**
	 * 人员编号-VARCHAR2(36)
	 */
    private String ryid;
	
	/**
	 * 房间编号-VARCHAR2(36)
	 */
    private String fjbh;
	
	/**
	 * 登记人-VARCHAR2(36)
	 */
    private String djr;
	
	/**
	 * 登记单位-VARCHAR2(36)
	 */
    private String djdw;
	
	/**
	 * 登记人名称-VARCHAR2(100)
	 */
    private String djrmc;
	
	/**
	 * 登记单位名称-VARCHAR2(200)
	 */
    private String djdwmc;
	
	/**
	 * 登记时间-DATE
	 */
    private Date djsj;
	
	/**
	 * MOVESIGN-CHAR(1)
	 */
    private String movesign;
	
	/**
	 * 设备ID-VARCHAR2(50)
	 */
    private String sbid;
	
	/**
	 * 录入角色(民警0,社会积极力量1)-CHAR(1)
	 */
    private String lrjs;
	
	/**
	 * 录入方式(后台系统0,移动终端1)-CHAR(1)
	 */
    private String lrfs;
	
	/**
	 * 录入网络(互联网E,公安网A)-CHAR(1)
	 */
    private String lrwl;
	
	/**
	 * 删除标记（0-未删除，1-已删除）-CHAR(1)
	 */
    private String deltag;
	
	/**
	 * 删除时间-DATE
	 */
    private Date deltime;
	
	/**
	 * 删除人-VARCHAR2(36)
	 */
    private String deluser;
	
	/**
	 * 录入终端gpsx-VARCHAR2(50)
	 */
    private String gpsx;
	
	/**
	 * 录入终端gpsy-VARCHAR2(50)
	 */
    private String gpsy;

    /**
     * 所属建筑物-VARCHAR2(36)
     */
    private String jzwid;
    /**
     * 证件编号-VARCHAR2(40)
     */
    private String zjbh;
    
    private String tf;
    private String  imei;
    private String  sim;
    
    /**
     * 新增字段-16-06-06
     * Mac地址
     * MAC_ADDRESS varchar2(20);
     */
    private String macAddress;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid == null ? null : ryid.trim();
    }

    public String getFjbh() {
        return fjbh;
    }

    public void setFjbh(String fjbh) {
        this.fjbh = fjbh == null ? null : fjbh.trim();
    }

    public String getDjr() {
        return djr;
    }

    public void setDjr(String djr) {
        this.djr = djr == null ? null : djr.trim();
    }

    public String getDjdw() {
        return djdw;
    }

    public void setDjdw(String djdw) {
        this.djdw = djdw == null ? null : djdw.trim();
    }

    public String getDjrmc() {
        return djrmc;
    }

    public void setDjrmc(String djrmc) {
        this.djrmc = djrmc == null ? null : djrmc.trim();
    }

    public String getDjdwmc() {
        return djdwmc;
    }

    public void setDjdwmc(String djdwmc) {
        this.djdwmc = djdwmc == null ? null : djdwmc.trim();
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getMovesign() {
        return movesign;
    }

    public void setMovesign(String movesign) {
        this.movesign = movesign == null ? null : movesign.trim();
    }

    public String getSbid() {
        return sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid == null ? null : sbid.trim();
    }

    public String getLrjs() {
        return lrjs;
    }

    public void setLrjs(String lrjs) {
        this.lrjs = lrjs == null ? null : lrjs.trim();
    }

    public String getLrfs() {
        return lrfs;
    }

    public void setLrfs(String lrfs) {
        this.lrfs = lrfs == null ? null : lrfs.trim();
    }

    public String getLrwl() {
        return lrwl;
    }

    public void setLrwl(String lrwl) {
        this.lrwl = lrwl == null ? null : lrwl.trim();
    }

    public String getDeltag() {
        return deltag;
    }

    public void setDeltag(String deltag) {
        this.deltag = deltag == null ? null : deltag.trim();
    }

    public Date getDeltime() {
        return deltime;
    }

    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }

    public String getDeluser() {
        return deluser;
    }

    public void setDeluser(String deluser) {
        this.deluser = deluser == null ? null : deluser.trim();
    }

    public String getGpsx() {
        return gpsx;
    }

    public void setGpsx(String gpsx) {
        this.gpsx = gpsx == null ? null : gpsx.trim();
    }

    public String getGpsy() {
        return gpsy;
    }

    public void setGpsy(String gpsy) {
        this.gpsy = gpsy == null ? null : gpsy.trim();
    }

	public String getJzwid() {
		return jzwid;
	}

	public void setJzwid(String jzwid) {
		this.jzwid = jzwid == null ? null : jzwid.trim();
	}

	public String getTf() {
		return tf;
	}

	public void setTf(String tf) {
		this.tf = tf == null ? null : tf.trim();
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei == null ? null : imei.trim();
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim == null ? null : sim.trim();
	}

	public String getZjbh() {
		return zjbh;
	}

	public void setZjbh(String zjbh) {
		this.zjbh = zjbh;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	
}