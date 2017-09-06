package com.kingmon.project.psam.appMapUrl.mapper;

import java.util.List;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.appMapUrl.model.mapurl;
@KMapper
public interface mapurlMapper {
    int deleteByPrimaryKey(String mapUrlId);

    int insert(mapurl record);

    int insertSelective(mapurl record);

    mapurl selectByPrimaryKey(String mapUrlId);

    int updateByPrimaryKeySelective(mapurl record);

    int updateByPrimaryKey(mapurl record);

	List<mapurl> selectMapUrlByCode(String areaCode);
}