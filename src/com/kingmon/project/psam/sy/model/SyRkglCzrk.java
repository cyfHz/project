package com.kingmon.project.psam.sy.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 常住人口
* @ClassName :SyRkglCzrk     
* @Description :   
* @createTime :2015年12月20日  上午9:34:58   
* @author ：zhaohuatai   
* @version :1.0
 */
public class SyRkglCzrk implements Serializable {
	/**^
	 * 人口ID-VARCHAR2(36)
	 */
    private String rkid;
    /**^
     * 户ID-VARCHAR2(36)
     */

    private String hid;

    /**^
     * 人口编号-VARCHAR2(36)
     */
    private String rkbh;

    /**…^
     * 公民身份号码-VARCHAR2(18)
     */
    private String gmsfhm;

    /**!^
     * 姓名-VARCHAR2(50)
     */
    private String xm;

    /**!
     * 曾用名-VARCHAR2(50)
     */
    private String zym;

    /**^
     * 性别-CHAR(1)
     */
    private String xb;

    /**!^
     * 出生日期-VARCHAR2(10)
     */
    private String csrq;

    /**!^
     * 民族-CHAR(2)
     */
    private String mz;
    //----------------------------------------------

    /**!
     * 常住户口所在地-VARCHAR2(500)
     */
    private String czhkszd;

    /**!
     * 常住户口所在地住址-VARCHAR2(500)
     */
    private String czhkszdzz;

    /**！
     * 常住户口所在地住址房间号-VARCHAR2(200)
     */
    private String czhkszdzzfjh;

    /**^
     *现居住地 -VARCHAR2(500)
     */
    private String xjzd;

    /**^
     * 现居住地住址-VARCHAR2(500)
     */
    private String xjzdzz;

    /**^
     * 现居住地住址房间号-VARCHAR2(50)
     */
    private String xjzdzzfjh;
//-------------------------------------------------
    /**^
     * 婚姻状况-VARCHAR2(36)
     */
    private String hyzk;

    /**^
     * 政治面貌-VARCHAR2(10)
     */
    private String zzmm;

    /**^
     * 学历-VARCHAR2(10)
     */
    private String xl;

    /**!
     * 固话-VARCHAR2(50)
     */
    private String lxfsgh;

    /**^
     * 手机-VARCHAR2(50)
     */
    private String lxfssj;

    /**!
     * 其它-VARCHAR2(50)
     */
    private String lxfsqt;

    /**^
     * 兵役状况-VARCHAR2(36)?
     */
    private String byzk;

    /**^
     * 职业-VARCHAR2(36)?
     */
    private String zy;

    /**!
     * 文化程度-VARCHAR2(36)?
     */
    private String whcd;

    /**
     * 服务处所-VARCHAR2(36)?
     */
    private String fwcs;

    /**!
     * 身高-NUMBER(6,2)
     */
    private BigDecimal sg;

    /**!
     * 血型-CHAR(2)
     */
    private String xx;

    /**!
     * 宗教信仰-VARCHAR2(36)
     */
    private String zjxy;
//-------------------------------------------------
    /**^
     * 与户主关系-VARCHAR2(36)
     */
    private String yhzgx;

    /**!
     * 出生时间-VARCHAR2(10)
     */
    private String cssj;

    /**!
     * 出生地国籍-VARCHAR2(200)
     */
    private String csdgj;

    /**!
     * 出生地省市县-VARCHAR2(200)
     */
    private String csdssx;

    /**!
     * 监控人公民身份号码-VARCHAR2(18)
     */
    private String jkrgmsfhm;

    /**!
     * 监护人姓名-VARCHAR2(50)
     */
    private String jhrxm;

    /**!
     * 监护人员关系-VARCHAR2(36)
     */
    private String jhrygx;

    /**!
     * 监护人二公民身份号码-VARCHAR2(18)
     */
    private String jhregmsfhm;

    /**!
     * 监护人二姓名-VARCHAR2(50)
     */
    private String jhrexm;

    /**!
     * 监护人二关系-VARCHAR2(36)
     */
    private String jhregx;

    /**!
     * 父亲公民身份号码-VARCHAR2(18)
     */
    private String fqgmsfhm;

    /**!
     * 父亲姓名-VARCHAR2(50)
     */
    private String fqxm;

    /**!
     * 母亲公民身份号码-VARCHAR2(18)
     */
    private String mqgmsfhm;

