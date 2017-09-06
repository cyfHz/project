package com.kingmon.project.psam.sy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64.Decoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragonsoft.commons.util.RkQueryUtil;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.DataRuleUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.psam.sy.mapper.SyjwryMapper;
import com.kingmon.project.psam.sy.mapper.SyjwryPicMapper;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjbPic;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.model.SyjwryPic;
import com.kingmon.project.psam.sy.service.ISyjwryPicService;
import com.kingmon.project.psam.sy.service.ISyjwryService;
/**
 * 
* @ClassName :SyFwJwryServiceImpl     
* @Description :   
* @createTime :2015年12月20日  上午9:38:14   
* @author ：zhaohuatai   
* @version :1.0
 */
@Service
public class SyjwryServiceImpl extends BaseService implements ISyjwryService {
	@Autowired
	private SyjwryMapper syjwryMapper;
	@Autowired
	private SyjwryPicMapper syjwryPicMapper;
	@Autowired
	private ISyjwryPicService  syjwryPicService;
	
	@Override
	public DataSet loadSyjwryDataSet(Map<String, String> params) {
		PaginationUtil.initPageAndSort(params);
		String ywm = params.get("ywm");
		if (ywm != null && !ywm.isEmpty()) {
			params.put("ywm", "%" + ywm + "%");
		}else{
			params.remove("ywm");
		}
		String zjhm = params.get("zjhm");
		if (zjhm != null && !zjhm.isEmpty()) {
			params.put("zjhm", "%" + zjhm + "%");
		}else{
			params.remove("zjhm");
		}
		//String dataAuth=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
		String dataAuth=SecurityUtils.getUserLevelStr();//-zht-20160219
		if(dataAuth==null ||dataAuth.isEmpty()){
			return DataSet.newDs();
		}
		if(dataAuth!=null && !dataAuth.isEmpty()){
			params.put("jwzrq",  dataAuth + "%");
		}
		
		return new DataSet(syjwryMapper.selectSyjwryCount(params), syjwryMapper.selectSyjwrylist(params));
	}

