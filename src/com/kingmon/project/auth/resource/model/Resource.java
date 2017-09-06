package com.kingmon.project.auth.resource.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用资源
 * app_resource
 * @author zhaohuatai
 * @date 2015年10月6日 上午10:11:42
 */
public class Resource implements Serializable{
	
	/**
	 * 应用资源ID-VARCHAR2(36)
	 */
    private String res_id;
	
	/**
	 * 应用领域ID-VARCHAR2(36)
	 */
    private String area_id;
	
	/**
	 * 资源名称-VARCHAR2(200)
	 */
    private String res_name;
	
	/**
	 * 资源描述-VARCHAR2(200)
	 */
    private String res_desc;
	
	/**
	 * #?
	 * 资源类型(选择)-VARCHAR2(100)
	 */
    private String res_type;
	
	/**
	 * #?
	 * 
	 * 资源路径类型-VARCHAR2(36)
	 */
    private String res_pathtype;
	
	/**
	 * 资源路径（真实的URL） -VARCHAR2(200)
	 */
    private String res_pvalue;
	
	/**
	 * 资源序号-VARCHAR2(20)
	 */
    private String res_order;
	
	/**
	 * 应用资源编号-VARCHAR2(40)
	 */
    private String res_code;
	
	/**
	 * 父资源ID-VARCHAR2(36)
	 */
    private String res_pid;
	
	/**
	 * #?
	 *1是,0否- CHAR(1)
	 */
    private String dhsxs;
	
	/** #?
	 * 1是,0否 -CHAR(1)
	 */
    private String sfyz;
	
	/**
	 * 有效标识：0为无效；1为有效； -CHAR(1)
	 */
    private String enabled;
	
	/**
	 * 操作时间 -VARCHAR2(20)
	 */
    private String opratetime;
	
	/**
	 * 是否存在链接 -CHAR(1)
	 */
    private String is_href;
	
	/**
	 * 是否地图操作 -CHAR(1)
	 */
    private String is_map_op;
	
	/**
	 * #?  文档中VARCHAR2(4000)
	 * 数据源信息 -VARCHAR2(500)
	 */
    private String map_op_resource;
	
	/**
	 * 采集模式1标准地址采集 0坐标采集 2依附地址采集-CHAR(1)
	 */
    private String cj_mode;
	
	/**
	 *  -VARCHAR2(36)
	 */
    private String djr;
	
	/**
	 * VARCHAR2(36)
	 */
    private String djdw;
	
	/**
	 * DATE
	 */
    private Date djsj;
	
	/**
	 * VARCHAR2(36)
	 */
    private String xgr;
	
	/**
	 * VARCHAR2(36)
	 */
    private String xgdw;
	
	/**
	 * DATE
	 */
    private Date zhxgxj;
	
	/**
	 * CHAR(1)
	 */
    private String zxbj;
	
	/**
	 * DATE
	 */
    private Date zxrq;
	
	/**
	 * 传输标志（1已传输0未传输） CHAR(1)
	 */
    private String movesign;
	
	/**
	 * 
	 * VARCHAR2(500)
	 */
    private String imagebig_value;
	
	/**
	 * VARCHAR2(500)
	 */
    private String imagesmall_value;
    /**
     * 所属子系统（模块标记）--PSAM
     */
    private String  moduleTag;
	/**
	 * @return the res_id
	 */
	public String getRes_id() {
		return res_id;
	}

	/**
	 * @param res_id the res_id to set
	 */
	public void setRes_id(String res_id) {
		this.res_id = res_id;
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
	 * @return the res_name
	 */
	public String getRes_name() {
		return res_name;
	}

	/**
	 * @param res_name the res_name to set
	 */
	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}

	/**
	 * @return the res_desc
	 */
	public String getRes_desc() {
		return res_desc;
	}

	/**
	 * @param res_desc the res_desc to set
	 */
	public void setRes_desc(String res_desc) {
		this.res_desc = res_desc;
	}

	/**
	 * @return the res_type
	 */
	public String getRes_type() {
		return res_type;
	}

