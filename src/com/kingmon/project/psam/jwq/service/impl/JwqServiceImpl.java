package com.kingmon.project.psam.jwq.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.geoShapeQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.OrgUtilX;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.datasync.DataSyncConst;
import com.kingmon.project.psam.datasync.model.DzDataSyncBiz;
import com.kingmon.project.psam.datasync.service.IMlphAndJzwDataSyncService;
import com.kingmon.project.psam.datasync.service.impl.DataSyncService;
import com.kingmon.project.psam.datasync.view.SyncParam;
import com.kingmon.project.psam.datasync.view.SyncParamItem;
import com.kingmon.project.psam.jwq.UtilX;
import com.kingmon.project.psam.jwq.mapper.JwqChangeMapper;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jwq.model.JwqChange;
import com.kingmon.project.psam.jwq.service.IJwqService;

@Service
public class JwqServiceImpl extends BaseService implements IJwqService {

	@Autowired
	private JwqMapper jwqMapper;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private JwqChangeMapper jwqChangeMapper;
	@Autowired
	private ElasticService elasticService;
	@Autowired
	private DataSyncService dataSyncService;
	@Value("${dev.data.process}")
	private String devDataProcess;
	@Autowired
	private IMlphAndJzwDataSyncService mlphAndJzwDataSyncService;
	
	private void saveOrUpdateJwqToElastic(Jwq jwq) {
		if (jwq != null && jwq.getJwqid() != null) {
			elasticService.indexDocument(ElasticTypes.JWQ, jwq.getJwqid(), ElasticUtil.toDocument(jwq));
		}
	}

