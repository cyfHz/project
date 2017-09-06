package com.kingmon.project.psam.dwxx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 单位信息
 * @author 
 * @date 
 */
public class Dwxx implements Serializable{

	/**
	 * 单位编码 VARCHAR2(36)
	 */
	private String zagldwbm;
	
	/**
	 * 单位名称 VARCHAR2(200)
	 */
	private String dwmc;
	
	/**
	 * 单位英文名称 VARCHAR2(100)
	 */
	private String dwywmc;
	
	/**
	 * 单位英文缩写 VARCHAR2(50)
	 */
	private String dwywsx;
	
	/**
	 * 组织机构代码 VARCHAR2(100)
	 */
	private String zzjgdm;
	
	/**
	 * 单位类型 VARCHAR2(50)
	 */
	private String dwlxdm;
	
	/**
	 * 经济类型 VARCHAR2(50)
	 */
	private String jjlxdm;
	
	/**
	 * 行业类别 VARCHAR2(50)
	 */
	private String hylbdm;
	
	/**
	 * 开业日期 Date
	 */
	private Date kyrq;
	
	/**
	 * 停业日期 Date
	 */
	private Date tyrq;
	
	/**
	 * 经营范围（主营）VARCHAR2(50)
	 */
	private String jyfwzy;
	
	/**
	 * 经营范围（兼营） VARCHAR2(50)
	 */
	private String jyfwjy;
	
	/**
	 * 经营面积_面积（平方米）NUMBER(8,2)
	 */
	private BigDecimal jymj_mjpfm;
	
	/**
	 * 经营方式 VARCHAR2(100)
	 */
	private String jyfs;
	
	/**
	 * 营业执照号 VARCHAR2(100)
	 */
	private String yyzzh;
	
	/**
	 * 营业执照有效期 Date
	 */
	private Date yyzzyxq;
	
	/**
	 * 营业执照起始日期 Date
	 */
	 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date yyzzyxq_qsrq;
	
	/**
	 * 营业执照截止日期 DATE
	 */
	 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date yyzzyxq_jzrq;
	
	/**
	 * 注册资金 VARCHAR2(50)
	 */
	private String zczb;
	
	/**
	 * 联系电话 VARCHAR2(50)
	 */
	private String lxdh;
	
	/**
	 * 单位地址 VARCHAR2(200)
	 */
	private String dwdz;
	
	/**
	 * 建筑物id VARCHAR2(50)
	 */
	private String jzwid;
	
	/**
	 * 房间id VARCHAR2(50)
	 */
	private String fjid;
	
	/**
	 * 省市县区 VARCHAR2(10)
	 */
	private String dwdz_ssxqdm;
	
	/**
	 * 区划内详细地址 VARCHAR2(100)
	 */
	private String dwdz_qhnxxdz;
	
	/**
	 * 网址 VARCHAR2(100)
	 */
	private String wz;
	
	/**
	 * 法定代表人 VARCHAR2(50)
	 */
	private String fddbr;
	
	/**
	 * 法人_人口编码 VARCHAR2(50)
	 */
	private String fddbr_rkbm;
	
	/**
	 * 法人_公民身份号码 VARCHAR2(50)
	 */
	private String fddbr_gmsfhm;
	
	/**
	 * 法人_姓名 VARCHAR2(50)
	 */
	private String fddbr_xm;
	
	/**
	 * 法人_证件种类 VARCHAR2(50)
	 */
	private String fddbr_cyzjdm;
	
	/**
	 * 法人_证件号码 VARCHAR2(50)
	 */
	private String fddbr_zjhm;
	
	/**
	 * 法人_外文姓 VARCHAR2(50)
	 */
	private String fddbr_wwx;
	
	/**
	 * 法人_外文名 VARCHAR2(50)
	 */
	private String fddbr_wwm;
	
	/**
	 * 法人_联系电话 VARCHAR2(50)
	 */
	private String fddbr_lxdh;
	
	/**
	 * 保卫负责人 VARCHAR2(50)
	 */
	private String bwfzr;
	
	/**
	 * 保卫负责人_人口编码 VARCHAR2(50)
	 */
	private String bwfzr_rkbm;
	
	/**
	 * 保卫负责人_公民身份号码 VARCHAR2(50)
	 */
	private String bwfzr_gmsfhm;
	
	/**
	 * 保卫负责人_姓名 VARCHAR2(50)
	 */
	private String bwfzr_xm;
	
	/**
	 * 保卫负责人_联系电话 VARCHAR2(50)
	 */
	private String bwfzr_lxdh;
	
	/**
	 * 重点单位标示 CHAR(1)
	 */
	private String zddwbs;
	
	/**
	 * 注销时间 Date
	 */
	private Date zxsj;
	
	/**
	 * 更新时间 Date
	 */
	private Date gxsj;
	
	/**
	 * 坐标X经度 NUMBER(12,8)
	 */
	private BigDecimal zbx;
	
	/**
	 * 坐标Y纬度 NUMBER(12,8)
	 */
	private BigDecimal zby;
	
	/**
	 * 登记公安机关机构代码 VARCHAR2(50)
	 */
	private String djdw_gajgjgdm;
	
