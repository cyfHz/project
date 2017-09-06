package com.kingmon.project.psam.jzw.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.JzwjgPic;
@KMapper
public interface JzwjgPicMapper {
    int deleteByPrimaryKey(String PICID);

    int insert(JzwjgPic record);

    int insertSelective(JzwjgPic record);

    JzwjgPic selectByPrimaryKey(String PICID);

    int updateByPrimaryKeySelective(JzwjgPic record);

    int updateByPrimaryKeyWithBLOBs(JzwjgPic record);

    int updateByPrimaryKey(JzwjgPic record);

	List<String> selectJgpicIdsByjgId(String jzwjgid);



	List<Map> selectJgpicByPicId(String jzwjgPicId);
}