	/**
	 * @param res_type the res_type to set
	 */
	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}

	/**
	 * @return the res_pathtype
	 */
	public String getRes_pathtype() {
		return res_pathtype;
	}

	/**
	 * @param res_pathtype the res_pathtype to set
	 */
	public void setRes_pathtype(String res_pathtype) {
		this.res_pathtype = res_pathtype;
	}

	/**
	 * @return the res_pvalue
	 */
	public String getRes_pvalue() {
		return res_pvalue;
	}

	/**
	 * @param res_pvalue the res_pvalue to set
	 */
	public void setRes_pvalue(String res_pvalue) {
		this.res_pvalue = res_pvalue;
	}

	/**
	 * @return the res_order
	 */
	public String getRes_order() {
		return res_order;
	}

	/**
	 * @param res_order the res_order to set
	 */
	public void setRes_order(String res_order) {
		this.res_order = res_order;
	}

	/**
	 * @return the res_code
	 */
	public String getRes_code() {
		return res_code;
	}

	/**
	 * @param res_code the res_code to set
	 */
	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	/**
	 * @return the res_pid
	 */
	public String getRes_pid() {
		return res_pid;
	}

	/**
	 * @param res_pid the res_pid to set
	 */
	public void setRes_pid(String res_pid) {
		this.res_pid = res_pid;
	}

	/**
	 * @return the dhsxs
	 */
	public String getDhsxs() {
		return dhsxs;
	}

	/**
	 * @param dhsxs the dhsxs to set
	 */
	public void setDhsxs(String dhsxs) {
		this.dhsxs = dhsxs;
	}

	/**
	 * @return the sfyz
	 */
	public String getSfyz() {
		return sfyz;
	}

	/**
	 * @param sfyz the sfyz to set
	 */
	public void setSfyz(String sfyz) {
		this.sfyz = sfyz;
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
	 * @return the is_href
	 */
	public String getIs_href() {
		return is_href;
	}

	/**
	 * @param is_href the is_href to set
	 */
	public void setIs_href(String is_href) {
		this.is_href = is_href;
	}

	/**
	 * @return the is_map_op
	 */
	public String getIs_map_op() {
		return is_map_op;
	}

	/**
	 * @param is_map_op the is_map_op to set
	 */
	public void setIs_map_op(String is_map_op) {
		this.is_map_op = is_map_op;
	}

	/**
	 * @return the map_op_resource
	 */
	public String getMap_op_resource() {
		return map_op_resource;
	}

	/**
	 * @param map_op_resource the map_op_resource to set
	 */
	public void setMap_op_resource(String map_op_resource) {
		this.map_op_resource = map_op_resource;
	}

	/**
	 * @return the cj_mode
	 */
	public String getCj_mode() {
		return cj_mode;
	}

	/**
	 * @param cj_mode the cj_mode to set
	 */
	public void setCj_mode(String cj_mode) {
		this.cj_mode = cj_mode;
	}

	/**
	 * @return the djr
	 */
	public String getDjr() {
		return djr;
	}

	/**
	 * @param djr the djr to set
	 */
	public void setDjr(String djr) {
		this.djr = djr;
	}

	/**
	 * @return the djdw
	 */
	public String getDjdw() {
		return djdw;
	}

	/**
	 * @param djdw the djdw to set
	 */
	public void setDjdw(String djdw) {
		this.djdw = djdw;
	}

	/**
	 * @return the djsj
	 */
	public Date getDjsj() {
		return djsj;
	}

	/**
	 * @param djsj the djsj to set
	 */
	public void setDjsj(Date djsj) {
		this.djsj = djsj;
	}

	/**
	 * @return the xgr
	 */
	public String getXgr() {
		return xgr;
	}

	/**
	 * @param xgr the xgr to set
	 */
	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	/**
	 * @return the xgdw
	 */
	public String getXgdw() {
		return xgdw;
	}

	/**
	 * @param xgdw the xgdw to set
	 */
	public void setXgdw(String xgdw) {
		this.xgdw = xgdw;
	}

	/**
	 * @return the zhxgxj
	 */
	public Date getZhxgxj() {
		return zhxgxj;
	}

	/**
	 * @param zhxgxj the zhxgxj to set
	 */
	public void setZhxgxj(Date zhxgxj) {
		this.zhxgxj = zhxgxj;
	}

	/**
	 * @return the zxbj
	 */
	public String getZxbj() {
		return zxbj;
	}

	/**
	 * @param zxbj the zxbj to set
	 */
	public void setZxbj(String zxbj) {
		this.zxbj = zxbj;
	}

	/**
	 * @return the zxrq
	 */
	public Date getZxrq() {
		return zxrq;
	}

	/**
	 * @param zxrq the zxrq to set
	 */
	public void setZxrq(Date zxrq) {
		this.zxrq = zxrq;
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
	 * @return the imagebig_value
	 */
	public String getImagebig_value() {
		return imagebig_value;
	}

	/**
	 * @param imagebig_value the imagebig_value to set
	 */
	public void setImagebig_value(String imagebig_value) {
		this.imagebig_value = imagebig_value;
	}

	/**
	 * @return the imagesmall_value
	 */
	public String getImagesmall_value() {
		return imagesmall_value;
	}

	/**
	 * @param imagesmall_value the imagesmall_value to set
	 */
	public void setImagesmall_value(String imagesmall_value) {
		this.imagesmall_value = imagesmall_value;
	}

	public String getModuleTag() {
		return moduleTag;
	}

	public void setModuleTag(String moduleTag) {
		this.moduleTag = moduleTag;
	}

}