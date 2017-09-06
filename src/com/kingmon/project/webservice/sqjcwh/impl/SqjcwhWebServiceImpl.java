package com.kingmon.project.webservice.sqjcwh.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.project.psam.sqjcwh.mapper.SqjcwhMapper;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.sqjcwh.SqjcwhQuery;
import com.kingmon.project.webservice.sqjcwh.SqjcwhWebService;

@WebService(endpointInterface="com.kingmon.project.webservice.sqjcwh.SqjcwhWebService")
public class SqjcwhWebServiceImpl implements SqjcwhWebService{

	@Inject
	private SqjcwhMapper sqjcwhMapper;
	@Resource
	private WebServiceContext wsContext;
	
	@Override
	public String sqjcwhSearch(String token,SqjcwhQuery sqjcwhQuery, int pageIndex, int pageSize) {
		Map<String, Object> resp = new HashMap<String, Object>();
		resp=ServiceAuthUtil.validateAuthToken(token);
		if(!"0".equals(resp.get("result"))){
			resp.put("data", new ArrayList<Object>());
			resp.put("sumNum", "0");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
		}

		Map<String, String> params = Maps.newHashMap();
		params.put("page", pageIndex + "");
		params.put("rows", pageSize + "");
		if (sqjcwhQuery != null) {
			if (!StringUtils.isEmpty(sqjcwhQuery.getDzbm())) {
				params.put("DZBM", sqjcwhQuery.getDzbm());
			}
			if (!StringUtils.isEmpty(sqjcwhQuery.getSqjcwhdm())) {
				params.put("SQJCWHDM", sqjcwhQuery.getSqjcwhdm());
			}
			if (!StringUtils.isEmpty(sqjcwhQuery.getSqjcwhmc())) {
				params.put("SQJCWHMC", "%" + sqjcwhQuery.getSqjcwhmc() + "%");
			}
		
		}
		PaginationUtil.initPageAndSort(params);
		List<Map<String, Object>> list = sqjcwhMapper.sqjcwhListForWebService(params);
	
		resp.put("result", "0");
//		resp.put("msg", "");
		resp.put("data", list);
		resp.put("sumNum", sqjcwhMapper.countForWebService(params));

		return JSON.toJSONString(resp,KConstants.serializerFeatures);
	}

}
