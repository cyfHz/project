package com.kingmon.project.webservice.xzjd.impl;

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
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.psam.xzjd.mapper.XzjdMapper;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.xzjd.XzjdQuery;
import com.kingmon.project.webservice.xzjd.XzjdWebService;

@WebService(endpointInterface = "com.kingmon.project.webservice.xzjd.XzjdWebService")
public class XzjdWebServiceImpl implements XzjdWebService {

	@Inject
	private XzjdMapper xzjdMapper;
	@Resource
	private WebServiceContext wsContext;

	@Override
	public String xzjdSearch(String token,XzjdQuery xzjdQuery, int pageIndex, int pageSize) {
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
		if (xzjdQuery != null) {
			if (!StringUtils.isEmpty(xzjdQuery.getDzbm())) {
				params.put("DZBM", xzjdQuery.getDzbm());
			}
			if (!StringUtils.isEmpty(xzjdQuery.getXzjddm())) {
				params.put("XZJDDM", xzjdQuery.getXzjddm());
			}
			if (!StringUtils.isEmpty(xzjdQuery.getXzjdmc())) {
				params.put("XZJDMC", "%" + xzjdQuery.getXzjdmc() + "%");
			}
		}
		PaginationUtil.initPageAndSort(params);
		List<Map<String, Object>> list = xzjdMapper.xzjdListForWebService(params);

		resp.put("result", "0");
//		resp.put("msg", "");
		resp.put("data", list);
		resp.put("sumNum", xzjdMapper.countForWebService(params));
		return JSON.toJSONString(resp,KConstants.serializerFeatures);
	}

}