    /**!
     * 母亲姓名-VARCHAR2(50)
     */
    private String mqxm;

    /**!
     * 配偶公民身份号码-VARCHAR2(18)
     */
    private String pogmsfhm;

    /**!
     * 配偶姓名-VARCHAR2(50)
     */
    private String poxm;

    /**!
     * 籍贯国籍-VARCHAR2(200)
     */
    private String jggj;

    /**!
     * 籍贯省市县-VARCHAR2(200)
     */
    private String jgssx;
//---------------------------------------------------
    /**^
     * 所属县区-VARCHAR2(36)
     */
    private String ssxq;
    /**^
     * 业务逻辑用，不是数据库字段
     */
    private String ssxq_mc;
    /**^
     * 派出所-VARCHAR2(36)
     */
    private String pcs;
    /**^
     * 不是数据库字段，业务逻辑用
     */
    private String pcs_mc;

    /**^
     * 街路巷(村)-VARCHAR2(36)
     */
    private String jlxc;
    /**^
     * 业务逻辑用，不是数据库字段
     */
    private String jlxc_mc;

    /**
     * 乡镇街道-VARCHAR2(36)
     */
    private String xzjd;
    /**^
     * 业务逻辑用，不是数据库字段
     */
    private String xzjd_mc;

 

	/**^
     * 居委会-VARCHAR2(200)
     */
    private String jwh;

    /**^
     * 门楼牌号-VARCHAR2(200)
     */
    private String mlph;

    /**^
     * 门(楼)详址-VARCHAR2(200)
     */
    private String mlxz;
//-----------------------------------------------------------
    /**
     * 责任人-VARCHAR2(50)
     */
    private String zrr;

    /**
     * 户籍地详址-VARCHAR2(200)
     */
    private String hjdxz;

    /**^
     * 地址编码-VARCHAR2(36)
     */
    private String dzbm;

    /**^
     * 地址名称-VARCHAR2(200)
     */
    private String dzmc;

    /**^
     * 建筑物房间ID-VARCHAR2(36)
     */
    private String jzwfjid;

    /**^写建筑物的
     * PGIS坐标X(经度)-NUMBER(17,8)
     */
    private BigDecimal zbx;

    /**
     * PGIS坐标Y(纬度)-NUMBER(17,8)
     */
    private BigDecimal zby;
//----------------------------------------------
    /**
     * 信息补充_从业状况-VARCHAR2(36)
     */
    private String xxbcCyzk;

    /**
     * 信息补充_经济状况-VARCHAR2(36)
     */
    private String xxbcJjzk;

    /**
     * 信息补充_交通工具-VARCHAR2(100)
     */
    private String xxbcJtgj;

    /**
     * 信息补充_政治面貌-VARCHAR2(36)
     */
    private String xxbcZzmm;

    /**
     * 信息补充_服务场所-VARCHAR2(200)
     */
    private String xxbcFwcs;

    /**
     * 信息补充_是否自动比对-CHAR(1)
     */
    private String xxbcSfzdbd;

    /**
     * 信息补充_服务场所详址-VARCHAR2(200)
     */
    private String xxbcFwcsxz;

    /**^
     * 信息补充_QQ-VARCHAR2(36)
     */
    private String xxbcQq;

    /**^
     * 信息补充_微信-VARCHAR2(36)
     */
    private String xxbcWx;

    /**^
     * 信息补充_微博-VARCHAR2(36)
     */
    private String xxbcWb;

    /**
     * 信息补充_MSN-VARCHAR2(36)
     */
    private String xxbcMsn;

    /**^
     * 信息补充_电子邮箱-VARCHAR2(36)
     */
    private String xxbcDzyx;

    /**
     * 信息补充_联系电话-VARCHAR2(20)
     */
    private String xxbcLxdh;

    /**
     * 信息补充_是否人户分离-CHAR(1)
     */
    private String xxbcSfrhfl;

    /**
     * 信息补充_人口类别-VARCHAR2(36)
     */
    private String xxbcRklb;

    /**
     * 信息补充_现住地详址-VARCHAR2(200)v
     */
    private String xxbcXzdxz;
//-----------------------------------------------------------
    /**^
     * 所属派出所-VARCHAR2(20)
     */
    private String sspcs;