	private void updateJwqToDocument(Map<String, Object> jwq) {
		try {
			elasticService.updateDocument(ElasticTypes.JWQ, (String) jwq.get("JWQID"),ElasticUtil.jwqMapToDocument(jwq));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public DataSet loadJwqDataSet(ParamObject po) {
		StringBuilder sql = new StringBuilder("");
				sql.append(" SELECT j.JWQBH, ")
				.append(" j.JWQID, ")
				.append(" j.JWQMC, ")
				.append(" j.JWQJJ, ")
				.append(" j.JWQMJ, ")
				.append(" j.JWHSL, ")
				.append(" j.NCGQSL, ")
				.append(" j.SFYX, ")
				.append(" j.QYRQ, ")
				.append(" j.SXRQ, ")
				.append(" j.XGSJ, ")
				.append(" j.BZ, ")
				.append(" j.BJZBZ, ")
				.append(" j.JWQXZ, ")
				.append(" org.ORGNA_CODE, ")
				.append(" org.ORGNA_NAME ")
				.append(" @from ENT_JWQ j left join APP_ORGANIZATION org on org.ORGNA_ID=j.PCSID ")
				.append(" where 1=1 ");
		String jwqbh = (String) po.getWebParam("jwqbh");
		if (StringUtils.hasText(jwqbh)) {
			sql.append(" and j.JWQBH like:jwqbh");
			po.addSQLParam("jwqbh", "%" +jwqbh+ "%");
		}
		String orgid = (String) po.getWebParam("orgid");
		if (StringUtils.hasText(orgid)) {
			orgid=SubApStrUtil.removeLastChars(orgid, "0");
		}else{
			String xzqh=SecurityUtils.getUserLevelStr();
			orgid = SubApStrUtil.removeLastChars(xzqh,"0");	
		}
		if (StringUtils.hasText(orgid)) {
			sql.append(" and org.ORGNA_ID like:orgid");
			po.addSQLParam("orgid", ""+orgid+"%");
		}		
		String jwqmc = (String) po.getWebParam("jwqmc");
		if (!SubApStrUtil.isEmptyAfterTrimE(jwqmc)) {
			sql.append(" and j.JWQMC like:jwqmc ");
			po.addSQLParam("jwqmc", "%" + jwqmc + "%");
		}
		String levelStr = "37";
		String orgCode = SecurityUtils.getUserOrgCode();
		int level = SecurityUtils.getUserLevel();
		if (level == 12) {
			levelStr = orgCode.substring(0, 9);
		} else {
			levelStr = orgCode.substring(0, level);
		}
		sql.append(" and j.JWQBH like:xzjddm_data_auth ");
		po.addSQLParam("xzjddm_data_auth", "" + levelStr + "%");

		if (po.hasOrder()) {
			sql.append(" order by ").append("j.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}else{
			sql.append(" order by j.JWQBH");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	private void validateRecord(Jwq jwq) {
		KAssert.hasText(jwq.getJwqmc(), "警务区名称不能为空");
		KAssert.hasText(jwq.getJwqbh(), "警务区编号不能为空");
		KAssert.hasText(jwq.getPcsid(), "派出所编号不能为空");
		KAssert.hasText(jwq.getSjxzqyid(), "上级行政区域不能为空");
	}



	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addJwq(Jwq jwq) {
		KAssert.hasText(jwq.getJwqmc(), "警务区名称不能为空");
		// KAssert.hasText(jwq.getJwqbh(), "警务区编号不能为空");
		KAssert.hasText(jwq.getPcsid(), "派出所编号不能为空");
		KAssert.hasText(jwq.getSjxzqyid(), "上级行政区域不能为空");
		// String jwqbh=jwq.getJwqbh();
		String pcsId = jwq.getPcsid();
		KAssert.notNull(pcsId, "所属派出所不能为空");
		Organization org = organizationMapper.selectOrgById(pcsId);
		KAssert.notNull(org, "未查询到所属派出所信息");
		String pcsCode = org.getOrgna_code();
		KAssert.notNull(pcsCode, "所属派出所编码为空");
		if (pcsCode.length() < 12) {
			AlertSLEUtil.Error("所属派出所编码长度不规范");
		}
		String jwqbhx = "";
		/*
		 * 警务区编码为12位 ——第一层（1、2位）表示省、自治区、直辖市公安厅、局和新疆生产建设兵团公安局；
		 * ——第二层（3、4位）表示省辖市、地区和省直辖行政单位，省直管县公安局（处）；
		 * ——第三层（5、6位）表示设区市的区，地区和省辖市下辖的县、市公安局（分局）；
		 * ——第四层（7、8、9位）表示公安派出所；
		 * ——第五层（10、11、12位）表示警务责任区。
		 * 
		 */
		List<String> listMap = jwqMapper.selectJwqBhListByPcsId(pcsId);
		if (listMap == null || listMap.size() == 0) {
			jwqbhx = pcsCode.substring(0, 9) + "001";
		} else {
			jwqbhx = UtilX.getRbm(pcsCode.substring(0, 9), listMap);
		}
		jwq.setJwqbh(jwqbhx);
		jwq.setJwqid(UUIDUtil.uuid());
		jwq.setXgsj(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS));
		jwqMapper.insertSelective(jwq);
		if (KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)) {
			Jwq jwqES = jwqMapper.selectByPrimaryKey(jwq.getJwqid());
			saveOrUpdateJwqToElastic(jwqES);
		} 

	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public Jwq getJwqById(String jwqid) {
		if (!StringUtils.hasText(jwqid)) {
			return null;
		}
		Jwq jwq = jwqMapper.selectByPrimaryKey(jwqid);
		return jwq;
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public Jwq loadJwqByIdForUpdate(String jwqid) {
		if (!StringUtils.hasText(jwqid)) {
			return null;
		}
		Jwq jwq = jwqMapper.selectByPrimaryKeyForUpdate(jwqid);
		return jwq;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updatejwq(Jwq jwq) {
		validateRecord(jwq);
		if ("0".equals(jwq.getSfyx())) {
			AlertSLEUtil.Error("该警务区信息已注销，不允许修改！");
		}
		jwq.setXgsj(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS));
		jwqMapper.updateByPrimaryKeySelective(jwq);

		if (KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)) {
			Jwq jwqES = jwqMapper.selectByPrimaryKey(jwq.getJwqid());
			saveOrUpdateJwqToElastic(jwqES);
		}
	}

	// NaN,undefined;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBjzbz(String jwqId, String newBjzbz) {
		KAssert.hasText(jwqId, "未选取警务区");
		newBjzbz = "" + newBjzbz;
		newBjzbz = newBjzbz.replaceAll("NaN,", "");
		newBjzbz = newBjzbz.replaceAll("undefined;", "");
		newBjzbz = newBjzbz.replaceAll("NaN;", "");
		String valRes = UtilX.validatePolygon(newBjzbz);
		newBjzbz=UtilX.removeRepeatLastPoints(newBjzbz);
		if (valRes != null) {
			AlertSLEUtil.Error(valRes);
		}
		// 警务区边界发生改变(前台传递第一个为原有警务区边界的值)
		Jwq jwq = getJwqById(jwqId);
		String oldBjzbz = jwq.getBjzbz();
		
		jwqMapper.updateBjzbz(jwqId, newBjzbz);
		if (KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)) {
			Jwq jwqES = jwqMapper.selectByPrimaryKey(jwqId);
			saveOrUpdateJwqToElastic(jwqES);
		}
		//asyc
		mlphAndJzwDataSyncService.asyncForJwqBjzbChange(jwq, newBjzbz, oldBjzbz);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void cancelJwq(String id) {
		String flag = "0";
		KAssert.notNull(id, "请选择一条要注销的数据");
		String nowDate = ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD);
		String nowDateTime = ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jwqid", id);
		map.put("sxrq", nowDate);
		map.put("sfyx", flag);
		map.put("xgsj", nowDateTime);
		jwqMapper.cancelJwq(map);
		if (KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)) {
			Jwq jwq = jwqMapper.selectByPrimaryKey(id);
			saveOrUpdateJwqToElastic(jwq);
		}

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void activateJwq(String id) {
		String flag = "1";
		String nowDate = ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD);
		String nowDateTime = ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		KAssert.notNull(id, "请选择一条要启用的数据");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jwqid", id);
		map.put("qyrq", nowDate);
		map.put("sfyx", flag);
		map.put("xgsj", nowDateTime);
		jwqMapper.activateJwq(map);
		if (KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)) {
			Jwq jwq = jwqMapper.selectByPrimaryKey(id);
			saveOrUpdateJwqToElastic(jwq);
		}
	}
	@Override
	public List<Jwq> loadJwqBjInSamePsc(String jwqid) {
		if(!StringUtils.hasText(jwqid)){
			return Collections.emptyList();
		}
		Jwq jwq = jwqMapper.selectByPrimaryKey(jwqid);
		if(jwq==null){
			return Collections.emptyList();
		}
		String pcsid=jwq.getPcsid();
		if(!StringUtils.hasText(pcsid)){
			return Collections.emptyList();
		}
		return jwqMapper.selectJwqListByPcsId(pcsid);
	}
	// -------------------------------------------------------------------------------------
	// [{type： "1",id:"";bh:"",mc:"",bjzbz:""}]
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public List<Map<String, Object>> loadUserJwqBianjie(String app_userId) {
		List<Jwq> jwqList = jwqMapper.selectJwqListByUserId(app_userId);
		List<Map<String, Object>> dataList = Lists.newArrayList();
		if (jwqList != null && jwqList.size() > 0) {
			Map<String, Object> map = null;
			for (Jwq jwq : jwqList) {
				map = Maps.newHashMap();
				map.put("type", "jwq");
				map.put("id", jwq.getJwqid());
				map.put("bh", jwq.getJwqbh());
				map.put("mc", jwq.getJwqmc());
				map.put("bjzbz", jwq.getBjzbz());
				dataList.add(map);
			}
		}
		return dataList;
	}

