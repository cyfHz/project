package com.kingmon.project.psam.mlph.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.FilterBuilders.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.IDCardUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organizationuser.mapper.OrganizationUserMapper;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.event.MlphUpdateEvent;
import com.kingmon.project.event.XzqhUpdateEvent;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jwq.service.IJwqService;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.jzw.serivice.impl.JzwjbxxServiceImpl;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.shpz.service.IShpzService;
import com.kingmon.project.psam.shpz.utils.ShpzUtil;
import com.kingmon.project.psam.sqrxx.mapper.SqrxxMapper;
import com.kingmon.project.psam.sqrxx.model.Sqrxx;
import com.kingmon.project.psam.util.QydmData;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;

@Service
public class MlphServiceImpl implements MlphService, ApplicationListener<XzqhUpdateEvent> , ApplicationEventPublisherAware{
	@Inject
	private MlphMapper mlphMapper;
	@Inject
	private JwqMapper jwqMapper;
	@Autowired
	private SqrxxMapper sqrxxMapper;
	@Autowired
	private JzwjbxxMapper jzwjbxxMapper;
	
	@Autowired
	private ElasticService elasticService;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private IJwqService jwqService;
	@Autowired
	private IShpzService shpzService;
	@Autowired
	private IJzwjbxxService jzwjbxxService;
	
	@Value("${dev.data.process}")
	private String devDataProcess;
	
	@Autowired
	private XzqhMapper xzqhMapper;
	
	@Autowired
	private  OrganizationUserMapper organizationUserMapper;
	
