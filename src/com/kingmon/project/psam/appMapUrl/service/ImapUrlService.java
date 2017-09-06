package com.kingmon.project.psam.appMapUrl.service;

import java.util.List;

import com.kingmon.project.psam.appMapUrl.model.mapurl;

public interface ImapUrlService {

	List<mapurl> findMapUrlByAreaCode(String areaCode);
    /**
     * 根据
     * @param areaCode
     * @return
     */
}
