package com.kingmon.project.webservice.jlx;

import javax.jws.WebService;

@WebService
public interface JlxWebService {
	/**
	 * 街路巷查询
	 * @param token
	 * @param jlxQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public String jlxSearch(String token,JlxQuery jlxQuery,int pageIndex,int pageSize);
}
