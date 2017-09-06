package com.kingmon.project.psam.sy.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *实有境外人员
* @ClassName :Syjwry     
* @Description :   
* @createTime :2015年12月20日  上午9:34:41   
* @author ：zhaohuatai   
* @version :1.0
 */
public class Syjwry implements Serializable {
	
	/**^
	 * 境外人员ID-VARCHAR2(36)
	 */
    private String jwryid;
	
	/**^
	 * 英文名-VARCHAR2(50)
	 */
    private String ywm;
	
	/**^
	 * 中文名-VARCHAR2(50)
	 */
    private String zwm;
	
	/**^
	 * 出生日期-DATE
	 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date csrq;
	
	/**^
	 * 国籍,参照GA59.7-1993 -VARCHAR2(36)
	 */
    private String gj;
	
	/**
	 * 职业-VARCHAR2(200)
	 */
    private String zy;
	
	/**
	 * 在华工作机构名称-VARCHAR2(200)
	 */
    private String zhgzjgmc;
	
	/**
	 * 在华工作机构代码-VARCHAR2(36)
	 */
    private String zhgzjgdm;
	
	/**^
	 * 证件种类,参照AG59.8-1993-VARCHAR2(36)
	 */
    private String zjzl;
	
	/**^
	 * 证件号码-VARCHAR2(50)
	 */
    private String zjhm;
	
	/**^
	 * 签证种类-VARCHAR2(36)
	 */
    private String qzzl;
	
	/**^
	 * 签证编号-VARCHAR2(50)
	 */
    private String qzbh;
	
	/**^
	 * 停留有效期-DATE
	 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tlyxq;
	
	/**^
	 * 境外人员联系电话-VARCHAR2(50)
	 */
    private String jwrylxdh;
	
	/**^
	 * 省市县（区）-VARCHAR2(36)
	 */
    private String ssxqdm;
    /**^
	 * 业务逻辑用-非数据库字段
	 */
    private String ssxqdm_mc;
	/**^
	 * 所属街路巷(小区)_地址编码-VARCHAR2(36)
	 */
    private String ssjlxxqDzbm;
    /**^
	 * 业务逻辑用-非数据库字段
	 */
    private String ssjlxxqDzbm_mc;
	
	

	/**^建筑物编码
	 * 地址编码-VARCHAR2(36)
	 */
    private String dzbm;
	
	/**^
	 * 房间编码-VARCHAR2(36)
	 */
    private String fjbm;
	
	/**^
	 * 房间号-VARCHAR2(36)
	 */
    private String fjh;
	
	/**^
	 * 现居住地详细地址-VARCHAR2(200)
	 */
    private String xxdz;
	
	/**^
	 * 房屋房主姓名-VARCHAR2(50)
	 */
    private String fwfzxm;
	
	/**^
	 * 房主身份证号-VARCHAR2(20)
	 */
    private String fzsfzh;
	
	/**
	 * 境外人员离开日期-DATE
	 */
    private Date jwrylkrq;
	
	/**^
	 * 备注-VARCHAR2(500)
	 */
    private String bz;
	
	/**^
	 * 属地派出所-VARCHAR2(36)
	 */
    private String sdpcs;
    /**
	 * 业务逻辑用 非数据字段
	 */
    private String sdpcs_mc;
	public String getSdpcs_mc() {
		return sdpcs_mc;
	}

	public void setSdpcs_mc(String sdpcs_mc) {
		this.sdpcs_mc = sdpcs_mc;
	}

	/**
	 * 警务责任区-VARCHAR2(36)
	 */
    private String jwzrq;
    /**
   	 * 业务逻辑用 非数据字段
   	 */
       private String jwzrq_mc;
	

	/**
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
	 * 现住址区划-VARCHAR2(20)
	 */
    private String xzzqh;
	
	/**
	 * "英文姓"-VARCHAR2(50)
	 */
    private String ywx;
	
