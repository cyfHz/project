package com.kingmon.project.psam.sqjcwh.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * #new# 添加或修改字段标识<br>
 * 
 * 地址-社区居村委会
 * @author zhaohuatai
 * @date 2015年10月5日 下午6:37:55
 */
public class Sqjcwh implements Serializable{
	
	/**
	 * 	主键uuid、<br>
	 *  必填** <br>
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
	 * 数据字典 项 
	 * 
	 * 地址元素类型代码-VARCHAR2(20)
	 */
    private String dzyslxdm;
    
    
    /**
     * 编码规则 6+3+3  <br>
     * 
     * 保证不重复 <br>
     * 
     *  必填** <br>
     *  
	 * 社区、居（村）委会代码-VARCHAR2(36)
	 */
    private String sqjcwhdm;
    
    
    /**
     *  必填** <br>
	 * 社区、居（村）委会名称-VARCHAR2(100)
	 */
    private String sqjcwhmc;
    
    /**
     * 
     *  必填** <br>
	 * 别名简称-VARCHAR2(50)
	 */
    private String bmjc;
    
    /**
     * 
     *  100	城镇	<br>
	 *	110	城区	             在市辖区和不设区的市，区、市政府驻地的实际建设连接到的居民委员会和其他区域。<br>
	 *	111	主城区	             没有农业用地，与政府驻地完全连接的城区。<br>
	 *	112	城乡结合区	有农业用地，与政府驻地部分连接的城区。<br>
	 *	
	 *  120	镇区	             在城区以外的县人民政府驻地和其他镇，政府驻地的实际建设连接到的居民委员会和其他区域；与政府驻地的实际建设不连接，且常住人口在3000人以上的独立的工矿区、开发区、科研单位、大专院校等特殊区域及农场、林场的场部驻地。<br>
	 *	121	镇中心区	没有农业用地，与政府驻地完全连接的镇区。<br>
	 *	122	镇乡结合区	有农业用地，与政府驻地部分连接的镇区。<br>
	 *	123	特殊区域	常住人口超过3000人，且不属于乡级政府驻地、完全连接的村级地域、部分连接的村级地域的下列区域：独立的工矿区；经各级人民政府批准的经济技术开发区、高新技术开发区、工业园区、科技园区等区域；科研单位、大专院校等区域；从事非农产业人员达到70%的类似村委会。<br>
	 *	
	 *  200	乡村	             城镇以外的区域。<br>
	 *	210	乡中心区	与乡政府驻地完全连接的区域。<br>
	 *	220	村庄	<br>
	 *
	 * 必填** <br>
	 * 地域城乡属性-VARCHAR2(3)
	 * 数据字典 项DZ_DATADIC-DECLARE_CODE:0003000000  
	 */
    private String dycxsxdm;
    
    /**
     * 此处为 乡镇街道 
     * 
	 * 上级行政区域_地址编码-VARCHAR2(36)
	 */
    private String sjxzqy_dzbm;
    
    /**
     * #new#+ 不是数据库字段
     * 上级行政区域_地址名称
     */
    private String sjxzqy_mc;
    
    /**
     * #new# 新加字段标识<br>
     * 不是必填，可能会出现
	 * 所属社区居村委会代码_地址编码-VARCHAR2(36)<br>
	 */
    private String sjsqjcwh_dzbm;
    
    /**
     * #new#+ 不是数据库字段
     * 所属社区居村委会代码_地址名称
     */
    private String sjsqjcwh_mc;
    
    /**
     *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 设立日期-DATE
	 */
    private Date slrq;
    
    /**
     *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 撤销时间-DATE
	 */
    private Date cxsj;
    
    /**
     *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 更新时间-DATE
	 */
    private Date gxsj;
    
    /**
     *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 启用日期-DATE
	 */
    private Date qyrq;
    
    /**
     *  时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 停用日期-DATE
	 */
    private Date tyrq;
    
    /**
	 * 使用状态DZ_SYZT(10,使用中;20,停用;30,未启用;40,维修;50,报废;60,撤销;90,其他)<br>
	 * 
	 * 数据字典 项DZ_DATADIC-DECLARE_CODE:0002000000 
	 *  
	 * 使用状态代码-VARCHAR2(2)
	 */
    private String syztdm;
    
