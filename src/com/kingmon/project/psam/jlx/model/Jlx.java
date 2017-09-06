package com.kingmon.project.psam.jlx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * #new#新加字段标识
 * 
 *  ******审核流程，待实现@2015-1009
 *  
 * 街路巷实体
 * @author zhaohuatai
 * @date 2015年10月5日 下午6:37:55
 */
public class Jlx implements Serializable{
	
	/**
	 * 主键uuid
	 * 
	 * 地址编码-VARCHAR2(36)
	 */
    private String dzbm;

    /**
   	 *	10	省市县（区），11	省，12	市，13	县（区），20	乡镇(街道)<br>
	 *	21	建制乡，22	建制镇，23	街道办事处<br>
	 *	30	社区、居(村)委会，31	社区，32	居委会，33	村委会，34	  组<br>
	 *	40	街路巷(小区)，41	  街路巷，42	  小区，43	  单位（住宅）院，44	  自然村<br>
	 *	50	建筑物<br>
	 *	60	建筑物单元房屋，61	单元（门），62	  室（号）<br>
	 *
	 * 必填** <br>
	 * 数据字典 项DZ_DATADIC-DECLARE_CODE:0001000000
	 * 
     * 地址元素类型代码-VARCHAR2(4)
     */
    private String dzyslxdm;

    /**
     * 无生成规则，通过代码生成,保证无重复即可，建议采用DZBM ，或 uuid<br>
     * 必填** <br>
     * 街路巷(小区)代码-VARCHAR2(100)
     */
    private String jlxxqdm;
    

    /**
     * 必填** <br>
     * 
     * 街路巷(小区)名称-VARCHAR2(100)
     */
    private String jlxxqmc;

    /**
     * 必填** <br>
     * 别名简称-VARCHAR2(100)
     */
    private String bmjc;

    /**
    *  防止出现此种情况，非必填** <br>
     * 所属街路巷（小区）_地址编码-VARCHAR2(36)
     */
    private String ssjlxxq_dzbm;
    
    /**
     * 
     * 所属最低一级行政区域_地址编码-VARCHAR2(36)
     */
    private String sszdyjxzqy_dzbm;
    
    /**
     * #new#+ 不是数据库字段
     *  防止出现此种情况，非必填** <br>
      * 所属街路巷（小区）_地址编码
      */
     private String sszdyjxzqy_mc;
     
    
//    /**
//     * #new# 新加字段标识
//     * 
//     * 标明SSZDYJXZQY_DZBM 是哪一级行政机构：   01:（省市县）行政区划 ; 02: 乡镇街道  ; 03:社区村委会
//     * 
//     * 所属最低一级行政机构标识 -VARCHAR2(36)
//     * 
//     */
//    private String sszdyjxzqy_bz;
    
    
    /**
   	 *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 *  
	 *  必填** <br>
     * 设立日期-DATE
     */
    private Date slrq;
    
    /**
   	 *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 *  
	 *  必填** <br>
     * 撤销时间-DATE
     */
    private Date cxsj;
    
    /**
	 *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 *  
	 *  必填** <br>
     * 更新时间-DATE
     */
    private Date gxsj;
    
    /**
	 *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 *  
	 *  必填** <br>
     * 启用日期-DATE
     */
    private Date qyrq;
    
    /**
     *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 *  必填** <br>
     * 停用日期-DATE
     */
    private Date tyrq;
    
    /**
     * 
	 * 使用状态DZ_SYZT(10,使用中;20,停用;30,未启用;40,维修;50,报废;60,撤销;90,其他)<br>
	 * 
	 *  数据字典 项DZ_DATADIC-DECLARE_CODE:0002000000 
     * 
     * --VARCHAR2(4)
     */
    private String syztdm;
    
    /**
     * 不用操作
     * 
     * VARCHAR2(10)
     */
    private String movesign;
    
    /**
     * 涉及到 撤销 时，必填<br>
     * 撤销原因 -VARCHAR2(100)
     */
    private String cxyy;
    
    /**
     * #new# 新加字段标记
     * 
 	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
	 * 
   	 * 撤销人-VARCHAR2(36)
   	 */
    private String cxr;
    
    /**
     * #new# 新加字段标记
     * 外键 来自 APP_ORGANIZATION <br>
	 * 
	 * 此处为当前登录用户所在单位<br>
	 * 
   	 *  撤销单位-VARCHAR2(36)
   	 */
    private String cxdw;
    
    /**
     * **注意**街路项按照全称来生成助记符****<br>
     * 
     * 必填<br>
     * 助记符-VARCHAR2(60)
     */
    private String zjf;
    
    /** 
 	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
     * 
     * 登记人-VARCHAR2(36)
     */
    private String djr;
    
    /**
     * 外键 来自 APP_ORGANIZATION <br>
	 * 
	 * 此处为当前登录用户所在单位<br>
     * 
     * 登记单位-VARCHAR2(36)
     */
    private String djdw;
    
    /**
     * 当前时间<br>
	 * 时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 登记时间 -DATE
     */
    private Date djsj;
    
    /**
 	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
     * 
     * 修改人--VARCHAR2(36)
     */
    private String xgr;
    
    /**
     * 外键 来自 APP_ORGANIZATION <br>
	 * 
	 * 此处为当前登录用户所在单位<br>
     * 
     * 修改单位--VARCHAR2(36)
     */
    private String xgdw;
    
    /**
     * 未用，不用操作<br>
     * 子节点个数--NUMBER
     */
    private BigDecimal childcount;
    
