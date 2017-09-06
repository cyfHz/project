package com.kingmon.project.webservice.xzqh.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.common.model.ServiceBzdzRejectIp;
import com.kingmon.project.webservice.common.service.BzdzRejectIpService;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.xzqh.XzqhQuery;
import com.kingmon.project.webservice.xzqh.XzqhWebService;

@WebService(endpointInterface = "com.kingmon.project.webservice.xzqh.XzqhWebService")
public class XzqhWebServiceImpl implements XzqhWebService{
	@Inject
	private XzqhMapper xzqhMapper;
	@Resource
	private WebServiceContext wsContext;

	@Override
	public String xzqhSearch(String token,XzqhQuery xzqhQuery, int pageindex, int pageSize) {
		Map<String,Object> resp = new HashMap<String, Object>();
		resp=ServiceAuthUtil.validateAuthToken(token);
		
		if(!"0".equals(resp.get("result"))){
			resp.put("data", new ArrayList<Object>());
			resp.put("sumNum", "0");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
		}
//		AuthToken authToken = ServiceAuthUtil.getAuthToken(token);
		
		Map<String,String> params =new HashMap<String, String>();
		params.put("page", pageindex+"");
		params.put("rows", pageSize+"");
		if(xzqhQuery!=null){
			if(!StringUtils.isEmpty(xzqhQuery.getDzbm())){
				params.put("DZBM", xzqhQuery.getDzbm());
			}
			if(!StringUtils.isEmpty(xzqhQuery.getXzqhmc())){
				params.put("XZQHMC", "%"+xzqhQuery.getXzqhmc()+"%");
			}
			if(!StringUtils.isEmpty(xzqhQuery.getXzqhdm())){
				params.put("XZQHDM", xzqhQuery.getXzqhdm());
			}
			if(!StringUtils.isEmpty(xzqhQuery.getSjxzqy_dzbm())){
				params.put("SJXZQY_DZBM", xzqhQuery.getSjxzqy_dzbm());
			}
		}
		PaginationUtil.initPageAndSort(params);
		resp.put("result", "0");
//		resp.put("msg", "");
		resp.put("data", xzqhMapper.xzqhListForWebService(params));
		resp.put("sumNum", xzqhMapper.countForWebService(params));
		return JSON.toJSONString(resp,KConstants.serializerFeatures);
	}

}
