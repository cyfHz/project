//package com.kingmon.project.psam.jzw.xmlbak;
//
//import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
//import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
//import static org.elasticsearch.index.query.QueryBuilders.termQuery;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.Validate;
//import org.apache.commons.lang.math.NumberUtils;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.kingmon.base.common.KConstants;
//import com.kingmon.base.data.DataSet;
//import com.kingmon.base.service.BaseService;
//import com.kingmon.base.util.KAssert;
//import com.kingmon.base.util.PaginationUtil;
//import com.kingmon.base.util.UUIDUtil;
//import com.kingmon.base.util.date.ZDateStyle;
//import com.kingmon.base.util.date.ZDateUtil;
//import com.kingmon.common.authUtil.DataRuleUtil;
//import com.kingmon.common.authUtil.SecurityUtils;
//import com.kingmon.project.auth.organizationuser.mapper.OrganizationUserMapper;
//import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
//import com.kingmon.project.common.dictionary.mapper.DictionaryMapper;
//import com.kingmon.project.common.dictionary.model.Dictionary;
//import com.kingmon.project.elastic.model.GeoPoint;
//import com.kingmon.project.elastic.service.ElasticService;
//import com.kingmon.project.elastic.service.ElasticServiceRest;
//import com.kingmon.project.elastic.util.ElasticTypes;
//import com.kingmon.project.elastic.util.ElasticUtil;
//import com.kingmon.project.psam.jwq.mapper.JwqMapper;
//import com.kingmon.project.psam.jwq.model.Jwq;
//import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
//import com.kingmon.project.psam.jzw.model.Jzwjbxx;
//import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
//import com.kingmon.project.psam.jzw.view.AccqUserInfo;
//import com.kingmon.project.psam.mlph.dao.MlphMapper;
//import com.kingmon.project.psam.mlph.model.Mlph;
//import com.kingmon.project.psam.sqrxx.mapper.SqrxxMapper;
//import com.kingmon.project.psam.sqrxx.model.Sqrxx;
//import com.kingmon.project.util.Pinyin4jUtil;
////@Service
//public class JzwjbxxServiceImpl_1206x extends BaseService implements IJzwjbxxService{
//
//	@Autowired
//	private JzwjbxxMapper jzwjbxxMapper;
//	@Autowired
//	private MlphMapper mlphMapper;
//	@Autowired
//	private SqrxxMapper sqrxxMapper;
//	@Autowired
//	private OrganizationUserMapper organizationUserMapper;
//	@Autowired
//	private JwqMapper jwqMapper;
//	
//	@Autowired
//	private ElasticServiceRest elasticServiceRest;
//	
//	@Autowired
//	private ElasticService elasticService;
//	
//	@Autowired
//	private DictionaryMapper dictionaryMapper;
//	
//	@Value("${dev.data.process}")
//	private String devDataProcess;
//	
//	@Transactional(rollbackFor = Exception.class,readOnly=true)
//	@Override
//	public DataSet loadJzwjbxxDataSet(Map<String,String> params){
//		/**
//		JZWMC
//		jlxdzbm -->  SSJLXXQ_DZBM
//		DZMC
//		 */
//		if(KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)){
//			PaginationUtil.initElasticPageAndSort(params);
//			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
//			String dzmc = params.get("DZMC");
//			if(dzmc!=null&&!dzmc.isEmpty()){
//				boolQueryBuilder.must(matchQuery("DZMC", dzmc));
//			}
//			String jzwmc=params.get("JZWMC");
//			if(jzwmc!=null && !jzwmc.isEmpty()){
//				boolQueryBuilder.must(matchQuery("JZWMC", jzwmc));
//			}
//			String jlxdzbm=params.get("jlxdzbm");
//			if(jlxdzbm!=null && ! jlxdzbm.isEmpty()){
//				boolQueryBuilder.must(termQuery("SSJLXXQ_DZBM",jlxdzbm));
//			}
//			
//			SearchResponse searchResponse = elasticService.getClient().prepareSearch("psam").setTypes("jzwjbxx")
//				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(boolQueryBuilder).addSort("DZBM", SortOrder.ASC)
//				.setFrom(NumberUtils.toInt(params.get("from"))).setSize(NumberUtils.toInt(params.get("size"))).execute()
//			.actionGet();
//			return ElasticUtil.searchResponse2Dataset(searchResponse);
//		}else{
//			
//			PaginationUtil.initPageAndSort(params);
//			String jzwmc = params.get("jzwmc");
//			if(jzwmc!=null && !jzwmc.isEmpty()){
//				params.put("jzwmc", "%"+jzwmc+"%");
//			}else{
//				params.remove("jzwmc");
//			}
//			String dzmc=params.get("dzmc");
//			if(dzmc!=null && !dzmc.isEmpty()){
//				params.put("dzmc", "%"+dzmc+"%");
//			}else{
//				params.remove("dzmc");
//			}
//			String jlxdzbm=params.get("ssjlxxq_dzbm");
//			if(jlxdzbm!=null && !"".equals(jlxdzbm)){
//				params.put("ssjlxxq_dzbm",jlxdzbm);
//			}else{
//				params.remove("ssjlxxq_dzbm");
//			}
//			
//			String sszdyjxzqy_dzbm=params.get("sszdyjxzqy_dzbm");
//			if(sszdyjxzqy_dzbm!=null && !"".equals(sszdyjxzqy_dzbm)){
//				params.put("sszdyjxzqy_dzbm",sszdyjxzqy_dzbm);
//			}else{
//				params.remove("sszdyjxzqy_dzbm");
//			}
//			
//			String xzqh=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
//			if(xzqh.length()>6){ xzqh = xzqh.substring(0,6); }
//			params.put("xzjddm_data_auth",""+xzqh+"%");
//			
//			return new DataSet(jzwjbxxMapper.selectJzwjbxxCount(params),jzwjbxxMapper.selectJzwjbxxList(params));
//		}
//	}
//	
//	@SuppressWarnings("unused")
//	private void updateJzwDocument(Jzwjbxx jzwjbxx){
//		try {
//			elasticService.updateDocument(ElasticTypes.JZWJBXX, jzwjbxx.getDzbm(), ElasticUtil.toDocument(jzwjbxx));
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void addJzwDocument(Jzwjbxx jzwjbxx){
//		try {
//			elasticService.indexDocument(ElasticTypes.JZWJBXX, jzwjbxx.getDzbm(), ElasticUtil.toDocument(jzwjbxx));
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}
//	private void addMlphDocument(Mlph mlph){
//		try {
//			elasticService.indexDocument(ElasticTypes.JZWJBXX, mlph.getDzbm(), ElasticUtil.toDocument(mlph));
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Transactional(rollbackFor = Exception.class,readOnly=true)
//	@Override
//	public Jzwjbxx getJzwJbxxById(String dzbm) {
//		return jzwjbxxMapper.selectByPrimaryKey(dzbm);
//	}
//
//	@SuppressWarnings("rawtypes")
//	private void validate(Map map){
//		//申请人信息
//		String sqrxm = (String) map.get("sqrxm");
//		String sqrlxdh = (String) map.get("sqrlxdh");
//		String sqdwmc = (String) map.get("sqdwmc");
//		String sqdwlxdh = (String) map.get("sqdwlxdh");
//		String sqrgmsfzhm = (String) map.get("sqrgmsfzhm");
//
//		KAssert.hasText(sqrxm, "申请人姓名不能为空");
//		KAssert.hasText(sqrlxdh, "申请人联系电话不能为空");
//		KAssert.hasText(sqrgmsfzhm, "申请人公民身份证号码不能为空");
//		KAssert.hasText(sqdwmc, "申请单位名称不能为空");
//		KAssert.hasText(sqdwlxdh, "申请单位联系电话不能为空");
//		//门楼牌号                                                  
//		String ssxqdm = (String) map.get("ssxqdm");          
//		String qhnxxdz = (String) map.get("qhnxxdz");        
//		String mlph = (String) map.get("mlph");              
//		String mlphqz = (String) map.get("mlphqz");          
//		String lsmlpbs = (String) map.get("lsmlpbs");        
//		String fromby = (String) map.get("fromby");          
//		String mlphlxid = (String) map.get("mlphlxid");      
//		String mlphlxmc = (String) map.get("mlphlxmc");      
//		KAssert.hasText(ssxqdm, "省市县（区）不能为空");     
//		KAssert.hasText(qhnxxdz, "区划内详细地址不能为空");  
//		KAssert.hasText(mlph, "门楼牌号不能为空");           
//		KAssert.hasText(mlphqz, "门路牌号前缀不能为空");     
//		KAssert.hasText(lsmlpbs, "临时门楼牌号标识不能为空");
//		KAssert.hasText(fromby, "数据来源不能为空");         
//		KAssert.hasText(mlphlxid, "门楼牌号类型不能为空");   
//		KAssert.hasText(mlphlxmc, "门楼牌号类型名称不能为空");
//		//建筑物                                             	   
//		String dzyslxdm = (String) map.get("dzyslxdm");
//		String dzmc = (String) map.get("dzmc");
//		String jzwmc = (String) map.get("jzwmc");
//		String bmjc = (String) map.get("bmjc");
//		BigDecimal zxdzzb = (BigDecimal) map.get("zxdzzb");
//		BigDecimal zxdhzb = (BigDecimal) map.get("zxdhzb");
//		String sszdyjxzqy_dzbm = (String) map.get("sszdyjxzqy_dzbm");
//		String ssjlxxq_dzbm = (String) map.get("ssjlxxq_dzbm");
//		String dzzczbz = (String) map.get("dzzczbz");
//		String dzzzybs = (String) map.get("dzzzybs");
//		Boolean isJZW = (Boolean) map.get("isJZW");
//		String djr = (String) map.get("djr");
//	/*	String macAddress = (String) map.get("macAddress");
//		String tfCardNum = (String) map.get("tfCardNum");
//		String imeiNum = (String) map.get("imeiNum");
//		String simNum = (String) map.get("simNum");
//		BigDecimal gpsX = (BigDecimal) map.get("gpsX");
//		BigDecimal gpsY = (BigDecimal) map.get("gpsY");*/
//		
//		KAssert.hasText(dzyslxdm, "地址元素类型不能为空");     
//		KAssert.hasText(dzmc, "地址名称不能为空");  
//		KAssert.hasText(jzwmc, "建筑物名称不能为空");           
//		KAssert.hasText(bmjc, "别名简称不能为空");     
//		KAssert.notNull(zxdzzb, "中心点横坐标不能为空");
//		KAssert.notNull(zxdhzb, "中心点纵坐标不能为空");         
//		KAssert.hasText(sszdyjxzqy_dzbm, "所属最低一级行政区域不能为空");   
//		KAssert.hasText(ssjlxxq_dzbm, "所属街路巷（小区）不能为空");
//		KAssert.hasText(dzzczbz, "地(住)址存在标识不能为空");     
//		KAssert.hasText(dzzzybs, "地(住)址在用标识不能为空");  
//		KAssert.notNull(isJZW, "是否建筑物 不能为空");           
//		KAssert.hasText(djr, "登记人不能为空");     
//		/*KAssert.hasText(macAddress, "Mac地址不能为空");
//		KAssert.hasText(tfCardNum, "TF卡号不能为空");        
//		KAssert.hasText(imeiNum, "IMEI号不能为空");   
//		KAssert.hasText(simNum, "SIM卡号不能为空");
//		KAssert.hasText(gpsX, "位置信息不能为空");   
//		KAssert.hasText(gpsY, "位置信息不能为空");*/
//		
//	}
//	/**
//	 * @param view
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public String addInfoAcquisition(Map view) {
//		validate(view);
//		//申请人信息
//		String sqrid=UUIDUtil.uuid();
//		String sqrxm = (String) view.get("sqrxm");
//		String sqrlxdh = (String) view.get("sqrlxdh");
//		String sqdwmc = (String) view.get("sqdwmc");
//		String sqdwlxdh = (String) view.get("sqdwlxdh");
//		String sqrgmsfzhm = (String) view.get("sqrgmsfzhm");
//		//门楼牌号                                                  
//		String ssxqdm = (String) view.get("ssxqdm");          
//		String qhnxxdz = (String) view.get("qhnxxdz");        
//		String mlph_ = (String) view.get("mlph");    
//		String mlphdzmc = (String) view.get("mlphdzmc");    
//		String mlphqz = (String) view.get("mlphqz");          
//		String lsmlpbs = (String) view.get("lsmlpbs");        
//		String fromby = (String) view.get("fromby");          
//		String mlphlxid = (String) view.get("mlphlxid");      
//		String mlphlxmc = (String) view.get("mlphlxmc");   
//		//建筑物                                             	   
//		String jzwjbxxid=UUIDUtil.uuid();
//		String dzmc = (String) view.get("dzmc");
//		String jzwmc = (String) view.get("jzwmc");
//		String bmjc = (String) view.get("bmjc");
//		String dzzczbz = (String) view.get("dzzczbz");
//		String dzzzybs = (String) view.get("dzzzybs");
//		
//		//建筑物-门楼牌号公有
//		String dzyslxdm = (String) view.get("dzyslxdm");//地址元素类型代码
//		String sszdyjxzqy_dzbm = (String) view.get("sszdyjxzqy_dzbm");//所属最低一级行政区划编码
//		String ssjlxxq_dzbm = (String) view.get("ssjlxxq_dzbm");//所属街路巷小区编码
//		BigDecimal zxdzzb = (BigDecimal) view.get("zxdzzb");//中心点横坐标
//		BigDecimal zxdhzb = (BigDecimal) view.get("zxdhzb");//中心点纵坐标
//		String djr = (String) view.get("djr");//登记人
//		String macAddress = (String) view.get("macAddress");
//		String tfCardNum = (String) view.get("tfCardNum");
//		String imeiNum = (String) view.get("imeiNum");
//		String simNum = (String) view.get("simNum");
//		BigDecimal gpsX = (BigDecimal) view.get("gpsX");
//		BigDecimal gpsY = (BigDecimal) view.get("gpsY");
//		
//		Boolean isJZW = (Boolean) view.get("isJZW");
//		Date date = new Date();
//		
//		Mlph mlph = new Mlph();
//		mlph.setSsxqdm(ssxqdm);
//		mlph.setQhnxxdz(qhnxxdz);
//		mlph.setMlph(mlph_);
//		mlph.setLsmlpbs(lsmlpbs);
//		
//		mlph.setDzyslxdm(dzyslxdm);
//		mlph.setSszdyjxzqy_dzbm(sszdyjxzqy_dzbm);
//		mlph.setSsjlxxq_dzbm(ssjlxxq_dzbm);
//		mlph.setMacAddress(macAddress);
//		mlph.setTfCardNum(tfCardNum);
//		mlph.setImeiNum(imeiNum);
//		mlph.setSimNum(simNum);
//		if (gpsX!=null&&gpsY!=null) {
//			mlph.setGpsx(gpsX);
//			mlph.setGpsy(gpsY);
//		}
//		mlph.setYwlsh(jzwjbxxid);
//		mlph.setDzbm(jzwjbxxid);
//		mlph.setDeltag("0");
//		
//		OrganizationUser user = organizationUserMapper.selectOrganizationUserByUserId(djr);
//		//数据来自终端
//		if ("1".equals(fromby)) {
//			mlph.setDjr(user.getAppuser_id());//登记人
//			mlph.setXgr(user.getAppuser_id());
//			mlph.setDjdw(user.getOrgna_id());
//			mlph.setXgdw(user.getOrgna_id());
//		}else{
//			mlph.setDjr(SecurityUtils.getSessionUser().getUserId());//登记人
//			mlph.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());// 登记时间
//			mlph.setXgr(SecurityUtils.getSessionUser().getUserId());
//			mlph.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
//		}
//		mlph.setGxsj(date);//更新时间
//		mlph.setDjsj(date);//登记时间
//		mlph.setSqrid(sqrid);// 申请人ID
//		//当前用户
//		mlph.setBldw_gajgjgdm("");//办理单位_公安机关机构代码
//		mlph.setBldw_gajgmc("");//办理单位_公安机关机构代码-VARCHAR2(20)
//		mlph.setBlr_xm("");//
//		mlph.setBlsj(date);
//		
//		//--------------警务区--
//		mlph.setSjgsdwdm("");//数据归属单位代码  -最底到警务责任区
//		mlph.setSjgsdwmc("");//数据归属单位名称
//		
//		mlph.setJwqbh("");//警务区编号（备选）-VARCHAR2(20)
//		mlph.setJwqmc("");
//		mlph.setFromby(fromby);
//		mlph.setMlphlxid(mlphlxid);
//		mlph.setMlphlxmc(mlphlxmc);
//		
//		mlph.setIsIndexed("1");
//		mlph.setCreateTime(date);
//		
//		//用mlphdzmc  接收参数赋值给dzmc
//		mlph.setDzmc(mlphdzmc);
//		mlph.setZxdhzb(zxdhzb);//中心点横坐标
//		mlph.setZxdzzb(zxdzzb);//中心点纵坐标
//		
//		mlph.setSszdyjxzqy_dzbm(sszdyjxzqy_dzbm);
//		
//		Jzwjbxx jzwjbxx = new Jzwjbxx();
//		jzwjbxx.setDzmc(dzmc);
//		jzwjbxx.setBmjc(bmjc);
//		jzwjbxx.setDzzczbz(dzzczbz);
//		jzwjbxx.setDzzzybs(dzzzybs);
//		jzwjbxx.setZxdhzb(zxdhzb);
//		jzwjbxx.setZxdzzb(zxdzzb);
//		jzwjbxx.setDzyslxdm(dzyslxdm);
//		jzwjbxx.setSszdyjxzqy_dzbm(sszdyjxzqy_dzbm);
//		jzwjbxx.setSsjlxxq_dzbm(ssjlxxq_dzbm);
//		jzwjbxx.setMacAddress(macAddress);
//		jzwjbxx.setTfCardNum(tfCardNum);
//		jzwjbxx.setImeiNum(imeiNum);
//		jzwjbxx.setSimNum(simNum);
//		if (gpsX!=null&&gpsY!=null) {
//			jzwjbxx.setGpsX(gpsX);
//			jzwjbxx.setGpsY(gpsY);
//		}
//		//如果是建筑物
//		if (isJZW) {
//			mlph.setSsjzw_dzbm(jzwjbxxid);
//			mlph.setJzwmc(jzwmc);
//			
//			jzwjbxx.setDzbm(jzwjbxxid);
//			jzwjbxx.setJzwmc(jzwmc);
//			jzwjbxx.setGxsj(date);//更新时间
//			jzwjbxx.setDzyslxdm("50");
//			jzwjbxx.setEnable("1");//?
//			jzwjbxx.setZjf(Pinyin4jUtil.convertToSpell(jzwmc));	
//		
//			jzwjbxx.setIsIndexed("1");
//			jzwjbxx.setCreateTime(date);
//			
//			//---不确定
//			jzwjbxx.setZaglssjwzrqdm("");//警务责任区代码
//			jzwjbxx.setZaglssjwzrqmc("");
//			
//			jzwjbxx.setDjdw(user.getOrgna_id());
//			jzwjbxx.setDjdw_gajgjgdm(user.getOrgna_id());//登记单位_公安机关机构代码
//			jzwjbxx.setDjdw_gajgmc("");//登记单位_公安机关机构名称
//			
//			jzwjbxx.setDjr(user.getAppuser_id());
//			jzwjbxx.setDjr_xm(user.getUser_name());
//			jzwjbxx.setDjsj(date);
//			
//			jzwjbxxMapper.insertSelective(jzwjbxx);
//		}
//		Sqrxx sqrxx = new Sqrxx();
//		sqrxx.setSqrid(sqrid);
//		sqrxx.setSqrxm(sqrxm);
//		sqrxx.setSqrlxdh(sqrlxdh);
//		sqrxx.setSqdwmc(sqdwmc);
//		sqrxx.setSqdwlxdh(sqdwlxdh);
//		sqrxx.setSqrgmsfzhm(sqrgmsfzhm);
//		
//		sqrxxMapper.insertSelective(sqrxx);
//		mlphMapper.insertSelective(mlph);
//		 
//		addJzwDocument(jzwjbxx);
//		addMlphDocument(mlph);
//		return jzwjbxxid;
//	}
//	
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public String addInfoAcquisition(
//			Jzwjbxx jzwjbxx,
//			Mlph mlph,
//			Sqrxx sqrxx,
//			Boolean isJZW,
//			String fromby,
//			AccqUserInfo accUser) {
//		String sqrid=UUIDUtil.uuid();
//		
//		String jzwjbxxid=UUIDUtil.uuid();
//		
//		sqrxx.setSqrid(sqrid);// 申请人ID
//		Date date=new Date();
//		String mlphId=jzwjbxxid;
////		String mlphId=UUIDUtil.uuid();
//		mlph.setYwlsh(mlphId);
//		mlph.setDzbm(mlphId);
//		mlph.setDeltag("0");
//		//------------?待确认
//		
//		mlph.setDjr(SecurityUtils.getSessionUser().getUserId());//登记单位
//		mlph.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());// 登记时间
//		mlph.setDjsj(date);//登记人
//		
//		mlph.setXgr(SecurityUtils.getSessionUser().getUserId());
//		mlph.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
//		
//		mlph.setGxsj(date);//更新时间
//		
//		//----------?待确认
//		mlph.setSqrid(sqrid);// 申请人ID
//		
//		//当前用户
//		mlph.setBldw_gajgjgdm("");//办理单位_公安机关机构代码
//		mlph.setBldw_gajgmc("");//办理单位_公安机关机构代码-VARCHAR2(20)
//		mlph.setBlr_xm("");//
//		mlph.setBlsj(date);
//		
//		//--------------警务区--
//		mlph.setSjgsdwdm("");//数据归属单位代码  -最底到警务责任区
//		mlph.setSjgsdwmc("");//数据归属单位名称
//		
//		mlph.setJwqbh("");//警务区编号（备选）-VARCHAR2(20)
//		mlph.setJwqmc("");
//		// jignququ 
////		mlph.setDrsj();
////		mlph.setSspcs(sspcs);//所属派出所
////		mlph.setSsfj(ssfj);// 所属分局-
////		mlph.setSssj(sssj);//所属市局
//		
//		
//		mlph.setFromby(fromby);
//		//mlph.setMlphlxid();
//		Dictionary dic=dictionaryMapper.selectDictByTypeAndCode("mlphlx", mlph.getMlphlxid());
//		if(dic!=null){
//			mlph.setMlphlxmc(dic.getDict_mc());//门楼牌号类型名称
//		}
//		mlph.setIsIndexed("1");
//		mlph.setCreateTime(date);
//		
//		//用mlphdzmc  接收参数赋值给dzmc
//		mlph.setDzmc(mlph.getMlphdzmc());
//		
//		mlph.setZxdhzb(jzwjbxx.getZxdhzb());
//		mlph.setZxdzzb(jzwjbxx.getZxdzzb());
//		
//		mlph.setSszdyjxzqy_dzbm(jzwjbxx.getSszdyjxzqy_dzbm());
//		//mlph.setSsjlxxq_dzbm("");
//		if(isJZW){
//			mlph.setSsjzw_dzbm(jzwjbxxid);
//			mlph.setJzwmc(jzwjbxx.getJzwmc());
//			
//			//----------------------
//			jzwjbxx.setDzbm(jzwjbxxid);
//			jzwjbxx.setGxsj(date);//更新时间
//			jzwjbxx.setDzyslxdm("50");
//			jzwjbxx.setEnable("1");//?
//			jzwjbxx.setZjf(Pinyin4jUtil.convertToSpell(jzwjbxx.getJzwmc()));	
//		
//			jzwjbxx.setIsIndexed("1");
//			jzwjbxx.setCreateTime(date);
//			
////			jzwjbxx.setXgr(SecurityUtils.getSessionUser().getUserId());//修改人
////			jzwjbxx.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());//修改单位
//			//---不确定
//			jzwjbxx.setZaglssjwzrqdm("");//警务责任区代码
//			jzwjbxx.setZaglssjwzrqmc("");
//			
//			jzwjbxx.setDjdw(SecurityUtils.getUserOrgId());
//			jzwjbxx.setDjdw_gajgjgdm(SecurityUtils.getUserOrgId());//登记单位_公安机关机构代码
//			jzwjbxx.setDjdw_gajgmc(SecurityUtils.getUserOrgName());//登记单位_公安机关机构代码
//			
//			jzwjbxx.setDjr(SecurityUtils.getSessionUser().getUserId());
//			jzwjbxx.setDjr_xm(SecurityUtils.getSessionUser().getName());
//			jzwjbxx.setDjsj(date);
//			
//			jzwjbxxMapper.insertSelective(jzwjbxx);
//			
//		}
////		Jwq jwq=jwqMapper.selectByPrimaryKey(jwqid);
//		 sqrxxMapper.insertSelective(sqrxx);
//		 mlphMapper.insertSelective(mlph);
//		 
//		 addJzwDocument(jzwjbxx);
//		 addMlphDocument(mlph);
//		 return jzwjbxxid;
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public void addJzwJbxx(Jzwjbxx jzwjbxx) {
//		jzwjbxx.setDzbm(UUIDUtil.uuid());
//		Date date = new Date();
//		jzwjbxx.setGxsj(date);
//		jzwjbxx.setDjsj(date);
//		jzwjbxx.setDjr(SecurityUtils.getSessionUser().getUserId());//修改人
//		jzwjbxx.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());//修改单位
//		jzwjbxxMapper.insertSelective(jzwjbxx);
//		
//		addJzwDocument(jzwjbxx);
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public void updateJzwjbxx(Jzwjbxx jzwjbxx) {
//		jzwjbxx.setXgr(SecurityUtils.getSessionUser().getUserId());
//		jzwjbxx.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
//		jzwjbxx.setGxsj(new Date());
//		jzwjbxxMapper.updateByPrimaryKeySelective(jzwjbxx);
//		//
//	//	updateJzwDocument(jzwjbxx);
//	}
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public void Cancellation(String[] ids, String flag) {
//		KAssert.notNull(ids, "未选择要注销的数据");
//		KAssert.notNull(flag, "未指定状态，请检查数据或联系管理员！");
////		String nowDate=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("dzbm",ids);
//		map.put("deltime",new Date());
//		map.put("enable", flag);
//		jzwjbxxMapper.batchCancellation(map);	
//		//#elastic#
//		for(String id:ids){
//		  // elasticServiceRest.delete(id);
//		}
//	}
//
//	@Override
//	public void updateJzwLocation(String dzbm, GeoPoint pgis2Point) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