	@Override
	public Syjwry loadSyjwryDeatil(String jwryid) {
		if(jwryid==null || "".equals(jwryid)){
			throw new ServiceLogicalException("请选择要查看的境外人员信息");
		}
		Syjwry syjwry=syjwryMapper.selectByPrimaryKey(jwryid);
		if(syjwry==null){
			throw new ServiceLogicalException("实有境外人员信息不存在！");
		}
		return syjwry;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateSyJwryAccInfo(Syjwry jwry,byte[] imgFile) {
		// TODO Auto-generated method stub
		KAssert.hasText(jwry.getYwm(), "英文名不能为空");
		KAssert.hasText(jwry.getYwx(), "英文姓不能为空");
		KAssert.hasText(jwry.getZjzl(), "证件种类不能为空");
		KAssert.hasText(jwry.getZjhm(), "证件号码不能为空");
		KAssert.hasText(jwry.getFjh(), "房间号不能为空");
		Date date=new Date();
		SyjwryPic picInfo = syjwryPicService.selectpicInfoByZjhm(jwry.getZjhm());
		//处理照片
				if(imgFile==null||imgFile.length==0){
					if(picInfo==null){
						AlertSLEUtil.Error("请上传照片");
					}else{
//							do nothing
						picInfo.setRklx("3");
						picInfo.setRkid(jwry.getJwryid());
						syjwryPicMapper.updateByPrimaryKeySelective(picInfo);
					}
				}else{
					if(picInfo==null){
						SyjwryPic pInfo=new SyjwryPic();
						pInfo.setId(UUIDUtil.uuid());
						pInfo.setPic(imgFile);
						pInfo.setRkid(jwry.getJwryid());
						pInfo.setRkzjbh(jwry.getZjhm());
						pInfo.setCreateUser(SecurityUtils.getUserId());
						pInfo.setCreateTime(date);
						pInfo.setRklx("3");
						syjwryPicService.addPic(pInfo);
					}else{
						picInfo.setPic(imgFile);
						picInfo.setCreateUser(SecurityUtils.getUserId());
						picInfo.setCreateTime(date);
						syjwryPicMapper.updateByPrimaryKeySelective(picInfo);
					}
				}
			
		syjwryMapper.updateByPrimaryKeySelective(jwry);
		
	}

	@Override
	public DataSet loadSyJwryDataSet(String jzwfjid) {
		// TODO Auto-generated method stub
		if(jzwfjid==null||"".equals(jzwfjid)){
			throw new ServiceLogicalException("请选择建筑物房间");
		}
		ParamObject po =new ParamObject();
		StringBuilder sql = new StringBuilder("")
		.append(" SELECT ")
		.append(" jw.ZWM, ")
		.append(" jw.YWM, ")
		.append(" jw.ZJHM, ")
		.append(" jw.JWRYLXDH, ")
		.append(" jw.XXDZ, ")
		.append(" jw.FWFZXM, ")
		.append(" jw.FZSFZH, ")
		.append(" jw.CSRQ ");
		sql.append(" @from SY_SYJWRY jw ");
		sql.append(" where 1=1 ");
		if(!SubApStrUtil.isEmptyAfterTrimE(jzwfjid)){
			sql.append(" and jw.FJBM=:jzwfjid ");
			po.addSQLParam("jzwfjid", jzwfjid);
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	@Override
	public void cancelsyJwry(String id) {
		// TODO Auto-generated method stub
		  String flag="1";
		  KAssert.notNull(id, "请选择一条要注销的数据");
		  Date nowDate=new Date();
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("jwryid",id);
		   map.put("zxrq",nowDate);
		   map.put("zxzt", flag);
		   syjwryMapper.cancelsyJwry(map);
	}

	@Override
	public void activatesyJwry(String id) {
		// TODO Auto-generated method stub
		  String flag="0";
		  KAssert.notNull(id, "请选择一条要启用的数据");
		  Date nowDate=new Date();
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("jwryid",id);
		   map.put("zxrq",nowDate);
		   map.put("zxzt", flag);
		   syjwryMapper.activatesyJwry(map);
	}

	@Override
	public List<Syjwry> selectJwryInfoByJzwfjId(String jzwfjid) {
		// TODO Auto-generated method stub
		if(jzwfjid==null || "".equals(jzwfjid)){
			throw new ServiceLogicalException("请选择房间");
		}
		List<Syjwry>  jwrylist=syjwryMapper.selectSyJwryInfoByJzwfjId(jzwfjid);
		return jwrylist;
	}

	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public List<Syjwry> selectJwryInfoByJzwfjIdKey(String jzwfjid) {
		// TODO Auto-generated method stub
		if(jzwfjid==null || "".equals(jzwfjid)){
			throw new ServiceLogicalException("请选择房间");
		}
		List<Syjwry>  jwrylist=syjwryMapper.selectSyJwryInfoByJzwfjIdKey(jzwfjid);
		return jwrylist;
	}
	
	
	//2016-0811
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public List<Syjwry> selectJwryInfoFwByJzwfjIdKey(String jzwfjid) {
		// TODO Auto-generated method stub
		if(jzwfjid==null || "".equals(jzwfjid)){
			throw new ServiceLogicalException("请选择房间");
		}
		List<Syjwry>  jwrylist=syjwryMapper.selectSyJwryInfoFwByJzwfjIdKey(jzwfjid);
		return jwrylist;
	}
	
	
	@Override
	public Syjwry selectJwryInfoByZjbh(String sfzh) {
		// TODO Auto-generated method stub
		if(sfzh==null||"".equals(sfzh)){
			return null;
		}
		return syjwryMapper.selectsyJwrybyzjhm(sfzh);
	}
	@Override
	public Map<String,String>  queryRkInfo(String sfzh){
		if(sfzh==null||"".equals(sfzh)){
			return null;
		}
		// API ----查询身份证库;---查询超时处理
//		Map<String, String> strReturns = RkQueryUtil.queryCzrk(sfzh);
		Map<String,String> strReturns=RkQueryUtil.queryCzrk(sfzh);
//		Map<String,String> strReturns=new HashMap<String,String>();
//		strReturns.put("XM", "小马");
//		strReturns.put("CYM", "xiaoma");
//		strReturns.put("BYZK", "0");
//		strReturns.put("WHCD", "10");
//		strReturns.put("HYZK", "10");
//	    strReturns.put("XP","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");
		if (strReturns != null) {
			//校验数据，
			
			//保存照片，(如果照片不为空，保存照片)
		
			if(strReturns.get("CSRQ")!=null && strReturns.get("CSRQ").length()>=8){
				String csrq=strReturns.get("CSRQ");
				String year=csrq.substring(0, 4);
				String month=csrq.substring(4, 6);
				String day=csrq.substring(6, 8);
				String CSRQ=year+"-"+month+"-"+day;
				strReturns.put("CSRQ", CSRQ);
			}
			if(strReturns.get("XP")!=null && !("".equals(strReturns.get("XP")))){
				SyjwryPic picInfo=syjwryPicMapper.selectPicInfoByZjhm(sfzh);
				Decoder decoder=java.util.Base64.getDecoder();
				 Date date = new Date();
				if(picInfo==null){//照片表里没有该记录，添加一条新的记录
					 
					  byte[] pic = decoder.decode(strReturns.get("XP"));
					
					  SyjwryPic pInfo=new SyjwryPic();
					   String uid=UUIDUtil.uuid();
						pInfo.setId(uid);
						pInfo.setPic(pic);
						pInfo.setRklx("3");
						pInfo.setRkzjbh(sfzh);
						pInfo.setCreateUser(SecurityUtils.getUserId());
						pInfo.setCreateTime(date);
						syjwryPicService.addPic(pInfo);
					   
					
				}else {//照片表里面有记录但照片信息为空
					if(picInfo.getPic()==null||picInfo.getPic().length==0){
						byte[] pic = null;
					    pic = decoder.decode(strReturns.get("XP"));
						picInfo.setPic(pic);
						picInfo.setCreateUser(SecurityUtils.getUserId());
						picInfo.setCreateTime(date);
						syjwryPicMapper.updateByPrimaryKeySelective(picInfo);
					}
				}
			
			}
			return strReturns;
		}
		return null;
	}

	@Override
	public long loadTodayWorkCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return syjwryMapper.loadjwryWorkCount(map);
	}

	@Override
	public List<Map<String, Object>> loadTodayWorkDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return syjwryMapper.loadjwryWorkDetail(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public DataSet selectSyJwrkForFw(Map<String, String> map) {
		String jzwfjid=map.get("jzwfjid");
    	if(jzwfjid==null || "".equals(jzwfjid)){
			throw new ServiceLogicalException("请选择房间");
		}
    	PaginationUtil.initPageAndSort(map);
		DataSet ds = new DataSet(syjwryMapper.selectSyJwrkForFwCount(map), syjwryMapper.selectSyJwrkForFw(map));
		return ds;
	}

	@Override
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public DataSet selectSyJwrkForFwLsry(Map<String, String> map) {
		String jzwfjid=map.get("jzwfjid");
    	if(jzwfjid==null || "".equals(jzwfjid)){
			throw new ServiceLogicalException("请选择房间");
		}
    	PaginationUtil.initPageAndSort(map);
		DataSet ds = new DataSet(syjwryMapper.selectSyJwrkForFwLsryCount(map), syjwryMapper.selectSyJwrkForFwLsry(map));
		return ds;
	}

	

}
