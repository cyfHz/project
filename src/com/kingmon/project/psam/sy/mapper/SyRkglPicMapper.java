package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyRkglPic;
@KMapper
public interface SyRkglPicMapper {


    int insertSelective(SyRkglPic record);

    SyRkglPic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SyRkglPic record);

    /**
     * 根据证件号码查询照片信息
     * @param gmsfhm
     * @return
     */
	public SyRkglPic selectPicInfoByZjhm(String gmsfhm);
	
	public SyRkglPic selectNotNullPicInfoByZjhm(String gmsfhm);
	
    /**
     * 根据证件号码加载照片信息
     * @param zjhm
     * @return
     */
	@SuppressWarnings("rawtypes")
	List<Map> selectRkglPicByzjhm(String zjhm);

}