	/**
	 * 登记公安机关名称 VARCHAR2(100)
	 */
	private String djdw_gajgmc;
	
	/**
	 * 所属派出所编码 VARCHAR2(50)
	 */
	private String sspcsbm;
	
	/**
	 * 所属分局编码 VARCHAR2(50)
	 */
	private String ssfjbm;
	
	/**
	 * 所属市局编码 VARCHAR2(50)
	 */
	private String sssjbm;
	
	/**
	 * 所属派出所名称 VARCHAR2(100)
	 */
	private String sspcsmc;
	
	/**
	 * 所属警务区编码 VARCHAR2(50)
	 */
	private String ssjwqbm;
	
	/**
	 * 所属警务区名称 VARCHAR2(100)
	 */
	private String ssjwqmc;
	
	/**
	 * 删除标记（0-未删除，1-已删除）CHAR(1)
	 */
	private String deltag;
	
	/**
	 * 对应业务表类型 VARCHAR2(50)
	 */
	private String table_value;
	
	/**
	 * 不是数据库字段，
	 * 省市县区名称 VARCHAR2(10)
	 */
	private String ssxq_mc;

	public String getZagldwbm() {
		return zagldwbm;
	}

	public void setZagldwbm(String zagldwbm) {
		this.zagldwbm = zagldwbm;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getDwywmc() {
		return dwywmc;
	}

	public void setDwywmc(String dwywmc) {
		this.dwywmc = dwywmc;
	}

	public String getDwywsx() {
		return dwywsx;
	}

	public void setDwywsx(String dwywsx) {
		this.dwywsx = dwywsx;
	}

	public String getZzjgdm() {
		return zzjgdm;
	}

	public void setZzjgdm(String zzjgdm) {
		this.zzjgdm = zzjgdm;
	}

	public String getDwlxdm() {
		return dwlxdm;
	}

	public void setDwlxdm(String dwlxdm) {
		this.dwlxdm = dwlxdm;
	}

	public String getJjlxdm() {
		return jjlxdm;
	}

	public void setJjlxdm(String jjlxdm) {
		this.jjlxdm = jjlxdm;
	}

	public String getHylbdm() {
		return hylbdm;
	}

	public void setHylbdm(String hylbdm) {
		this.hylbdm = hylbdm;
	}

	public Date getKyrq() {
		return kyrq;
	}

	public void setKyrq(Date kyrq) {
		this.kyrq = kyrq;
	}

	public Date getTyrq() {
		return tyrq;
	}

	public void setTyrq(Date tyrq) {
		this.tyrq = tyrq;
	}

	public String getJyfwzy() {
		return jyfwzy;
	}

	public void setJyfwzy(String jyfwzy) {
		this.jyfwzy = jyfwzy;
	}

	public String getJyfwjy() {
		return jyfwjy;
	}

	public void setJyfwjy(String jyfwjy) {
		this.jyfwjy = jyfwjy;
	}

	public BigDecimal getJymj_mjpfm() {
		return jymj_mjpfm;
	}

	public void setJymj_mjpfm(BigDecimal jymj_mjpfm) {
		this.jymj_mjpfm = jymj_mjpfm;
	}

	public String getJyfs() {
		return jyfs;
	}

	public void setJyfs(String jyfs) {
		this.jyfs = jyfs;
	}

	public String getYyzzh() {
		return yyzzh;
	}

	public void setYyzzh(String yyzzh) {
		this.yyzzh = yyzzh;
	}

	public Date getYyzzyxq() {
		return yyzzyxq;
	}

	public void setYyzzyxq(Date yyzzyxq) {
		this.yyzzyxq = yyzzyxq;
	}

	public Date getYyzzyxq_qsrq() {
		return yyzzyxq_qsrq;
	}

	public void setYyzzyxq_qsrq(Date yyzzyxq_qsrq) {
		this.yyzzyxq_qsrq = yyzzyxq_qsrq;
	}

	public Date getYyzzyxq_jzrq() {
		return yyzzyxq_jzrq;
	}

	public void setYyzzyxq_jzrq(Date yyzzyxq_jzrq) {
		this.yyzzyxq_jzrq = yyzzyxq_jzrq;
	}

	public String getZczb() {
		return zczb;
	}

	public void setZczb(String zczb) {
		this.zczb = zczb;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getDwdz() {
		return dwdz;
	}

	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}

	public String getJzwid() {
		return jzwid;
	}

	public void setJzwid(String jzwid) {
		this.jzwid = jzwid;
	}

	public String getFjid() {
		return fjid;
	}

	public void setFjid(String fjid) {
		this.fjid = fjid;
	}

	public String getDwdz_ssxqdm() {
		return dwdz_ssxqdm;
	}

	public void setDwdz_ssxqdm(String dwdz_ssxqdm) {
		this.dwdz_ssxqdm = dwdz_ssxqdm;
	}

	public String getDwdz_qhnxxdz() {
		return dwdz_qhnxxdz;
	}

	public void setDwdz_qhnxxdz(String dwdz_qhnxxdz) {
		this.dwdz_qhnxxdz = dwdz_qhnxxdz;
	}

	public String getWz() {
		return wz;
	}

	public void setWz(String wz) {
		this.wz = wz;
	}

	public String getFddbr() {
		return fddbr;
	}

	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}

