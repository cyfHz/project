package com.kingmon.project.webservice.fwxx;

import javax.jws.WebService;

@WebService
public interface FwxxWebService {
	/**
	 * 房屋信息查询
	 * @param token
	 * @param fwxxQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public String fwxxSearch(String token,FwxxQuery fwxxQuery,int pageIndex,int pageSize);
}
