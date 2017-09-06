package com.kingmon.project.psam.xzqhdw.mapper;

import com.kingmon.base.common.KMapper;


@KMapper
public interface XzqhDwMapper {
    /**
     * 根据民警所属的组织机构代码查询行政区划代码
     * @param dwid 民警所属的组织机构代码
     * @return
     */
    String selectXzqhdmByDwid(String dwid);
}