	@Override
	public List<Map<String, Object>> findJwqByPoint(double lat, double lng) {

		if (KConstants.DEV_DATA_PROCESS_DATABASE.equals(devDataProcess)) {
			// return findJwqByPointFormDB(lat,lng);
			return null;
		} else {
			return findJwqByPointFromElastic(lat, lng);
		}

	}

	private List<Map<String, Object>> findJwqByPointFromElastic(double lat, double ln) {
		SearchResponse searchResponse = elasticService.getClient().prepareSearch("psam").setTypes("jwq")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(geoShapeQuery("BJZB", ShapeBuilder.newPoint(ln, lat))).setFrom(0).setSize(100).execute()
				.actionGet();
		if (searchResponse == null || searchResponse.getHits() == null || searchResponse.getHits().getHits() == null
				|| searchResponse.getHits().getHits().length == 0) {
			return null;
		}
		SearchHits hits = searchResponse.getHits();
		List<Map<String, Object>> resList = Lists.newArrayList();
		Iterator<SearchHit> it = hits.iterator();
		while (it.hasNext()) {
			Map<String, Object> resMap = Maps.newHashMap();

			Map<String, Object> map = it.next().getSource();
		if(map.get("SFYX")!=null&&map.get("SFYX").equals("1")){
			resMap.put("JWQID", map.get("JWQID"));
			resMap.put("JWQMC", map.get("JWQMC"));
			resMap.put("JWQBH", map.get("JWQBH"));
			resMap.put("SFYX", map.get("SFYX"));
			resList.add(resMap);
		 }
		}
		return resList;
	}
	// ----------------------------------------------------------------------------------------------------
	/**
	 * 查找坐标点所在警务区
	 * @param lat
	 * @param lng
	 * @return 	JWQMC : value,<br>
	 * 			JWQHB : value<br>
	 * 			JWQID : value<br>
	 * 			SFYX  : value<br>
	 */
	public List<Map<String, Object>> findJwqByPointAndUserPerm(String app_userid, double lat, double lng) {
		if (!StringUtils.hasText(app_userid)) {
			return null;
		}
		int userLevel = SecurityUtils.getUserLevel();// zht-20160219
		List<Map<String, Object>> bianjieList = findJwqByPoint(lat, lng);
		if (bianjieList == null || bianjieList.size() == 0) {
			return null;
		}
		switch (userLevel) {
		case 12:// 警务区
			List<Jwq> jwqList = jwqMapper.selectJwqListByUserId(app_userid);
			if (jwqList == null || jwqList.size() == 0) {
				return null;
			}
			return process_level_12(jwqList, bianjieList);
		case 9:// 派出所9
		case 10:// 派出所case 10
		case 6:// 分局6
		case 4:// 市局 4
		case 2:// 省厅 2
		default:
			return process_level_expect_12(bianjieList);
		}
	}

