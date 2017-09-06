package com.kingmon.project.webservice.sqjcwh;

import javax.jws.WebService;

@WebService
public interface SqjcwhWebService {
	/**
	 * 社区居村委会查询服务
	 * @param token
	 * @param sqjcwhQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public String sqjcwhSearch(String token,SqjcwhQuery sqjcwhQuery,int pageIndex,int pageSize);
}
