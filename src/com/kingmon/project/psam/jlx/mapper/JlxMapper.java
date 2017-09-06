package com.kingmon.project.psam.jlx.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jlx.model.Jlx;

@KMapper
public interface JlxMapper {
	/**
	 * 申请添加
	 * @param shpzdm
	 * @param dzbm
	 */
	void applyUseJlx( @Param("spzt")String spzt, @Param("shpzdm")String shpzdm,@Param("dzbm")String dzbm);
	/**
	 * 查询审核配置代码
	 * @param pzlx
	 * @return
	 */
	String queryShpzdm(String pzlx);
	/**
	 * 审批
	 * @param dzbm
	 * @param spzt
	 */
	void reviewJlx(String dzbm,String spzt);
	/**
	 * 根据地址编码删除街路巷（小区）
	 * 
	 * @param DZBM
	 *            地址编码
	 */
    int deleteByPrimaryKey(String DZBM);
    /**
	 * 添加街路巷（小区）(部分字段为空)
	 * 
	 * @param record
	 *            街路巷（小区）信息
	 */
    int insertSelective(Jlx record);
    
    /**
	 * 根据地址编码查询街路巷（小区）-没有关联查询
	 * 
	 * @param DZBM
	 *            地址编码
	 * @return Sqjcwh 街路巷（小区）信息
	 */
    Jlx selectByPrimaryKey(String DZBM);
    
	/**
	 * 根据地址编码查询街路巷（小区）
	 * 
	 * @param DZBM
	 *            地址编码
	 * @return Sqjcwh 街路巷（小区）信息
	 */
    Jlx selectByPrimaryKeyL(String DZBM);
    /**
	 * 根据地址编码查询街路巷详细信息
	 * @param DZBM
	 * @return
	 */
	public Map selectDetailByPrimaryKey(String DZBM);
	public Map<String,Object> selectZdyjxzqyByDzbm(String DZBM);
	/**
	 * 更新街路巷（小区）信息（更新部分字段）
	 * 
	 * @param record
	 *            街路巷（小区）信息
	 */
    int updateByPrimaryKeySelective(Jlx record);
    
	/**
	 * 查询是否有该街路巷（小区）
	 * @param DZBM 地址编码
	 */
	long queryCount(String dzbm,String jlxxqdm);
	/**
	 * 撤销街路巷（小区）
	 * @param DZBM 地址编码
	 */
	void revokeJlx(Jlx jlx);
	/**
	 * 启用街路巷（小区）
	 * @param DZBM 地址编码
	 */
	void activateJlx(Jlx jlx);
	
	//------------------
	
	public List<Map<String,Object>> selectJlxBMMCList(Map<String,String> params);
	
	public Long selectJlxBMMCCount(Map<String,String> params);
	/**
	 * 根据最低一级行政区划获取街路巷的信息（二级联动用）
	 * @param sszdyjxzqydzbm
	 * @return
	 */
	public List<Jlx> loadAjaxData(String sszdyjxzqy_dzbm);
//	/**
//	 * 获取所有的街路巷信息
//	 * @return
//	 */
//	List<Jlx> loadAllAjaxData();
	
	public String selectjlxmcBydzbm(String dzbm);
	/**
	 * 根据街路巷小区地址编码查询街路巷小区所属最低一级行政区域的代码
	 * @param dzbm 街路巷小区编码
	 * @return
	 */
	public String selectQhdmBydzbm(String dzbm);
	
	/**
	 * 根据所属最低一级行政区域的代码查询行政区划名称
	 * @param sszdyjxzqyDzbm 所属最低一级行政区域地址编码
	 * @return
	 */
	public String selectXzqhmcBySszdyjxzqyDzbm(String sszdyjxzqyDzbm);
	/**
	 * 街路巷查询服务 查询接口
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> jlxListForWebService(Map<String, String> params);
	
	/**
	 * 街路巷查询服务 查询总条数
	 * @param params
	 * @return
	 */
	public Long countForWebService(Map<String,String> params);
}