	//------------------event --------------------------
	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		  this.publisher = publisher;
	}

	@Override
	public void onApplicationEvent(XzqhUpdateEvent event) {
		System.out.println("XzqhUpdateEvent: "+event.getSource());
	}
	//---------------------event-----------------------
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public DataSet mlphList(Map<String, String> params) {
		/**
		 * SSZDYJXZQY_DZBM jlxdzbm --> SSJLXXQ_DZBM DZMC MLPH SJGSDWMC
		 */
		String SJGSDWDM = params.get("SSZDYJXZQY_DZBM");
		String qydm = "";
		if(SJGSDWDM != null &&  !"".equals(SJGSDWDM)){
//			String xzqh=SecurityUtils.getUserLevelStr();
//			SJGSDWDM = SubApStrUtil.removeLastChars(xzqh,"0");	
			List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(SJGSDWDM);
			qydm = QydmData.getQydm(map);
		}else{
			qydm = SecurityUtils.getUserLevelStr();
		}		
		params.put("qydmForEs", qydm);
		qydm+="%";
		params.put("qydm", qydm);
		if (KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)) {
			PaginationUtil.initElasticPageAndSort(params);

			PrefixQueryBuilder  prefixQueryBuilder = QueryBuilders.prefixQuery("SJGSDWDM", params.get("qydmForEs"));

			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
			String dzmc = params.get("DZMC");
			if (dzmc != null && !dzmc.isEmpty()) {
				boolQueryBuilder.must(matchPhraseQuery("DZMC", dzmc));
			}
			String mlpph = params.get("MLPH");
			if (mlpph != null && !mlpph.isEmpty()) {
				boolQueryBuilder.must(matchPhraseQuery("MLPH", mlpph));
			}

			String sjgsdwmc = params.get("SJGSDWMC");
			if (sjgsdwmc != null && !sjgsdwmc.isEmpty()) {
				boolQueryBuilder.must(matchPhraseQuery("SJGSDWMC", sjgsdwmc));
			}

			String jlxdzbm = params.get("jlxdzbm");
			if (jlxdzbm != null && !jlxdzbm.isEmpty()) {
				boolQueryBuilder.must(termQuery("SSJLXXQ_DZBM", jlxdzbm));
			}
			//精确查询时使用
//			String xzqhdm = params.get("SSZDYJXZQY_DZBM");
//			if (xzqhdm != null && !xzqhdm.isEmpty()) {
//				boolQueryBuilder.must(termQuery("SSZDYJXZQY_DZBM", xzqhdm));
//			}
			
			String ywlsh = params.get("YWLSH");
			if (ywlsh != null && !ywlsh.isEmpty()) {
				boolQueryBuilder.must(termQuery("YWLSH", ywlsh));
			}
			boolQueryBuilder.must(prefixQueryBuilder);
			SearchRequestBuilder builder = elasticService.getClient().prepareSearch("psam").setTypes("mlph")
			.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(boolQueryBuilder)
			.addSort("YWLSH", SortOrder.ASC).setFrom(NumberUtils.toInt(params.get("from")))
			.setSize(NumberUtils.toInt(params.get("size")));
			
			String zbisnull = params.get("ZBISNULL");
			if (zbisnull != null && "1".equals(zbisnull)) {
				builder.setPostFilter(missingFilter("LOCATION"));
			}

			SearchResponse searchResponse = builder.execute().actionGet();
			return ElasticUtil.searchResponse2Dataset(searchResponse);
		} else {
			PaginationUtil.initPageAndSort(params);
//			params.put("SJGSDWDM",  SJGSDWDM + "%");
			String dzmc = params.get("DZMC");
			if (dzmc != null && !dzmc.isEmpty()) {
				params.put("DZMC", "%" + dzmc + "%");
			}
			String mlpph = params.get("MLPH");
			if (mlpph != null && !mlpph.isEmpty()) {
				params.put("MLPH", "%" + mlpph + "%");
			}
			String sjgsdwmc = params.get("SJGSDWMC");
			if (sjgsdwmc != null && !sjgsdwmc.isEmpty()) {
				params.put("SJGSDWMC", "%" + sjgsdwmc + "%");
			}
			String jlxdzbm = params.get("jlxdzbm");
			if (jlxdzbm != null && !"".equals(jlxdzbm)) {
				params.put("SSJLXXQ_DZBM", "%" + jlxdzbm + "%");
			}
			if(params.get("sort")==null){
				params.put("sort", "YWLSH");//默认按主键排序，避免数据重复bug
			}
			return new DataSet(mlphMapper.mlphListCount(params), mlphMapper.mlphList(params));
		}

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addMlph(Map<String, Object> params) {
		validateMlph(params);
		String uuidx= UUIDUtil.uuid();
		params.put("DZBM", uuidx);
		params.put("YWLSH",uuidx);
		params.put("DJR", SecurityUtils.getSessionUser().getUserId());//getName()
		params.put("DJDW", SecurityUtils.getSessionUser().getOrganizationId());
		params.put("DJSJ", new Date());
		params.put("SHPZDM", "1");// 派出所申请
		params.put("FROMBY", "0");
		
		String jwqbh = (String) params.get("JWQBH");
		Jwq jwq=jwqMapper.selectByJwqbh(jwqbh);
		if(jwq==null){
			AlertSLEUtil.Error("未查询到所选警务区");
		}
		
		//省市区县代码
		String dzbm=(String)params.get("SSXQDM");
		if(dzbm!=null && dzbm.isEmpty()){
			String xzqhdm = xzqhMapper.selectXzqhdmById(dzbm);
			params.put("SSXQDM", xzqhdm);
		}
/* 
	警务区编码为12位
		——第一层（1、2位）表示省、自治区、直辖市公安厅、局和新疆生产建设兵团公安局；
		——第二层（3、4位）表示省辖市、地区和省直辖行政单位，省直管县公安局（处）；
		——第三层（5、6位）表示设区市的区，地区和省辖市下辖的县、市公安局（分局）；
		——第四层（7、8、9位）表示公安派出所；
		——第五层（10、11、12位）表示警务责任区。

*/
		params.put("SSPCS", jwqbh.substring(0,9)+"000");
		params.put("SSFJ",  jwqbh.substring(0,6)+"000000");
		params.put("SSSJ",  jwqbh.substring(0,4)+"00000000");
		
		//给数据归属单位添加派出所的信息
		Organization org = organizationMapper.selectOrgByCode((String)params.get("SSPCS"));
//		params.put("SJGSDWDM", params.get("SSPCS"));
//		params.put("SJGSDWMC", org.getOrgna_name());
		
		params.put("SJGSDWDM",jwq.getJwqbh());
		params.put("SJGSDWMC", jwq.getJwqmc());
		
		params.put("FROMBY", "0");
		//处理申请人信息
		
		String sqrgmsfzhm = (String) params.get("sqrgmsfzhm");
		String sqdwlxdh= (String) params.get("sqdwlxdh");
		String sqdwmc =(String) params.get("sqdwmc");
		String sqrlxdh = (String) params.get("sqrlxdh");
		String sqrxm = (String) params.get("sqrxm");
		
		if(sqrgmsfzhm!=null&&!sqrgmsfzhm.isEmpty()){
			if(!IDCardUtil.validateCard(sqrgmsfzhm)){
				AlertSLEUtil.Error("申请人身份证号码格式有误");
			}
			Sqrxx sqrxx = sqrxxMapper.selectBySfzh(sqrgmsfzhm);
			if(sqrxx==null){
				sqrxx = new Sqrxx();
			}
			sqrxx.setSqdwlxdh(sqdwlxdh);
			sqrxx.setSqdwmc(sqdwmc);
			sqrxx.setSqrlxdh(sqrlxdh);
			sqrxx.setSqrxm(sqrxm);
			
			if(sqrxx.getSqrid()==null){
				String uuid = UUID.randomUUID().toString();
				sqrxx.setSqrid(uuid);
				sqrxx.setSqrgmsfzhm(sqrgmsfzhm);
				sqrxxMapper.insert(sqrxx);
				params.put("SQRID", uuid);
			}else{
				sqrxxMapper.updateByPrimaryKey(sqrxx);
				params.put("SQRID", sqrxx.getSqrid());
			}
		}else{
			if(!StringUtils.isEmpty(sqdwlxdh)||!StringUtils.isEmpty(sqdwmc)||!StringUtils.isEmpty(sqrlxdh)||!StringUtils.isEmpty(sqrxm)){
				//身份证号码为空，但填写了申请人信息的其他任何一项
				AlertSLEUtil.Error("申请人身份证号码必须填写");
			}
			
			params.put("SQRID", null);
		}
		
		/*
		 处理审批状态
		根据该市局代码  从 DZ_SHPZ找到配置
		PZDM=0：不需要审核	设置spzt为 2 通过
		PZDM=1：派出所审核	（警务区）设置spzt为0 正在审核    （市局/区县/派出所） 设为2 通过
		PZDM=2：区县审核     	（警务区/派出所）设置spzt为0 正在审核     （市局/区县） 设为2 通过 
		PZDM=3：市局审核 	（警务区/派出所/区县）设置spzt为0 正在审核   （ 市局）设为2 通过
		//TODO 省厅 增加？
		*/
		String sssj = (String) params.get("SSSJ");
		String shpzdm=shpzService.findShpzDm("2", sssj);
		if (shpzdm == null) {
			AlertSLEUtil.Error("未进行审核配置，请先配置");
		}
		String spzt =  ShpzUtil.getSpzt(shpzdm,SecurityUtils.getUserLevel());
		params.put("SPZT", spzt);
		mlphMapper.addMlph(params);
		// 更新ELastic
		elasticService.indexDocument(ElasticTypes.MLPH, (String) params.get("YWLSH"), ElasticUtil.mlphMapToDocument(params));

	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveMlph(Map<String, Object> params) {
		
		validateMlph(params);
		String jwqbh = (String) params.get("JWQBH");
		params.put("SSPCS", jwqbh.substring(0,9)+"000");
		params.put("SSFJ",  jwqbh.substring(0,6)+"000000");
		params.put("SSSJ",  jwqbh.substring(0,4)+"00000000");
		//给数据归属单位添加派出所的信息
//		Organization org = organizationMapper.selectOrgByCode((String)params.get("SSPCS"));
//				
//		params.put("SJGSDWDM", params.get("SSPCS"));
//		params.put("SJGSDWMC", org.getOrgna_name());
		
		Jwq jwq=jwqMapper.selectByJwqbh(jwqbh);
		if(jwq==null){
			AlertSLEUtil.Error("未查询到所选警务区");
		}
//		double ZXDHZB =Double.parseDouble((String) params.get("ZXDHZB"));
//		double ZXDZZB=Double.parseDouble((String) params.get("ZXDZZB")) ;
		double ZXDHZB = 0;
		double ZXDZZB = 0;//!("".equals(map.get("ZXRQ"))
		if(params.get("ZXDHZB")!=null&&params.get("ZXDZZB")!=null&&
				!("".equals(params.get("ZXDHZB")))&&!("".equals(params.get("ZXDHZB")))){
			ZXDHZB =Double.parseDouble((String) params.get("ZXDHZB"));
			ZXDZZB=Double.parseDouble((String) params.get("ZXDZZB")) ;
		}
		List<Map<String,Object>> jwqfw=jwqService.findJwqByPointAndUserPerm(SecurityUtils.getUserId(),ZXDZZB,ZXDHZB);
		if(jwqfw==null||jwqfw.size()<=0){
			AlertSLEUtil.Error("未查询到当前所选坐标点的警务区。");
		}
		int is =0;
		for(Map<String,Object> map :jwqfw){
			if(jwq.getJwqid().equals(map.get("JWQID"))){
				is++;
			}
		}	
		if(is==0){
			AlertSLEUtil.Error("所选坐标点与当前警务区不符。");
		}
		
		params.put("SJGSDWDM",jwq.getJwqbh());
		params.put("SJGSDWMC", jwq.getJwqmc());
		
		//省市区县代码
		String dzbm=(String)params.get("SSXQDM");
		if(dzbm!=null && !dzbm.isEmpty()){
			String xzqhdm = xzqhMapper.selectXzqhdmById(dzbm);
			if(xzqhdm!=null&&!xzqhdm.isEmpty()){
				params.put("SSXQDM", xzqhdm);
			}
		}
				
		//处理申请人信息
		String sqrgmsfzhm = (String) params.get("sqrgmsfzhm");
		String sqdwlxdh= (String) params.get("sqdwlxdh");
		String sqdwmc =(String) params.get("sqdwmc");
		String sqrlxdh = (String) params.get("sqrlxdh");
		String sqrxm = (String) params.get("sqrxm");
		
		if(sqrgmsfzhm!=null&&!sqrgmsfzhm.isEmpty()){
			if(!IDCardUtil.validateCard(sqrgmsfzhm)){
				AlertSLEUtil.Error("申请人身份证号码格式有误");
			}
			Sqrxx sqrxx = sqrxxMapper.selectBySfzh(sqrgmsfzhm);
			if(sqrxx==null){
				sqrxx = new Sqrxx();
			}
			sqrxx.setSqdwlxdh(sqdwlxdh);
			sqrxx.setSqdwmc(sqdwmc);
			sqrxx.setSqrlxdh(sqrlxdh);
			sqrxx.setSqrxm(sqrxm);
			
			if(sqrxx.getSqrid()==null){
				String uuid = UUID.randomUUID().toString();
				sqrxx.setSqrid(uuid);
				sqrxx.setSqrgmsfzhm(sqrgmsfzhm);
				sqrxxMapper.insert(sqrxx);
				params.put("SQRID", uuid);
			}else{
				sqrxxMapper.updateByPrimaryKey(sqrxx);
				params.put("SQRID", sqrxx.getSqrid());
			}
		}else{
			if(!StringUtils.isEmpty(sqdwlxdh)||!StringUtils.isEmpty(sqdwmc)||!StringUtils.isEmpty(sqrlxdh)||!StringUtils.isEmpty(sqrxm)){
				//身份证号码为空，但填写了申请人信息的其他任何一项
				AlertSLEUtil.Error("申请人身份证号码必须填写");
			}
			
			params.put("SQRID", null);
		}
		
		params.put("GXSJ", new Date());
		params.put("XGR", SecurityUtils.getSessionUser().getUserId());//getName()
		params.put("XGDW", SecurityUtils.getSessionUser().getOrganizationId());
		mlphMapper.saveMlph(params);
		
		// 更新ELastic
		elasticService.updateDocument(ElasticTypes.MLPH, (String) params.get("YWLSH"), ElasticUtil.mlphMapToDocument(params));
		publisher.publishEvent(new MlphUpdateEvent(mlphMapper.selectMlphByYwlsh((String) params.get("YWLSH"))));
		GeoPoint point=new GeoPoint(ZXDZZB,ZXDHZB);
		jzwjbxxService.updateJzwLocationOnlySelf((String)params.get("DZBM"),point);
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void settag(Map<String, Object> params) {
		if("1".equals(params.get("tag"))){
			params.put("DELUSER", SecurityUtils.getSessionUser().getName());
			params.put("DELTIME", new Date());
		}else{
			params.put("DELUSER", null);
			params.put("DELTIME", null);
		}
		params.put("GXSJ", new Date());
		mlphMapper.settag(params);

		// 更新ELastic
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("YWLSH", params.get("YWLSH"));
		map.put("DELTAG", params.get("tag"));
		map.put("DELUSER", params.get("DELUSER"));
		map.put("DELTIME", params.get("DELTIME"));

		elasticService.updateDocument(ElasticTypes.MLPH, (String) params.get("YWLSH"), ElasticUtil.mlphMapToDocument(map));

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void applyUseMlph(List<String> ywlshs, String pzlx) {
		if(ywlshs==null||ywlshs.size()==0){
			AlertSLEUtil.Error("请选择要申请的数据");
		}
		for(String ywlsh : ywlshs){
			Mlph mlph=mlphMapper.selectMlphByYwlsh(ywlsh);
			if(mlph!=null){
				String jlxdm=mlph.getSsjlxxq_dzbm();
				if(jlxdm==null||jlxdm.isEmpty()){
					AlertSLEUtil.Error("选择数据所属街路巷为空，请先维护该数据");
				}
				String qhdm = mlph.getSssj();
				if(qhdm==null||qhdm.length()<6){
					AlertSLEUtil.Error("门楼牌号所属市局代码有误");
				}
				//String qhdm=jlxMapper.selectQhdmBydzbm(jlxdm);
				String shpzdm=shpzService.findShpzDm("2", qhdm);
				String spzt = mlph.getSpzt();
				if (spzt == null|| KConstants.SPZT_INIT.equals(spzt)|| KConstants.SPZT_QXSH_YES.equals(spzt) || KConstants.SPZT_SUCCESS.equals(spzt)) {
					AlertSLEUtil.Error("不需要再次申请。");
				}
				mlphMapper.applyUseMlph(KConstants.SPZT_INIT,shpzdm, ywlsh);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("SHPZDM", shpzdm);
				map.put("SPZT", spzt);
				map.put("YWLSH", ywlsh);
				elasticService.updateDocument(ElasticTypes.MLPH, ywlsh, ElasticUtil.mlphMapToDocument(map));
			}
		}
		
//		String pzdm = mlphMapper.queryShpzdm(pzlx);
//		mlphMapper.applyUseMlph(pzdm, ywlshs);
//
//		// 更新ELastic
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("SHPZDM", "0");
//		map.put("SPZT", "0");
//		for (String ywlsh : ywlshs) {
//			map.put("YWLSH", ywlsh);
//			elasticService.updateDocument(ElasticTypes.MLPH, ywlsh, ElasticUtil.mlphMapToDocument(map));
//		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void reviewMlph(String ywlsh, String flag) {
		Mlph mlph = findMlphByYwlsh(ywlsh);
		if(mlph==null){
			AlertSLEUtil.Error("数据有误");
		}
		
		String sssj = mlph.getSssj();
		if(sssj==null||sssj.length()<6){
			AlertSLEUtil.Error("门楼牌号市局代码有误");
		}
		sssj = sssj.substring(0,6);
		String shpzdm=shpzService.findShpzDm("2", sssj);
		if(shpzdm == null){
			AlertSLEUtil.Error("未进行审核配置，请先配置");
		}
		int userLevel = SecurityUtils.getUserLevel();//zht-20160219
		//根据其审核配置进行审核
		String spzt_old = mlph.getSpzt();
		String spzt=ShpzUtil.getShResult(shpzdm,userLevel,flag,spzt_old);
		mlphMapper.reviewMlph(spzt, shpzdm,ywlsh);
		// 更新ELastic
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SHPZDM",shpzdm);
		map.put("SPZT", spzt);
		map.put("YWLSH", ywlsh);
		elasticService.updateDocument(ElasticTypes.MLPH, ywlsh, ElasticUtil.mlphMapToDocument(map));
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public Mlph findMlphByYwlsh(String ywlsh) {
		if (!StringUtils.hasText(ywlsh)) {
			return null;
		}
		return mlphMapper.selectMlphByYwlsh(ywlsh);
	}

	
	@Transactional(rollbackFor = Exception.class)
	@Override 
	public void updateMlphLocation(String ywlsh, GeoPoint point) {
		if(ywlsh==null||ywlsh.isEmpty()){
			AlertSLEUtil.Error("数据有误,业务流水号为空");
		}
		if(point==null||point.getLon()==0||point.getLat()==0){
			AlertSLEUtil.Error("经纬度有误");
		}
		//检索该点所属警务区 （ES）
		List<Map<String,Object>>  jwqList = jwqService.findJwqByPointAndUserPerm(SecurityUtils.getUserId(),point.getLat(), point.getLon());
		if(jwqList==null||jwqList.isEmpty()){
			AlertSLEUtil.Error("操作失败！所选点不在用户管辖的警务区边界范围内,不能标记坐标!");
		}
		Jwq jwq=jwqMapper.selectByPrimaryKey((String) jwqList.get(0).get("JWQID"));
		if(jwq==null){
			AlertSLEUtil.Error("操作失败！所选点未查询到警务区,不能标记坐标!");
		}
		Map<String, Object> map = Maps.newHashMap();
		//需要修改字段包括
		map.put("YWLSH", ywlsh);
		map.put("JWQBH",jwq.getJwqbh());//警务区编号
		map.put("JWQMC", jwq.getJwqmc());//警务区名称
		map.put("SJGSDWDM",jwq.getJwqbh());//数据归属单位
		map.put("SJGSDWMC",jwq.getJwqmc());//数据归属名称
		map.put("XGR", SecurityUtils.getUserId());// 修改人
		map.put("GXSJ",new Date());//修改时间
		map.put("XGDW", SecurityUtils.getUserOrgCode());//修改单位
		map.put("SSPCS", jwq.getJwqbh().substring(0,9)+"000");//所属派出所
		map.put("SSFJ",  jwq.getJwqbh().substring(0,6)+"000000");//所属分局
		map.put("SSSJ", jwq.getJwqbh().substring(0,4)+"00000000");//所属市局
		map.put("ZXDHZB", new BigDecimal(point.getLon()).setScale(8,RoundingMode.CEILING));
		map.put("ZXDZZB", new BigDecimal(point.getLat()).setScale(8,RoundingMode.CEILING));
		mlphMapper.updateMlphLocation(map);
		// 更新ELastic
		elasticService.updateDocument(ElasticTypes.MLPH, ywlsh, ElasticUtil.mlphMapToDocument(map));
		
		String ssjzw_dzbm=findSsJzwDzbm( ywlsh);
		if(ssjzw_dzbm != null && !ssjzw_dzbm.isEmpty()){	//Ssjzw_dzbm 所属建筑物地址编码  ！= null 即为 建筑物
			IJzwjbxxService jzwService=SpringBeanFacUtil.getBean(JzwjbxxServiceImpl.class);
			jzwService.updateJzwLocationOnlySelf(ssjzw_dzbm, point);
		}
	
	}
	@Transactional(rollbackFor = Exception.class)
	@Override 
	public void updateMlphLocationOnlySelf(String ywlsh, GeoPoint point) {
		if(ywlsh==null||ywlsh.isEmpty()){
			AlertSLEUtil.Error("数据有误,业务流水号为空");
		}
		if(point==null||point.getLon()==0||point.getLat()==0){
			AlertSLEUtil.Error("经纬度有误");
		}
		//检索该点所属警务区 （ES）
		List<Map<String,Object>>  jwqList = jwqService.findJwqByPointAndUserPerm(SecurityUtils.getUserId(),point.getLat(), point.getLon());
		if(jwqList==null||jwqList.isEmpty()){
			AlertSLEUtil.Error("操作失败！所选点不在用户管辖的警务区边界范围内,不能标记坐标!");
		}
		Jwq jwq=jwqMapper.selectByPrimaryKey((String) jwqList.get(0).get("JWQID"));
		if(jwq==null){
			AlertSLEUtil.Error("操作失败！所选点未查询到警务区,不能标记坐标!");
		}
		Map<String, Object> map = Maps.newHashMap();
		//需要修改字段包括
		map.put("YWLSH", ywlsh);
		map.put("JWQBH",jwq.getJwqbh());//警务区编号
		map.put("JWQMC", jwq.getJwqmc());//警务区名称
		map.put("SJGSDWDM",jwq.getJwqbh());//数据归属单位
		map.put("SJGSDWMC",jwq.getJwqmc());//数据归属名称
		map.put("XGR", SecurityUtils.getUserId());// 修改人
		map.put("GXSJ",new Date());//修改时间
		map.put("XGDW", SecurityUtils.getUserOrgCode());//修改单位
		map.put("SSPCS", jwq.getJwqbh().substring(0,9)+"000");//所属派出所
		map.put("SSFJ",  jwq.getJwqbh().substring(0,6)+"000000");//所属分局
		map.put("SSSJ", jwq.getJwqbh().substring(0,4)+"00000000");//所属市局
		map.put("ZXDHZB", new BigDecimal(point.getLon()).setScale(8,RoundingMode.CEILING));
		map.put("ZXDZZB", new BigDecimal(point.getLat()).setScale(8,RoundingMode.CEILING));
		mlphMapper.updateMlphLocation(map);
		// 更新ELastic
		elasticService.updateDocument(ElasticTypes.MLPH, ywlsh, ElasticUtil.mlphMapToDocument(map));
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet markList(Map<String, String> params) {

		if (KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)) {
			PaginationUtil.initElasticPageAndSort(params);
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
			String dzmc = params.get("DZMC");
			if (dzmc != null && !dzmc.isEmpty()) {
				boolQueryBuilder.must(matchQuery("DZMC", dzmc));
			}
			SearchResponse searchResponse = elasticService.getClient().prepareSearch("psam").setTypes("mlph").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(boolQueryBuilder).setPostFilter(missingFilter("LOCATION")).setFrom(NumberUtils.toInt(params.get("from"))).setSize(NumberUtils.toInt(params.get("size"))).execute().actionGet();
			return ElasticUtil.searchResponse2Dataset(searchResponse);
		} else {
			PaginationUtil.initPageAndSort(params);
			String dzmc = params.get("DZMC");
			if (dzmc != null && !dzmc.isEmpty()) {
				params.put("DZMC", "%" + dzmc + "%");
			}
			params.put("ZBISNULL", "1");
			return new DataSet(mlphMapper.mlphListCount(params), mlphMapper.mlphList(params));
		}
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void cancel(Map<String, Object> params) {
		
		params.put("DELUSER", SecurityUtils.getSessionUser().getUserId());//getName()
		params.put("DELTIME", new Date());
		params.put("GXSJ", new Date());
		params.put("DELTAG","1");
		mlphMapper.settag(params);

		// 更新ELastic
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("YWLSH", params.get("YWLSH"));
		map.put("DELTAG", 1);
		map.put("DELUSER", params.get("DELUSER"));
		map.put("DELTIME", params.get("DELTIME"));
		map.put("CXYY", params.get("CXYY"));
		elasticService.updateDocument(ElasticTypes.MLPH, (String) params.get("YWLSH"), ElasticUtil.mlphMapToDocument(map));

	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public void enable(Map<String, Object> params) {
	    params.put("DELUSER", null);
	    params.put("DELTIME", null);
		params.put("GXSJ", new Date());
		params.put("DELTAG","0");
		mlphMapper.settag(params);

		// 更新ELastic
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("YWLSH", params.get("YWLSH"));
		map.put("DELTAG", 0);
		map.put("DELUSER", null);
		map.put("DELTIME", null);

		elasticService.updateDocument(ElasticTypes.MLPH, (String) params.get("YWLSH"), ElasticUtil.mlphMapToDocument(map));

	}
	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public String findSsJzwDzbm(String ywlsh){
		String ssjzw_dzbm=mlphMapper.selectSsJzwDzbm(ywlsh);
		String ssJzwDzbm=(ssjzw_dzbm==null||ssjzw_dzbm.isEmpty())?ywlsh:ssjzw_dzbm;
		long count=jzwjbxxMapper.selectCountByPrimaryKey(ssJzwDzbm);
		if(count>0){
			return ssJzwDzbm;
		}else{
			return null;
		}
	}
	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public Map<String,Object> selectUsers(Map<String,Object> mlph){
		String DJR=(String)mlph.get("DJR");
		String XGR=(String)mlph.get("XGR");
		String DELUSER=(String)mlph.get("DELUSER");
		String DJDW=(String)mlph.get("DJDW");
		String XGDW=(String)mlph.get("XGDW");
		if(DJR!=null && !DJR.isEmpty()){
			OrganizationUser djr=organizationUserMapper.selectByPrimaryKey(DJR);
			if(djr!=null){
				mlph.put("DJR", djr.getUser_name());
			}
		}
		if(XGR!=null && !XGR.isEmpty()){
			OrganizationUser xgr=organizationUserMapper.selectByPrimaryKey(XGR);
			if(xgr!=null){
				mlph.put("XGR", xgr.getUser_name());
			}
		}
		if(DELUSER!=null && !DELUSER.isEmpty()){
			OrganizationUser deluser=organizationUserMapper.selectByPrimaryKey(DELUSER);
			if(deluser!=null){
				mlph.put("DELUSER", deluser.getUser_name());
			}
		}
		if(DJDW!=null && !DJDW.isEmpty()){
			Organization djdw=organizationMapper.selectOrgById(DJDW);
			if(djdw!=null){
				mlph.put("DJDW", djdw.getOrgna_name());
			}
		}
		if(XGDW!=null && !XGDW.isEmpty()){
			Organization xgdw=organizationMapper.selectOrgById(XGDW);
			if(xgdw!=null){
				mlph.put("XGDW", xgdw.getOrgna_name());
			}
		}
		return mlph;
	}
	
	
	private void validateMlph(Map<String, Object> params) {
		String jwqbh = (String) params.get("JWQBH");
		KAssert.hasText(jwqbh, "所属警务区不能为空");
		if(jwqbh.length()<12||!NumberUtils.isDigits(jwqbh)){//12位全数字
			AlertSLEUtil.Error("警务区编号："+jwqbh+"有误，应全为数字且编号长度不能小于12位");
		}
		String dzmc =  (String) params.get("DZMC");
		KAssert.hasText(dzmc, "地址不能为空");
		if(dzmc.length()>200){
			AlertSLEUtil.Error("地址有误，长度不能超过200个字符");
		}
		String qhnxxdz =  (String) params.get("QHNXXDZ");
		KAssert.hasText(qhnxxdz, "区划内详细地址不能为空");
		if(qhnxxdz.length()>200){
			AlertSLEUtil.Error("区划内详细地址有误，长度不能超过200个字符");
		}
		
		String ssxqdm =  (String) params.get("SSXQDM");
		KAssert.hasText(ssxqdm, "省市县（区）代码不能为空");
		if(ssxqdm.length()>36){
			AlertSLEUtil.Error("省市县（区）代码有误，长度不能超过36个字符");
		}
		
		String jwqmc =  (String) params.get("JWQMC");
		KAssert.hasText(jwqmc, "警务区名称不能为空");
		if(jwqmc.length()>200){
			AlertSLEUtil.Error("警务区名称有误，长度不能超过200个字符");
		}
		String mlph =  (String) params.get("MLPH");
		KAssert.hasText(mlph, "门(楼)牌号不能为空");
		if(mlph.length()>150){
			AlertSLEUtil.Error("门(楼)牌号有误，长度不能超过150个字符");
		}
		String mlphlxid =  (String) params.get("MLPHLXID");
//		KAssert.hasText(mlphlxid, "门楼牌号类型不能为空");
		if(mlphlxid.length()>1){
			AlertSLEUtil.Error("门楼牌号类型有误，长度不能超过1个字符");
		}
		
		String zxdhzb =  (String) params.get("ZXDHZB");
		String zxdzzb =  (String) params.get("ZXDZZB");
		if(zxdhzb!=null&&!zxdhzb.isEmpty()&&!NumberUtils.isNumber(zxdhzb)){
			AlertSLEUtil.Error("中心点横坐标必须为数字");
		}
		if(zxdzzb!=null&&!zxdzzb.isEmpty()&&!NumberUtils.isNumber(zxdzzb)){
			AlertSLEUtil.Error("中心点纵坐标必须为数字");
		}
	}
	
	public DataSet mlphQrList(Map<String, String> params,long num){
		String SJGSDWDM = params.get("SSZDYJXZQY_DZBM");
		String qydm = "";
		if(SJGSDWDM != null &&  !"".equals(SJGSDWDM)){
			List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(SJGSDWDM);
			qydm = QydmData.getQydm(map);
		}else{
			qydm = SecurityUtils.getUserLevelStr();
		}		
		params.put("qydmForEs", qydm);
		qydm+="%";
		params.put("qydm", qydm);
			PaginationUtil.initElasticPageAndSort(params);

			PrefixQueryBuilder  prefixQueryBuilder = QueryBuilders.prefixQuery("SJGSDWDM", params.get("qydmForEs"));

			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
			String dzmc = params.get("DZMC");
			if (dzmc != null && !dzmc.isEmpty()) {
				boolQueryBuilder.must(matchPhraseQuery("DZMC", dzmc));
			}
			String mlpph = params.get("MLPH");
			if (mlpph != null && !mlpph.isEmpty()) {
				boolQueryBuilder.must(matchPhraseQuery("MLPH", mlpph));
			}

			String sjgsdwmc = params.get("SJGSDWMC");
			if (sjgsdwmc != null && !sjgsdwmc.isEmpty()) {
				boolQueryBuilder.must(matchPhraseQuery("SJGSDWMC", sjgsdwmc));
			}

			String jlxdzbm = params.get("jlxdzbm");
			if (jlxdzbm != null && !jlxdzbm.isEmpty()) {
				boolQueryBuilder.must(termQuery("SSJLXXQ_DZBM", jlxdzbm));
			}
			String ywlsh = params.get("YWLSH");
			if (ywlsh != null && !ywlsh.isEmpty()) {
				boolQueryBuilder.must(termQuery("YWLSH", ywlsh));
			}
			boolQueryBuilder.must(prefixQueryBuilder);
			SearchRequestBuilder builder = elasticService.getClient().prepareSearch("psam").setTypes("mlph")
			.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(boolQueryBuilder)
			.addSort("YWLSH", SortOrder.ASC).setFrom(NumberUtils.toInt(params.get("from")))
			.setSize((int)num);
			
			String zbisnull = params.get("ZBISNULL");
			if (zbisnull != null && "1".equals(zbisnull)) {
				builder.setPostFilter(missingFilter("LOCATION"));
			}
			SearchResponse searchResponse = builder.execute().actionGet();
			return ElasticUtil.searchResponse2Dataset(searchResponse);
	}
}