	/**
	 * bianjieList(前端描点在搜索引擎总获得) 中在jwqList（用户警务区列表）中存在，
	 * 说明，该点在用户管辖的警务区内部，允许进行下一步操作<br/>
	 * 
	 * @param jwqList
	 *            : 用户已分配的警务区列表
	 * @param bianjieList
	 *            ：根据坐标点查询出来的警务区列表
	 * @return : JWQMC : value; JWQID : value; JWQBH : value
	 */
	private List<Map<String, Object>> process_level_12(List<Jwq> jwqList, List<Map<String, Object>> bianjieList) {
		if (jwqList == null || jwqList.size() == 0) {
			return null;
		}
		if (bianjieList == null || bianjieList.size() == 0) {
			return null;
		}
		List<Map<String, Object>> resList = Lists.newArrayList();
		for (Map<String, Object> map : bianjieList) {
			String jwqid = (String) map.get("JWQID");
			for (Jwq jwq : jwqList) {
				if (jwq.getJwqid().equals(jwqid)) {
					resList.add(map);
				}
			}
		}
		return resList.size() == 0 ? null : resList;
	}
	/*
	 * 警务区编码为12位 ——第一层（1、2位）表示省、自治区、直辖市公安厅、局和新疆生产建设兵团公安局；
	 * ——第二层（3、4位）表示省辖市、地区和省直辖行政单位，省直管县公安局（处）；
	 * ——第三层（5、6位）表示设区市的区，地区和省辖市下辖的县、市公安局（分局）；
	 * ——第四层（7、8、9位）表示公安派出所；
	 * ——第五层（10、11、12位）表示警务责任区。
	 * 
	 */
	private List<Map<String, Object>> process_level_expect_12(List<Map<String, Object>> bianjieList) {
		List<Map<String, Object>> resList = Lists.newArrayList();
		int userlevel = SecurityUtils.getUserLevel();// zht-20160219
//		String orgCode =SecurityUtils.getUserOrgCode();
		//Organization org = organizationMapper.selectOrgByUserId(app_userid);
		//String orgCode = org.getOrgna_code();
		String orgCode =SecurityUtils.getUserOrgCode();
		String orgPrefix = orgCode.substring(0, userlevel);

		for (Map<String, Object> map : bianjieList) {
			String jwqbh = (String) map.get("JWQBH");
			// 12：警务区 ； 9：派出所 ； 6:分局 ； 4:市局 ； 2: 省厅
			if (jwqbh == null || jwqbh.length() < userlevel) {
				continue;
			}
			String jwqbhPrefix = ("" + jwqbh).substring(0, userlevel);

			if ((jwqbhPrefix).equals(orgPrefix)) {// 在自己的管辖区域内部的警务区
				resList.add(map);
			}
		}
		return resList;
	}