    /**
     * 不用操作
	 * MOVESIGN-CHAR(1)
	 */
    private String movesign;
    
    /**
     * 涉及到 撤销 时，必填<br>
     * 
   	 *  撤销原因-VARCHAR2(100)
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
     * 根据BMJC 拼音生成 ZJF<br>
	 * 助记符-VARCHAR2(60)
	 */
    private String zjf;
    
    /**
 	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
	 * 登记人-VARCHAR2(36)
	 */
    private String djr;
    
    /**
     * 外键 来自 APP_ORGANIZATION <br>
	 * 
	 * 此处为当前登录用户所在单位<br>
	 * 登记单位-VARCHAR2(36)
	 */
    private String djdw;
    
    /**
	 * 当前时间<br>
   	 * 登记时间(yyyy-mm-dd hh:mi:ss)-DATE
	 */
    private Date djsj;
    
    /**
	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
	 * 修改人-VARCHAR2(36)
	 */
    private String xgr;
    
    /**
     * 外键 来自 APP_ORGANIZATION <br>
	 * 此处为当前登录用户所在单位<br>
	 * 修改单位-VARCHAR2(36)
	 */
    private String xgdw;
    
    /**
     * 不用操作
	 * 子节点个数-NUMBER
	 */
    private BigDecimal childcount;
    
    /**
	 * 不用操作
	 * 社区、居（村）委会代码（临时）-VARCHAR2(36)
	 */
    private String sqjcwhdm_new;
    /**
     * #new#
     * 撤销标记char(1) 1:撤销（注销），其他正常
     */
    private String cxbj;
    
    /**
	 * #新增字段# 归属代码
	 *   -VARCHAR2(36)
	 */
	private String sqjcwhgsdm;
	
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

	public String getSqjcwhdm() {
		return sqjcwhdm;
	}

	public void setSqjcwhdm(String sqjcwhdm) {
		this.sqjcwhdm = sqjcwhdm;
	}

	public String getSqjcwhmc() {
		return sqjcwhmc;
	}

	public void setSqjcwhmc(String sqjcwhmc) {
		this.sqjcwhmc = sqjcwhmc;
	}

	public String getBmjc() {
		return bmjc;
	}

	public void setBmjc(String bmjc) {
		this.bmjc = bmjc;
	}

	public String getDycxsxdm() {
		return dycxsxdm;
	}

	public void setDycxsxdm(String dycxsxdm) {
		this.dycxsxdm = dycxsxdm;
	}

	public String getSjxzqy_dzbm() {
		return sjxzqy_dzbm;
	}

	public void setSjxzqy_dzbm(String sjxzqy_dzbm) {
		this.sjxzqy_dzbm = sjxzqy_dzbm;
	}

	public String getSjsqjcwh_dzbm() {
		return sjsqjcwh_dzbm;
	}

	public void setSjsqjcwh_dzbm(String sjsqjcwh_dzbm) {
		this.sjsqjcwh_dzbm = sjsqjcwh_dzbm;
	}

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

	public String getSqjcwhdm_new() {
		return sqjcwhdm_new;
	}

	public void setSqjcwhdm_new(String sqjcwhdm_new) {
		this.sqjcwhdm_new = sqjcwhdm_new;
	}

	public String getCxbj() {
		return cxbj;
	}

	public void setCxbj(String cxbj) {
		this.cxbj = cxbj;
	}


	public String getSjsqjcwh_mc() {
		return sjsqjcwh_mc;
	}

	public void setSjsqjcwh_mc(String sjsqjcwh_mc) {
		this.sjsqjcwh_mc = sjsqjcwh_mc;
	}

	public String getSjxzqy_mc() {
		return sjxzqy_mc;
	}

	public void setSjxzqy_mc(String sjxzqy_mc) {
		this.sjxzqy_mc = sjxzqy_mc;
	}

	public String getSqjcwhgsdm() {
		return sqjcwhgsdm;
	}

	public void setSqjcwhgsdm(String sqjcwhgsdm) {
		this.sqjcwhgsdm = sqjcwhgsdm;
	}
	

}