    /**
     * 
     * 导入时间-VARCHAR2(14)
     */
    private String drsj;
    
    /**
     * 未用，不用操作
     */
    private String zjf_temp;
    /**
     * #new#
     * 撤销标记char(1) 1:撤销（注销），其他正常
     */
    private String cxbj;
    /**
     * #new#
     * 审核结果char(1) 0:正在申请 1、区县审核未通过2、区县审核通过 3、市局审核不通过 4、 市局审核通过5、最终审核通过
     */
    private String spzt;
    /**
     * #new#
     * 审核配置代码char(1) 0:不需要审核1、派出所申请2、区县审核3、区县审核+市局审核 4、市局审核
     */
    private String shpzdm;
    
    /**
   	 * #新增字段# 归属代码
   	 *   -VARCHAR2(36)
   	 */
   	private String jlxgsdm;
   	
	public String getDzbm() {
		return dzbm;
	}

	public void setDzbm(String dzbm) {
		this.dzbm = dzbm;
	}

	public String getDzyslxdm() {
		return dzyslxdm;
	}

	public void setDzyslxdm(String dzyslxdm) {
		this.dzyslxdm = dzyslxdm;
	}

	public String getJlxxqdm() {
		return jlxxqdm;
	}

	public void setJlxxqdm(String jlxxqdm) {
		this.jlxxqdm = jlxxqdm;
	}

	public String getJlxxqmc() {
		return jlxxqmc;
	}

	public void setJlxxqmc(String jlxxqmc) {
		this.jlxxqmc = jlxxqmc;
	}

	public String getBmjc() {
		return bmjc;
	}

	public void setBmjc(String bmjc) {
		this.bmjc = bmjc;
	}

	public String getSsjlxxq_dzbm() {
		return ssjlxxq_dzbm;
	}

	public void setSsjlxxq_dzbm(String ssjlxxq_dzbm) {
		this.ssjlxxq_dzbm = ssjlxxq_dzbm;
	}

	public String getSszdyjxzqy_dzbm() {
		return sszdyjxzqy_dzbm;
	}

	public void setSszdyjxzqy_dzbm(String sszdyjxzqy_dzbm) {
		this.sszdyjxzqy_dzbm = sszdyjxzqy_dzbm;
	}

//	public String getSszdyjxzqy_bz() {
//		return sszdyjxzqy_bz;
//	}
//
//	public void setSszdyjxzqy_bz(String sszdyjxzqy_bz) {
//		this.sszdyjxzqy_bz = sszdyjxzqy_bz;
//	}

	public Date getSlrq() {
		return slrq;
	}

	public void setSlrq(Date slrq) {
		this.slrq = slrq;
	}

	public Date getCxsj() {
		return cxsj;
	}

	public void setCxsj(Date cxsj) {
		this.cxsj = cxsj;
	}

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

	public Date getQyrq() {
		return qyrq;
	}

	public void setQyrq(Date qyrq) {
		this.qyrq = qyrq;
	}

	public Date getTyrq() {
		return tyrq;
	}

	public void setTyrq(Date tyrq) {
		this.tyrq = tyrq;
	}

	public String getSyztdm() {
		return syztdm;
	}

	public void setSyztdm(String syztdm) {
		this.syztdm = syztdm;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public String getCxyy() {
		return cxyy;
	}

	public void setCxyy(String cxyy) {
		this.cxyy = cxyy;
	}

	public String getCxr() {
		return cxr;
	}

	public void setCxr(String cxr) {
		this.cxr = cxr;
	}

	public String getCxdw() {
		return cxdw;
	}

	public void setCxdw(String cxdw) {
		this.cxdw = cxdw;
	}

	public String getZjf() {
		return zjf;
	}

	public void setZjf(String zjf) {
		this.zjf = zjf;
	}

	public String getDjr() {
		return djr;
	}

	public void setDjr(String djr) {
		this.djr = djr;
	}

	public String getDjdw() {
		return djdw;
	}

	public void setDjdw(String djdw) {
		this.djdw = djdw;
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
		this.xgr = xgr;
	}

	public String getXgdw() {
		return xgdw;
	}

	public void setXgdw(String xgdw) {
		this.xgdw = xgdw;
	}

	public BigDecimal getChildcount() {
		return childcount;
	}

	public void setChildcount(BigDecimal childcount) {
		this.childcount = childcount;
	}

	public String getDrsj() {
		return drsj;
	}

	public void setDrsj(String drsj) {
		this.drsj = drsj;
	}

	public String getZjf_temp() {
		return zjf_temp;
	}

	public void setZjf_temp(String zjf_temp) {
		this.zjf_temp = zjf_temp;
	}

	public String getCxbj() {
		return cxbj;
	}

	public void setCxbj(String cxbj) {
		this.cxbj = cxbj;
	}

	public String getSpzt() {
		return spzt;
	}

	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}

	public String getShpzdm() {
		return shpzdm;
	}

	public void setShpzdm(String shpzdm) {
		this.shpzdm = shpzdm;
	}

	public String getSszdyjxzqy_mc() {
		return sszdyjxzqy_mc;
	}

	public void setSszdyjxzqy_mc(String sszdyjxzqy_mc) {
		this.sszdyjxzqy_mc = sszdyjxzqy_mc;
	}

	public String getJlxgsdm() {
		return jlxgsdm;
	}

	public void setJlxgsdm(String jlxgsdm) {
		this.jlxgsdm = jlxgsdm;
	}


}