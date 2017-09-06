package com.kingmon.project.psam.xzjd.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.xzjd.model.Xzjd;

public interface IXzjdService {

	/**
	 * 添加乡镇街道（部分字段可为空）
	 * 
	 * @param record
	 *            乡镇街道信息
	 */
	void addXzjd(Xzjd record);

	/**
	 * 根据地址编码查询乡镇街道
	 * 
	 * @param DZBM
	 *            地址编码
	 * @return Xzjd 乡镇街道信息
	 */
	Xzjd getXzjdById(String DZBM);

	/**
	 * 更新乡镇街道信息（更新部分字段）
	 * 
	 * @param record
	 *            乡镇街道信息
	 */
	void updateXzjd(Xzjd record);

	/**
	 * 撤销乡镇街道
	 * 
	 * @param record
	 */
	void revokeXzjd(String dzbm, String cxyy);

	/**
	 * 启用乡镇街道
	 * 
	 * @param dzbm
	 *            地址编码
	 */
	void activateXzjd(String dzbm);

	/**
	 * 根据条件查询乡镇街道信息
	 * 
	 * @param paramObject
	 *            查询条件<br/>
	 *            乡镇街道代码 XZJDDM 类型：String;<br/>
	 *            乡镇街道名称 XZJDMC 类型：String;<br/>
	 *            使用状态 SYZTDM 类型：String;<br/>
	 * 
	 * @return DataSet: 返回值：乡镇街道集合DataSet
	 */
	public DataSet loadXzjdDataSet(ParamObject paramObject);
	
//	/**
//	 * 加载map(name,dzbm)形式的行政区划集合
//	 * @return  map(name,id)形式的行政区划集合
//	 */
//	@SuppressWarnings("rawtypes")
//	public List<Map>loadXzqhCombox();\
	/**
	 *  根据地址编码查询乡镇街道的详细信息
	 * @param dzbm 
	 * @return
	 */
	public Map selectDetailByPrimaryKey(String dzbm);
	
	/**
	 * 从Excel导入
	 * @param workbook
	 */
	public String importFromExcel(Workbook workbook,String type);
}