    /**^
     * 警务责任区代码-VARCHAR2(36)
     */
    private String jwzrqdm;
    /**^
     * 业务逻辑用，不是数据库字段
     */
    private String jwzrq_mc;
  

	/**^
     * 备注-VARCHAR2(200)
     */
    private String bz;

    /**^
     * 注销状态(1已注销;0未注销)-VARCHAR2(2)
     */
    private String zxzt;

    /**
     * 注销日期-DATE
     */
    private Date zxrq;

    /**
     * 删除标记（0-未删除，1-已删除）-CHAR(1)
     */
    private String deltag;

    /**
     * 删除时间-DATE
     */
    private Date deltime;

    /**^
     * 登记人-VARCHAR2(36)
     */
    private String djr;

    /**^
     * 登记单位-VARCHAR2(36)
     */
    private String djdw;

    /**^
     * 登记人名称-VARCHAR2(100)
     */
    private String djrmc;

    /**^
     * 登记单位名称-VARCHAR2(200)
     */
    private String djdwmc;

    /**^
     * 登记时间-DATE
     */
    private Date djsj;

    /**^
     * 修改人-VARCHAR2(36)
     */
    private String xgr;

    /**^
     * 修改单位-VARCHAR2(36)
     */
    private String xgdw;

    /**^
     * 修改人名称-VARCHAR2(100)
     */
    private String xgrmc;

    /**^
     * 修改单位名称-VARCHAR2(200)
     */
    private String xgdwmc;

    /**^
     * 更新时间-DATE
     */
    private Date gxsj;

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

    /**^
     * 录入方式(后台系统0,移动终端1)-CHAR(1)
     */
    private String lrfs;

    /**
     * 录入网络(互联网E,公安网A)-CHAR(1)
     */
    private String lrwl;
    /**
     * 业务逻辑用，非数据库字段
     */
    private String jzwid;
    
    /**
     * 新增字段-16-06-06
     * Mac地址
     * MAC_ADDRESS varchar2(20);
     */
    private String macAddress;
    
    /**
     * 新增字段-16-06-06
     * TF卡号 varchar2(36);
     */
    private String tfCardNum;
    
    /**
     * 新增字段-16-06-06
     * IMEI号 varchar2(20);
     */
    private String imeiNum;
    
    /**
     * 新增字段-16-06-06
     * SIM卡号-varchar2(20)
     */
    private String simNum;

    public String getJzwid() {
		return jzwid;
	}

	public void setJzwid(String jzwid) {
		this.jzwid = jzwid;
	}

	public String getRkid() {
        return rkid;
    }

    public void setRkid(String rkid) {
        this.rkid = rkid == null ? null : rkid.trim();
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid == null ? null : hid.trim();
    }

    public String getRkbh() {
        return rkbh;
    }

    public void setRkbh(String rkbh) {
        this.rkbh = rkbh == null ? null : rkbh.trim();
    }

    public String getGmsfhm() {
        return gmsfhm;
    }

    public void setGmsfhm(String gmsfhm) {
        this.gmsfhm = gmsfhm == null ? null : gmsfhm.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }

    public String getZym() {
        return zym;
    }

    public void setZym(String zym) {
        this.zym = zym == null ? null : zym.trim();
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb == null ? null : xb.trim();
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq == null ? null : csrq.trim();
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz == null ? null : mz.trim();
    }

    public String getCzhkszd() {
        return czhkszd;
    }

    public void setCzhkszd(String czhkszd) {
        this.czhkszd = czhkszd == null ? null : czhkszd.trim();
    }

    public String getCzhkszdzz() {
        return czhkszdzz;
    }

    public void setCzhkszdzz(String czhkszdzz) {
        this.czhkszdzz = czhkszdzz == null ? null : czhkszdzz.trim();
    }

    public String getCzhkszdzzfjh() {
        return czhkszdzzfjh;
    }

    public void setCzhkszdzzfjh(String czhkszdzzfjh) {
        this.czhkszdzzfjh = czhkszdzzfjh == null ? null : czhkszdzzfjh.trim();
    }

    public String getXjzd() {
        return xjzd;
    }

    public void setXjzd(String xjzd) {
        this.xjzd = xjzd == null ? null : xjzd.trim();
    }

    public String getXjzdzz() {
        return xjzdzz;
    }