	/**
	 * "现住址所属分局代码"-VARCHAR2(20)
	 */
    private String xzzssfjdm;
	
	/**
	 * "人员类别?"-VARCHAR2(2)
	 */
    private String rylb;
	
	/**
	 * "国家代码?"-VARCHAR2(10)
	 */
    private String gjdm;
	
	/**
	 * 现住址户编码-VARCHAR2(50)
	 */
    private String xzzhuid;
	
	/**
	 * 采集人员?-VARCHAR2(50)
	 */
    private String cjry;
	
	/**
	 * 采集时间-DATE
	 */
    private Date cjsj;
	
	/**
	 * 人口分类?-VARCHAR2(50)
	 */
    private String rkfl;
	
	/**
	 * 来华身份-VARCHAR2(50)
	 */
    private String lhsf;
	
	/**
	 * 中文水平-VARCHAR2(50)
	 */
    private String zwsp;
	
	/**
	 * 入境口岸-VARCHAR2(50)
	 */
    private String rjka;
	
	/**
	 * 入境日期-DATE
	 */
    private Date rjrq;
	
	/**
	 * "签证次数"-VARCHAR2(10)
	 */
    private String qzcs;
	
	/**
	 * 居留情况:1.居留许可, 2.居留签注 3.其他-VARCHAR2(50)
	 */
    private String jlqk;
	
	/**
	 * 居留许可号码-VARCHAR2(20)
	 */
    private String jlxkhm;
	
	/**
	 * 居留事由-VARCHAR2(100)
	 */
    private String jlsy;
	
	/**
	 * 签发机关-VARCHAR2(50)
	 */
    private String qfjg;
	
	/**
	 * "工作学习身份"-VARCHAR2(50)
	 */
    private String gzxxsf;
	
	/**
	 * 任职日期-DATE
	 */
    private Date rzrq;
	
	/**
	 * 离职日期-DATE
	 */
    private Date lzrq;
	
	/**
	 * 入住方式:1.租房,2.购房 3.建房 4.其他-VARCHAR2(20)
	 */
    private String rzfs;
	
	/**
	 * 入住时间-DATE
	 */
    private Date rzsj;
	
	/**
	 * 住所联系方式-VARCHAR2(20)
	 */
    private String zslxfs;
	
	/**
	 * 居住地性质 1.星级宾馆 2. 普通旅馆 3.居住房 4.商住楼 5.单位内部房 6.其他住宿房-VARCHAR2(50)
	 */
    private String jzdxz;
	
	/**
	 * 随行人员亲属关系1-VARCHAR2(20)
	 */
    private String sxryqsgx1;
	
	/**
	 * 随行人员中文姓名1-VARCHAR2(20)
	 */
    private String sxryzwxm1;
	
	/**
	 * 随行人员英文姓名1--VARCHAR2(20)
	 */
    private String sxryywxm1;
	
	/**
	 * 随行人员性别1-VARCHAR2(10)
	 */
    private String sxryxb1;
	
	/**
	 * 随行人员出生日期1-DATE
	 */
    private Date sxrycsrq1;
	
	/**
	 * 随行人员单位名称1-VARCHAR2(100)
	 */
    private String sxrydwmc1;
	
	/**
	 * 随行人员亲属关系2-VARCHAR2(20)
	 */
    private String sxryqsgx2;
	
	/**
	 *随行人员中文姓名2-VARCHAR2(20) 
	 */
    private String sxryzwxm2;
	
	/**
	 * 随行人员英文姓名2-VARCHAR2(20)
	 */
    private String sxryywxm2;
	
	/**
	 * 随行人员性别2-VARCHAR2(10)
	 */
    private String sxryxb2;
	
	/**
	 * 随行人员出生日期2-DATE
	 */
    private Date sxrycsrq2;
	
	/**
	 * 随行人员单位名称2-VARCHAR2(100)
	 */
    private String sxrydwmc2;
	