	public String getFddbr_rkbm() {
		return fddbr_rkbm;
	}

	public void setFddbr_rkbm(String fddbr_rkbm) {
		this.fddbr_rkbm = fddbr_rkbm;
	}

	public String getFddbr_gmsfhm() {
		return fddbr_gmsfhm;
	}

	public void setFddbr_gmsfhm(String fddbr_gmsfhm) {
		this.fddbr_gmsfhm = fddbr_gmsfhm;
	}

	public String getFddbr_xm() {
		return fddbr_xm;
	}

	public void setFddbr_xm(String fddbr_xm) {
		this.fddbr_xm = fddbr_xm;
	}

	public String getFddbr_cyzjdm() {
		return fddbr_cyzjdm;
	}

	public void setFddbr_cyzjdm(String fddbr_cyzjdm) {
		this.fddbr_cyzjdm = fddbr_cyzjdm;
	}

	public String getFddbr_zjhm() {
		return fddbr_zjhm;
	}

	public void setFddbr_zjhm(String fddbr_zjhm) {
		this.fddbr_zjhm = fddbr_zjhm;
	}

	public String getFddbr_wwx() {
		return fddbr_wwx;
	}

	public void setFddbr_wwx(String fddbr_wwx) {
		this.fddbr_wwx = fddbr_wwx;
	}

	public String getFddbr_wwm() {
		return fddbr_wwm;
	}

	public void setFddbr_wwm(String fddbr_wwm) {
		this.fddbr_wwm = fddbr_wwm;
	}

	public String getFddbr_lxdh() {
		return fddbr_lxdh;
	}

	public void setFddbr_lxdh(String fddbr_lxdh) {
		this.fddbr_lxdh = fddbr_lxdh;
	}

	public String getBwfzr() {
		return bwfzr;
	}

	public void setBwfzr(String bwfzr) {
		this.bwfzr = bwfzr;
	}

	public String getBwfzr_rkbm() {
		return bwfzr_rkbm;
	}

	public void setBwfzr_rkbm(String bwfzr_rkbm) {
		this.bwfzr_rkbm = bwfzr_rkbm;
	}

	public String getBwfzr_gmsfhm() {
		return bwfzr_gmsfhm;
	}

	public void setBwfzr_gmsfhm(String bwfzr_gmsfhm) {
		this.bwfzr_gmsfhm = bwfzr_gmsfhm;
	}

	public String getBwfzr_xm() {
		return bwfzr_xm;
	}

	public void setBwfzr_xm(String bwfzr_xm) {
		this.bwfzr_xm = bwfzr_xm;
	}

	public String getBwfzr_lxdh() {
		return bwfzr_lxdh;
	}

	public void setBwfzr_lxdh(String bwfzr_lxdh) {
		this.bwfzr_lxdh = bwfzr_lxdh;
	}

	public String getZddwbs() {
		return zddwbs;
	}

	public void setZddwbs(String zddwbs) {
		this.zddwbs = zddwbs;
	}

	public Date getZxsj() {
		return zxsj;
	}

	public void setZxsj(Date zxsj) {
		this.zxsj = zxsj;
	}

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
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

	public String getDjdw_gajgjgdm() {
		return djdw_gajgjgdm;
	}

	public void setDjdw_gajgjgdm(String djdw_gajgjgdm) {
		this.djdw_gajgjgdm = djdw_gajgjgdm;
	}

	public String getDjdw_gajgmc() {
		return djdw_gajgmc;
	}

	public void setDjdw_gajgmc(String djdw_gajgmc) {
		this.djdw_gajgmc = djdw_gajgmc;
	}

	public String getSspcsbm() {
		return sspcsbm;
	}

	public void setSspcsbm(String sspcsbm) {
		this.sspcsbm = sspcsbm;
	}

	public String getSsfjbm() {
		return ssfjbm;
	}

	public void setSsfjbm(String ssfjbm) {
		this.ssfjbm = ssfjbm;
	}

	public String getSssjbm() {
		return sssjbm;
	}

	public void setSssjbm(String sssjbm) {
		this.sssjbm = sssjbm;
	}

	public String getSspcsmc() {
		return sspcsmc;
	}

	public void setSspcsmc(String sspcsmc) {
		this.sspcsmc = sspcsmc;
	}

	public String getSsjwqbm() {
		return ssjwqbm;
	}

	public void setSsjwqbm(String ssjwqbm) {
		this.ssjwqbm = ssjwqbm;
	}

	public String getSsjwqmc() {
		return ssjwqmc;
	}

	public void setSsjwqmc(String ssjwqmc) {
		this.ssjwqmc = ssjwqmc;
	}

	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	public String getTable_value() {
		return table_value;
	}

	public void setTable_value(String table_value) {
		this.table_value = table_value;
	}

	public String getSsxq_mc() {
		return ssxq_mc;
	}

	public void setSsxq_mc(String ssxq_mc) {
		this.ssxq_mc = ssxq_mc;
	}
	
	
	
}
