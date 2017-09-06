package com.kingmon.project.psam.jzw.serivice.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.PinyinUtil;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organizationuser.mapper.OrganizationUserMapper;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.common.dictionary.mapper.DictionaryMapper;
import com.kingmon.project.common.dictionary.model.Dictionary;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jlx.mapper.JlxMapper;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jwq.service.IJwqService;
import com.kingmon.project.psam.jzw.JzwJgNewUtil;
import com.kingmon.project.psam.jzw.mapper.JzwdyMapper;
import com.kingmon.project.psam.jzw.mapper.JzwfjMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
import com.kingmon.project.psam.jzw.mapper.JzwlcMapper;
import com.kingmon.project.psam.jzw.model.Jzwdy;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.jzw.model.Jzwjg;
import com.kingmon.project.psam.jzw.model.Jzwlc;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.mlph.service.impl.MlphServiceImpl;
import com.kingmon.project.psam.sqrxx.mapper.SqrxxMapper;
import com.kingmon.project.psam.sqrxx.model.Sqrxx;
import com.kingmon.project.psam.util.QydmData;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.util.Pinyin4jUtil;
@Service
public class JzwjbxxServiceImpl extends BaseService implements IJzwjbxxService{
	@Autowired
	private JzwjbxxMapper jzwjbxxMapper;
	@Autowired
	private JzwjgMapper jzwjgMapper;
	@Autowired
	private JzwdyMapper jzwdyMapper;
	@Autowired
	private JzwlcMapper jzwlcMapper;
	@Autowired
	private JzwfjMapper jzwfjMapper;
	
	@Autowired
	private MlphMapper mlphMapper;
	@Autowired
	private SqrxxMapper sqrxxMapper;
	@Autowired
	private OrganizationUserMapper organizationUserMapper;
	@Autowired
	private IJwqService jwqService;
	@Autowired
	private JwqMapper jwqMapper;
	@Autowired
	private ElasticService elasticService;
	
	@Autowired
	private DictionaryMapper dictionaryMapper;
	
	@Autowired
	private JlxMapper jlxMapper;
	
