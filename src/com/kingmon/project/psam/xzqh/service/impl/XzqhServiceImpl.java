package com.kingmon.project.psam.xzqh.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.poi.ss.formula.functions.Rows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.PinyinUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.event.XzqhUpdateEvent;
import com.kingmon.project.psam.util.QydmData;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.psam.xzqh.model.Xzqh;
import com.kingmon.project.psam.xzqh.service.XzqhService;

@Service
public class XzqhServiceImpl extends BaseService implements XzqhService, ApplicationEventPublisherAware {
	@Inject
	private XzqhMapper xzqhMapper;
	
	
	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		  this.publisher = publisher;
	}
	
	
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet xzqhList(Map<String,String> params){
		PaginationUtil.initPageAndSort(params);
		String xzqhmc = params.get("XZQHMC");
		if(xzqhmc!=null&&!xzqhmc.isEmpty()){
			params.put("XZQHMC", "%"+xzqhmc+"%");
		}
		
		//添加 行政区划代码模糊查询20160714
		String xzqhdm = params.get("XZQHDM");
		if(xzqhdm!=null&&!xzqhdm.isEmpty()){
			params.put("XZQHDM", "%"+xzqhdm+"%");
		}
		
		//String xzqh=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
		String xzqh=SecurityUtils.getUserLevelStr();//-zht-20160219
		if(xzqh.length()>6){xzqh = xzqh.substring(0,6);}
		params.put("xzqhdm_data_auth", ""+xzqh+"%");
		if("".equals(params.get("sort"))||params.get("sort")==null){
			params.put("sort", "XZQHDM");//默认按主键排序，避免数据重复bug
		}
		String SJXZQY_DZBM=params.get("SJXZQY_DZBM");
		if(SJXZQY_DZBM!=null && !"".equals(SJXZQY_DZBM)){
			List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(SJXZQY_DZBM);
			String qydm=QydmData.getQydm(map);
			qydm+="%";
			params.put("qydm", qydm);
			params.remove("SJXZQY_DZBM");
		}
		
		//String xzqhCode=SubApStrUtil.coverChars(xzqhCode, '0', 6);
		return new DataSet(xzqhMapper.xzqhListCount(params),xzqhMapper.xzqhList(params));
	}
	@Cacheable(value="defaultQueryResultCache",key="'XzqhServiceImpl_getChild'+#id+#showXzjd+#showSqjcwh+#type")
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet getChild(String id, boolean showXzjd, boolean showSqjcwh,String type) {
		if(id==null||id.isEmpty()||id.startsWith(KConstants.cacheable_prefix)){//根节点...//null被缓
			//String xzqh=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
			String xzqh=SecurityUtils.getUserLevelStr();//-zht-20160219
			String xzqhCode=xzqh;
			//if(xzqhCode.length()<6){
				xzqhCode=SubApStrUtil.coverChars(xzqhCode, '0', 6);
		//	}
			id=xzqhMapper.selectDzbmByDm(xzqhCode);
			List<Map<String, Object>> list=Lists.newArrayList();
			list=xzqhMapper.selectXzqhByXzqhdm(xzqhCode);
			if(showXzjd==false){
				if(list.size()==1){
					long a=xzqhMapper.getChildCount(""+list.get(0).get("NODEID"));
					if(a<=0){
						list=xzqhMapper.selectXzqhBydzbm(id);
					}
				}
			}
			if(showXzjd){
				long countXzqh=xzqhMapper.getChildCount(id);
				String sql="select count(1) from dz_xzjd j where j.sjxzqy_dzbm=:id";
				long a=jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC().addSQLParam("id", id));
				if(countXzqh<1&&a<1){
					sql="SELECT DZBM as NODEID,XZQHMC as NODETEXT,'XZJD' as NODETYPE FROM DZ_XZQH where dzbm =:id";
					list = (List<Map<String, Object>>) jdbcBaseDao.jdbcQueryMapList(sql,ParamObject.new_NP_NC().addSQLParam("id", id));
				}
			}
			if(showSqjcwh){
				long countXzqh=xzqhMapper.getChildCount(id);
				String sqlXz="select count(1) from dz_xzjd j where j.sjxzqy_dzbm=:id";
				long countXzjd=jdbcBaseDao.jdbcQueryCount(sqlXz, ParamObject.new_NP_NC().addSQLParam("id", id));
				String sqlSqj="select count(1) from dz_sqjcwh j where j.sjxzqy_dzbm=:id";
				long a=jdbcBaseDao.jdbcQueryCount(sqlSqj, ParamObject.new_NP_NC().addSQLParam("id", id));
				if(countXzqh<1&&countXzjd<1&&a<1){
					String sql="SELECT DZBM as NODEID,XZQHMC as NODETEXT,'SQJCWH' as NODETYPE FROM DZ_XZQH where dzbm =:id";
					list = (List<Map<String, Object>>) jdbcBaseDao.jdbcQueryMapList(sql,ParamObject.new_NP_NC().addSQLParam("id", id));
				}
			}
			return new DataSet(0l, list);
		}
		DataSet ds = new DataSet(0l, new ArrayList<Map<String, Object>>());
		if("XZQH".equals(type)){//展开行政区划节点
			ds = new DataSet(xzqhMapper.getChildCount(id),xzqhMapper.getChild(id));
			
			if(showXzjd==false){//如果不展开乡镇街道节点
				List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
				Map<String,Object> map =new HashMap<String,Object>();
				for(int i=0;i<ds.getTotal();i++){
					String dzbm =""+ds.getRows().get(i).get("NODEID");
					long a=xzqhMapper.getChildCount(dzbm);
					if(a<=0){
//						ds = new DataSet(xzqhMapper.getChildCount(id),xzqhMapper.getXzqhXzjd(id));
						map=xzqhMapper.getXzqhJd(dzbm);
					}else{
						map=xzqhMapper.getXzqh(dzbm);
					}
					list.add(map);
				}
				ds.setTotal((long)list.size());
				ds.setRows(list);
			}else if(showXzjd==true){//展开乡镇街道节点
				List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
				Map<String,Object> map =new HashMap<String,Object>();
				for(int j=0;j<ds.getTotal();j++){
					String dzbm =""+ds.getRows().get(j).get("NODEID");
					long a=xzqhMapper.getCount(dzbm);
					long a1=xzqhMapper.getXzjdCount(dzbm);//是否有乡镇街道
					long a2=xzqhMapper.getSqcjwhCount(dzbm);//
					if(a>0|a1>0){
						map=xzqhMapper.getXzqh(dzbm);
					}else{
						map=xzqhMapper.getXzqhJd(dzbm);
					}
					list.add(map);
				}
				ds.setTotal((long)list.size());
				ds.setRows(list);
				if(showSqjcwh){
					List<Map<String,Object>> list1=new ArrayList<Map<String, Object>>();
					Map<String,Object> map1 =new HashMap<String,Object>();
					for(int j=0;j<ds.getTotal();j++){
						String dzbm =""+ds.getRows().get(j).get("NODEID");
						long a=xzqhMapper.getCount(dzbm);
						long a1=xzqhMapper.getXzjdCount(dzbm);//是否有乡镇街道
						long a2=xzqhMapper.getSqcjwhCount(dzbm);//
						if(a>0|a1>0){
							map1=xzqhMapper.getXzqh(dzbm);
							if(a2>0){
								map1=xzqhMapper.getXzqh(dzbm);
							}
						}else{
							map1=xzqhMapper.getXzqhXzsqj(dzbm);
						}
						list1.add(map1);
					}
					ds.setTotal((long)list1.size());
					ds.setRows(list1);
				}
			}
			
			if(ds.getTotal()<1&&showXzjd){//展开行政区划最末节点
				ds = new DataSet(xzqhMapper.getXzjdCount(id),xzqhMapper.getXzjd(id));
				
				if(showSqjcwh){
					List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
					Map<String,Object> map =new HashMap<String,Object>();
					for(int j=0;j<ds.getTotal();j++){
						String dzbm =""+ds.getRows().get(j).get("NODEID");
						long a=xzqhMapper.getCount(dzbm);
						long a1=xzqhMapper.getXzjdCount(dzbm);//是否有乡镇街道
						long a2=xzqhMapper.getSqcjwhCount(dzbm);//
						if(a>0|a1>0|a2>0){
							map=xzqhMapper.getXzjdMap(dzbm);
						}else{
							map=xzqhMapper.getXzqhJdSqj(dzbm);
						}
						list.add(map);
					}
					ds.setTotal((long)list.size());
					ds.setRows(list);
				}
//					List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
//					Map<String,Object> map =new HashMap<String,Object>();
//					for(int j=0;j<ds.getTotal();j++){
//						String dzbm =""+ds.getRows().get(j).get("NODEID");
//						long a1=xzqhMapper.getXzjdCount(dzbm);//是否有乡镇街道
//						long a2=xzqhMapper.getSqcjwhCount(dzbm);//
//						if(a1>0|a2>0){
//							map=xzqhMapper.getXzjdMap(dzbm);
//							if(showSqjcwh==false){
//								map=xzqhMapper.getXzqhSqj(dzbm);
//							}
//						}else{
//							map=xzqhMapper.getXzqhSqj(dzbm);
//						}
//						list.add(map);
//					}
//					ds.setTotal((long)list.size());
//					ds.setRows(list);
				}
		}
		if("XZJD".equals(type)&&showSqjcwh){//展开乡镇街道节点
			ds = new DataSet(xzqhMapper.getSqcjwhCount(id),xzqhMapper.getSqcjwh(id));
		}
		return ds;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addXzqh(Map<String, Object> params) {
		validateXzqh(params);//params
		Date date = new Date();
		params.put("DZBM", UUIDUtil.uuid());
		params.put("GXSJ", date);
		
		params.put("DJR", SecurityUtils.getSessionUser().getUserId());//SecurityUtils.getSessionUser().getName()
		params.put("DJDW", SecurityUtils.getSessionUser().getOrganizationId());
		params.put("DJSJ", date);
		
		params.put("CXBJ", KConstants.CXBJ_0);
		if (params.get("BMJC")!=null) {
			params.put("ZJF",PinyinUtil.toSzm((String) params.get("BMJC")));
		}
		params.put("QYRQ", date);//启用日期
//		params.put("SLRQ", date); //设立日期
		if (params.get("SLRQ")!=null && !("").equals(params.get("SLRQ"))) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				params.put("SLRQ",df.parse((String)params.get("SLRQ")));
			} catch (ParseException e) {
				AlertSLEUtil.Error("设立日期填写错误");
			}
		}
		xzqhMapper.addXzqh(params);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveXzqh(Map<String, Object> params) {
		validateXzqh(params);//params
		
		params.put("GXSJ", new Date());
		params.put("XGR", SecurityUtils.getSessionUser().getUserId());//.getName()
		params.put("XGDW", SecurityUtils.getSessionUser().getOrganizationId());
		if (params.get("SLRQ")!=null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				params.put("SLRQ",df.parse((String)params.get("SLRQ")));
			} catch (ParseException e) {
				AlertSLEUtil.Error("设立日期填写错误");
			}
		}
		if (params.get("BMJC")!=null) {
			params.put("ZJF", PinyinUtil.toSzm((String) params.get("BMJC")));
		}
		if (KConstants.SYTZDM_STOPUSE.equals(params.get("SYZTDM").toString())) {
			params.put("TYRQ", new Date());
		}
		params.get("SLRQ");
		
		xzqhMapper.saveXzqh(params);
		
		publisher.publishEvent(new XzqhUpdateEvent(xzqhMapper.selectXzqhByDzbm((String)params.get("DZBM"))));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void cancelXzqh(Map<String, Object> params) {
		params.put("CXSJ", new Date());//撤销时间
		params.put("GXSJ", new Date());//更新时间
		params.put("CXR", SecurityUtils.getSessionUser().getUserId());//.getName()
		params.put("CXDW", SecurityUtils.getSessionUser().getOrganizationId());
		params.put("SYZTDM","20");//设置使用状态为停用
		params.put("TYRQ", new Date());//状态为停用,设置停用日期 
		xzqhMapper.cancelXzqh(params);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void activateXzqh(Map<String, Object> params) {
		params.put("QYRQ", new Date());//启用日期时间
		
		params.put("GXSJ", new Date());
		params.put("XGR", SecurityUtils.getSessionUser().getUserId());
		params.put("XGDW", SecurityUtils.getSessionUser().getOrganizationId());
		xzqhMapper.activateXzqh(params);
	}
	
	private void validateXzqh(Map<String, Object> params){
		
		String sjxzqhbm = (String) params.get("SJXZQY_DZBM");
		KAssert.hasText(sjxzqhbm, "上级行政区域不能为空");
		Xzqh sjxzqh  = selectXzqhByDzbm(sjxzqhbm);
		if(sjxzqh==null){
			AlertSLEUtil.Error("上级行政区划不存在");
		}
		
		String xzqhdm = (String) params.get("XZQHDM");
		KAssert.hasText(xzqhdm, "行政区划代码 不能为空");
		
		KAssert.isNumber(xzqhdm,"行政区划代码必须全为数字");
		
		
		if(!xzqhdm.startsWith("37")){
			AlertSLEUtil.Error("行政区划代码有误，必须以37开头");
		}
		if(xzqhdm.length()!=6){
			AlertSLEUtil.Error("行政区划代码长度必须为6位标准编码");
		}
		
		String dzyslxdm = (String) params.get("DZYSLXDM");
		KAssert.hasText(dzyslxdm, "地址元素类型不能为空");
		KAssert.hasValidLength(1,2,dzyslxdm, "地址元素类型有误");
		
		if(!"12".equals(dzyslxdm)&&!"13".equals(dzyslxdm)){//只能添加市，区县
			AlertSLEUtil.Error("地址元素类型有误");
		}
		
		
		if("11".equals(sjxzqh.getDzyslxdm())&&!"12".equals(dzyslxdm)){//上级为省 下级只能为市
			AlertSLEUtil.Error("地址元素类型有误");
		}
		if("12".equals(sjxzqh.getDzyslxdm())&&!"13".equals(dzyslxdm)){//上级为市 下级只能为区县
			AlertSLEUtil.Error("地址元素类型有误");
		}
		if("13".equals(sjxzqh.getDzyslxdm())){
			AlertSLEUtil.Error("上级行政区划只能为省或市");
		}
		
		
		String sjxzqhPre = "37";
		if("12".equals(dzyslxdm)){
			sjxzqhPre =  sjxzqh.getXzqhdm().substring(0,2);
		}else if("13".equals(dzyslxdm)){
			sjxzqhPre =  sjxzqh.getXzqhdm().substring(0,4);
		}
		if(!xzqhdm.startsWith(sjxzqhPre)){
			AlertSLEUtil.Error("行政区划代码有误，必须以"+sjxzqhPre+"开头");
		}
		
		String dzbm = (String) params.get("DZBM");
		
		//校验代码已存在
		Xzqh dbXzqh = xzqhMapper.selectXzqhBydm(xzqhdm);
		if(StringUtils.isEmpty(params.get("DZBM"))){//新增
			if(dbXzqh!=null){
				AlertSLEUtil.Error("行政区划代码已存在！");
			}
		}else{//修改
			if(dbXzqh!=null&&!dbXzqh.getDzbm().equals(dzbm)){
				AlertSLEUtil.Error("行政区划代码已存在！");
			}
		}
		
		
		
		String xzqhmc = (String) params.get("XZQHMC");
		KAssert.hasText(xzqhmc, "行政区划名称 不能为空");
		KAssert.hasValidLength(1,30,xzqhmc, "行政区划名称 长度有误");
		
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("XZQHMC", xzqhmc);
		PaginationUtil.initPageAndSort(map);
		List<Map<String, Object>> sameNameXzqhList = xzqhMapper.xzqhList(map);
		//校验名称已存在
		if(StringUtils.isEmpty(dzbm)){//新增
			if(sameNameXzqhList!=null&&sameNameXzqhList.size()>0){
				AlertSLEUtil.Error("行政区划名称已存在！");
			}
		}else{//修改
			for(Map<String, Object> m : sameNameXzqhList){
				if(!dzbm.equals(m.get("DZBM"))){
					AlertSLEUtil.Error("行政区划名称已存在！");
				}
			}
		}
		
		String syztdm = (String) params.get("SYZTDM");
		KAssert.hasText(syztdm, "使用状态 不能为空");
		KAssert.hasValidLength(1,2,syztdm, "使用状态有误");
		
		String bmjc = (String) params.get("BMJC");
		KAssert.hasText(bmjc, "别名简称不能为空");
		KAssert.hasValidLength(1,30,bmjc, "别名简称长度有误");
		
		
	}
	@Override
	public Xzqh selectXzqhBydm(String xzqhdm) {
		if(!StringUtils.hasText(xzqhdm)){
			return null;
		}
		return xzqhMapper.selectXzqhBydm(xzqhdm);
	}
	@Override
	public List<Xzqh> selectXzqhBydmList(String xzqhdm){
		if(!StringUtils.hasText(xzqhdm)){
			return null;
		}
		return xzqhMapper.selectXzqhBydmList(xzqhdm);
	}
	@Override
	public Xzqh selectXzqhByDzbm(String dzbm) {
		if(!StringUtils.hasText(dzbm)){
			return null;
		}
		return xzqhMapper.selectXzqhByDzbm(dzbm);
	}
	
	public List<Map<String,String>> selectXzqyMapByDzbm(String dzbm){
		if(!StringUtils.hasText(dzbm)){
			return null;
		}
		return xzqhMapper.selectXzqyMapByDzbm(dzbm);
	}
	
	public Map<String,Object> selectDetailByPrimaryKey(String dzbm){
		if(!StringUtils.hasText(dzbm)){
			return null;
		}
		return xzqhMapper.selectDetailByPrimaryKey(dzbm);
	}
}
