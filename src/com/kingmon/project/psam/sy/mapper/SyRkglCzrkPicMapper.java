package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyRkglCzrkPic;
@KMapper
public interface SyRkglCzrkPicMapper {


    int insertSelective(SyRkglCzrkPic record);

    SyRkglCzrkPic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SyRkglCzrkPic record);

    /**
     * 根据证件号码查询照片信息
     * @param gmsfhm
     * @return
     */
	public SyRkglCzrkPic selectPicInfoByZjhm(String gmsfhm);
	
	public SyRkglCzrkPic selectNotNullPicInfoByZjhm(String gmsfhm);
	
    /**
     * 根据证件号码加载照片信息
     * @param zjhm
     * @return
     */
	@SuppressWarnings("rawtypes")
	List<Map> selectRkglPicByzjhm(String zjhm);

}