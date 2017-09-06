package com.kingmon.project.auth.organization.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 应用组织机构
 * app_organization
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:55:45
 */
public class Organization implements Serializable{
	public Organization() {
	}
	public Organization(String orgna_id) {
		super();
		this.orgna_id = orgna_id;
	}
	public Organization(String orgna_id,String orgna_code) {
		super();
		this.orgna_id = orgna_id;
		this.orgna_code = orgna_code;
	}
	/**
	 * 应用组织机构ID-VARCHAR2(36)
	 */
    private String orgna_id;
	
	/**
	 * 应用组织机构编号-VARCHAR2(40)
	 */
    private String orgna_code;
	
	/**
	 * 应用组织机构名称 -VARCHAR2(300)
	 */
    private String orgna_name;
	
	/**
	 * 应用组织机构简称-VARCHAR2(260)
	 */
    private String orgna_jc;
	
	/**
	 *  应用组织机构类型-字典表APP_DICTIONARY类别=ORGNA_TYPE  -VARCHAR2(32)
	 */
    private String orgna_type;
	
	/**
	 *
	 * 应用组织机构性质-字典表APP_DICTIONARY类别=ORGNA_PEROPERTY   -VARCHAR2(36)
	 */
    private String orgna_property;
	
	/**
	 * 应用组织机构级别-字典表-APP_DICTIONARY类别=ORGNA_LEVEL -VARCHAR2(32)
	 */
    private String orgna_level;
	
	/**
	 * 应用组织机构地址-VARCHAR2(200)
	 */
    private String orgna_address;
	
	/**
	 * 应用组织机构邮编-VARCHAR2(40)
	 */
    private String orgna_zipcode;
	
	/**
	 * 应用组织机构电话-VARCHAR2(40)
	 */
    private String orgna_tel;
	
	/**
	 * 应用组织机构传真 -VARCHAR2(40)
	 */
    private String orgna_fax;
	
	/**
	 * 应用组织机构email -VARCHAR2(200)
	 */
    private String orgna_email;
	
	/**
	 * 父组织机构ID -VARCHAR2(36)
	 */
    private String porgna_id;
	
	/**
	 *未用
	 * 
	 * 企业组织机构ID--VARCHAR2(32)
	 */
    private String entorgna_id;
	
	/**
	 * 应用领域ID -VARCHAR2(36)
	 */
    private String area_id;
	
	/**
	 * 有效标识：1为有效；0为无效；-CHAR(1)
	 */
    private String enabled;
	
	/**
	 * 有效时间 -VARCHAR2(20)
	 */
    private String orgna_validity_start;
	
	/**
	 *失效时间 -VARCHAR2(20)
	 */
    private String orgna_validity_end;
	
	/**
	 * 未用
	 * 层次号NUMBER(8)
	 */ 
    private Integer cch;
	
	/**
	 * 未用
	 * 层次标志 VARCHAR2(3)
	 */
    private String ccbz;
	
	/**
	 *未用
	 * 汇总标志  -CHAR(1)
	 */
    private String hzbz;
	
	/**
	 * 操作时间 -VARCHAR2(20)
	 */
    private String opratetime;
	
	/**
	 * 排列序号 -VARCHAR2(20)
	 */
    private String orgna_order;
	
	/**
	 * 备注0 -VARCHAR2(40)
	 */
    private String bz0;
	
	/**
	 * 备注1(所在行政区划) -VARCHAR2(40)
	 */
    private String bz1;
	
	/**
	 *  备注2 -VARCHAR2(40)
	 */
    private String bz2;
	
	/**
	 *  备注3 -VARCHAR2(40)
	 */
    private String bz3;
	
	/**
	 *  备注4 -VARCHAR2(40)
	 */
    private String bz4;
	
	

	/**
	 *  备注5 -VARCHAR2(40)
	 */
    private String bz5;
	
	/**
	 *  备注6 -VARCHAR2(40)
	 */
    private String bz6;
	
	/**
	 *  备注7 -VARCHAR2(40)
	 */
    private String bz7;
	
	/**
	 *传输标志（1已传输0未传输） -VARCHAR2(10)
	 */
    private String movesign;
	
	/**#?
	 * 子节点-NUMBER
	 */
    private BigDecimal orgna_hischildnode;
	
	/**
	 * 有效性（1为有效   0为无效) 0为派出所队伍建设 1是队伍建设 -VARCHAR2(1)
	 */
    private String orgna_yxx;
	
	/**
	 * 是否直属局（0否 1是）-VARCHAR2(10)
	 */
    private String sfzsj;
    
    /**
   	 * #新增字段# 归属代码
   	 *   -VARCHAR2(36)
   	 */
   	private String orgna_gsdm;
   	
