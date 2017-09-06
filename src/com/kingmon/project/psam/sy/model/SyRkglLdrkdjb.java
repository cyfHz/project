package com.kingmon.project.psam.sy.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 流动人口登记表
* @ClassName :SyRkglLdrkdjb     
* @Description :   
* @createTime :2015年12月20日  上午9:35:41   
* @author ：zhaohuatai   
* @version :1.0
 */
public class SyRkglLdrkdjb implements Serializable {
	/**^
	 * 流动ID-VARCHAR2(36)
	 */
    private String ldid;

	/**^
	 * 自动生成的编码-VARCHAR2(50)
	 */
    private String xlh;

	/**……=
	 * 自增ID-VARCHAR2(50)
	 */
    private String id;

	/**^
	 * 姓名-VARCHAR2(50)
	 */
    private String xm;

	/**^
	 * 别名-VARCHAR2(50)
	 */
    private String bm;

	/**^
	 * 性别-VARCHAR2(10)
	 */
    private String xb;

	/**^
	 * 民族-VARCHAR2(10)
	 */
    private String mz;

	/**^
	 * 身份证号-VARCHAR2(20)
	 */
    private String sfzh;

	/**^
	 * 出生日期-DATE
	 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date csrq;

	/**
	 * 常住户口所在地-VARCHAR2(500)
	 */
    private String czhkszd;

	/**
	 * 常住户口所在地住址-VARCHAR2(200)
	 */
    private String czhkszdzz;

	/**
	 * 常住户口所在地住址房间号-VARCHAR2(50)
	 */
    private String czhkszdzzfjh;

	/**
	 * 住所类别-VARCHAR2(10)
	 */
    private String zslb;

	/**^
	 * 服兵役情况 1是，0否-CHAR(1)
	 */
    private String fbyqk;

	/**^
	 * 居住事由-VARCHAR2(10)
	 */
    private String jzsy;

	/**^
	 * 婚姻状况-VARCHAR2(10)
	 */
    private String hyzk;

	/**^
	 * 学历-VARCHAR2(10)
	 */
    private String xl;

	/**^
	 * 政治面貌-VARCHAR2(10)
	 */
    private String zzmm;

	/**^
	 * 联系方式-手机-VARCHAR2(50)
	 */
    private String lxfsSj;

	/**
	 * 联系方式-固话-VARCHAR2(50)
	 */
    private String lxfsGh;

	/**
	 * 其他联系方式-VARCHAR2(50)
	 */
    private String lxfsXlt;

	/**^
	 * 到达本地日期-DATE
	 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ddbdrq;

	/**^
	 * 拟居住期限-VARCHAR2(20)
	 */
    private String njzqx;

	/**^
	 * 是否申领居住证（0否1是）-CHAR(1)
	 */
    private String sfsljzz;

	/**
	 * 居住地有无工作单位-CHAR(1)
	 */
    private String jzdywgzdw;

	/**
	 * 从事职业-VARCHAR2(10)
	 */
    private String cszy;

	/**
	 * 专业技术职务-VARCHAR2(10)
	 */
    private String zyjszw;

	/**
	 * 职业资格-VARCHAR2(10)
	 */
    private String zyzg;

	/**
	 * 地市代码-CHAR(4)
	 */
    private String dsdm;

	/**^
	 * 所属警务区-VARCHAR2(36)
	 */
    private String ssjwq;
    /**^
	 * 业务逻辑用-非数据字段
	 */
    private String ssjwq_mc;
	/**^
	 * 所属派出所-VARCHAR2(36)
	 */
    private String sspcs;
    /**^
 	 * 业务逻辑用-非数据字段
 	 */
     private String sspcs_mc;

	

	/**
	 * 其他联系方式-qq-VARCHAR2(20)
	 */
    private String qtlxfsQq;

	/**
	 * 其他联系方式-email-VARCHAR2(20)
	 */
    private String qtlxfsEmail;

	/**
	 * 工作单位-VARCHAR2(100)
	 */
    private String gzdw;

	/**
	 * 单位所属分局-VARCHAR2(50)
	 */
    private String dwssfj;

	/**
	 * 单位所属派出所-VARCHAR2(50)
	 */
    private String dwsspcs;

	/**
	 *单位负责人 -VARCHAR2(50)
	 */
    private String dwfzr;

	/**
	 * 单位负责人联系电话-VARCHAR2(50)
	 */
    private String dwlxdh;

	/**
	 * 单位地址-VARCHAR2(200)
	 */
    private String dwdz;