	public static void main(String[] sd) {
		String jwqbh = "371302710049";
		String pcsid = "371302710000";
		System.out.println(jwqbh.substring(0, 9));
		System.out.println(pcsid.substring(0, 9));
	}
	
	
	
	
// ----------------------------------------------以下为拆分合并逻辑，截止20160329没有启用------------------------------------------------------------------------------------------------------------------
	// -------------------------Hebing-----------------------------------------------------
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Object checkJwqHebing(String[] jwqIds) {
		if (jwqIds == null || jwqIds.length < 2) {
			return new KJSONMSG(300, "请选择多于一条要合并的数据");
		}
		List<String> jwqIdsList = Arrays.asList(jwqIds);
		List<Jwq> jwqList = jwqMapper.selectJwqByIds(jwqIdsList);
		if (jwqList == null || jwqList.size() < 2) {
			return new KJSONMSG(300, "未查询到选取的数据");
		}
		String pcsId = jwqList.get(0).getPcsid();
		for (Jwq jwq : jwqList) {
			if (jwq.getJwqbh().equals("000")) {
				AlertSLEUtil.Error("所选要合并的数据,编码不符合警务区");
			}
			if (!("" + pcsId).equals(jwq.getPcsid())) {
				AlertSLEUtil.Error("所选要合并的数据不属于同一派出所");
			}
		}
		Organization pcs = organizationMapper.selectOrgById(pcsId);
		KAssert.notNull(pcs, "未查询到要合并警务区所属派出所");
		StringBuilder sql = new StringBuilder("SELECT j.JWQID, j.PCSID,j.JWQBH,j.JWQMC,j.SFYX ");
		sql.append(" @from ENT_JWQ j ");
		sql.append(" where 1=1 ");
		ParamObject po = ParamObject.new_NP_C();
		if (StringUtils.hasText(pcsId)) {
			sql.append(" and j.PCSID =:pcsId");
			po.addSQLParam("pcsId", pcsId);
		}
		if (po.hasOrder()) {
			sql.append(" order by ").append("org.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		DataSet ds = getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
		return new KJSONMSG(200, "数据加载成功", ds);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void processJwqHeBing(String[] fromIds, String toId) {
		// --自身逻辑
		if (fromIds == null || fromIds.length < 2) {
			AlertSLEUtil.Error("请选择要合并的数据");
		}
		Jwq toJwq = jwqMapper.selectByPrimaryKey(toId);
		KAssert.notNull(toJwq, "未查询到合并到的警务区");

		String toJwqCode = toJwq.getJwqbh();

		String type = OrgUtilX.checkOrg(toJwqCode);
		if (!"jwq".equals(type)) {
			AlertSLEUtil.Error("选择的数据不是【警务区】");
		}
		List<String> fromJwqIdsList = Arrays.asList(fromIds);
		List<Jwq> fromjwqList = jwqMapper.selectJwqByIds(fromJwqIdsList);
		for (Jwq fromJwq : fromjwqList) {
			String typex = OrgUtilX.checkOrg(fromJwq.getJwqbh());
			if (!"jwq".equals(typex)) {
				AlertSLEUtil.Error("选择的数据不是【警务区】");
			}
			if (!("" + fromJwq.getPcsid()).equals(toJwq.getPcsid())) {
				AlertSLEUtil.Error("要合并到的目标警务区,和被合的警务区不属于同一派出所");
			}
		}

		for (String fromId : fromIds) {
			JwqChange change = new JwqChange(UUIDUtil.uuid(), fromId, toId, "1", SecurityUtils.getUserId(), new Date());
			jwqChangeMapper.insertSelective(change);

			Jwq fromJwq = new Jwq();
			fromJwq.setJwqid(fromId);
			if (fromId.equals(toId)) {
				fromJwq.setSfyx("1");
			} else {
				fromJwq.setSfyx("0");
				Map<String, Object> map = Maps.newHashMap();
				map.put("JWQID", fromId);
				map.put("SFYX", "0");
				updateJwqToDocument(map);
			}
			jwqMapper.updateByPrimaryKeySelective(fromJwq);
		}
		// 边界处理****************
		String toJwqBjzb = "" + toJwq.getBjzbz();
		for (Jwq fromJwq : fromjwqList) {
			if (!fromJwq.getJwqid().equals(toId)) {
				toJwqBjzb += toJwqBjzb + ";";
			}
		}
		toJwq.setBjzbz(toJwqBjzb);
		jwqMapper.updateByPrimaryKeySelective(toJwq);

		Map<String, Object> mapx = Maps.newHashMap();
		mapx.put("JWQID", toJwq.getJwqid());
		mapx.put("BJZBZ", toJwqBjzb);
		updateJwqToDocument(mapx);

		// ------数据同步???-----------------------------------------------------------------
		List<SyncParamItem> syncParamItemList = Lists.newArrayList();
		for (Jwq fromJwq : fromjwqList) {
			SyncParamItem item = new SyncParamItem(fromJwq.getJwqbh(), toJwq.getJwqbh(), fromJwq.getJwqbh());
			syncParamItemList.add(item);
		}
		SyncParam syncParam = SyncParam.newSP().setSyncParamItemList(syncParamItemList)
				.setCraeteUserId(SecurityUtils.getUserId());
		dataSyncService.createAndExecuteSyncTask(new DzDataSyncBiz(DataSyncConst.PSAM_SYNC_JWQ_CHAIFEN), syncParam);
	}

	// -------------------------ChaiFen-----------------------------------------------------
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public DataSet loadJwqChaiFenDataGrid(String fromJwqId) {
		if (fromJwqId == null || fromJwqId.isEmpty()) {
			return DataSet.newDs();
		}
		Jwq jwq = jwqMapper.selectByPrimaryKey(fromJwqId);
		if (jwq == null || jwq.getPcsid() == null) {
			return DataSet.newDs();
		}
		String pcsId = jwq.getPcsid();
		Organization pcs = organizationMapper.selectOrgById(pcsId);
		KAssert.notNull(pcs, "未查询到要拆分警务区所属派出所");
		StringBuilder sql = new StringBuilder("SELECT j.JWQID, j.PCSID,j.JWQBH,j.JWQMC,j.SFYX ");
		sql.append(" @from ENT_JWQ j ");
		sql.append(" where 1=1 ");

		ParamObject po = ParamObject.new_NP_C();
		if (StringUtils.hasText(pcsId)) {
			sql.append(" and j.PCSID =:pcsId");
			po.addSQLParam("pcsId", pcsId);
		}
		// 警务区拆分 ，拆分到的目标警务区不准再包含自己
		sql.append(" and  j.JWQID !=:fromJwqId ");
		po.addSQLParam("fromJwqId", fromJwqId);

		if (po.hasOrder()) {
			sql.append(" order by ").append("org.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void processJwqChaiFen(String fromId, String[] toIds) {

		KAssert.notNull(fromId, "要拆分的警务区不能为空");
		if (toIds == null || toIds.length == 0) {
			AlertSLEUtil.Error("要拆分到的目标警务区不能为空");
		}
		List<String> toJwqIdList = Arrays.asList(toIds);
		if (toJwqIdList.contains(fromId)) {
			AlertSLEUtil.Error("要拆分到的目标警务区,不能包含被拆分的警务区");
		}

		Jwq fromJwq = jwqMapper.selectByPrimaryKey(fromId);
		KAssert.notNull(fromId, "未查询到要拆分的警务区");
		List<Jwq> toJwqList = jwqMapper.selectJwqByIds(toJwqIdList);

		for (Jwq toJwq : toJwqList) {
			if (!("" + fromJwq.getPcsid()).equals(toJwq.getPcsid())) {
				AlertSLEUtil.Error("要拆分到的目标警务区,和被拆分的警务区不属于同一派出所");
			}
			if (toJwq.getJwqid().equals(fromJwq.getJwqid())) {
				AlertSLEUtil.Error("要拆分到的目标警务区,不能包含被拆分的警务区");
			}
		}
		// -----------------save-------------------------
		Jwq jwq = new Jwq();
		jwq.setJwqid(fromId);
		jwq.setSfyx("0");
		jwqMapper.updateByPrimaryKeySelective(jwq);

		Map<String, Object> map = Maps.newHashMap();
		map.put("JWQID", fromId);
		map.put("SFYX", "0");
		updateJwqToDocument(map);

		for (String toId : toIds) {
			JwqChange change = new JwqChange(UUIDUtil.uuid(), fromId, toId, "2", SecurityUtils.getUserId(), new Date());
			jwqChangeMapper.insertSelective(change);
		}

		// ------------------process--dataSync------------------------------------------------------------
		String oneRepeatSourceValue = fromJwq.getJwqbh();
		List<String> targetValues = Lists.newArrayList();
		for (Jwq toJwq : toJwqList) {
			targetValues.add(toJwq.getJwqbh());
		}
		List<SyncParamItem> syncParamItemList = Lists.newArrayList();

		SyncParam syncParam = SyncParam.newSP();// .setParamValues(oneRepeatSourceValue,
												// targetValues).setCraeteUserId(SecurityUtils.getUserId());
		dataSyncService.createAndExecuteSyncTask(new DzDataSyncBiz(DataSyncConst.PSAM_SYNC_JWQ_CHAIFEN), syncParam);
	}

}
