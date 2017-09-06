package com.kingmon.project.psam.shpz.mapper;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.shpz.model.Shpz;
@KMapper
public interface ShpzMapper {
	
    int deleteByPrimaryKey(String pzid);

    int insertSelective(Shpz record);

    Shpz selectByPrimaryKey(String pzid);
    
    Shpz selectShpz(@Param("pzlx")String pzlx,@Param("orgCode")String orgCode);
    
   // Shpz selectJlxShpzByOrgCode(@Param("orgCode")String orgCode);
    
    int updateByPrimaryKeySelective(Shpz record);

}