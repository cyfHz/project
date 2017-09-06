package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyjwryPic;
@KMapper
public interface SyjwryPicMapper {


    int insertSelective(SyjwryPic record);

    SyjwryPic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SyjwryPic record);

    /**
     * 根据证件号码查询照片信息
     * @param gmsfhm
     * @return
     */
	public SyjwryPic selectPicInfoByZjhm(String gmsfhm);
	
	public SyjwryPic selectNotNullPicInfoByZjhm(String gmsfhm);
	
    /**
     * 根据证件号码加载照片信息
     * @param zjhm
     * @return
     */
	@SuppressWarnings("rawtypes")
	List<Map> selectRkglPicByzjhm(String zjhm);

}