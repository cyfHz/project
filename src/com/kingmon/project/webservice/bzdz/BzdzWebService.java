package com.kingmon.project.webservice.bzdz;

import javax.jws.WebService;

@WebService
public interface BzdzWebService {
	public String jybzdzSearch(String token,BzdzQuery bzdzQuery,int pageindex,int pageSize);

	public String bzdzSearchByPk(String token,String dzbm);
}


