package com.kingmon.project.psam.sqjcwh.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sqjcwh.model.Sqjcwh;
@KMapper
public interface SqjcwhMapper {
	/**
	 * 添加社区居村委会(部分字段为空)
	 * 
	 * @param record
	 *            社区居村委会信息
	 */
    int insertSelective(Sqjcwh record);
	/**
	 * 根据地址编码查询社区居村委会
	 * 
	 * @param DZBM
	 *            地址编码
	 * @return Sqjcwh 社区居村委会信息
	 */
    Sqjcwh selectByPrimaryKeyL(String DZBM);
	/**
	 * 更新社区居村委会信息（更新部分字段）
	 * 
	 * @param record
	 *            社区居村委会信息
	 */
    int updateByPrimaryKeySelective(Sqjcwh record);
	/**
	 * 撤销社区居村委会
	 * @param DZBM 地址编码
	 */
	void revokeSqjcwh(Sqjcwh sqjcwh);
	/**
	 * 启用社区居村委会
	 * @param DZBM 地址编码
	 */
	void activateSqjcwh(Sqjcwh sqjcwh);

	/**
	 * 
	 * @param dzbm
	 * @param sqjcwhdm
	 * @return
	 */
	long queryCount(@Param("dzbm")String dzbm,@Param("sqjcwhdm")String sqjcwhdm);
	/**
	 * 根据地址编码查询社区居村委会详细信息
	 * @param DZBM
	 * @return
	 */
	public Map selectDetailByPrimaryKey(String DZBM);
	
	
	/**
	 * selectSqjcwhdmByDzbm
	 * @param DZBM dzbm
	 * @return
	 */
	public String selectSqjcwhdmByDzbm(String dzbm);
	
	
	/**
	 * 
	 * @param DZBM dzbm
	 * @return
	 */
	public String selectSjxzqyDzbmByKey(String dzbm);
	
	/**
	 * 社区居村委会查询服务 查询接口
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> sqjcwhListForWebService(Map<String, String> params);
	/**
	 * 社区居村委会查询服务 查询总条数
	 * @param params
	 * @return
	 */
	public Long countForWebService(Map<String,String> params);
	
	//select t.xzjddm  from DZ_XZJD t where t.sjxzqy_dzbm=:sjxzqy_dzbm
	public List<String> selectSqjcwhdmListByXzjdDzbm(String sjxzqy_dzbm);
		
	
}