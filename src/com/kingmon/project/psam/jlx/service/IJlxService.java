package com.kingmon.project.psam.jlx.service;

import java.util.List;
import java.util.Map;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.jlx.model.Jlx;

public interface IJlxService {
	/**
	 * 提交添加申请
	 * @param dzbm
	 * @param pzlx
	 */
	public void applyUseJlx(String dzbm);
	/**
	 * 审批
	 * @param dzbm
	 */
	public void reviewJlx(String dzbm,String spzt);
	/**
	 * 根据条件查询街路巷（小区）信息
	 * 
	 * @param paramObject
	 *            查询条件<br/>
	 *            街路巷（小区）代码 JLXXQDM 类型：String;<br/>
	 *            街路巷（小区）名称 JLXXQMC 类型：String;<br/>
	 *            使用状态 SYZTDM 类型：String;<br/>
	 * 
	 * @return DataSet: 返回值：街路巷（小区）集合DataSet
	 */
	public DataSet loadJlxDataSet(ParamObject paramObject);
	/**
	 * 更新街路巷（小区）信息（更新部分字段）
	 * 
	 * @param jlx
	 *            街路巷（小区）信息
	 */
	public void updateJlx(Jlx jlx);
	/**
	 * 根据地址编码查询街路巷（小区）
	 * 
	 * @param DZBM
	 *            地址编码
	 * @return Jlx 街路巷（小区）信息
	 */
	public Jlx getJlxById(String dbzm);
	
	/**
	 *  根据地址编码查询街路巷（小区）-没有关联查询，只是基本信息
	 * @param dbzm
	 * @return
	 */
	public Jlx getSimpleJlxById(String dbzm);
	/**
	 * 添加街路巷（小区）
	 * 
	 * @param jlx
	 *
	 */
	public void addJlx(Jlx jlx);
	/**
	 * 启用街路巷（小区）
	 * 
	 * @param jlx
	 *            街路巷（小区）信息
	 */
	public void activateJlx(String dzbm);
	/**
	 * 撤销街路巷（小区）
	 * 
	 * @param jlx
	 *
	 *	 *        街路巷（小区）信息
	 */
	public void revokeJlx(String dzbm, String cxyy);
	
	//------------------------------------
	
	public DataSet loadJlxBMMCDataSet(Map<String,String> params);

	/**
	 * 
	 * @param paramObject
	 * @param sszdyjxzqy_dzbm 所属最低一级行政区域代码
	 * @param sszdyjxzqy_xtype sszdyjxzqy_dzbm 是什么类型 xzqh， xzjd， sqjcwh
	 * @param isLoadFromSuperXzqy 是否从sszdyjxzqy_dzbm  0：只有本级节点；  1:本节点 + 上级节点 ，两个节点的数据，； 2:本级节点，上级节点一直到市级节点
	 * @return
	 */
	public DataSet loadJlxAjaxData(ParamObject paramObject,String sszdyjxzqy_dzbm,String sszdyjxzqy_xtype,String isLoadFromSuperXzqy);
	
	
	/**
	 * 根据地址编码查询街路巷详细信息
	 * @param dzbm
	 * @return
	 */
	public Map selectDetailByPrimaryKey(String dzbm);
	
	/**
	 * 获取街路巷最低一级行政区域类型
	 * 
	 * @param jlx_sszdyjxzqy_dzbm
	 * @return : xqhq, xzjd,sqjcwh,未查询到  返回  null
	 */
	public String getJlxSszdyjxzqyType(String jlx_sszdyjxzqy_dzbm);
	
	/**
	 * 根据所属最低一级行政区划查询信息
	 * @param dzbm
	 * @return
	 */
	public List<Map<String,String>>selectXzqyMapByDzbm(String dzbm);
	
	public Map<String,Object> selectXzqhmcBySszdyjxzqyDzbm(String dzbm);
}
