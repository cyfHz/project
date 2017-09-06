package com.kingmon.project.psam.sqjcwh.service;


import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.sqjcwh.model.Sqjcwh;

public interface ISqjcwhService {
	/**
	 * 启用社区居委会
	 * 
	 * @param sqjcwh
	 *            社区居村委会信息
	 */
	public void activateSqjcwh(String dzbm);
	/**
	 * 撤销社区居委会
	 * 
	 * @param sqjcwh
	 *            社区居村委会信息
	 */
	public void revokeSqjcwh(String dzbm, String cxyy);
	/**
	 * 更新社区居村委会信息（更新部分字段）
	 * 
	 * @param sqjcwh
	 *            社区居村委会信息
	 */
	public void updateSqjcwh(Sqjcwh sqjcwh);
	/**
	 * 添加社区居村委会
	 * 
	 * @param sqjcwh
	 * @return Sqjcwh 社区居村委会信息
	 */
	public void addSqjcwh(Sqjcwh sqjcwh);
	/**
	 * 根据地址编码查询社区居村委会
	 * 
	 * @param DZBM
	 *            地址编码
	 * @return Sqjcwh 社区居村委会信息
	 */
	public Sqjcwh getSqjcwhById(String dbzm);
	
	/**
	 * 根据条件查询社区居村委会信息
	 * 
	 * @param paramObject
	 *            查询条件<br/>
	 *            社区居村委会代码 SQJCWHDM 类型：String;<br/>
	 *            社区居村委会名称 SQJCWHMC 类型：String;<br/>
	 *            使用状态 SYZTDM 类型：String;<br/>
	 * 
	 * @return DataSet: 返回值：社区居村委会集合DataSet
	 */
	public DataSet loadSqjcwhDataSet(ParamObject po);
	/**
	 * 根据地址编码查询社区居村委会详细信息
	 * @param dzbm
	 * @return
	 */
	public Map selectDetailByPrimaryKey(String dzbm);
}
