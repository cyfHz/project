package com.kingmon.project.webservice.lcxx;

import javax.jws.WebService;

@WebService
public interface LcxxWebService {
	/**
	 * 楼层信息
	 * @param token
	 * @param lcxxQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public String getLcxx(String token,LcxxQuery lcxxQuery,int pageIndex,int pageSize);
}
