package com.kingmon.project.psam.xzjd.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.xzjd.model.Xzjd;

@KMapper
public interface XzjdMapper {
	/**
	 * 添加乡镇街道（部分字段可为空）
	 * 
	 * @param record
	 *            乡镇街道信息
	 */
	void insertSelective(Xzjd record);

	/**
	 * 根据地址编码查询乡镇街道
	 * 
	 * @param DZBM
	 *            地址编码
	 * @return Xzjd 乡镇街道信息
	 */
	Xzjd selectByPrimaryKeyL(String DZBM);

	/**
	 * 更新乡镇街道信息（更新部分字段）
	 * 
	 * @param record
	 *            乡镇街道信息
	 */
	void updateByPrimaryKeySelective(Xzjd record);
	
	/**
	 * 撤销乡镇街道
	 * @param DZBMS 地址编码
	 */
	void revokeXzjd(Xzjd xzjd);
	
	/**
	 * 启用乡镇街道
	 * @param xzjd 乡镇街道
	 */
	void activateXzjd(Xzjd xzjd);

	/**
	 * 
	 * @param dzbm
	 * @param xzjddm
	 * @return
	 */
	long queryCount(@Param("dzbm")String dzbm,@Param("xzjddm")String xzjddm);
	/**
	 * 根据地址编码查询乡镇街道的详细信息
	 * @param dzbm
	 * @return
	 */
	public Map selectDetailByPrimaryKey(String dzbm);
	
	/**
	 * 批量添加乡镇街道
	 * @param xzjdList 乡镇街道集合
	 */
	public void batchInsertXzjd(ArrayList<Xzjd> xzjdList);
	
	/**
	 * 
	 * @param DZBM dzbm
	 * @return
	 */
	public String selectSjxzqyDzbmByKey(String dzbm);
	
	/**
	 * 乡镇街道查询服务 查询接口
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> xzjdListForWebService(Map<String,String> params);
	/**
	 * 乡镇街道查询服务 查询总条数
	 * @param params
	 * @return
	 */
	public Long countForWebService(Map<String,String> params);
	
	//select t.xzjddm  from DZ_XZJD t where t.sjxzqy_dzbm=:sjxzqy_dzbm
	public List<String> selectSzjdbhListBySjxzqyDzbm(String sjxzqy_dzbm);
	
	
}