	/**
	 * 已工作年限-VARCHAR2(10)
	 */
    private String ygznx;

	/**
	 * 现居住地-VARCHAR2(500)
	 */
    private String xjzd;

	/**^
	 * 现居住地住址-VARCHAR2(200)
	 */
    private String xjzdzz;

	/**^
	 * 现居住地住址房间号-VARCHAR2(50)
	 */
    private String xjzdzzfjh;

	/**^房主姓名
	 * 房主-VARCHAR2(50)
	 */
    private String fz;

	/**^
	 * 房主身份证号-VARCHAR2(20)
	 */
    private String fzSfzh;

	/**
	 * 劳动合同签订情况-VARCHAR2(10)
	 */
    private String ldhtqdqk;

	/**
	 * 参保情况-VARCHAR2(10)
	 */
    private String cbqk;

	/**
	 * 是否生育-VARCHAR2(10)
	 */
    private String sfsy;

	/**
	 * -
	 */
    private BigDecimal syznNan;

	/**
	 * -
	 */
    private BigDecimal syznNv;

	/**
	 * 婚育证明编号-VARCHAR2(50)
	 */
    private String hyzmbh;

	/**
	 * 是否采取节育措施-VARCHAR2(10)
	 */
    private String sfcqjycs;

	/**
	 * 公安机关代码-VARCHAR2(50)
	 */
    private String gajgdm;

	/**
	 * 填报时间/居住证有效期（开始时间）-DATE
	 */
    private Date tbsj;

	/**
	 * 填报单位代码（外网：团体:团体编号/个人：空，公安网：单位代码）-VARCHAR2(50)
	 */
    private String tbdwdm;

	/**
	 * 填报人姓名（外网：团体：用户名/个人：姓名 ，公安网：姓名）-VARCHAR2(50)
	 */
    private String tbrxm;

	/**
	 * 填报人身份证号（外网：用户名/个人：身份证号 ，公安网：身份证号）-VARCHAR2(18)
	 */
    private String tbrsfzh;

	/**^
	 * 删除标记（0-未删除，1-已删除）-VARCHAR2(2)
	 */
    private String deltag;

	/**
	 * 删除时间-DATE
	 */
    private Date deltime;

	/**
	 * 传输标志（0：未传输，1：已传输）-VARCHAR2(2)
	 */
    private String movesign;

	/**
	 * 最后修改时间-DATE
	 */
    private Date zhxgsj;

	/**
	 * 审批状态（户籍民警审核，0：未审核，1：通过，2：不通过）-VARCHAR2(2)
	 */
    private String spzt;

	/**
	 * 数据来源(字典表:ldrk_dict_xxly)-VARCHAR2(2)
	 */
    private String sjly;

	/**
	 * 户籍地行政区划-VARCHAR2(20)
	 */
    private String hjdxzqh;

	/**^
	 * 现住地行政区划-VARCHAR2(20)
	 */
    private String xzdxzqh;

	/**
	 * 数据有效性（0未验证1有效2无效）-VARCHAR2(20)
	 */
    private String sjyxx;

	/**
	 * 是否参加工作（0：无，1：有）-VARCHAR2(20)
	 */
    private String sfcjgz;

	/**
	 * 审核通过之后标准地址-NVARCHAR2(500)
	 */
    private Object bzdz;

	/**
	 * 数据有效性-验证时间-DATE
	 */
    private Date sjyxxYzsj;

	/**
	 * （社区民警）是否漏审（0：否，1：是）-CHAR(1)
	 */
    private String sfls;

	/**
	 * （户籍民警）受理时间-DATE
	 */
    private Date slsj;

	/**
	 * 最终状态（字典表：ldrk_dict_czlx）-CHAR(1)
	 */
    private String zzzt;

	/**
	 * 受理时产生的流水号-VARCHAR2(50)
	 */
    private String lsh;

	/**
	 * 操作类型ldrk_dict_xxlx-VARCHAR2(5)
	 */
    private String czlx;

	/**^
	 * 注销标志（0未注销1注销）-VARCHAR2(1)
	 */
    private String zxbz;

	/**
	 * 注销时间-DATE
	 */
    private Date zxsj;

	/**
	 * 审核人（姓名）-VARCHAR2(50) 字段修改
	 */
    private String shrxm;

	/**
	 * （户籍民警）审核时间-DATE
	 */
    private Date shsj;

	/**
	 * 审核单位代码-VARCHAR2(200)
	 */
    private String shdwdm;

