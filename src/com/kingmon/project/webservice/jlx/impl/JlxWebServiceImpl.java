package com.kingmon.project.webservice.jlx.impl;

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
import com.kingmon.project.psam.jlx.mapper.JlxMapper;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.jlx.JlxQuery;
import com.kingmon.project.webservice.jlx.JlxWebService;

@WebService(endpointInterface = "com.kingmon.project.webservice.jlx.JlxWebService")
public class JlxWebServiceImpl implements JlxWebService {

	@Inject
	private JlxMapper jlxMapper;
	@Resource
	private WebServiceContext wsContext;
	
	@Override
	public String jlxSearch(String token,JlxQuery jlxQuery, int pageIndex, int pageSize) {
		Map<String, Object> resp = new HashMap<String, Object>();
		resp=ServiceAuthUtil.validateAuthToken(token);
		if(!"0".equals(resp.get("result"))){
			resp.put("data", new ArrayList<Object>());
			resp.put("sumNum", "0");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
		}
//		AuthToken authToken = ServiceAuthUtil.getAuthToken(token);
//		if (authToken == null) {
//			resp.put("result", "1");
//			resp.put("msg", "未登录");
//			resp.put("data", new ArrayList<Object>());
//			resp.put("sumNum", "0");
//			return JSON.toJSONString(resp,KConstants.serializerFeatures);
//		}

		Map<String, String> params = Maps.newHashMap();
		params.put("page", pageIndex + "");
		params.put("rows", pageSize + "");
		if (jlxQuery != null) {
			if (!StringUtils.isEmpty(jlxQuery.getDzbm())) {
				params.put("DZBM", jlxQuery.getDzbm());
			}
			if (!StringUtils.isEmpty(jlxQuery.getJlxxqdm())) {
				params.put("JLXXQDM", jlxQuery.getJlxxqdm());
			}
			if (!StringUtils.isEmpty(jlxQuery.getJlxmc())) {
				params.put("JLXXQMC", "%" + jlxQuery.getJlxmc() + "%");
			}
			if (!StringUtils.isEmpty(jlxQuery.getJlxmc())) {
				params.put("XZQHMC", "%" + jlxQuery.getXzqhmc() + "%");
			}
			if (!StringUtils.isEmpty(jlxQuery.getXzqhbm())) {
				params.put("SSZDYJXZQY_DZBM",jlxQuery.getXzqhbm());
			}
		}
		PaginationUtil.initPageAndSort(params);
		
		List<Map<String, Object>> list = jlxMapper.jlxListForWebService(params);
//		Map<Object, Object> xzqhmcMap = Maps.newHashMap();
//		for (int i = 0; i < list.size(); i++) {
//			Map<String, Object> map = list.get(i);
//			String sszdyjxzqy_dzbm = (String) map.get("sszdyjxzqy_dzbm");
//			String xzqhmc = "";
//			if (xzqhmcMap.get(sszdyjxzqy_dzbm)!=null) {
//				map.put("xzqhmc", (String)xzqhmcMap.get(sszdyjxzqy_dzbm));
//			}else{
//				if (!StringUtils.isEmpty(sszdyjxzqy_dzbm)) {
//					xzqhmc = jlxMapper.selectXzqhmcBySszdyjxzqyDzbm(sszdyjxzqy_dzbm);
//					if (xzqhmc!=null&&!("".equals(xzqhmc))) {
//						map.put("xzqhmc", xzqhmc);
//						xzqhmcMap.put(sszdyjxzqy_dzbm, xzqhmc);
//					}
//				}
//			}
//		}
//		if (!StringUtils.isEmpty(jlxQuery.getXzqhmc())) {
//			for (int i = 0; i < list.size(); i++) {
//				Map<String, Object> map = list.get(i);
//				String xzqhmc = (String) map.get("xzqhmc");
//				if (!(xzqhmc.contains(jlxQuery.getXzqhmc()))) {
//					list.remove(i);
//				}
//			}
//		}
		
		
		resp.put("result", "0");
//		resp.put("msg", "");
		resp.put("data", list);
		resp.put("sumNum", jlxMapper.countForWebService(params));

		return JSON.toJSONString(resp,KConstants.serializerFeatures);
	}

}
