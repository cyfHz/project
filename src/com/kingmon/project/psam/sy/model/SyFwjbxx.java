package com.kingmon.project.psam.sy.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SyFwjbxx implements Serializable {
	
	/**
	 * 房屋ID -VARCHAR2(36)
	 */
    private String fwid;

    /**
     * 房屋编号-VARCHAR2(50)
     */
    private String fwbh;

    /**
     * 省市县（区）-VARCHAR2(36)外键
     */
    private String ssxqdm;
    
    /**
     * 业务逻辑用，不是数据库字段
     */
    private String ssxq_mc;
    
	/**
     * 所属街路巷(小区)_地址编码-VARCHAR2(36)外键
     */
    private String ssjlxxq_dzbm;
    
    /**
     * 业务逻辑用，不是数据库字段
     */
    private String ssjlxxq_mc;
    
    /**
     * 地址编码 -VARCHAR2(36)-
     */
    private String dzbm;

    /**
     * 房间编码 -VARCHAR2(36)外键
     */
    private String fjbm;

    /**
     * 房间号-VARCHAR2(36)
     */
    private String fjh;

    /**
     * 房屋地址详细地址-VARCHAR2(200)
     */
    private String fwdz;

    /**
     * VARCHAR2(2)
     * 房屋类别(10 单元楼,20筒子楼,30 别墅,40 自建小楼,50 独立平房,60 四合院平房,90 其他)
     */
    private String fwlb;

    /**
     * VARCHAR2(2)
     * 房屋性质（全部、1 公有、2 私有、9其他）
     */
    private String fwxz;

    /**
     * VARCHAR2(36)
     * 房屋用途(1 办公,2 厂房,3 商用,4 居住,5 商住两用,9其他)
     */
    private String fwyt;

    /**
     * VARCHAR2(2)
     * 房屋类型（楼房、平房、地下室）
     */
    private String fwlx;

    /**
     * VARCHAR2(36)
     * 户型（*室*厅）
     */
    private String hx;

    /**
     * 
     * 房屋间数- NUMBER(8)
     */
    private Integer fwjs;

    /**
     * 房屋面积(平米) -NUMBER(12,2)
     * 
     */
    private BigDecimal fwmj;

    /**
     * 房屋产权证号-VARCHAR2(50)
     */
    private String fwcqzh;

    /**
     * 房主姓名-VARCHAR2(50)
     */
    private String fzxm;

    /**
     * 房主身份证号码,输入身份证号检索房主信息，自动提取房主姓名。-VARCHAR2(20)
     */
    private String fzsfzhm;

    /**
     * 房主联系电话-VARCHAR2(50)
     */
    private String fzlxdh;

    /**
     *是否出租房屋,全部、是、否（根据是是否存在有效的出租信息进行判断）-VARCHAR2(1) 
     */
    private String sfczfw;

    /**
     * 托管人姓名-VARCHAR2(1)
     */
    private String tgrxm;

    /**
     * 托管人身份证号码,输入身份证号检索托管人信息，自动提取姓名。-VARCHAR2(20)
     */
    private String tgrsfzhm;

    /**
     * 托管人联系电话,GA 240.15---VARCHAR2(50)
     */
    private String tgrlxdh;

    /**
     * 与房主关系,国标字典项-VARCHAR2(36)
     */
    private String yfzgx;

    /**
     * 房屋所属单位编码-VARCHAR2(36)
     */
    private String fwssdwbm;

    /**
     * VARCHAR2(200)
     * 房屋所属单位名称,输入房屋所属单位名称检索单位信息、如存在的自动提取房屋所属单位编码，如检索不到，需手工填写房屋所属单位名称
     */
    private String fwssdwmc;

    /**
     * 属地派出所-VARCHAR2(36)
     */
    private String sdpcs;

    /**
     * 不是数据库字段，业务逻辑用
     */
    private String sdpcs_mc;
    
    /**
     * 警务责任区 -VARCHAR2(36)
     */
    private String jwzrq;
    /**
     * 不是数据库字段，业务逻辑用
     */
    private String jwzrq_mc;
    /**
     * 备注-VARCHAR2(500)
     */
    private String bz;
//------------------------------------------------------------------
    /**
     *注销状态 (1已注销;0未注销)-VARCHAR2(2)
     */
    private String zxzt;

    /**
     * 注销日期-DATE
     */
    private Date zxrq;

    /**
     * 删除标记（0-未删除，1-已删除）
     */
    private String deltag;

    /**
     * 删除时间
     */
    private Date deltime;

    /**
     * 登记人-VARCHAR2(36)
     */
    private String djr;

    /**
     * 登记单位-VARCHAR2(36)
     */
    private String djdw;

    /**
     * 登记单位名称-VARCHAR2(200)
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
     * 修改人-VARCHAR2(36)
     */
    private String xgr;

    /**
     * 修改单位-VARCHAR2(36)
     */
    private String xgdw;

    /**
     * 修改人名称-VARCHAR2(100)
     */
    private String xgrmc;

    /**
     * 修改单位名称-VARCHAR2(200)
     */
    private String xgdwmc;

    /**
     * 更新时间-DATE
     */
    private Date gxsj;

    /**
     * 
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
     * 录入方式(后台系统0,移动终端1))-CHAR(1)
     */
    private String lrfs;

    /**
     * 
     * 录入网络(互联网E,公安网A)-CHAR(1)
     */
    private String lrwl;
    
  //-------------------------------------------------  
    /**
     * 二维码-clob
     */
    private String qrCode;
    
    /**
     * Mac地址
     * MAC_ADDRESS varchar2(20);
     */
    private String macAddress;
    
    /**
     * 
     * TF卡号 varchar2(36);
     */
    private String tfCardNum;
    
    /**
     * IMEI号 varchar2(20);
     */
    private String imeiNum;
    
    /**
     * SIM卡号-varchar2(20)
     */
    private String simNum;
    
    /**
     * NUMBER(11,8)
     */
    private BigDecimal gpsX;
    
    /**
     * NUMBER(11,8)
     */
    private BigDecimal gpsY;
    
    /**
   	 * #新增字段# 归属代码
   	 *   -VARCHAR2(36)
   	 */
   	private String fwgsdm;
    //-------------------------------------------------     

    public String getFwid() {
        return fwid;
    }

    public void setFwid(String fwid) {
        this.fwid = fwid == null ? null : fwid.trim();
    }

    public String getFwbh() {
        return fwbh;
    }

    public void setFwbh(String fwbh) {
        this.fwbh = fwbh == null ? null : fwbh.trim();
    }

    public String getSsxqdm() {
        return ssxqdm;
    }

    public void setSsxqdm(String ssxqdm) {
        this.ssxqdm = ssxqdm == null ? null : ssxqdm.trim();
    }


    public String getSsjlxxq_dzbm() {
		return ssjlxxq_dzbm;
	}

	public void setSsjlxxq_dzbm(String ssjlxxq_dzbm) {
		this.ssjlxxq_dzbm = ssjlxxq_dzbm;
	}

	public String getDzbm() {
        return dzbm;
    }

    public void setDzbm(String dzbm) {
        this.dzbm = dzbm == null ? null : dzbm.trim();
    }

    public String getFjbm() {
        return fjbm;
    }

    public void setFjbm(String fjbm) {
        this.fjbm = fjbm == null ? null : fjbm.trim();
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh == null ? null : fjh.trim();
    }

    public String getFwdz() {
        return fwdz;
    }

    public void setFwdz(String fwdz) {
        this.fwdz = fwdz == null ? null : fwdz.trim();
    }

    public String getFwlb() {
        return fwlb;
    }

    public void setFwlb(String fwlb) {
        this.fwlb = fwlb == null ? null : fwlb.trim();
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz == null ? null : fwxz.trim();
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt == null ? null : fwyt.trim();
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx == null ? null : fwlx.trim();
    }

    public String getHx() {
        return hx;
    }

    public void setHx(String hx) {
        this.hx = hx == null ? null : hx.trim();
    }

    public Integer getFwjs() {
        return fwjs;
    }

    public void setFwjs(Integer fwjs) {
        this.fwjs = fwjs;
    }

    public BigDecimal getFwmj() {
        return fwmj;
    }

    public void setFwmj(BigDecimal fwmj) {
        this.fwmj = fwmj;
    }

    public String getFwcqzh() {
        return fwcqzh;
    }

    public void setFwcqzh(String fwcqzh) {
        this.fwcqzh = fwcqzh == null ? null : fwcqzh.trim();
    }

    public String getFzxm() {
        return fzxm;
    }

    public void setFzxm(String fzxm) {
        this.fzxm = fzxm == null ? null : fzxm.trim();
    }

    public String getFzsfzhm() {
        return fzsfzhm;
    }

    public void setFzsfzhm(String fzsfzhm) {
        this.fzsfzhm = fzsfzhm == null ? null : fzsfzhm.trim();
    }

    public String getFzlxdh() {
        return fzlxdh;
    }

    public void setFzlxdh(String fzlxdh) {
        this.fzlxdh = fzlxdh == null ? null : fzlxdh.trim();
    }

    public String getSfczfw() {
        return sfczfw;
    }

    public void setSfczfw(String sfczfw) {
        this.sfczfw = sfczfw == null ? null : sfczfw.trim();
    }

    public String getTgrxm() {
        return tgrxm;
    }

    public void setTgrxm(String tgrxm) {
        this.tgrxm = tgrxm == null ? null : tgrxm.trim();
    }

    public String getTgrsfzhm() {
        return tgrsfzhm;
    }

    public void setTgrsfzhm(String tgrsfzhm) {
        this.tgrsfzhm = tgrsfzhm == null ? null : tgrsfzhm.trim();
    }

    public String getTgrlxdh() {
        return tgrlxdh;
    }

    public void setTgrlxdh(String tgrlxdh) {
        this.tgrlxdh = tgrlxdh == null ? null : tgrlxdh.trim();
    }

    public String getYfzgx() {
        return yfzgx;
    }

    public void setYfzgx(String yfzgx) {
        this.yfzgx = yfzgx == null ? null : yfzgx.trim();
    }

    public String getFwssdwbm() {
        return fwssdwbm;
    }

    public void setFwssdwbm(String fwssdwbm) {
        this.fwssdwbm = fwssdwbm == null ? null : fwssdwbm.trim();
    }

    public String getFwssdwmc() {
        return fwssdwmc;
    }

    public void setFwssdwmc(String fwssdwmc) {
        this.fwssdwmc = fwssdwmc == null ? null : fwssdwmc.trim();
    }

    public String getSdpcs() {
        return sdpcs;
    }

    public void setSdpcs(String sdpcs) {
        this.sdpcs = sdpcs == null ? null : sdpcs.trim();
    }

    public String getJwzrq() {
        return jwzrq;
    }

    public void setJwzrq(String jwzrq) {
        this.jwzrq = jwzrq == null ? null : jwzrq.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZxzt() {
        return zxzt;
    }

    public void setZxzt(String zxzt) {
        this.zxzt = zxzt == null ? null : zxzt.trim();
    }

    public Date getZxrq() {
        return zxrq;
    }

    public void setZxrq(Date zxrq) {
        this.zxrq = zxrq;
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

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr == null ? null : xgr.trim();
    }

    public String getXgdw() {
        return xgdw;
    }

    public void setXgdw(String xgdw) {
        this.xgdw = xgdw == null ? null : xgdw.trim();
    }

    public String getXgrmc() {
        return xgrmc;
    }

    public void setXgrmc(String xgrmc) {
        this.xgrmc = xgrmc == null ? null : xgrmc.trim();
    }

    public String getXgdwmc() {
        return xgdwmc;
    }

    public void setXgdwmc(String xgdwmc) {
        this.xgdwmc = xgdwmc == null ? null : xgdwmc.trim();
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
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

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getTfCardNum() {
		return tfCardNum;
	}

	public void setTfCardNum(String tfCardNum) {
		this.tfCardNum = tfCardNum;
	}

	public String getImeiNum() {
		return imeiNum;
	}

	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}

	public String getSimNum() {
		return simNum;
	}

	public void setSimNum(String simNum) {
		this.simNum = simNum;
	}

	public BigDecimal getGpsX() {
		return gpsX;
	}

	public void setGpsX(BigDecimal gpsX) {
		this.gpsX = gpsX;
	}

	public BigDecimal getGpsY() {
		return gpsY;
	}

	public void setGpsY(BigDecimal gpsY) {
		this.gpsY = gpsY;
	}
    public String getSsxq_mc() {
		return ssxq_mc;
	}

	public void setSsxq_mc(String ssxq_mc) {
		this.ssxq_mc = ssxq_mc;
	}

	public String getSsjlxxq_mc() {
		return ssjlxxq_mc;
	}

	public void setSsjlxxq_mc(String ssjlxxq_mc) {
		this.ssjlxxq_mc = ssjlxxq_mc;
	}

	public String getSdpcs_mc() {
		return sdpcs_mc;
	}

	public void setSdpcs_mc(String sdpcs_mc) {
		this.sdpcs_mc = sdpcs_mc;
	}

	public String getJwzrq_mc() {
		return jwzrq_mc;
	}

	public void setJwzrq_mc(String jwzrq_mc) {
		this.jwzrq_mc = jwzrq_mc;
	}

	public String getFwgsdm() {
		return fwgsdm;
	}

	public void setFwgsdm(String fwgsdm) {
		this.fwgsdm = fwgsdm;
	}
    
}