	@Value("${dev.data.process}")
	private String devDataProcess;
	@Autowired
	private XzqhMapper xzqhMapper;
	private void updateJzwDocument(Jzwjbxx jzwjbxx){
		try {
			elasticService.updateDocument(ElasticTypes.JZWJBXX, jzwjbxx.getDzbm(), ElasticUtil.toDocument(jzwjbxx));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void addJzwDocument(Jzwjbxx jzwjbxx){
		try {
			elasticService.indexDocument(ElasticTypes.JZWJBXX, jzwjbxx.getDzbm(), ElasticUtil.toDocument(jzwjbxx));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	private void addMlphDocument(Mlph mlph){
		try {
			elasticService.indexDocument(ElasticTypes.MLPH, mlph.getDzbm(), ElasticUtil.toDocument(mlph));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadJzwjbxxDataSet(Map<String,String> params){
		String sszdyjxzqy_dzbm = params.get("sszdyjxzqy_dzbm");
		String qydm="";
		if(sszdyjxzqy_dzbm == null || sszdyjxzqy_dzbm.isEmpty()){
			String xzqh=SecurityUtils.getUserLevelStr();
			xzqh=xzqh+"00000000";
			if(xzqh.length()>6){ 
//				xzqh = xzqh.substring(0,6); 
				xzqh=SubApStrUtil.removeLastChars(xzqh, "0");
			}
			sszdyjxzqy_dzbm=xzqhMapper.selectDzbmByDm(xzqh);
			qydm = xzqh;
		}else{
			List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(sszdyjxzqy_dzbm);
			qydm=QydmData.getQydm(map);
//			qydm+="%";
			params.put("qydm", qydm);
		}
		params.put("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
		if(KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)){
			PaginationUtil.initElasticPageAndSort(params);
			PrefixQueryBuilder  prefixQueryBuilder =null; 
			QueryStringQueryBuilder queryStringQueryBuilder = null;
			QueryBuilder queryBuilder = null;
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
			String dzmc = params.get("DZMC");
			if(dzmc!=null&&!dzmc.isEmpty()){
				queryStringQueryBuilder = QueryBuilders.queryStringQuery("\""+dzmc+"\"");
				queryBuilder = queryStringQueryBuilder.field("DZMC");
				boolQueryBuilder.must(queryBuilder);
			//	boolQueryBuilder.must(matchQuery("DZMC","\""+dzmc+"\""));
			//	boolQueryBuilder.must(matchPhraseQuery("DZMC", dzmc));
			}
			String jzwmc=params.get("JZWMC");
			if(jzwmc!=null && !jzwmc.isEmpty()){
				//boolQueryBuilder.must(matchQuery("JZWMC", jzwmc));
//				boolQueryBuilder.must(matchPhraseQuery("JZWMC", jzwmc));
				queryStringQueryBuilder = QueryBuilders.queryStringQuery("\""+jzwmc+"\"");
				queryBuilder = queryStringQueryBuilder.field("JZWMC").field("ZJF");
				boolQueryBuilder.must(queryBuilder);
			}
			String ssjlxxq_dzbm=params.get("ssjlxxq_dzbm");
			if(ssjlxxq_dzbm!=null && ! ssjlxxq_dzbm.isEmpty()){
				boolQueryBuilder.must(termQuery("SSJLXXQ_DZBM",ssjlxxq_dzbm));
			}
			sszdyjxzqy_dzbm=params.get("sszdyjxzqy_dzbm");
			//精确查询时使用
//			if(sszdyjxzqy_dzbm!=null && ! sszdyjxzqy_dzbm.isEmpty()){
//				boolQueryBuilder.must(termQuery("SSZDYJXZQY_DZBM",sszdyjxzqy_dzbm));
//				QueryBuilders.prefixQuery("ZAGLSSJWZRQDM", sszdyjxzqy_dzbm);
//			}
			//模糊查询
			prefixQueryBuilder = QueryBuilders.prefixQuery("ZAGLSSJWZRQDM", qydm);
			
			boolQueryBuilder.must(prefixQueryBuilder);

			SearchResponse searchResponse = elasticService.getClient().prepareSearch("psam").setTypes("jzwjbxx")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(boolQueryBuilder).addSort("DZBM", SortOrder.ASC)
				.setFrom(NumberUtils.toInt(params.get("from"))).setSize(NumberUtils.toInt(params.get("size"))).execute()
			.actionGet();
			return ElasticUtil.searchResponse2Dataset(searchResponse);
		}else{
			
			PaginationUtil.initPageAndSort(params);
			String jzwmc = params.get("jzwmc");
			if(jzwmc!=null && !jzwmc.isEmpty()){
				params.put("jzwmc", "%"+jzwmc+"%");
			}else{
				params.remove("jzwmc");
			}
			String dzmc=params.get("dzmc");
			if(dzmc!=null && !dzmc.isEmpty()){
				params.put("dzmc", "%"+dzmc+"%");
			}else{
				params.remove("dzmc");
			}
			String jlxdzbm=params.get("ssjlxxq_dzbm");
			if(jlxdzbm!=null && !"".equals(jlxdzbm)){
				params.put("ssjlxxq_dzbm",jlxdzbm);
			}else{
				params.remove("ssjlxxq_dzbm");
			}
			
			sszdyjxzqy_dzbm=params.get("sszdyjxzqy_dzbm");
			if(sszdyjxzqy_dzbm!=null && !"".equals(sszdyjxzqy_dzbm)){
//				params.put("sszdyjxzqy_dzbm",sszdyjxzqy_dzbm);
				List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(sszdyjxzqy_dzbm);
				qydm=QydmData.getQydm(map);
				qydm+="%";
				params.put("qydm", qydm);
				params.remove("sszdyjxzqy_dzbm");
			}else{
				params.remove("sszdyjxzqy_dzbm");
			}
			
			//String xzqh=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
			String xzqh=SecurityUtils.getUserLevelStr();//-zht-20160219
			if(xzqh.length()>6){ xzqh = xzqh.substring(0,6); }
			params.put("xzjddm_data_auth",""+xzqh+"%");
			
			return new DataSet(jzwjbxxMapper.selectJzwjbxxCount(params),jzwjbxxMapper.selectJzwjbxxList(params));
		}
	}
	

	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public Jzwjbxx getJzwJbxxById(String dzbm) {
		return jzwjbxxMapper.selectByPrimaryKey(dzbm);
	}

	@SuppressWarnings("rawtypes")
	private void validate(Map map){
		//申请人信息
//		String sqrxm = (String) map.get("sqrxm");
//		String sqrlxdh = (String) map.get("sqrlxdh");
//		String sqdwmc = (String) map.get("sqdwmc");
//		String sqdwlxdh = (String) map.get("sqdwlxdh");
//		String sqrgmsfzhm = (String) map.get("sqrgmsfzhm");

//		KAssert.hasText(sqrxm, "申请人姓名不能为空");
//		KAssert.hasText(sqrlxdh, "申请人联系电话不能为空");
//		KAssert.hasText(sqrgmsfzhm, "申请人公民身份证号码不能为空");
//		KAssert.hasText(sqdwmc, "申请单位名称不能为空");
//		KAssert.hasText(sqdwlxdh, "申请单位联系电话不能为空");
		//门楼牌号                                                  
		String ssxqdm = (String) map.get("ssxqdm");          
		String qhnxxdz = (String) map.get("qhnxxdz");        
		String mlph = (String) map.get("mlph");              
		String mlphqz = (String) map.get("mlphqz");          
		String lsmlpbs = (String) map.get("lsmlpbs");        
		String fromby = (String) map.get("fromby");          
		String mlphlxid = (String) map.get("mlphlxid");      
//		String mlphlxmc = (String) map.get("mlphlxmc");      
		KAssert.hasText(ssxqdm, "省市县（区）不能为空");     
		KAssert.hasText(qhnxxdz, "区划内详细地址不能为空");  
		KAssert.hasText(mlph, "门楼牌号不能为空");           
		
		//KAssert.hasText(mlphqz, "门路牌号前缀不能为空");     
		
		KAssert.hasText(lsmlpbs, "临时门楼牌号标识不能为空");
		KAssert.hasText(fromby, "数据来源不能为空");         
		KAssert.hasText(mlphlxid, "门楼牌号类型不能为空");   
//		KAssert.hasText(mlphlxmc, "门楼牌号类型名称不能为空");
		//建筑物                                             	   
		String dzyslxdm = (String) map.get("dzyslxdm");
		BigDecimal zxdzzb=null;
		BigDecimal zxdhzb=null;
		Boolean isJZW=null;
		if ("1".equals(fromby)) {
			zxdzzb = new BigDecimal(""+map.get("zxdzzb"));
			zxdhzb = new BigDecimal(""+map.get("zxdhzb"));
			isJZW = Boolean.parseBoolean(""+map.get("isJZW"));
			String djr = (String) map.get("djr")==null?"":(String) map.get("djr");
			KAssert.hasText(djr, "登记人不能为空");   
		}else{
			if (map.get("zxdzzb")==null) {
				zxdzzb=null;
			}else{
				zxdzzb = new BigDecimal((String)map.get("zxdzzb"));
			}
			if (map.get("zxdhzb")==null) {
				zxdhzb=null;
			}else{
				zxdhzb = new BigDecimal((String)map.get("zxdhzb"));
			}
			if (map.get("isJZW")==null) {
				isJZW = null;
			}else{
				isJZW = Boolean.parseBoolean((String)map.get("isJZW"));
			}
		}
		KAssert.notNull(isJZW, "是否建筑物 不能为空");   
		if (isJZW) {
			String dzzczbz = (String) map.get("dzzczbz");
			String dzzzybs = (String) map.get("dzzzybs");
			String dzmc = (String) map.get("dzmc");
			String jzwmc = (String) map.get("jzwmc");
			String bmjc = (String) map.get("bmjc");	

			KAssert.hasText(dzmc, "地址名称不能为空");  
			KAssert.hasText(jzwmc, "建筑物名称不能为空");           
//			KAssert.hasText(bmjc, "别名简称不能为空");     
			KAssert.hasText(dzzczbz, "地(住)址存在标识不能为空");     
			KAssert.hasText(dzzzybs, "地(住)址在用标识不能为空");  
		}
		String sszdyjxzqy_dzbm = (String) map.get("sszdyjxzqy_dzbm");
		String ssjlxxq_dzbm = (String) map.get("ssjlxxq_dzbm");

	
	/*	String macAddress = (String) map.get("macAddress");
		String tfCardNum = (String) map.get("tfCardNum");
		String imeiNum = (String) map.get("imeiNum");
		String simNum = (String) map.get("simNum");
		BigDecimal gpsX = (BigDecimal) map.get("gpsX");
		BigDecimal gpsY = (BigDecimal) map.get("gpsY");*/
		
		KAssert.hasText(dzyslxdm, "地址元素类型不能为空");     

		KAssert.notNull(zxdzzb, "中心点横坐标不能为空");
		KAssert.notNull(zxdhzb, "中心点纵坐标不能为空");         
//		KAssert.hasText(sszdyjxzqy_dzbm, "所属最低一级行政区域不能为空");   
		KAssert.hasText(ssjlxxq_dzbm, "所属街路巷（小区）不能为空");

		
	}
	/**
	 * @param view
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String addInfoAcquisition(Map view) {
		validate(view);
		
		Date date = new Date();
		String fromby = (String) view.get("fromby");   
		String jwqid = (String) view.get("jwqid"); 
		if(jwqid==null||jwqid.isEmpty()){
			AlertSLEUtil.Error("所属警务区为空，请重新选点");
		}
		Jwq jwq=jwqMapper.selectByPrimaryKey(jwqid);
		if(jwq==null){
			AlertSLEUtil.Error("未查询到警务区数据，请重新选点");
		}
		BigDecimal zxdzzb;//中心点横坐标
		BigDecimal zxdhzb;//中心点纵坐标
		BigDecimal gpsX=null;
		BigDecimal gpsY=null;
		Boolean isJZW =true;
		if ("1".equals(fromby)) {
			zxdzzb = new BigDecimal(""+view.get("zxdzzb"));
			zxdhzb = new BigDecimal(""+view.get("zxdhzb"));
			isJZW = (Boolean.parseBoolean(""+view.get("isJZW")));
			gpsX = new BigDecimal(""+view.get("gpsX"));
			gpsY = new BigDecimal(""+view.get("gpsY"));
		}else{
			zxdzzb = new BigDecimal((String)view.get("zxdzzb")==null?"":(String)view.get("zxdzzb"));
			zxdhzb = new BigDecimal((String)view.get("zxdhzb")==null?"":(String)view.get("zxdhzb"));
			isJZW = Boolean.parseBoolean((String)view.get("isJZW")==null?"":(String)view.get("isJZW"));
		}
		//申请人信息
		IOrganizationService orgService=SpringBeanFacUtil.getBean(IOrganizationService.class);
		IOrganizationUserService orgUserService=SpringBeanFacUtil.getBean(IOrganizationUserService.class);
		Organization org=orgService.findOrgByUserId(SecurityUtils.getUserId());
		OrganizationUser orgUser=orgUserService.findByUserId(SecurityUtils.getUserId());
		String sqrid=UUIDUtil.uuid();
		String sqrxm = orgUser.getUser_name();// 申请人姓名
		String sqrlxdh =orgUser.getUser_mobile();// (String) view.get("sqrlxdh");// 申请人联系电话
		String sqdwmc =org.getOrgna_name();// (String) view.get("sqdwmc");// 申请单位名称
		String sqdwlxdh =org.getOrgna_tel();// (String) view.get("sqdwlxdh");//申请单位联系电话
		String sqrgmsfzhm =orgUser.getUser_sfzh();// (String) view.get("sqrgmsfzhm");//申请人公民身份证号码
		
		//门楼牌号                                                  
		String ssxqdm = (String) view.get("ssxqdm");//省市县（区）    
		//省市区县代码
		if(ssxqdm!=null && ssxqdm.isEmpty()){
			ssxqdm = xzqhMapper.selectXzqhdmById(ssxqdm);
		}
		
		String qhnxxdz = (String) view.get("qhnxxdz");   //区划内详细地址     
		String mlph_ = (String) view.get("mlph");    
		String mlphdzmc = (String) view.get("mlphdzmc");  //不是数据库字段，为了绑定接收参数，业务逻辑中赋值给dzmc  
		String mlphqz = (String) view.get("mlphqz");  //门楼牌号前缀        
		String lsmlpbs = (String) view.get("lsmlpbs");//临时门（楼）牌标识        
		String mlphlxid = (String) view.get("mlphlxid");  // 门楼牌号类型ID 对应 字典表的mlphlx    
		String mlphlxmc = (String) view.get("mlphlxmc");// 门楼牌号类型名称
		
		String rowline = (String) view.get("rowline");// 所属排--20160224 添加
		
		//mlph.setMlphlxid();
		if (mlphlxmc==null) {
			Dictionary dic=dictionaryMapper.selectDictByTypeAndCode("mlphlx", mlphlxid);
			if(dic!=null){
				mlphlxmc = dic.getDict_mc();//门楼牌号类型名称
			}
		}
		
		//建筑物                                             	   
		String jzwjbxxid=UUIDUtil.uuid();
		
		String dzmc = (String) view.get("dzmc");//地址
		String jzwmc = (String) view.get("jzwmc");// 建筑物名称
		String bmjc = (String) view.get("bmjc");//别名简称-
		String dzzczbz = (String) view.get("dzzczbz");// 地(住)址存在标识
		String dzzzybs = (String) view.get("dzzzybs");// 地(住)址在用标识
		
		//建筑物-门楼牌号公有
		String dzyslxdm = (String) view.get("dzyslxdm");//地址元素类型代码
		String sszdyjxzqy_dzbm = (String) view.get("sszdyjxzqy_dzbm");//所属最低一级行政区划编码
		String ssjlxxq_dzbm = (String) view.get("ssjlxxq_dzbm");//所属街路巷小区编码
		String appuser_id = (String) view.get("appuser_id");//登记人
		
		String macAddress = (String) view.get("macAddress");
		String tfCardNum = (String) view.get("tfCardNum");
		String imeiNum = (String) view.get("imeiNum");
		String simNum = (String) view.get("simNum");
		
		Mlph mlph = new Mlph();
		
		mlph.setYwlsh(jzwjbxxid);//业务流水号
		mlph.setDzbm(jzwjbxxid);//地址编码
		mlph.setDzyslxdm(dzyslxdm);//地址元素类型代码
		
		mlph.setDzmc(mlphdzmc);//用mlphdzmc  接收参数赋值给dzmc
		mlph.setSsxqdm(ssxqdm);//省市县（区）
		mlph.setQhnxxdz(qhnxxdz);// 区划内详细地址-
		
		mlph.setSsjlxxq_dzbm(ssjlxxq_dzbm);//所属街路巷(小区)_地址编码
			String jlxmc=jlxMapper.selectjlxmcBydzbm(ssjlxxq_dzbm);
		mlph.setSsjlxxq_jlxxqmc(jlxmc);
		if (isJZW) {
			mlph.setSsjzw_dzbm(jzwjbxxid);//所属建筑物_地址编码
			mlph.setJzwmc(jzwmc);
		}
		mlph.setMlph(mlph_);//门(楼)牌号
		mlph.setLsmlpbs(lsmlpbs);// 临时门（楼）牌标识
		
//		mlph.setRowline(rowline); //所属排--20160224 添加
		
		mlph.setBldw_gajgjgdm("");//办理单位_公安机关机构代码**************>?
		mlph.setBldw_gajgmc("");//办理单位_公安机关名称**************>?
		
		mlph.setSjgsdwdm("");//数据归属单位代码  -最底到警务责任区**************>?
		mlph.setSjgsdwmc("");//数据归属单位名称**************>?
		
		
		//数据来自终端**************>?
		if ("1".equals(fromby)) {
			OrganizationUser user = organizationUserMapper.selectOrganizationUserByUserId(appuser_id);
			//String useridFormDevice=user.getAppuser_id();
			//String useridFromServer=SecurityUtils.getSessionUser().getUserId();
			mlph.setDjr(user.getAppuser_id());//登记人
			mlph.setDjdw(user.getOrgna_id());//登记单位
			
			mlph.setBlr_xm(user.getUser_name());//办理人_姓名-**************>?
			mlph.setBlsj(date);//办理时间**************>?
		}else{
			mlph.setDjr(SecurityUtils.getSessionUser().getUserId());//登记人
			mlph.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());// 登记时间
			mlph.setBlr_xm(SecurityUtils.getSessionUser().getName());//办理人_姓名
			mlph.setBlsj(date);//办理时间
		}
		mlph.setDjsj(date);//登记时间
		
		mlph.setXgr(null);//修改人
		mlph.setXgdw(null);//修改单位
		mlph.setGxsj(date);//更新时间
		mlph.setSqrid(sqrid);// 申请人ID
		
		mlph.setZxdhzb(zxdhzb);//中心点横坐标
		mlph.setZxdzzb(zxdzzb);//中心点纵坐标
		
		mlph.setDeltag("0");
		mlph.setDeluser(null);
		mlph.setDeltime(null);
		
		mlph.setSszdyjxzqy_dzbm(sszdyjxzqy_dzbm);//所属最低一级行政区划——地址代码
		
		mlph.setJwqbh(jwq.getJwqbh());//警务区编号（备选）-VARCHAR2(20)@####? 如何获取？代码还是外键
		mlph.setJwqmc(jwq.getJwqmc());//@####? 如何获取？代码还是外键
		mlph.setFromby(fromby);
		mlph.setMlphlxid(mlphlxid);//**************>?
		if(mlphlxmc==null||mlphlxmc.isEmpty()){
			Dictionary mlphlxmcDic=dictionaryMapper.selectDictByTypeAndCode("mlphlx", mlphlxid);
			if(mlphlxmcDic!=null){
				mlph.setMlphlxmc(mlphlxmcDic.getDict_mc());
			}
		}else{
			mlph.setMlphlxmc(mlphlxmc);
		}
		
		// jingwuqu 
		mlph.setDrsj(null);//导入时间//
		
		
		String jwqbh=jwq.getJwqbh();
		KAssert.hasText(jwqbh, "选取点所在警务区编号为空，请联系相关人员维护该警务区数据");
		if(jwqbh.length()<12){
			AlertSLEUtil.Error("选取点所在警务区编号不满足12位，格式不正确，请联系相关人员维护该警务区数据");	
		}
		/* 
		警务区编码为12位
			——第一层（1、2位）表示省、自治区、直辖市公安厅、局和新疆生产建设兵团公安局；
			——第二层（3、4位）表示省辖市、地区和省直辖行政单位，省直管县公安局（处）；
			——第三层（5、6位）表示设区市的区，地区和省辖市下辖的县、市公安局（分局）；
			——第四层（7、8、9位）表示公安派出所；
			——第五层（10、11、12位）表示警务责任区。
	*/
		String Org_code_pcs=jwqbh.substring(0,9)+"000";
		String Org_code_fj=jwqbh.substring(0,6)+"000000";
		String Org_code_sj=jwqbh.substring(0,4)+"00000000";
		mlph.setSspcs(Org_code_pcs);
		mlph.setSsfj(Org_code_fj);
		mlph.setSssj(Org_code_sj);
//------------------------------------------------------------------
		
		mlph.setSpzt("");//**************>?
		//mlph.setShpzdm("");//**************>?
		
		mlph.setIsIndexed("1");
		mlph.setCreateTime(date);
		//---------------------------
		mlph.setQrCode(null);
		mlph.setMacAddress(macAddress);
		mlph.setTfCardNum(tfCardNum);
		mlph.setImeiNum(imeiNum);
		mlph.setSimNum(simNum);
		if (gpsX!=null&&gpsY!=null) {
			mlph.setGpsx(gpsX);
			mlph.setGpsy(gpsY);
		}
//--------------------------------------------------------------------------
	if (isJZW) {
		Jzwjbxx jzwjbxx = new Jzwjbxx();
		
		jzwjbxx.setDzbm(jzwjbxxid);//地址编码
		
		jzwjbxx.setDzyslxdm("50");//建筑物类型代码-
		jzwjbxx.setDzmc(dzmc);//地址
		jzwjbxx.setJzwmc(jzwmc);//建筑物名称
		jzwjbxx.setBmjc(bmjc);// 别名简称
		
		jzwjbxx.setZxdhzb(zxdhzb);//中心点横坐标
		jzwjbxx.setZxdzzb(zxdzzb);//中心点纵坐标
		
		jzwjbxx.setZaglssjwzrqdm(jwq.getJwqbh());//警务责任区代码 @####?
		jzwjbxx.setZaglssjwzrqmc(jwq.getJwqmc());// @####?
		
		jzwjbxx.setSszdyjxzqy_dzbm(sszdyjxzqy_dzbm);//所属最低一级行政区域_地址编码
		jzwjbxx.setSsjlxxq_dzbm(ssjlxxq_dzbm);//所属街路巷（小区）_地址编码
		
		jzwjbxx.setDzzczbz(dzzczbz);//地(住)址存在标识
		jzwjbxx.setDzzzybs(dzzzybs);// 地(住)址在用标识
		
		jzwjbxx.setGxsj(null);//更新时间
		jzwjbxx.setQyrq(date);//启用日期
		jzwjbxx.setTyrq(null);//停用日期
		
		SessionUser suser=SecurityUtils.getSessionUser();
		String userOrgCode=suser.getOrganizationCode();
		String userOrgName=suser.getOrganizationName();
		
		jzwjbxx.setDjdw_gajgjgdm(userOrgCode);//登记单位_公安机关机构代码@####??
		jzwjbxx.setDjdw_gajgmc(userOrgName);//登记单位_公安机关机构名称@####?
		//数据来自终端@####?
		if ("1".equals(fromby)) {
			OrganizationUser user = organizationUserMapper.selectOrganizationUserByUserId(appuser_id);
			jzwjbxx.setDjr(user.getAppuser_id());//登记人
			jzwjbxx.setDjdw(user.getOrgna_id());//登记单位
			jzwjbxx.setDjr_xm(user.getUser_name());
		}else{
			jzwjbxx.setDjr(SecurityUtils.getSessionUser().getUserId());//登记人
			jzwjbxx.setDjr_xm(SecurityUtils.getSessionUser().getName());
			jzwjbxx.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());// 登记单位
		}
		jzwjbxx.setDjr(SecurityUtils.getUserId());//登记人**************>?
		jzwjbxx.setDjr_xm(SecurityUtils.getUserName()==null?SecurityUtils.getLoginname():SecurityUtils.getUserName());//登记人_姓名**************>?
		jzwjbxx.setDjsj(date);
		jzwjbxx.setDjdw(SecurityUtils.getUserOrgCode());//登记单位_公安机关机构代码**************>?
		jzwjbxx.setZjf(PinyinUtil.toSzm(jzwmc));//Pinyin4jUtil.convertToSpell(jzwmc)
		jzwjbxx.setXgr(null);//修改人
		jzwjbxx.setXgdw(null);//修改单位
		
		jzwjbxx.setEnable("1");
		jzwjbxx.setDeltime(null);
		
		jzwjbxx.setIsIndexed("1");
		jzwjbxx.setCreateTime(date);
		
		jzwjbxx.setQrCode(null);
		jzwjbxx.setMacAddress(macAddress);
		jzwjbxx.setTfCardNum(tfCardNum);
		jzwjbxx.setImeiNum(imeiNum);
		jzwjbxx.setSimNum(simNum);
		if (gpsX!=null&&gpsY!=null) {
			jzwjbxx.setGpsX(gpsX);
			jzwjbxx.setGpsY(gpsY);
		}
		jzwjbxxMapper.insertSelective(jzwjbxx);
		addJzwDocument(jzwjbxx);
	}
		Sqrxx sqrxx = new Sqrxx();
		sqrxx.setSqrid(sqrid);
		sqrxx.setSqrxm(sqrxm);
		sqrxx.setSqrlxdh(sqrlxdh);
		sqrxx.setSqdwmc(sqdwmc);
		sqrxx.setSqdwlxdh(sqdwlxdh);
		sqrxx.setSqrgmsfzhm(sqrgmsfzhm);
		
		sqrxxMapper.insertSelective(sqrxx);
		mlphMapper.insertSelective(mlph);
		 
		addMlphDocument(mlph);
		return jzwjbxxid;
	}


/*	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addJzwJbxx(Jzwjbxx jzwjbxx) {
		jzwjbxx.setDzbm(UUIDUtil.uuid());
		Date date = new Date();
		jzwjbxx.setGxsj(date);
		jzwjbxx.setDjsj(date);
		jzwjbxx.setDjr(SecurityUtils.getSessionUser().getUserId());//修改人
		jzwjbxx.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());//修改单位
		jzwjbxxMapper.insertSelective(jzwjbxx);
		
		addJzwDocument(jzwjbxx);
	}*/

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateJzwjbxx(Jzwjbxx jzwjbxx) {
		KAssert.notNull(jzwjbxx.getSszdyjxzqy_dzbm(), "所属最低一级行政区域不能为空");
		KAssert.notNull(jzwjbxx.getSsjlxxq_dzbm(), "所属街路巷（小区）不能为空");
		KAssert.notNull(jzwjbxx.getDzyslxdm(), "建筑物类型代码不能为空");
		KAssert.notNull(jzwjbxx.getSsjlxxq_dzbm(), "所属街路巷（小区）不能为空");
		KAssert.notNull(jzwjbxx.getJzwmc(), "建筑物名称不能为空");
		KAssert.notNull(jzwjbxx.getZaglssjwzrqmc(), "警务责任区名称不能为空");
		jzwjbxx.setXgr(SecurityUtils.getSessionUser().getUserId());
		jzwjbxx.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		jzwjbxx.setGxsj(new Date());
		String JwqId=jzwjbxx.getZaglssjwzrqdm();
		if(JwqId==null ||"".equals(JwqId)){
			throw new ServiceLogicalException("警务区信息不能为空");
		}
		Jwq jwq=jwqMapper.selectByPrimaryKey(JwqId);
		if(jwq==null||"".equals(jwq)){
			throw new ServiceLogicalException("警务区信息不能为空");
		}
		jzwjbxx.setZaglssjwzrqdm(jwq.getJwqbh());
		
		jzwjbxx.setZjf(PinyinUtil.toSzm(jzwjbxx.getJzwmc()));//助记符
		
		jzwjbxxMapper.updateByPrimaryKeySelective(jzwjbxx);
		//
		Jzwjbxx Jzwjbxx2=jzwjbxxMapper.selectByPrimaryKey(jzwjbxx.getDzbm());
		updateJzwDocument(Jzwjbxx2);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void cancelJzw(String[] ids, String flag) {
		KAssert.notNull(ids, "未选择要注销的数据");
		KAssert.notNull(flag, "未指定状态，请检查数据或联系管理员！");
//		String nowDate=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		Map<String, Object> map = new HashMap<String, Object>();
		Date datex=new Date();
		map.put("dzbm",ids);
		map.put("deltime",datex);
		map.put("enable", flag);
		map.put("gxsj", datex);
		map.put("xgr", SecurityUtils.getUserId());
		map.put("xgdw", SecurityUtils.getUserOrgId());
		jzwjbxxMapper.batchcancelJzw(map);	
		//#elastic#
		for(String id:ids){
			Map<String, Object> mapx = new HashMap<String, Object>();
			mapx.put("DZBM", id);
			mapx.put("DELTIME", datex);
			mapx.put("ENABLE", flag);
			map.put("GXSJ", datex);
			map.put("XGR", SecurityUtils.getUserId());
			map.put("XGDW", SecurityUtils.getUserOrgId());
			elasticService.updateDocument(ElasticTypes.JZWJBXX, id, ElasticUtil.jzwMapToDocument(mapx));
		}
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void activeJzw(String[] ids, String flag) {
		KAssert.notNull(ids, "未选择要启用的数据");
		KAssert.notNull(flag, "未指定状态，请检查数据或联系管理员！");
//		String nowDate=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		Map<String, Object> map = new HashMap<String, Object>();
		Date date=new Date();
		map.put("dzbm",ids);
		map.put("deltime",date);
		map.put("enable", flag);
		map.put("gxsj", date);
		map.put("xgr", SecurityUtils.getUserId());
		map.put("xgdw", SecurityUtils.getUserOrgId());
		jzwjbxxMapper.batchActiveJzw(map);
		//#elastic#
		for(String id:ids){
			Map<String, Object> mapx = new HashMap<String, Object>();
			mapx.put("DZBM", id);
			mapx.put("DELTIME", new Date());
			mapx.put("ENABLE", flag);
			map.put("GXSJ", date);
			map.put("XGR", SecurityUtils.getUserId());
			map.put("XGDW", SecurityUtils.getUserOrgId());
			elasticService.updateDocument(ElasticTypes.JZWJBXX, id, ElasticUtil.jzwMapToDocument(mapx));
		}
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateJzwLocation(String dzbm, GeoPoint point) {
		if(dzbm==null||dzbm.isEmpty()){
			AlertSLEUtil.Error("数据有误地址编码为空");
		}
		if(point==null||point.getLon()==0||point.getLat()==0){
			AlertSLEUtil.Error("经纬度有误");
		}
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
		map.put("DZBM", dzbm);
		map.put("XGR", SecurityUtils.getUserId());// 修改人
		map.put("GXSJ",new Date());//修改时间
		map.put("XGDW", SecurityUtils.getUserOrgCode());//修改单位
		map.put("ZAGLSSJWZRQDM",jwq.getJwqbh());//数据归属单位
		map.put("ZAGLSSJWZRQMC",jwq.getJwqmc());//数据归属名称
		map.put("ZXDHZB", new BigDecimal(point.getLon()).setScale(8,RoundingMode.CEILING));
		map.put("ZXDZZB", new BigDecimal(point.getLat()).setScale(8,RoundingMode.CEILING));
		jzwjbxxMapper.updateJzwLocation(map);
		elasticService.updateDocument(ElasticTypes.JZWJBXX, dzbm, ElasticUtil.jzwMapToDocument(map));	// 更新ELastic
		
		Mlph mlph=mlphMapper.selectMlphByJzwId(dzbm);
		if(mlph!=null){
			MlphService jwqService=SpringBeanFacUtil.getBean(MlphServiceImpl.class);
			jwqService.updateMlphLocationOnlySelf(mlph.getYwlsh(), point);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateJzwLocationOnlySelf(String dzbm, GeoPoint point) {
		if(dzbm==null||dzbm.isEmpty()){
			AlertSLEUtil.Error("数据有误地址编码为空");
		}
		if(point==null||point.getLon()==0||point.getLat()==0){
			AlertSLEUtil.Error("经纬度有误");
		}
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
		map.put("DZBM", dzbm);
		map.put("XGR", SecurityUtils.getUserId());// 修改人
		map.put("GXSJ",new Date());//修改时间
		map.put("XGDW", SecurityUtils.getUserOrgCode());//修改单位
		map.put("ZAGLSSJWZRQDM",jwq.getJwqbh());//数据归属单位
		map.put("ZAGLSSJWZRQMC",jwq.getJwqmc());//数据归属名称
		map.put("ZXDHZB", new BigDecimal(point.getLon()).setScale(8,RoundingMode.CEILING));
		map.put("ZXDZZB", new BigDecimal(point.getLat()).setScale(8,RoundingMode.CEILING));
		jzwjbxxMapper.updateJzwLocation(map);
		elasticService.updateDocument(ElasticTypes.JZWJBXX, dzbm, ElasticUtil.jzwMapToDocument(map));	// 更新ELastic
	}

	public DataSet loadJzwQrDataSet(Map<String,String> params,long num){
		String sszdyjxzqy_dzbm = params.get("sszdyjxzqy_dzbm");
		String qydm="";
		if(sszdyjxzqy_dzbm == null || sszdyjxzqy_dzbm.isEmpty()){
			String xzqh=SecurityUtils.getUserLevelStr();
			xzqh=xzqh+"00000000";
			if(xzqh.length()>6){ 
//				xzqh = xzqh.substring(0,6); 
				xzqh=SubApStrUtil.removeLastChars(xzqh, "0");
			}
			sszdyjxzqy_dzbm=xzqhMapper.selectDzbmByDm(xzqh);
			qydm = xzqh;
		}else{
			List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(sszdyjxzqy_dzbm);
			qydm=QydmData.getQydm(map);
//			qydm+="%";
			params.put("qydm", qydm);
		}
		params.put("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
			PaginationUtil.initElasticPageAndSort(params);
			PrefixQueryBuilder  prefixQueryBuilder =null; 
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
			String dzmc = params.get("DZMC");
			if(dzmc!=null&&!dzmc.isEmpty()){
//				boolQueryBuilder.must(matchQuery("DZMC", dzmc));
				boolQueryBuilder.must(matchPhraseQuery("DZMC", dzmc));
			}
			String jzwmc=params.get("JZWMC");
			if(jzwmc!=null && !jzwmc.isEmpty()){
				//boolQueryBuilder.must(matchQuery("JZWMC", jzwmc));
				boolQueryBuilder.must(matchPhraseQuery("JZWMC", jzwmc));
			}
			String ssjlxxq_dzbm=params.get("ssjlxxq_dzbm");
			if(ssjlxxq_dzbm!=null && ! ssjlxxq_dzbm.isEmpty()){
				boolQueryBuilder.must(termQuery("SSJLXXQ_DZBM",ssjlxxq_dzbm));
			}
			//模糊查询
			prefixQueryBuilder = QueryBuilders.prefixQuery("ZAGLSSJWZRQDM", qydm);
			boolQueryBuilder.must(prefixQueryBuilder);
			SearchResponse searchResponse = elasticService.getClient().prepareSearch("psam").setTypes("jzwjbxx")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(boolQueryBuilder).addSort("DZBM", SortOrder.ASC)
				.setFrom(NumberUtils.toInt(params.get("from"))).setSize((int)num).execute()
			.actionGet();
			return ElasticUtil.searchResponse2Dataset(searchResponse);
	}
//	1. 对第三方应用开放检索接口：
//	方案A:  tep1:建筑物检索；step2：根据建筑物检索单元;step3：根据单元检索楼层;step4：根据楼层检索房间
//	方案B:  tep1:建筑物检索；step2：根据建筑物直接提供房间。 
//	接口1：参数：String jzwmc或者jzwxz
//	  返回值《集合》 Lit<Map>：jzwid，jzwmc，jzwxz
//	接口2：参数：String jzwid
//	  返回值《集合》 Lit<Map>：
//	jzwfjid，jzwfjmc，jzwfjxh，
//	jzwdyid，jzwdyxh，jzwdymc，
//	jzwlcid，jzwlcxh，jzwlcmc，
	
	public List<Map<String,Object>> openApiFj(String jzwid){
		Jzwjg jzwjg=jzwjgMapper.selectByJzwId(jzwid);
		if(jzwjg==null){
			return Collections.emptyList();
		}
		List<Map<String,Object>> dataList=Lists.newArrayList();
		String jzwjgId=jzwjg.getJzwjgid();
		List<Jzwdy> jzwdyListx=jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgId);
		List<Jzwlc> jzwlcListx=jzwlcMapper.selectSortedJzwlcByJzwJgid(jzwjgId);
		List<Jzwfj> jzwfjListx=jzwfjMapper.selectSortedFjByJzwjgId(jzwjgId);
		for(Jzwfj jzwfj:jzwfjListx){
			Map<String,Object> map=Maps.newHashMap();
			map.put("jzwfjid", jzwfj.getJzwfjid());
			map.put("jzwfjmc", jzwfj.getFjmc());
			map.put("jzwfjxh", jzwfj.getFjxh());
			
			Jzwdy dy=JzwJgNewUtil.getDyByid(jzwfj.getJzwdyid(), jzwdyListx);
			if(dy!=null){
				map.put("jzwdyid", dy.getJzwdyid());
				map.put("jzwdyxh", dy.getDymc());
				map.put("jzwdymc", dy.getDyxh());
			}
			Jzwlc lc=JzwJgNewUtil.getLcByid(jzwfj.getJzwlcid(), jzwlcListx);
			if(lc!=null){
				map.put("jzwlcid", lc.getJzwlcid());
				map.put("jzwlcxh", lc.getLcmc());
				map.put("jzwlcmc", lc.getLcxh());
			}
			
		}
		return dataList;
	}

	@Override
	public long loadWorkJzwCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return jzwjbxxMapper.loadWorkJzwCount(map);
	}

	@Override
	public List<Map<String, Object>> loadTodayWorkDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return jzwjbxxMapper.loadTodayWorkDetail(map);
	}
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public List<Map<String,Object>> selectJzwForMlph(String mlph){
		if(mlph==null||mlph.isEmpty()){
			return null;
		}
		List<Map<String,Object>> list=jzwjbxxMapper.selectJzwForMlph(mlph);
		return list;
	}
}
