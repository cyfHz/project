package com.kingmon.project.psam.jzw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.Jzwdy;
@KMapper
public interface JzwdyMapper {

	int insertSelective(Jzwdy record);

    Jzwdy selectByPrimaryKey(String JZWDYID);

    int updateByPrimaryKeySelective(Jzwdy record);

	public List<Jzwdy> selectSortedJzwdyByJzwJgid(String jzwjgId);

	public Long selectJzwDyCountByJzwjgId(String jzwjgId);
	
	/**
	 * 单元信息查询服务 查询接口
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> jzwdyListForWebService(Map<String, String> params);
	/**
	 * 单元信息查询服务 查询总条数
	 * @param params
	 * @return
	 */
	public Long countForWebService(Map<String,String> params);
	
	public void updateDyMcForDyId(Jzwdy jzwdy);
}