	/**
	 * 出租房屋id（ldrk_czfw表id）-VARCHAR2(50)
	 */
    private String czfwId;

	/**
	 * 单元号-VARCHAR2(200)
	 */
    private String dyh;

	/**
	 * 房号-VARCHAR2(200)
	 */
    private String fh;

	/**^
	 * 街路巷小区-VARCHAR2(200)
	 */
    private String jlxxq;

	/**^
	 * 楼号-VARCHAR2(200)
	 */
    private String lh;

	/**^
	 * 门牌号-VARCHAR2(200)
	 */
    private String mph;

	/**
	 * 其它-VARCHAR2(200)
	 */
    private String qt;

	/**^
	 * 社区居村委会-VARCHAR2(200)
	 */
    private String sqjcwh;

	/**^
	 * 乡镇街道-VARCHAR2(200)
	 */
    private String xzjd;

	/**
	 * 居住证有效期（截至时间，开始时间tbsj）-DATE
	 */
    private Date jzzyxq;

	/**
	 * 是否允许制证（0否1是）-CHAR(1)
	 */
    private String sfyxzz;

	/**
	 * 是否比对在逃（0：否，1：比中，2：未比中）-CHAR(1)
	 */
    private String sfbdZt;

	/**
	 * 是否比对涉毒（0：否，1：比中，2：未比中）-CHAR(1)
	 */
    private String sfbdSd;

	/**
	 * 是否比对前科（0：否，1：比中，2：未比中）-CHAR(1)
	 */
    private String sfbdQk;

	/**
	 * 签发标志（分县市局签发 0：未审核，1：通过，2：不通过）-CHAR(1)
	 */
    private String qfbz;

	/**
	 * 签发时间-DATE
	 */
    private Date qfsj;

	/**
	 * 签发人（身份证号）-VARCHAR2(20)
	 */
    private String qfr;

	/**
	 * 是否警综抽取(0否  1是)-VARCHAR2(2)
	 */
    private String sfjzcq;

	/**
	 * 制证标志（0：未制证，1：已制证，2：制证不合格）-CHAR(1)
	 */
    private String zzbz;

	/**
	 * 制证时间-DATE
	 */
    private Date zzsj;

	/**
	 * 导入标志1成功2失败(指的是数据不合法),3编辑成功-CHAR(1)
	 */
    private String drbz;

	/**
	 * 比对在逃时间-DATE
	 */
    private Date bdsjZt;

	/**
	 * 比对涉毒时间-DATE
	 */
    private Date bdsjSd;

	/**
	 * 比对前科时间-DATE
	 */
    private Date bdsjQk;

	/**
	 * 地市导入(存机构代码前4位 济南3701)-VARCHAR2(4)
	 */
    private String dsdr;

	/**
	 * 地市数据主键-VARCHAR2(50)
	 */
    private String dszj;

	/**
	 * 居住证发证时间(开始时间)-DATE
	 */
    private Date fzsj;

	/**
	 * 审核人身份证号-VARCHAR2(20)
	 */
    private String shrsfzh;

	/**
	 * 待注销前-审批状态(spzt)-VARCHAR2(2)
	 */
    private String dzxqSpzt;

	/**
	 * 待注销前-操作类型(czlx)-VARCHAR2(5)
	 */
    private String dzxqCzlx;

	/**
	 * 是否二代证读取-CHAR(1)
	 */
    private String sfedzdq;

	/**
	 * 儿童是否预接种  1是，0否-CHAR(1)
	 */
    private String etsfyfjz;

	/**
	 * 是否E点通抽取（ 1是，0否）-VARCHAR2(1)
	 */
    private String sfedtcq;

	/**
	 * 是否补邻补发换领居住证-VARCHAR2(1)
	 */
    private String sfbhb;

	/**
	 * 民警核实标志-VARCHAR2(1)
	 */
    private String hsbz;

	/**
	 * 核实时间-DATE
	 */
    private Date hssj;

	/**
	 * 核实人身份证号-VARCHAR2(18)
	 */
    private String hsrsfzh;

	/**
	 * 工作单位所在分局-VARCHAR2(36)
	 */
    private String gzdwszfj;

	/**
	 * 工作单位所属派出所-VARCHAR2(50)
	 */
    private String dwpcs;

	/**^
	 * 地址_省市县(区）-VARCHAR2(36)
	 */
    private String dzSsxq;
    /**
     * 业务逻辑用，不是数据库字段
     */
    private String dzSsxq_mc;



