package com.kingmon.project.psam.jzw.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.JzwfjPic;
@KMapper
public interface JzwfjPicMapper {
	
    int deleteByPrimaryKey(String picid);

    int insertSelective(JzwfjPic record);

    JzwfjPic selectByPrimaryKey(String picid);

    int updateByPrimaryKeySelective(JzwfjPic record);

	List<String> selectFjPicIdsByfjId(String jzwfjid);
	
	List<Map> selectFjPicByPicId(String picid);
}