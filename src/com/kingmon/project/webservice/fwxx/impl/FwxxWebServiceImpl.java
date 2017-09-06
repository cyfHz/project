package com.kingmon.project.webservice.fwxx.impl;

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
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.sy.mapper.SyFwjbxxMapper;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.fwxx.FwxxQuery;
import com.kingmon.project.webservice.fwxx.FwxxWebService;

@WebService(endpointInterface = "com.kingmon.project.webservice.fwxx.FwxxWebService")
public class FwxxWebServiceImpl implements FwxxWebService {

	@Inject
	private SyFwjbxxMapper syFwjbxxMapper;
	@Inject
	private JzwjgMapper jzwjgMapper;
	@Inject
	private JzwjbxxMapper jzwjbxxMapper;
	@Inject
	private JwqMapper jwqMapper;
	@Inject
	private OrganizationMapper organizationMapper;
	@Resource
	private WebServiceContext wsContext;
	
	@Override
	public String fwxxSearch(String token,FwxxQuery fwxxQuery, int pageIndex, int pageSize) {
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
		if (fwxxQuery != null) {
			if (!StringUtils.isEmpty(fwxxQuery.getJlx())) {
				params.put("SSJLXXQ_DZBM", fwxxQuery.getJlx());
			}
			if (!StringUtils.isEmpty(fwxxQuery.getXzqh())) {
				params.put("SSXQDM", fwxxQuery.getXzqh());
			}
			if (!StringUtils.isEmpty(fwxxQuery.getJzwid())) {
				String jzwjgId = jzwjgMapper.selectJzwjgidByJzwid(fwxxQuery.getJzwid());
				if (!StringUtils.isEmpty(jzwjgId)) {
					params.put("JZWJGID", jzwjgMapper.selectJzwjgidByJzwid(fwxxQuery.getJzwid()));
				}else{
					params.put("JZWJGID", "不存在的值");
				}
			}
			if (!StringUtils.isEmpty(fwxxQuery.getJzwFjid())) {
				params.put("JZWFJID", fwxxQuery.getJzwFjid());
			}
			if (!StringUtils.isEmpty(fwxxQuery.getJzwdyid())) {
				params.put("JZWDYID", fwxxQuery.getJzwdyid());
			}
			if (!StringUtils.isEmpty(fwxxQuery.getKeyword())) {
				params.put("FWDZ", "%"+fwxxQuery.getKeyword()+"%");
			}
			if (!StringUtils.isEmpty(fwxxQuery.getFjxh())) {
				params.put("FJXH", "%"+fwxxQuery.getFjxh()+"%");
			}
		}
		PaginationUtil.initPageAndSort(params);
		List<Map<String, Object>> list = syFwjbxxMapper.syFwxxListForWebService(params);
		Map<Object, Object> sspcsMap = Maps.newHashMap();
		Map<Object, Object> jwqMap = Maps.newHashMap();
		Map<Object, Jzwjbxx> jzwjbxxMap = Maps.newHashMap();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String sspcs = (String) map.get("sspcs");//派出所编码
			if (!StringUtils.isEmpty(sspcs)) {
				if (sspcsMap.get(sspcs)!=null) {
					map.put("organ_name", sspcsMap.get(sspcs)+"");//派出所名称
				}else{
					Organization sspcs_org = organizationMapper.selectOrgById(sspcs);
					if (sspcs_org!=null) {
						map.put("organ_name", sspcs_org.getOrgna_name());//派出所名称
						sspcsMap.put(sspcs, sspcs_org.getOrgna_name());
					}
				}
			}else{
//				map.put("organ_name", "");
			}
			String sjgsdwdm = (String) map.get("sjgsdwdm");//警务区编码
			if (!StringUtils.isEmpty(sjgsdwdm)) {
				if (jwqMap.get(sjgsdwdm)!=null) {
					map.put("jwqmc", jwqMap.get(sjgsdwdm)+"");//警务区名称
				}else{
					Jwq jwq = jwqMapper.selectByPrimaryKey(sjgsdwdm);
					if (jwq!=null) {
						map.put("jwqmc", jwq.getJwqmc());//警务区名称
						jwqMap.put(sjgsdwdm, jwq.getJwqmc());
					}					
				}
			}else{
//				map.put("jwqmc", "");
			}
			String jzwjgid = (String) map.get("jzwid");//建筑物结构Id
			if (!StringUtils.isEmpty(jzwjgid)) {
				if (jzwjbxxMap.get(jzwjgid)!=null) {
					Jzwjbxx jzwjbxx = jzwjbxxMap.get(jzwjgid);
					map.put("jzwid", jzwjbxx.getDzbm());//建筑物Id
					map.put("zxdhzb", jzwjbxx.getZxdhzb());//横坐标x  
					map.put("zxdzzb", jzwjbxx.getZxdzzb());//纵坐标y
				}else{
					String jzwid = jzwjgMapper.selectJzwidByJzwjgid(jzwjgid);
					map.put("jzwid", jzwid);//建筑物Id
					Jzwjbxx jzwjbxx = jzwjbxxMapper.selectByPrimaryKey(jzwid);
					if (jzwjbxx!=null) {
						map.put("zxdhzb", jzwjbxx.getZxdhzb());//横坐标x  
						map.put("zxdzzb", jzwjbxx.getZxdzzb());//纵坐标y
						jzwjbxxMap.put(jzwjgid, jzwjbxx);
					}
				}
			}else{
//				map.put("jzwid", "");
//				map.put("zxdhzb", "");//横坐标x  
//				map.put("zxdzzb", "");//纵坐标y
			}
		}
		resp.put("result", "0");
//		resp.put("msg", "");
		resp.put("data", list);
		resp.put("sumNum", syFwjbxxMapper.countForWebService(params));
		return JSON.toJSONString(resp,KConstants.serializerFeatures);
	}

}