	/**
	 * 地址_乡镇街道-VARCHAR2(36)
	 */
    private String dzXzjd;

	/**^
	 * 详细地址-VARCHAR2(300)
	 */
    private String xxdz;

	/**
	 * 地址编码-VARCHAR2(36)
	 */
    private String dzbm;

	/**
	 * 房间编码-VARCHAR2(36)
	 */
    private String fjbm;

	/**
	 * 坐标X(PGIS)-NUMBER(17,8)
	 */
    private BigDecimal zbx;

	/**
	 * 坐标Y(PGIS)-NUMBER(17,8)
	 */
    private BigDecimal zby;

	/**
	 * 坐标X(天地图)-NUMBER(11,8)
	 */
    private BigDecimal zbxTdt;

	/**
	 * 坐标Y(天地图)-NUMBER(11,8)
	 */
    private BigDecimal zbyTdt;

	/**
	 * 坐标X(GPS)-NUMBER(11,8)
	 */
    private BigDecimal zbxGps;

	/**
	 * 坐标Y(GPS)-NUMBER(11,8)
	 */
    private BigDecimal zbyGps;

	/**
	 * 外部系统ID-VARCHAR2(36)
	 */
    private String objectid;

	/**
	 * 备注-VARCHAR2(500)
	 */
    private String bz;

	/**
	 * 注销状态(1已注销;0未注销)-VARCHAR2(2)
	 */
    private String zxzt;

	/**
	 * 注销日期-DATE
	 */
    private Date zxrq;

	/**
	 * 登记人-VARCHAR2(36)
	 */
    private String djr;

	/**
	 * 登记人名称-VARCHAR2(100)
	 */
    private String djrmc;

	/**
	 * 登记单位名称-VARCHAR2(200)
	 */
    private String djdwmc;

	/**
	 * 登记单位-VARCHAR2(36)
	 */
    private String djdw;