    public void setXjzdzz(String xjzdzz) {
        this.xjzdzz = xjzdzz == null ? null : xjzdzz.trim();
    }

    public String getXjzdzzfjh() {
        return xjzdzzfjh;
    }

    public void setXjzdzzfjh(String xjzdzzfjh) {
        this.xjzdzzfjh = xjzdzzfjh == null ? null : xjzdzzfjh.trim();
    }

    public String getHyzk() {
        return hyzk;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk == null ? null : hyzk.trim();
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm == null ? null : zzmm.trim();
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl == null ? null : xl.trim();
    }

    public String getLxfsgh() {
        return lxfsgh;
    }

    public void setLxfsgh(String lxfsgh) {
        this.lxfsgh = lxfsgh == null ? null : lxfsgh.trim();
    }

    public String getLxfssj() {
        return lxfssj;
    }

    public void setLxfssj(String lxfssj) {
        this.lxfssj = lxfssj == null ? null : lxfssj.trim();
    }

    public String getLxfsqt() {
        return lxfsqt;
    }

    public void setLxfsqt(String lxfsqt) {
        this.lxfsqt = lxfsqt == null ? null : lxfsqt.trim();
    }

    public String getByzk() {
        return byzk;
    }

    public void setByzk(String byzk) {
        this.byzk = byzk == null ? null : byzk.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public String getWhcd() {
        return whcd;
    }

    public void setWhcd(String whcd) {
        this.whcd = whcd == null ? null : whcd.trim();
    }

    public String getFwcs() {
        return fwcs;
    }

    public void setFwcs(String fwcs) {
        this.fwcs = fwcs == null ? null : fwcs.trim();
    }

    public BigDecimal getSg() {
        return sg;
    }

    public void setSg(BigDecimal sg) {
        this.sg = sg;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx == null ? null : xx.trim();
    }

    public String getZjxy() {
        return zjxy;
    }

    public void setZjxy(String zjxy) {
        this.zjxy = zjxy == null ? null : zjxy.trim();
    }

    public String getYhzgx() {
        return yhzgx;
    }

    public void setYhzgx(String yhzgx) {
        this.yhzgx = yhzgx == null ? null : yhzgx.trim();
    }

    public String getCssj() {
        return cssj;
    }

    public void setCssj(String cssj) {
        this.cssj = cssj == null ? null : cssj.trim();
    }

    public String getCsdgj() {
        return csdgj;
    }

    public void setCsdgj(String csdgj) {
        this.csdgj = csdgj == null ? null : csdgj.trim();
    }

    public String getCsdssx() {
        return csdssx;
    }

    public void setCsdssx(String csdssx) {
        this.csdssx = csdssx == null ? null : csdssx.trim();
    }

    public String getJkrgmsfhm() {
        return jkrgmsfhm;
    }

    public void setJkrgmsfhm(String jkrgmsfhm) {
        this.jkrgmsfhm = jkrgmsfhm == null ? null : jkrgmsfhm.trim();
    }

    public String getJhrxm() {
        return jhrxm;
    }

    public void setJhrxm(String jhrxm) {
        this.jhrxm = jhrxm == null ? null : jhrxm.trim();
    }

    public String getJhrygx() {
        return jhrygx;
    }

    public void setJhrygx(String jhrygx) {
        this.jhrygx = jhrygx == null ? null : jhrygx.trim();
    }

    public String getJhregmsfhm() {
        return jhregmsfhm;
    }

    public void setJhregmsfhm(String jhregmsfhm) {
        this.jhregmsfhm = jhregmsfhm == null ? null : jhregmsfhm.trim();
    }

    public String getJhrexm() {
        return jhrexm;
    }

    public void setJhrexm(String jhrexm) {
        this.jhrexm = jhrexm == null ? null : jhrexm.trim();
    }

    public String getJhregx() {
        return jhregx;
    }

    public void setJhregx(String jhregx) {
        this.jhregx = jhregx == null ? null : jhregx.trim();
    }

    public String getFqgmsfhm() {
        return fqgmsfhm;
    }

    public void setFqgmsfhm(String fqgmsfhm) {
        this.fqgmsfhm = fqgmsfhm == null ? null : fqgmsfhm.trim();
    }

    public String getFqxm() {
        return fqxm;
    }

    public void setFqxm(String fqxm) {
        this.fqxm = fqxm == null ? null : fqxm.trim();
    }

    public String getMqgmsfhm() {
        return mqgmsfhm;
    }

    public void setMqgmsfhm(String mqgmsfhm) {
        this.mqgmsfhm = mqgmsfhm == null ? null : mqgmsfhm.trim();
    }

    public String getMqxm() {
        return mqxm;
    }

    public void setMqxm(String mqxm) {
        this.mqxm = mqxm == null ? null : mqxm.trim();
    }

    public String getPogmsfhm() {
        return pogmsfhm;
    }

    public void setPogmsfhm(String pogmsfhm) {
        this.pogmsfhm = pogmsfhm == null ? null : pogmsfhm.trim();
    }

    public String getPoxm() {
        return poxm;
    }

    public void setPoxm(String poxm) {
        this.poxm = poxm == null ? null : poxm.trim();
    }

    public String getJggj() {
        return jggj;
    }

    public void setJggj(String jggj) {
        this.jggj = jggj == null ? null : jggj.trim();
    }

    public String getJgssx() {
        return jgssx;
    }

    public void setJgssx(String jgssx) {
        this.jgssx = jgssx == null ? null : jgssx.trim();
    }

    public String getSsxq() {
        return ssxq;
    }

    public void setSsxq(String ssxq) {
        this.ssxq = ssxq == null ? null : ssxq.trim();
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs == null ? null : pcs.trim();
    }

    public String getJlxc() {
        return jlxc;
    }

    public void setJlxc(String jlxc) {
        this.jlxc = jlxc == null ? null : jlxc.trim();
    }

    public String getXzjd() {
        return xzjd;
    }

    public void setXzjd(String xzjd) {
        this.xzjd = xzjd == null ? null : xzjd.trim();
    }

    public String getJwh() {
        return jwh;
    }

    public void setJwh(String jwh) {
        this.jwh = jwh == null ? null : jwh.trim();
    }

    public String getMlph() {
        return mlph;
    }

    public void setMlph(String mlph) {
        this.mlph = mlph == null ? null : mlph.trim();
    }

    public String getMlxz() {
        return mlxz;
    }

    public void setMlxz(String mlxz) {
        this.mlxz = mlxz == null ? null : mlxz.trim();
    }

    public String getZrr() {
        return zrr;
    }

    public void setZrr(String zrr) {
        this.zrr = zrr == null ? null : zrr.trim();
    }

    public String getHjdxz() {
        return hjdxz;
    }

    public void setHjdxz(String hjdxz) {
        this.hjdxz = hjdxz == null ? null : hjdxz.trim();
    }

    public String getDzbm() {
        return dzbm;
    }

    public void setDzbm(String dzbm) {
        this.dzbm = dzbm == null ? null : dzbm.trim();
    }

    public String getDzmc() {
        return dzmc;
    }

    public void setDzmc(String dzmc) {
        this.dzmc = dzmc == null ? null : dzmc.trim();
    }

    public String getJzwfjid() {
        return jzwfjid;
    }

    public void setJzwfjid(String jzwfjid) {
        this.jzwfjid = jzwfjid == null ? null : jzwfjid.trim();
    }

    public BigDecimal getZbx() {
        return zbx;
    }

    public void setZbx(BigDecimal zbx) {
        this.zbx = zbx;
    }

    public BigDecimal getZby() {
        return zby;
    }

    public void setZby(BigDecimal zby) {
        this.zby = zby;
    }

    public String getXxbcCyzk() {
        return xxbcCyzk;
    }

    public void setXxbcCyzk(String xxbcCyzk) {
        this.xxbcCyzk = xxbcCyzk == null ? null : xxbcCyzk.trim();
    }

    public String getXxbcJjzk() {
        return xxbcJjzk;
    }

    public void setXxbcJjzk(String xxbcJjzk) {
        this.xxbcJjzk = xxbcJjzk == null ? null : xxbcJjzk.trim();
    }

    public String getXxbcJtgj() {
        return xxbcJtgj;
    }

    public void setXxbcJtgj(String xxbcJtgj) {
        this.xxbcJtgj = xxbcJtgj == null ? null : xxbcJtgj.trim();
    }

    public String getXxbcZzmm() {
        return xxbcZzmm;
    }

    public void setXxbcZzmm(String xxbcZzmm) {
        this.xxbcZzmm = xxbcZzmm == null ? null : xxbcZzmm.trim();
    }

    public String getXxbcFwcs() {
        return xxbcFwcs;
    }

    public void setXxbcFwcs(String xxbcFwcs) {
        this.xxbcFwcs = xxbcFwcs == null ? null : xxbcFwcs.trim();
    }

    public String getXxbcSfzdbd() {
        return xxbcSfzdbd;
    }

    public void setXxbcSfzdbd(String xxbcSfzdbd) {
        this.xxbcSfzdbd = xxbcSfzdbd == null ? null : xxbcSfzdbd.trim();
    }

    public String getXxbcFwcsxz() {
        return xxbcFwcsxz;
    }

    public void setXxbcFwcsxz(String xxbcFwcsxz) {
        this.xxbcFwcsxz = xxbcFwcsxz == null ? null : xxbcFwcsxz.trim();
    }

    public String getXxbcQq() {
        return xxbcQq;
    }

    public void setXxbcQq(String xxbcQq) {
        this.xxbcQq = xxbcQq == null ? null : xxbcQq.trim();
    }

    public String getXxbcWx() {
        return xxbcWx;
    }

    public void setXxbcWx(String xxbcWx) {
        this.xxbcWx = xxbcWx == null ? null : xxbcWx.trim();
    }

    public String getXxbcWb() {
        return xxbcWb;
    }

    public void setXxbcWb(String xxbcWb) {
        this.xxbcWb = xxbcWb == null ? null : xxbcWb.trim();
    }

    public String getXxbcMsn() {
        return xxbcMsn;
    }

    public void setXxbcMsn(String xxbcMsn) {
        this.xxbcMsn = xxbcMsn == null ? null : xxbcMsn.trim();
    }

    public String getXxbcDzyx() {
        return xxbcDzyx;
    }

    public void setXxbcDzyx(String xxbcDzyx) {
        this.xxbcDzyx = xxbcDzyx == null ? null : xxbcDzyx.trim();
    }

    public String getXxbcLxdh() {
        return xxbcLxdh;
    }

    public void setXxbcLxdh(String xxbcLxdh) {
        this.xxbcLxdh = xxbcLxdh == null ? null : xxbcLxdh.trim();
    }

    public String getXxbcSfrhfl() {
        return xxbcSfrhfl;
    }

    public void setXxbcSfrhfl(String xxbcSfrhfl) {
        this.xxbcSfrhfl = xxbcSfrhfl == null ? null : xxbcSfrhfl.trim();
    }

    public String getXxbcRklb() {
        return xxbcRklb;
    }

    public void setXxbcRklb(String xxbcRklb) {
        this.xxbcRklb = xxbcRklb == null ? null : xxbcRklb.trim();
    }

    public String getXxbcXzdxz() {
        return xxbcXzdxz;
    }

    public void setXxbcXzdxz(String xxbcXzdxz) {
        this.xxbcXzdxz = xxbcXzdxz == null ? null : xxbcXzdxz.trim();
    }

    public String getSspcs() {
        return sspcs;
    }

    public void setSspcs(String sspcs) {
        this.sspcs = sspcs == null ? null : sspcs.trim();
    }

    public String getJwzrqdm() {
        return jwzrqdm;
    }

    public void setJwzrqdm(String jwzrqdm) {
        this.jwzrqdm = jwzrqdm == null ? null : jwzrqdm.trim();
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
    public String getSsxq_mc() {
 		return ssxq_mc;
 	}

 	public void setSsxq_mc(String ssxq_mc) {
 		this.ssxq_mc = ssxq_mc;
 	}

 	public String getPcs_mc() {
 		return pcs_mc;
 	}

 	public void setPcs_mc(String pcs_mc) {
 		this.pcs_mc = pcs_mc;
 	}

 	public String getJlxc_mc() {
 		return jlxc_mc;
 	}

 	public void setJlxc_mc(String jlxc_mc) {
 		this.jlxc_mc = jlxc_mc;
 	}

 	public String getXzjd_mc() {
 		return xzjd_mc;
 	}

 	public void setXzjd_mc(String xzjd_mc) {
 		this.xzjd_mc = xzjd_mc;
 	}
 	  public String getJwzrq_mc() {
 			return jwzrq_mc;
 		}

 		public void setJwzrq_mc(String jwzrq_mc) {
 			this.jwzrq_mc = jwzrq_mc;
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
 	
 		
}