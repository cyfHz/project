package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
@KMapper
public interface SyFwjbxxMapper {


    int insertSelective(SyFwjbxx record);

    SyFwjbxx selectByPrimaryKey(String fwid);
	
	int updateByPrimaryKey(SyFwjbxx record);
	
	public List<Map<String,Object>> selectSYFwjbxxList(Map<String,String> params);
	
	public Long selectSYFwjbxxCount(Map<String,String> params);
	
	public SyFwjbxx selectYFwjbxxByFjbm(String fjbm);
//	/**
//	 * 修改房屋信息
//	 * @param params
//	 */
//	void updateFw(Map<String, Object> params);
	/**
	 * 房屋查询服务
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> syFwxxListForWebService(Map<String, String> params);
	/**
	 * 房屋查询服务 查询总条数
	 * @param params
	 * @return
	 */
	public Long countForWebService(Map<String,String> params);
	
	
	//-------------------------------------------------------------------
	  SyFwjbxx selectByJwzfjId(String jwzfjId);
	  
	  Long selectCountByPrimaryKey(String fwid);
	
}