package com.kingmon.project.webservice.dyxx;

import javax.jws.WebService;

@WebService
public interface DyxxWebService {
	/**
	 * 单元信息查询
	 * @param token
	 * @param dyxxQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public String getDyxx(String token,DyxxQuery dyxxQuery,int pageIndex,int pageSize);
}
