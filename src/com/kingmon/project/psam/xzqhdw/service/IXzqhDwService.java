package com.kingmon.project.psam.xzqhdw.service;

public interface IXzqhDwService {
	/**
	 * 根据组织机构ID查询 行政区划bianma 
	 * @param dwid
	 * @return
	 */
	String selectXzqhdmByDwid(String dwid);
}