	/**
	 * @return the orgna_id
	 */
	public String getOrgna_id() {
		return orgna_id;
	}

	/**
	 * @param orgna_id the orgna_id to set
	 */
	public void setOrgna_id(String orgna_id) {
		this.orgna_id = orgna_id;
	}

	/**
	 * @return the orgna_code
	 */
	public String getOrgna_code() {
		return orgna_code;
	}

	/**
	 * @param orgna_code the orgna_code to set
	 */
	public void setOrgna_code(String orgna_code) {
		this.orgna_code = orgna_code;
	}

	/**
	 * @return the orgna_name
	 */
	public String getOrgna_name() {
		return orgna_name;
	}

	/**
	 * @param orgna_name the orgna_name to set
	 */
	public void setOrgna_name(String orgna_name) {
		this.orgna_name = orgna_name;
	}

	/**
	 * @return the orgna_jc
	 */
	public String getOrgna_jc() {
		return orgna_jc;
	}

	/**
	 * @param orgna_jc the orgna_jc to set
	 */
	public void setOrgna_jc(String orgna_jc) {
		this.orgna_jc = orgna_jc;
	}

	/**
	 * @return the orgna_type
	 */
	public String getOrgna_type() {
		return orgna_type;
	}

	/**
	 * @param orgna_type the orgna_type to set
	 */
	public void setOrgna_type(String orgna_type) {
		this.orgna_type = orgna_type;
	}

	/**
	 * @return the orgna_property
	 */
	public String getOrgna_property() {
		return orgna_property;
	}

	/**
	 * @param orgna_property the orgna_property to set
	 */
	public void setOrgna_property(String orgna_property) {
		this.orgna_property = orgna_property;
	}

	/**
	 * @return the orgna_level
	 */
	public String getOrgna_level() {
		return orgna_level;
	}

	/**
	 * @param orgna_level the orgna_level to set
	 */
	public void setOrgna_level(String orgna_level) {
		this.orgna_level = orgna_level;
	}

	/**
	 * @return the orgna_address
	 */
	public String getOrgna_address() {
		return orgna_address;
	}

	/**
	 * @param orgna_address the orgna_address to set
	 */
	public void setOrgna_address(String orgna_address) {
		this.orgna_address = orgna_address;
	}

	/**
	 * @return the orgna_zipcode
	 */
	public String getOrgna_zipcode() {
		return orgna_zipcode;
	}

	/**
	 * @param orgna_zipcode the orgna_zipcode to set
	 */
	public void setOrgna_zipcode(String orgna_zipcode) {
		this.orgna_zipcode = orgna_zipcode;
	}

	/**
	 * @return the orgna_tel
	 */
	public String getOrgna_tel() {
		return orgna_tel;
	}

	/**
	 * @param orgna_tel the orgna_tel to set
	 */
	public void setOrgna_tel(String orgna_tel) {
		this.orgna_tel = orgna_tel;
	}

	/**
	 * @return the orgna_fax
	 */
	public String getOrgna_fax() {
		return orgna_fax;
	}

	/**
	 * @param orgna_fax the orgna_fax to set
	 */
	public void setOrgna_fax(String orgna_fax) {
		this.orgna_fax = orgna_fax;
	}

	/**
	 * @return the orgna_email
	 */
	public String getOrgna_email() {
		return orgna_email;
	}

	/**
	 * @param orgna_email the orgna_email to set
	 */
	public void setOrgna_email(String orgna_email) {
		this.orgna_email = orgna_email;
	}

	/**
	 * @return the porgna_id
	 */
	public String getPorgna_id() {
		return porgna_id;
	}

	/**
	 * @param porgna_id the porgna_id to set
	 */
	public void setPorgna_id(String porgna_id) {
		this.porgna_id = porgna_id;
	}

	/**
	 * @return the entorgna_id
	 */
	public String getEntorgna_id() {
		return entorgna_id;
	}

	/**
	 * @param entorgna_id the entorgna_id to set
	 */
	public void setEntorgna_id(String entorgna_id) {
		this.entorgna_id = entorgna_id;
	}

	/**
	 * @return the area_id
	 */
	public String getArea_id() {
		return area_id;
	}

	/**
	 * @param area_id the area_id to set
	 */
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the orgna_validity_start
	 */
	public String getOrgna_validity_start() {
		return orgna_validity_start;
	}

	/**
	 * @param orgna_validity_start the orgna_validity_start to set
	 */
	public void setOrgna_validity_start(String orgna_validity_start) {
		this.orgna_validity_start = orgna_validity_start;
	}

	/**
	 * @return the orgna_validity_end
	 */
	public String getOrgna_validity_end() {
		return orgna_validity_end;
	}

	/**
	 * @param orgna_validity_end the orgna_validity_end to set
	 */
	public void setOrgna_validity_end(String orgna_validity_end) {
		this.orgna_validity_end = orgna_validity_end;
	}

