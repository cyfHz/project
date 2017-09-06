package com.kingmon.project.webservice.xzqh;

import javax.jws.WebService;

@WebService
public interface XzqhWebService {
	/**
	 * 行政区划查询
	 * @param token
	 * @param xzqhQuery
	 * @param pageindex 
	 * @param pageSize
	 * @return
	 */
	public String xzqhSearch(String token,XzqhQuery xzqhQuery,int pageindex,int pageSize);
}


