package com.kingmon.project.psam.jzw.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.Jzwjg;

@KMapper
public interface JzwjgMapper {
//
    int insertSelective(Jzwjg record);
//
    Jzwjg selectByPrimaryKey(String jzwjgid);
    
    Jzwjg selectByJzwId(String jzwid);
    
    int updateByPrimaryKeySelective(Jzwjg record);
    
    int resetJzwjg(Jzwjg record);
	
	public List<Map<String,Object>> selectJzwjgDyLcInfo(Map<String,String> params);  
	
	public Long selectJzwjgDyLcCount(Map<String,String> params);  
	
	/**
	 * 查询所属建筑物id
	 * @param jzwjgid
	 * @return
	 */
	public String selectJzwidByJzwjgid(String jzwjgid);
	
	/**
	 * 
	 * @param jzwid
	 * @return
	 */
	public String selectJzwjgidByJzwid(String jzwid);
}