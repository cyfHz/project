package com.kingmon.project.webservice.xzjd;

import javax.jws.WebService;

@WebService
public interface XzjdWebService {
	/**
	 * 乡镇街道查询
	 * @param token
	 * @param xzjdQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public String xzjdSearch(String token,XzjdQuery xzjdQuery,int pageIndex,int pageSize);
}