	/**
	 * 采集人员联系电话-VARCHAR2(20)
	 */
    private String cjrylxdh;
	
	/**
	 * 统计标志 0 未统计 1 统计-VARCHAR2(2)
	 */
    private String tjflg;
	
	/**
	 * 
	 */
    private Date xzzhuidgxsj;
	
	/**
	 * 境外人员照片-BLOB
	 */
    private byte[] jwryzp;
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

    public String getJwryid() {
        return jwryid;
    }

    public void setJwryid(String jwryid) {
        this.jwryid = jwryid == null ? null : jwryid.trim();
    }

    public String getYwm() {
        return ywm;
    }

    public void setYwm(String ywm) {
        this.ywm = ywm == null ? null : ywm.trim();
    }

    public String getZwm() {
        return zwm;
    }

    public void setZwm(String zwm) {
        this.zwm = zwm == null ? null : zwm.trim();
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj == null ? null : gj.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public String getZhgzjgmc() {
        return zhgzjgmc;
    }

    public void setZhgzjgmc(String zhgzjgmc) {
        this.zhgzjgmc = zhgzjgmc == null ? null : zhgzjgmc.trim();
    }

    public String getZhgzjgdm() {
        return zhgzjgdm;
    }

    public void setZhgzjgdm(String zhgzjgdm) {
        this.zhgzjgdm = zhgzjgdm == null ? null : zhgzjgdm.trim();
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl == null ? null : zjzl.trim();
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm == null ? null : zjhm.trim();
    }

    public String getQzzl() {
        return qzzl;
    }

    public void setQzzl(String qzzl) {
        this.qzzl = qzzl == null ? null : qzzl.trim();
    }

    public String getQzbh() {
        return qzbh;
    }

    public void setQzbh(String qzbh) {
        this.qzbh = qzbh == null ? null : qzbh.trim();
    }

    public Date getTlyxq() {
        return tlyxq;
    }

    public void setTlyxq(Date tlyxq) {
        this.tlyxq = tlyxq;
    }

    public String getJwrylxdh() {
        return jwrylxdh;
    }

    public void setJwrylxdh(String jwrylxdh) {
        this.jwrylxdh = jwrylxdh == null ? null : jwrylxdh.trim();
    }

    public String getSsxqdm() {
        return ssxqdm;
    }

    public void setSsxqdm(String ssxqdm) {
        this.ssxqdm = ssxqdm == null ? null : ssxqdm.trim();
    }

    public String getSsjlxxqDzbm() {
        return ssjlxxqDzbm;
    }

    public void setSsjlxxqDzbm(String ssjlxxqDzbm) {
        this.ssjlxxqDzbm = ssjlxxqDzbm == null ? null : ssjlxxqDzbm.trim();
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

    public String getXxdz() {
        return xxdz;
    }

    public void setXxdz(String xxdz) {
        this.xxdz = xxdz == null ? null : xxdz.trim();
    }

    public String getFwfzxm() {
        return fwfzxm;
    }

    public void setFwfzxm(String fwfzxm) {
        this.fwfzxm = fwfzxm == null ? null : fwfzxm.trim();
    }

    public String getFzsfzh() {
        return fzsfzh;
    }

    public void setFzsfzh(String fzsfzh) {
        this.fzsfzh = fzsfzh == null ? null : fzsfzh.trim();
    }

    public Date getJwrylkrq() {
        return jwrylkrq;
    }

    public void setJwrylkrq(Date jwrylkrq) {
        this.jwrylkrq = jwrylkrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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

    public String getXzzqh() {
        return xzzqh;
    }

    public void setXzzqh(String xzzqh) {
        this.xzzqh = xzzqh == null ? null : xzzqh.trim();
    }

    public String getYwx() {
        return ywx;
    }

    public void setYwx(String ywx) {
        this.ywx = ywx == null ? null : ywx.trim();
    }

    public String getXzzssfjdm() {
        return xzzssfjdm;
    }

    public void setXzzssfjdm(String xzzssfjdm) {
        this.xzzssfjdm = xzzssfjdm == null ? null : xzzssfjdm.trim();
    }

    public String getRylb() {
        return rylb;
    }

    public void setRylb(String rylb) {
        this.rylb = rylb == null ? null : rylb.trim();
    }

    public String getGjdm() {
        return gjdm;
    }

    public void setGjdm(String gjdm) {
        this.gjdm = gjdm == null ? null : gjdm.trim();
    }

    public String getXzzhuid() {
        return xzzhuid;
    }

    public void setXzzhuid(String xzzhuid) {
        this.xzzhuid = xzzhuid == null ? null : xzzhuid.trim();
    }

    public String getCjry() {
        return cjry;
    }

    public void setCjry(String cjry) {
        this.cjry = cjry == null ? null : cjry.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getRkfl() {
        return rkfl;
    }

    public void setRkfl(String rkfl) {
        this.rkfl = rkfl == null ? null : rkfl.trim();
    }

    public String getLhsf() {
        return lhsf;
    }

    public void setLhsf(String lhsf) {
        this.lhsf = lhsf == null ? null : lhsf.trim();
    }

    public String getZwsp() {
        return zwsp;
    }

    public void setZwsp(String zwsp) {
        this.zwsp = zwsp == null ? null : zwsp.trim();
    }

    public String getRjka() {
        return rjka;
    }

    public void setRjka(String rjka) {
        this.rjka = rjka == null ? null : rjka.trim();
    }

    public Date getRjrq() {
        return rjrq;
    }

    public void setRjrq(Date rjrq) {
        this.rjrq = rjrq;
    }

    public String getQzcs() {
        return qzcs;
    }

    public void setQzcs(String qzcs) {
        this.qzcs = qzcs == null ? null : qzcs.trim();
    }

    public String getJlqk() {
        return jlqk;
    }

    public void setJlqk(String jlqk) {
        this.jlqk = jlqk == null ? null : jlqk.trim();
    }

    public String getJlxkhm() {
        return jlxkhm;
    }

    public void setJlxkhm(String jlxkhm) {
        this.jlxkhm = jlxkhm == null ? null : jlxkhm.trim();
    }

    public String getJlsy() {
        return jlsy;
    }

    public void setJlsy(String jlsy) {
        this.jlsy = jlsy == null ? null : jlsy.trim();
    }

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg == null ? null : qfjg.trim();
    }

    public String getGzxxsf() {
        return gzxxsf;
    }

    public void setGzxxsf(String gzxxsf) {
        this.gzxxsf = gzxxsf == null ? null : gzxxsf.trim();
    }

    public Date getRzrq() {
        return rzrq;
    }

    public void setRzrq(Date rzrq) {
        this.rzrq = rzrq;
    }

    public Date getLzrq() {
        return lzrq;
    }

    public void setLzrq(Date lzrq) {
        this.lzrq = lzrq;
    }

    public String getRzfs() {
        return rzfs;
    }

    public void setRzfs(String rzfs) {
        this.rzfs = rzfs == null ? null : rzfs.trim();
    }

    public Date getRzsj() {
        return rzsj;
    }

    public void setRzsj(Date rzsj) {
        this.rzsj = rzsj;
    }

    public String getZslxfs() {
        return zslxfs;
    }

    public void setZslxfs(String zslxfs) {
        this.zslxfs = zslxfs == null ? null : zslxfs.trim();
    }

    public String getJzdxz() {
        return jzdxz;
    }

    public void setJzdxz(String jzdxz) {
        this.jzdxz = jzdxz == null ? null : jzdxz.trim();
    }

    public String getSxryqsgx1() {
        return sxryqsgx1;
    }

    public void setSxryqsgx1(String sxryqsgx1) {
        this.sxryqsgx1 = sxryqsgx1 == null ? null : sxryqsgx1.trim();
    }

    public String getSxryzwxm1() {
        return sxryzwxm1;
    }

    public void setSxryzwxm1(String sxryzwxm1) {
        this.sxryzwxm1 = sxryzwxm1 == null ? null : sxryzwxm1.trim();
    }

    public String getSxryywxm1() {
        return sxryywxm1;
    }

    public void setSxryywxm1(String sxryywxm1) {
        this.sxryywxm1 = sxryywxm1 == null ? null : sxryywxm1.trim();
    }

    public String getSxryxb1() {
        return sxryxb1;
    }

    public void setSxryxb1(String sxryxb1) {
        this.sxryxb1 = sxryxb1 == null ? null : sxryxb1.trim();
    }

    public Date getSxrycsrq1() {
        return sxrycsrq1;
    }

    public void setSxrycsrq1(Date sxrycsrq1) {
        this.sxrycsrq1 = sxrycsrq1;
    }

    public String getSxrydwmc1() {
        return sxrydwmc1;
    }

    public void setSxrydwmc1(String sxrydwmc1) {
        this.sxrydwmc1 = sxrydwmc1 == null ? null : sxrydwmc1.trim();
    }

    public String getSxryqsgx2() {
        return sxryqsgx2;
    }

    public void setSxryqsgx2(String sxryqsgx2) {
        this.sxryqsgx2 = sxryqsgx2 == null ? null : sxryqsgx2.trim();
    }

    public String getSxryzwxm2() {
        return sxryzwxm2;
    }

    public void setSxryzwxm2(String sxryzwxm2) {
        this.sxryzwxm2 = sxryzwxm2 == null ? null : sxryzwxm2.trim();
    }

    public String getSxryywxm2() {
        return sxryywxm2;
    }

    public void setSxryywxm2(String sxryywxm2) {
        this.sxryywxm2 = sxryywxm2 == null ? null : sxryywxm2.trim();
    }

    public String getSxryxb2() {
        return sxryxb2;
    }

    public void setSxryxb2(String sxryxb2) {
        this.sxryxb2 = sxryxb2 == null ? null : sxryxb2.trim();
    }

    public Date getSxrycsrq2() {
        return sxrycsrq2;
    }

    public void setSxrycsrq2(Date sxrycsrq2) {
        this.sxrycsrq2 = sxrycsrq2;
    }

    public String getSxrydwmc2() {
        return sxrydwmc2;
    }

    public void setSxrydwmc2(String sxrydwmc2) {
        this.sxrydwmc2 = sxrydwmc2 == null ? null : sxrydwmc2.trim();
    }

    public String getCjrylxdh() {
        return cjrylxdh;
    }

    public void setCjrylxdh(String cjrylxdh) {
        this.cjrylxdh = cjrylxdh == null ? null : cjrylxdh.trim();
    }

    public String getTjflg() {
        return tjflg;
    }

    public void setTjflg(String tjflg) {
        this.tjflg = tjflg == null ? null : tjflg.trim();
    }

    public Date getXzzhuidgxsj() {
        return xzzhuidgxsj;
    }

    public void setXzzhuidgxsj(Date xzzhuidgxsj) {
        this.xzzhuidgxsj = xzzhuidgxsj;
    }

    public byte[] getJwryzp() {
        return jwryzp;
    }

    public void setJwryzp(byte[] jwryzp) {
        this.jwryzp = jwryzp;
    }
    public String getSsxqdm_mc() {
		return ssxqdm_mc;
	}

	public void setSsxqdm_mc(String ssxqdm_mc) {
		this.ssxqdm_mc = ssxqdm_mc;
	}

	public String getSsjlxxqDzbm_mc() {
		return ssjlxxqDzbm_mc;
	}

	public void setSsjlxxqDzbm_mc(String ssjlxxqDzbm_mc) {
		this.ssjlxxqDzbm_mc = ssjlxxqDzbm_mc;
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