	/**
	 * 登记时间(yyyy-mm-dd hh:mi:ss)-DATE
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
	 * 移动设备为设备ID(后台录入时为IP)-VARCHAR2(50)
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
    
	private byte[] zp;

    public String getLdid() {
        return ldid;
    }

    public void setLdid(String ldid) {
        this.ldid = ldid == null ? null : ldid.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm == null ? null : bm.trim();
    }
    public String getJzwid() {
  		return jzwid;
  	}

  	public void setJzwid(String jzwid) {
  		this.jzwid = jzwid;
  	}

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb == null ? null : xb.trim();
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz == null ? null : mz.trim();
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh == null ? null : sfzh.trim();
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
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

    public String getZslb() {
        return zslb;
    }

    public void setZslb(String zslb) {
        this.zslb = zslb == null ? null : zslb.trim();
    }

    public String getFbyqk() {
        return fbyqk;
    }

    public void setFbyqk(String fbyqk) {
        this.fbyqk = fbyqk == null ? null : fbyqk.trim();
    }

    public String getJzsy() {
        return jzsy;
    }

    public void setJzsy(String jzsy) {
        this.jzsy = jzsy == null ? null : jzsy.trim();
    }

    public String getHyzk() {
        return hyzk;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk == null ? null : hyzk.trim();
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl == null ? null : xl.trim();
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm == null ? null : zzmm.trim();
    }

    public String getLxfsSj() {
        return lxfsSj;
    }

    public void setLxfsSj(String lxfsSj) {
        this.lxfsSj = lxfsSj == null ? null : lxfsSj.trim();
    }

    public String getLxfsGh() {
        return lxfsGh;
    }

    public void setLxfsGh(String lxfsGh) {
        this.lxfsGh = lxfsGh == null ? null : lxfsGh.trim();
    }

    public String getLxfsXlt() {
        return lxfsXlt;
    }

    public void setLxfsXlt(String lxfsXlt) {
        this.lxfsXlt = lxfsXlt == null ? null : lxfsXlt.trim();
    }

    public Date getDdbdrq() {
        return ddbdrq;
    }

    public void setDdbdrq(Date ddbdrq) {
        this.ddbdrq = ddbdrq;
    }

    public String getNjzqx() {
        return njzqx;
    }

    public void setNjzqx(String njzqx) {
        this.njzqx = njzqx == null ? null : njzqx.trim();
    }

    public String getSfsljzz() {
        return sfsljzz;
    }

    public void setSfsljzz(String sfsljzz) {
        this.sfsljzz = sfsljzz == null ? null : sfsljzz.trim();
    }

    public String getJzdywgzdw() {
        return jzdywgzdw;
    }

    public void setJzdywgzdw(String jzdywgzdw) {
        this.jzdywgzdw = jzdywgzdw == null ? null : jzdywgzdw.trim();
    }

    public String getCszy() {
        return cszy;
    }

    public void setCszy(String cszy) {
        this.cszy = cszy == null ? null : cszy.trim();
    }

    public String getZyjszw() {
        return zyjszw;
    }

    public void setZyjszw(String zyjszw) {
        this.zyjszw = zyjszw == null ? null : zyjszw.trim();
    }

    public String getZyzg() {
        return zyzg;
    }

    public void setZyzg(String zyzg) {
        this.zyzg = zyzg == null ? null : zyzg.trim();
    }

    public String getDsdm() {
        return dsdm;
    }

    public void setDsdm(String dsdm) {
        this.dsdm = dsdm == null ? null : dsdm.trim();
    }

    public String getSsjwq() {
        return ssjwq;
    }

    public void setSsjwq(String ssjwq) {
        this.ssjwq = ssjwq == null ? null : ssjwq.trim();
    }

    public String getSspcs() {
        return sspcs;
    }

    public void setSspcs(String sspcs) {
        this.sspcs = sspcs == null ? null : sspcs.trim();
    }

    public String getQtlxfsQq() {
        return qtlxfsQq;
    }

    public void setQtlxfsQq(String qtlxfsQq) {
        this.qtlxfsQq = qtlxfsQq == null ? null : qtlxfsQq.trim();
    }

    public String getQtlxfsEmail() {
        return qtlxfsEmail;
    }

    public void setQtlxfsEmail(String qtlxfsEmail) {
        this.qtlxfsEmail = qtlxfsEmail == null ? null : qtlxfsEmail.trim();
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw == null ? null : gzdw.trim();
    }

    public String getDwssfj() {
        return dwssfj;
    }

    public void setDwssfj(String dwssfj) {
        this.dwssfj = dwssfj == null ? null : dwssfj.trim();
    }

    public String getDwsspcs() {
        return dwsspcs;
    }

    public void setDwsspcs(String dwsspcs) {
        this.dwsspcs = dwsspcs == null ? null : dwsspcs.trim();
    }

    public String getDwfzr() {
        return dwfzr;
    }

    public void setDwfzr(String dwfzr) {
        this.dwfzr = dwfzr == null ? null : dwfzr.trim();
    }

    public String getDwlxdh() {
        return dwlxdh;
    }

    public void setDwlxdh(String dwlxdh) {
        this.dwlxdh = dwlxdh == null ? null : dwlxdh.trim();
    }

    public String getDwdz() {
        return dwdz;
    }

    public void setDwdz(String dwdz) {
        this.dwdz = dwdz == null ? null : dwdz.trim();
    }

    public String getYgznx() {
        return ygznx;
    }

    public void setYgznx(String ygznx) {
        this.ygznx = ygznx == null ? null : ygznx.trim();
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

    public String getFz() {
        return fz;
    }

    public void setFz(String fz) {
        this.fz = fz == null ? null : fz.trim();
    }

    public String getFzSfzh() {
        return fzSfzh;
    }

    public void setFzSfzh(String fzSfzh) {
        this.fzSfzh = fzSfzh == null ? null : fzSfzh.trim();
    }

    public String getLdhtqdqk() {
        return ldhtqdqk;
    }

    public void setLdhtqdqk(String ldhtqdqk) {
        this.ldhtqdqk = ldhtqdqk == null ? null : ldhtqdqk.trim();
    }

    public String getCbqk() {
        return cbqk;
    }

    public void setCbqk(String cbqk) {
        this.cbqk = cbqk == null ? null : cbqk.trim();
    }

    public String getSfsy() {
        return sfsy;
    }

    public void setSfsy(String sfsy) {
        this.sfsy = sfsy == null ? null : sfsy.trim();
    }

    public BigDecimal getSyznNan() {
        return syznNan;
    }

    public void setSyznNan(BigDecimal syznNan) {
        this.syznNan = syznNan;
    }

    public BigDecimal getSyznNv() {
        return syznNv;
    }

    public void setSyznNv(BigDecimal syznNv) {
        this.syznNv = syznNv;
    }

    public String getHyzmbh() {
        return hyzmbh;
    }

    public void setHyzmbh(String hyzmbh) {
        this.hyzmbh = hyzmbh == null ? null : hyzmbh.trim();
    }

    public String getSfcqjycs() {
        return sfcqjycs;
    }

    public void setSfcqjycs(String sfcqjycs) {
        this.sfcqjycs = sfcqjycs == null ? null : sfcqjycs.trim();
    }

    public String getGajgdm() {
        return gajgdm;
    }

    public void setGajgdm(String gajgdm) {
        this.gajgdm = gajgdm == null ? null : gajgdm.trim();
    }

    public Date getTbsj() {
        return tbsj;
    }

    public void setTbsj(Date tbsj) {
        this.tbsj = tbsj;
    }

    public String getTbdwdm() {
        return tbdwdm;
    }

    public void setTbdwdm(String tbdwdm) {
        this.tbdwdm = tbdwdm == null ? null : tbdwdm.trim();
    }

    public String getTbrxm() {
        return tbrxm;
    }

    public void setTbrxm(String tbrxm) {
        this.tbrxm = tbrxm == null ? null : tbrxm.trim();
    }

    public String getTbrsfzh() {
        return tbrsfzh;
    }

    public void setTbrsfzh(String tbrsfzh) {
        this.tbrsfzh = tbrsfzh == null ? null : tbrsfzh.trim();
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

    public String getMovesign() {
        return movesign;
    }

    public void setMovesign(String movesign) {
        this.movesign = movesign == null ? null : movesign.trim();
    }

    public Date getZhxgsj() {
        return zhxgsj;
    }

    public void setZhxgsj(Date zhxgsj) {
        this.zhxgsj = zhxgsj;
    }

    public String getSpzt() {
        return spzt;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt == null ? null : spzt.trim();
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly == null ? null : sjly.trim();
    }

    public String getHjdxzqh() {
        return hjdxzqh;
    }

    public void setHjdxzqh(String hjdxzqh) {
        this.hjdxzqh = hjdxzqh == null ? null : hjdxzqh.trim();
    }

    public String getXzdxzqh() {
        return xzdxzqh;
    }

    public void setXzdxzqh(String xzdxzqh) {
        this.xzdxzqh = xzdxzqh == null ? null : xzdxzqh.trim();
    }

    public String getSjyxx() {
        return sjyxx;
    }

    public void setSjyxx(String sjyxx) {
        this.sjyxx = sjyxx == null ? null : sjyxx.trim();
    }

    public String getSfcjgz() {
        return sfcjgz;
    }

    public void setSfcjgz(String sfcjgz) {
        this.sfcjgz = sfcjgz == null ? null : sfcjgz.trim();
    }

    public Object getBzdz() {
        return bzdz;
    }

    public void setBzdz(Object bzdz) {
        this.bzdz = bzdz;
    }

    public Date getSjyxxYzsj() {
        return sjyxxYzsj;
    }

    public void setSjyxxYzsj(Date sjyxxYzsj) {
        this.sjyxxYzsj = sjyxxYzsj;
    }

    public String getSfls() {
        return sfls;
    }

    public void setSfls(String sfls) {
        this.sfls = sfls == null ? null : sfls.trim();
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getZzzt() {
        return zzzt;
    }

    public void setZzzt(String zzzt) {
        this.zzzt = zzzt == null ? null : zzzt.trim();
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh == null ? null : lsh.trim();
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx == null ? null : czlx.trim();
    }

    public String getZxbz() {
        return zxbz;
    }

    public void setZxbz(String zxbz) {
        this.zxbz = zxbz == null ? null : zxbz.trim();
    }

    public Date getZxsj() {
        return zxsj;
    }

    public void setZxsj(Date zxsj) {
        this.zxsj = zxsj;
    }

    public String getShrxm() {
		return shrxm;
	}

	public void setShrxm(String shrxm) {
		this.shrxm = shrxm == null ? null : shrxm.trim();
	}

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getShdwdm() {
        return shdwdm;
    }

    public void setShdwdm(String shdwdm) {
        this.shdwdm = shdwdm == null ? null : shdwdm.trim();
    }

    public String getCzfwId() {
        return czfwId;
    }

    public void setCzfwId(String czfwId) {
        this.czfwId = czfwId == null ? null : czfwId.trim();
    }

    public String getDyh() {
        return dyh;
    }

    public void setDyh(String dyh) {
        this.dyh = dyh == null ? null : dyh.trim();
    }

    public String getFh() {
        return fh;
    }

    public void setFh(String fh) {
        this.fh = fh == null ? null : fh.trim();
    }

    public String getJlxxq() {
        return jlxxq;
    }

    public void setJlxxq(String jlxxq) {
        this.jlxxq = jlxxq == null ? null : jlxxq.trim();
    }

    public String getLh() {
        return lh;
    }

    public void setLh(String lh) {
        this.lh = lh == null ? null : lh.trim();
    }

    public String getMph() {
        return mph;
    }

    public void setMph(String mph) {
        this.mph = mph == null ? null : mph.trim();
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt == null ? null : qt.trim();
    }

    public String getSqjcwh() {
        return sqjcwh;
    }

    public void setSqjcwh(String sqjcwh) {
        this.sqjcwh = sqjcwh == null ? null : sqjcwh.trim();
    }

    public String getXzjd() {
        return xzjd;
    }

    public void setXzjd(String xzjd) {
        this.xzjd = xzjd == null ? null : xzjd.trim();
    }

    public Date getJzzyxq() {
        return jzzyxq;
    }

    public void setJzzyxq(Date jzzyxq) {
        this.jzzyxq = jzzyxq;
    }

    public String getSfyxzz() {
        return sfyxzz;
    }

    public void setSfyxzz(String sfyxzz) {
        this.sfyxzz = sfyxzz == null ? null : sfyxzz.trim();
    }

    public String getSfbdZt() {
        return sfbdZt;
    }

    public void setSfbdZt(String sfbdZt) {
        this.sfbdZt = sfbdZt == null ? null : sfbdZt.trim();
    }

    public String getSfbdSd() {
        return sfbdSd;
    }

    public void setSfbdSd(String sfbdSd) {
        this.sfbdSd = sfbdSd == null ? null : sfbdSd.trim();
    }

    public String getSfbdQk() {
        return sfbdQk;
    }

    public void setSfbdQk(String sfbdQk) {
        this.sfbdQk = sfbdQk == null ? null : sfbdQk.trim();
    }

    public String getQfbz() {
        return qfbz;
    }

    public void setQfbz(String qfbz) {
        this.qfbz = qfbz == null ? null : qfbz.trim();
    }

    public Date getQfsj() {
        return qfsj;
    }

    public void setQfsj(Date qfsj) {
        this.qfsj = qfsj;
    }

    public String getQfr() {
        return qfr;
    }

    public void setQfr(String qfr) {
        this.qfr = qfr == null ? null : qfr.trim();
    }

    public String getSfjzcq() {
        return sfjzcq;
    }

    public void setSfjzcq(String sfjzcq) {
        this.sfjzcq = sfjzcq == null ? null : sfjzcq.trim();
    }

    public String getZzbz() {
        return zzbz;
    }

    public void setZzbz(String zzbz) {
        this.zzbz = zzbz == null ? null : zzbz.trim();
    }

    public Date getZzsj() {
        return zzsj;
    }

    public void setZzsj(Date zzsj) {
        this.zzsj = zzsj;
    }

    public String getDrbz() {
        return drbz;
    }

    public void setDrbz(String drbz) {
        this.drbz = drbz == null ? null : drbz.trim();
    }

    public Date getBdsjZt() {
        return bdsjZt;
    }

    public void setBdsjZt(Date bdsjZt) {
        this.bdsjZt = bdsjZt;
    }

    public Date getBdsjSd() {
        return bdsjSd;
    }

    public void setBdsjSd(Date bdsjSd) {
        this.bdsjSd = bdsjSd;
    }

    public Date getBdsjQk() {
        return bdsjQk;
    }

    public void setBdsjQk(Date bdsjQk) {
        this.bdsjQk = bdsjQk;
    }

    public String getDsdr() {
        return dsdr;
    }

    public void setDsdr(String dsdr) {
        this.dsdr = dsdr == null ? null : dsdr.trim();
    }

    public String getDszj() {
        return dszj;
    }

    public void setDszj(String dszj) {
        this.dszj = dszj == null ? null : dszj.trim();
    }

    public Date getFzsj() {
        return fzsj;
    }

    public void setFzsj(Date fzsj) {
        this.fzsj = fzsj;
    }

    public String getShrsfzh() {
        return shrsfzh;
    }

    public void setShrsfzh(String shrsfzh) {
        this.shrsfzh = shrsfzh == null ? null : shrsfzh.trim();
    }

    public String getDzxqSpzt() {
        return dzxqSpzt;
    }

    public void setDzxqSpzt(String dzxqSpzt) {
        this.dzxqSpzt = dzxqSpzt == null ? null : dzxqSpzt.trim();
    }

    public String getDzxqCzlx() {
        return dzxqCzlx;
    }

    public void setDzxqCzlx(String dzxqCzlx) {
        this.dzxqCzlx = dzxqCzlx == null ? null : dzxqCzlx.trim();
    }

    public String getSfedzdq() {
        return sfedzdq;
    }

    public void setSfedzdq(String sfedzdq) {
        this.sfedzdq = sfedzdq == null ? null : sfedzdq.trim();
    }

    public String getEtsfyfjz() {
        return etsfyfjz;
    }

    public void setEtsfyfjz(String etsfyfjz) {
        this.etsfyfjz = etsfyfjz == null ? null : etsfyfjz.trim();
    }

    public String getSfedtcq() {
        return sfedtcq;
    }

    public void setSfedtcq(String sfedtcq) {
        this.sfedtcq = sfedtcq == null ? null : sfedtcq.trim();
    }

    public String getSfbhb() {
        return sfbhb;
    }

    public void setSfbhb(String sfbhb) {
        this.sfbhb = sfbhb == null ? null : sfbhb.trim();
    }

    public String getHsbz() {
        return hsbz;
    }

    public void setHsbz(String hsbz) {
        this.hsbz = hsbz == null ? null : hsbz.trim();
    }

    public Date getHssj() {
        return hssj;
    }

    public void setHssj(Date hssj) {
        this.hssj = hssj;
    }

    public String getHsrsfzh() {
        return hsrsfzh;
    }

    public void setHsrsfzh(String hsrsfzh) {
        this.hsrsfzh = hsrsfzh == null ? null : hsrsfzh.trim();
    }

    public String getGzdwszfj() {
        return gzdwszfj;
    }

    public void setGzdwszfj(String gzdwszfj) {
        this.gzdwszfj = gzdwszfj == null ? null : gzdwszfj.trim();
    }

    public String getDwpcs() {
        return dwpcs;
    }

    public void setDwpcs(String dwpcs) {
        this.dwpcs = dwpcs == null ? null : dwpcs.trim();
    }

    public String getDzSsxq() {
        return dzSsxq;
    }

    public void setDzSsxq(String dzSsxq) {
        this.dzSsxq = dzSsxq == null ? null : dzSsxq.trim();
    }

    public String getDzXzjd() {
        return dzXzjd;
    }

    public void setDzXzjd(String dzXzjd) {
        this.dzXzjd = dzXzjd == null ? null : dzXzjd.trim();
    }

    public String getXxdz() {
        return xxdz;
    }

    public void setXxdz(String xxdz) {
        this.xxdz = xxdz == null ? null : xxdz.trim();
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

    public BigDecimal getZbxTdt() {
        return zbxTdt;
    }

    public void setZbxTdt(BigDecimal zbxTdt) {
        this.zbxTdt = zbxTdt;
    }

    public BigDecimal getZbyTdt() {
        return zbyTdt;
    }

    public void setZbyTdt(BigDecimal zbyTdt) {
        this.zbyTdt = zbyTdt;
    }

    public BigDecimal getZbxGps() {
        return zbxGps;
    }

    public void setZbxGps(BigDecimal zbxGps) {
        this.zbxGps = zbxGps;
    }

    public BigDecimal getZbyGps() {
        return zbyGps;
    }

    public void setZbyGps(BigDecimal zbyGps) {
        this.zbyGps = zbyGps;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid == null ? null : objectid.trim();
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

    public String getDjr() {
        return djr;
    }

    public void setDjr(String djr) {
        this.djr = djr == null ? null : djr.trim();
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

    public String getDjdw() {
        return djdw;
    }

    public void setDjdw(String djdw) {
        this.djdw = djdw == null ? null : djdw.trim();
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

    public byte[] getZp() {
        return zp;
    }

    public void setZp(byte[] zp) {
        this.zp = zp;
    }
	public String getDzSsxq_mc() {
		return dzSsxq_mc;
	}

	public void setDzSsxq_mc(String dzSsxq_mc) {
		this.dzSsxq_mc = dzSsxq_mc;
	}
	
	public String getSsjwq_mc() {
		return ssjwq_mc;
	}

	public void setSsjwq_mc(String ssjwq_mc) {
		this.ssjwq_mc = ssjwq_mc;
	}

	public String getSspcs_mc() {
		return sspcs_mc;
	}

	public void setSspcs_mc(String sspcs_mc) {
		this.sspcs_mc = sspcs_mc;
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