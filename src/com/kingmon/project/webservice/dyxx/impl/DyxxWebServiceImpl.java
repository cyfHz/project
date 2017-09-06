package com.kingmon.project.webservice.dyxx.impl;

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
import com.kingmon.project.psam.jzw.mapper.JzwdyMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.dyxx.DyxxQuery;
import com.kingmon.project.webservice.dyxx.DyxxWebService;

@WebService(endpointInterface="com.kingmon.project.webservice.dyxx.DyxxWebService")
public class DyxxWebServiceImpl implements DyxxWebService{

	@Inject
	private JzwdyMapper jzwdyMapper;

	@Inject
	private JzwjgMapper jzwjgMapper;
	@Resource
	private WebServiceContext wsContext;
	
	@Override
	public String getDyxx(String token,DyxxQuery dyxxQuery, int pageIndex, int pageSize) {
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
		
		if (dyxxQuery != null) {
			if (!StringUtils.isEmpty(dyxxQuery.getJzwdyid())) {
				params.put("JZWDYID", dyxxQuery.getJzwdyid());
			}
			if (!StringUtils.isEmpty(dyxxQuery.getJzwid())) {
				String jzwjgId = jzwjgMapper.selectJzwjgidByJzwid(dyxxQuery.getJzwid());
				if (!StringUtils.isEmpty(jzwjgId)) {
					params.put("JZWJGID", jzwjgMapper.selectJzwjgidByJzwid(dyxxQuery.getJzwid()));
				}else{
					params.put("JZWJGID", "不存在的值");
				}
			}
		}
		PaginationUtil.initPageAndSort(params);
		
		List<Map<String, Object>> list = jzwdyMapper.jzwdyListForWebService(params);
		Map<Object, Object> jzwidMap = Maps.newHashMap();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String jzwjgid  = (String) map.get("jzwid");
			String jzwid = null;
			if (!StringUtils.isEmpty(jzwjgid)) {
				if (jzwidMap.get(jzwjgid)!=null) {
					jzwid = (String) jzwidMap.get(jzwjgid);
				}else{
					jzwid = jzwjgMapper.selectJzwidByJzwjgid(jzwjgid);
					jzwidMap.put(jzwjgid, jzwid);
				}
			}
			
			map.put("jzwid",jzwid);
		}
	
		resp.put("result", "0");
//		resp.put("msg", "");
		resp.put("data", list);
		resp.put("sumNum",  jzwdyMapper.countForWebService(params));

		return JSON.toJSONString(resp,KConstants.serializerFeatures);
	}

}