	/**
	 * @return the cch
	 */
	public Integer getCch() {
		return cch;
	}

	/**
	 * @param cch the cch to set
	 */
	public void setCch(Integer cch) {
		this.cch = cch;
	}

	/**
	 * @return the ccbz
	 */
	public String getCcbz() {
		return ccbz;
	}

	/**
	 * @param ccbz the ccbz to set
	 */
	public void setCcbz(String ccbz) {
		this.ccbz = ccbz;
	}

	/**
	 * @return the hzbz
	 */
	public String getHzbz() {
		return hzbz;
	}

	/**
	 * @param hzbz the hzbz to set
	 */
	public void setHzbz(String hzbz) {
		this.hzbz = hzbz;
	}

	/**
	 * @return the opratetime
	 */
	public String getOpratetime() {
		return opratetime;
	}

	/**
	 * @param opratetime the opratetime to set
	 */
	public void setOpratetime(String opratetime) {
		this.opratetime = opratetime;
	}

	/**
	 * @return the orgna_order
	 */
	public String getOrgna_order() {
		return orgna_order;
	}

	/**
	 * @param orgna_order the orgna_order to set
	 */
	public void setOrgna_order(String orgna_order) {
		this.orgna_order = orgna_order;
	}

	/**
	 * @return the bz0
	 */
	public String getBz0() {
		return bz0;
	}

	/**
	 * @param bz0 the bz0 to set
	 */
	public void setBz0(String bz0) {
		this.bz0 = bz0;
	}

	/**
	 * @return the bz1
	 */
	public String getBz1() {
		return bz1;
	}

	/**
	 * @param bz1 the bz1 to set
	 */
	public void setBz1(String bz1) {
		this.bz1 = bz1;
	}

	/**
	 * @return the bz2
	 */
	public String getBz2() {
		return bz2;
	}

	/**
	 * @param bz2 the bz2 to set
	 */
	public void setBz2(String bz2) {
		this.bz2 = bz2;
	}

	/**
	 * @return the bz3
	 */
	public String getBz3() {
		return bz3;
	}

	/**
	 * @param bz3 the bz3 to set
	 */
	public void setBz3(String bz3) {
		this.bz3 = bz3;
	}

//	/**
//	 * @return the bz4
//	 */
//	public String getBz4() {
//		return bz4;
//	}
//
//	/**
//	 * @param bz4 the bz4 to set
//	 */
//	public void setBz4(String bz4) {
//		this.bz4 = bz4;
//	}
	public String getBz4() {
		return bz4;
	}

	public void setBz4(String bz4) {
		this.bz4 = bz4;
	}
	/**
	 * @return the bz5
	 */
	public String getBz5() {
		return bz5;
	}

	/**
	 * @param bz5 the bz5 to set
	 */
	public void setBz5(String bz5) {
		this.bz5 = bz5;
	}

	/**
	 * @return the bz6
	 */
	public String getBz6() {
		return bz6;
	}

	/**
	 * @param bz6 the bz6 to set
	 */
	public void setBz6(String bz6) {
		this.bz6 = bz6;
	}

	/**
	 * @return the bz7
	 */
	public String getBz7() {
		return bz7;
	}

	/**
	 * @param bz7 the bz7 to set
	 */
	public void setBz7(String bz7) {
		this.bz7 = bz7;
	}

	/**
	 * @return the movesign
	 */
	public String getMovesign() {
		return movesign;
	}

	/**
	 * @param movesign the movesign to set
	 */
	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	/**
	 * @return the orgna_hischildnode
	 */
	public BigDecimal getOrgna_hischildnode() {
		return orgna_hischildnode;
	}

	/**
	 * @param orgna_hischildnode the orgna_hischildnode to set
	 */
	public void setOrgna_hischildnode(BigDecimal orgna_hischildnode) {
		this.orgna_hischildnode = orgna_hischildnode;
	}

	/**
	 * @return the orgna_yxx
	 */
	public String getOrgna_yxx() {
		return orgna_yxx;
	}

	/**
	 * @param orgna_yxx the orgna_yxx to set
	 */
	public void setOrgna_yxx(String orgna_yxx) {
		this.orgna_yxx = orgna_yxx;
	}

	/**
	 * @return the sfzsj
	 */
	public String getSfzsj() {
		return sfzsj;
	}

	/**
	 * @param sfzsj the sfzsj to set
	 */
	public void setSfzsj(String sfzsj) {
		this.sfzsj = sfzsj;
	}
	
	public String getOrgna_gsdm() {
		return orgna_gsdm;
	}
	
	public void setOrgna_gsdm(String orgna_gsdm) {
		this.orgna_gsdm = orgna_gsdm;
	}

}