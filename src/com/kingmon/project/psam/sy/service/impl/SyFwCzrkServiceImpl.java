package com.kingmon.project.psam.sy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.IDCardUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.SubSpringBeanUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.psam.jlx.mapper.JlxMapper;
import com.kingmon.project.psam.jlx.model.Jlx;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jzw.mapper.JzwdyMapper;
import com.kingmon.project.psam.jzw.mapper.JzwfjMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
import com.kingmon.project.psam.jzw.model.Jzwdy;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.jzw.model.Jzwjg;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.sy.mapper.SyFwCzrkMapper;
import com.kingmon.project.psam.sy.mapper.SyRkglCzrkMapper;
import com.kingmon.project.psam.sy.mapper.SyRkglCzrkPicMapper;
import com.kingmon.project.psam.sy.model.SyFwCzrk;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglCzrkPic;
import com.kingmon.project.psam.sy.service.ISyFwCzrkService;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkPicService;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.psam.xzqh.model.Xzqh;
/**
 *  常住人口房屋关系
* @ClassName :SyFwCzrkServiceImpl     
* @Description :   
* @createTime :2015年12月20日  上午9:37:23   
* @author ：zhaohuatai   
* @version :1.0
 */
@Service
public class SyFwCzrkServiceImpl extends BaseService implements ISyFwCzrkService {
	@Autowired
	private SyFwCzrkMapper syFwCzrkMapper;
	@Autowired
	private SyRkglCzrkMapper syRkglCzrkMapper;
	@Autowired
	private JzwjgMapper jzwjgMapper;
	@Autowired
	private JzwfjMapper jzwfjMapper;
	@Autowired
	private JzwjbxxMapper jzwjbxxMapper;
	@Autowired
	private XzqhMapper xzqhMapper;
	@Autowired
	private JlxMapper jlxMapper;
	@Autowired
	private JzwdyMapper jzwdyMapper;
	@Autowired
	private JwqMapper jwqMapper;
	@Autowired
	private SyRkglCzrkPicMapper syRkglPicMapper;
	@Autowired
	private  OrganizationMapper organizationMapper;
	
	@Autowired
	private  MlphMapper mlphMapper;
	@Autowired
	private ISyRkglCzrkPicService syRkglCzrkPicService;
	@Override
	public SyRkglCzrk loadFwCzrkAccInfo(String jzwfjid) {
		SyRkglCzrk czrk=new SyRkglCzrk();
		Jzwfj jzwfj=jzwfjMapper.selectByPrimaryKey(jzwfjid);
		if(jzwfj==null){
			throw new ServiceLogicalException("未查询到房间数据");
		}
		String jzwjgId=jzwfj.getJzwjgid();
		Jzwjg jzwjg=jzwjgMapper.selectByPrimaryKey(jzwjgId);
		if(jzwjg==null){
			throw new ServiceLogicalException("未查询到建筑物结构数据");
		}
		String jzwjbxxid=jzwjg.getJzwid();
		Jzwjbxx jzwjbxx=jzwjbxxMapper.selectByPrimaryKey(jzwjbxxid);
		if(jzwjbxx==null){
			throw new ServiceLogicalException("未查询到建筑物数据");
		}
		//暂时处理
		Mlph mlph=mlphMapper.selectMlphByJzwId(jzwjbxxid);
		KAssert.notNull(mlph, "根据建该房屋所属建筑物未查询到门楼牌号信息，请联系相关人员维护该建筑物信息");
		czrk.setMlph(mlph.getMlph());
		
//		czrk.setHid(uuid);//户ID
//		czrk.setRkbh(uuid);//人口编号
		czrk.setJzwfjid(jzwfjid);
		czrk.setJzwid(jzwjbxxid);
		czrk.setDzbm(jzwjbxx.getDzbm());//地址编码
		czrk.setZbx(jzwjbxx.getGpsX());//PGIS坐标X(经度)
		czrk.setZby(jzwjbxx.getGpsY());//PGIS坐标Y(纬度)
		
		 List<Map<String,String>> xzqy=xzqhMapper.selectXzqyMapByDzbm(jzwjbxx.getSszdyjxzqy_dzbm());
		 if(xzqy==null||xzqy.size()<=0){
			 throw new ServiceLogicalException("建筑物所属行政区域代码为空，请联系相关人员维护该建筑物信息");
		 }
		 Map<String,String> map=xzqy.get(0);
		 czrk.setSsxq_mc(map.get("MC"));
		 String qudm=map.get("QUDM");
		 if(qudm==null||qudm.length()<6){
			 throw new ServiceLogicalException("建筑物所属行政区域数据错误，请联系相关人员维护该建筑物信息");
		 }
		 if(qudm!=null&&qudm.length()>=6){
			 String xzqhdm=qudm.substring(0,6);
			 Xzqh xzqh=xzqhMapper.selectXzqhBydm(xzqhdm);
			 czrk.setSsxq(xzqh.getDzbm());//省市县（区）
			 czrk.setSsxq_mc(xzqh.getXzqhmc());
//			 czrk.setXzjd(xzqh.getXzqhdm());//乡镇街道
//			 czrk.setXzjd_mc(xzqh.getXzqhmc());
			
		 }
		 Jlx jlx=jlxMapper.selectByPrimaryKey(jzwjbxx.getSsjlxxq_dzbm());
			if(jlx==null){
				throw new ServiceLogicalException("建筑物所属街路巷小区数据错误，请联系相关人员维护该建筑物信息");
			}
			czrk.setJlxc(jlx.getDzbm());//所属街路巷(小区)
			czrk.setJlxc_mc(jlx.getJlxxqmc());
			
			Jzwdy jzwdy=jzwdyMapper.selectByPrimaryKey(jzwfj.getJzwdyid());
			String dymc="";
			if(jzwdy!=null){
				dymc=jzwdy.getDymc();
			}
			czrk.setMlxz((""+jzwjbxx.getDzmc())+dymc+jzwfj.getFjmc());//门楼详细地址
			 String jwqdm=jzwjbxx.getZaglssjwzrqdm();
			 if(jwqdm==null||jwqdm.length()<12){
				 throw new ServiceLogicalException("建筑物所属警务区数据错误，请联系相关人员维护该建筑物信息");
			 }
			 Jwq jwq= jwqMapper.selectByJwqbh(jwqdm);
			 if(jwq==null){
				 throw new ServiceLogicalException("建筑物所属警务区数据为空，请联系相关人员维护该建筑物信息");
			 }
			 if(jwqdm!=null&&jwqdm.length()>=12){
				 czrk.setJwzrqdm(jwqdm);
				 czrk.setJwzrq_mc(jwq.getJwqmc());
				
				 String pcsOrgCode=jwqdm.substring(0, 9)+"000";
				 Organization psc_org =organizationMapper.selectOrgByCode(pcsOrgCode);
				 if(psc_org==null){
					 throw new ServiceLogicalException("建筑物所属警务区-派出所数据为空，请联系相关人员维护该建筑物信息");
				 }
				 czrk.setSspcs(psc_org.getOrgna_code());
				 czrk.setPcs_mc(psc_org.getOrgna_name());
			 }
			 
		return czrk;
	}
   @Transactional(rollbackFor = Exception.class)
	@Override
	public void savefwCzrkAccInfo(SyRkglCzrk czrk,byte[] imgFile) {
	   
		KAssert.hasText(czrk.getXm(), "姓名不能为空");
		KAssert.hasText(czrk.getGmsfhm(), "身份证号不能为空");
		if(!IDCardUtil.validateCard(czrk.getGmsfhm())){
			AlertSLEUtil.Error("身份证号有误，请重新输入");
		}
		KAssert.hasText(czrk.getSsxq(), "所属省市区县不能为空");
		KAssert.hasText(czrk.getJlxc(), "所属街路巷(小区)不能为空");
//		KAssert.hasText(czrk.getXzjd(), "乡镇街道不能为空");
		KAssert.hasText(czrk.getMlxz(), "门楼详址不能为空");
		KAssert.hasText(czrk.getSspcs(), "派出所信息不能为空");
		KAssert.hasText(czrk.getJwzrqdm(), "建筑物责任区代码不能为空");
		KAssert.hasText(czrk.getJzwfjid(), "房间号不能为空");
		KAssert.hasText(czrk.getLrfs(), "录入方式不能为空");
		KAssert.hasText(czrk.getCsrq(), "出生日期不能为空");
		KAssert.hasText(czrk.getDzbm(), "地址编码不能为空");
		SyFwCzrk fwczrk=syFwCzrkMapper.selectFwCzrkByfjbhAndZjhm(czrk.getGmsfhm(), czrk.getJzwfjid());
		if(fwczrk!=null){
			throw new ServiceLogicalException("当前房间对应该常住人口信息， 信息已经采集完成！");
		}
		String sfCsrq=IDCardUtil.getBirthByIdCard(czrk.getGmsfhm());
		String csrq=czrk.getCsrq();
		String[] strarray=csrq.split("-"); 
		String strCsrq = "";
		  for (int i = 0; i < strarray.length; i++) {
			  strCsrq=strCsrq+strarray[i];
		  }
		  if(!strCsrq.equals(sfCsrq)){
			  throw new ServiceLogicalException("出生日期与身份证日期不一致，请检查!");
		  }
	
		Date date=new Date();
		//查询
		SyRkglCzrk czrkfromDb=syRkglCzrkMapper.selectRkglCzrkById(czrk.getGmsfhm());
		
		SyRkglCzrkPic picInfo=syRkglPicMapper.selectNotNullPicInfoByZjhm(czrk.getGmsfhm());
		
		if(czrkfromDb==null){
		    String uuID=UUIDUtil.uuid();
			czrk.setRkid(uuID);
			czrk.setHid(uuID);
			czrk.setRkbh(uuID);
			czrk.setZxzt("0");//注销状态 (1已注销;0未注销)-VARCHAR2(2)
			czrk.setDeltag("0");//删除标记（0-未删除，1-已删除）
			czrk.setDjr(SecurityUtils.getUserId());
			czrk.setDjdw(SecurityUtils.getUserOrgId());
			czrk.setDjrmc(SecurityUtils.getLoginname());
			czrk.setDjdwmc(SecurityUtils.getUserOrgName());
			czrk.setDjsj(date);
			czrk.setLrfs(czrk.getLrfs());
			//czrk.setDzbm(dzbm);
			SyFwCzrk fwCzrk=new SyFwCzrk();
			fwCzrk.setId(UUIDUtil.uuid());
			fwCzrk.setRyid(czrk.getRkid());
			fwCzrk.setFjbh(czrk.getJzwfjid());
			fwCzrk.setDjr(SecurityUtils.getUserId());
			fwCzrk.setDjdw(SecurityUtils.getUserOrgId());
			fwCzrk.setDjrmc(SecurityUtils.getLoginname());
			fwCzrk.setDjdwmc(SecurityUtils.getUserOrgName());
			fwCzrk.setDjsj(date);
			fwCzrk.setLrfs(czrk.getLrfs());//录入方式(后台系统0,移动终端1)-CHAR(1)
			fwCzrk.setDeltag("0");// 删除标记（0-未删除，1-已删除）-CHAR(1)
			fwCzrk.setJzwid(czrk.getJzwid());
			fwCzrk.setZjbh(czrk.getGmsfhm());
			
			fwCzrk.setMacAddress(czrk.getMacAddress());
			fwCzrk.setTf(czrk.getTfCardNum());
			fwCzrk.setImei(czrk.getImeiNum());
			fwCzrk.setSim(czrk.getSimNum());
			
			syFwCzrkMapper.insertSelective(fwCzrk);
			syRkglCzrkMapper.insertSelective(czrk);
		}else{//常住人口再次采集，数据库中已经有该人员数据，
			
			//常住人口管理表存储
		 	SubSpringBeanUtil.copy(czrk, czrkfromDb, true);
		    czrkfromDb.setXgr(SecurityUtils.getUserId());
		    czrkfromDb.setXgdw(SecurityUtils.getUserOrgId());
		    czrkfromDb.setXgrmc(SecurityUtils.getLoginname());
		    czrkfromDb.setXgdwmc(SecurityUtils.getUserOrgName());
		    czrkfromDb.setGxsj(date);
		    
			  //常住人采集中间表
		    	SyFwCzrk fwCzrk=new SyFwCzrk();
				fwCzrk.setId(UUIDUtil.uuid());
				fwCzrk.setRyid(czrkfromDb.getRkid());
				fwCzrk.setFjbh(czrk.getJzwfjid());
				fwCzrk.setDjr(SecurityUtils.getUserId());
				fwCzrk.setDjdw(SecurityUtils.getUserOrgId());
				fwCzrk.setDjrmc(SecurityUtils.getLoginname());
				fwCzrk.setDjdwmc(SecurityUtils.getUserOrgName());
				fwCzrk.setDjsj(date);
				fwCzrk.setLrfs(czrk.getLrfs());//录入方式(后台系统0,移动终端1)-CHAR(1)
				fwCzrk.setDeltag("0");// 删除标记（0-未删除，1-已删除）-CHAR(1)
				fwCzrk.setJzwid(czrk.getJzwid());
				fwCzrk.setZjbh(czrk.getGmsfhm());
				
//				fwCzrk.setMacAddress(czrk.getMacAddress());
				fwCzrk.setTf(czrk.getTfCardNum());
				fwCzrk.setImei(czrk.getImeiNum());
				fwCzrk.setSim(czrk.getSimNum());
				
				syFwCzrkMapper.insertSelective(fwCzrk);
				syRkglCzrkMapper.updateByPrimaryKeySelective(czrkfromDb);
				
		}
		//处理照片
		if(imgFile==null||imgFile.length==0){
			if(picInfo==null){
				//edit by masheng
			//	AlertSLEUtil.Error("请上传照片");
			}else{
//					do nothing
				picInfo.setRklx("1");
				picInfo.setRkid(czrk.getRkid());
				syRkglPicMapper.updateByPrimaryKeySelective(picInfo);
			}
		}else{
			if(picInfo==null){
				SyRkglCzrkPic pInfo=new SyRkglCzrkPic();
				pInfo.setId(UUIDUtil.uuid());
				pInfo.setPic(imgFile);
				pInfo.setRkid(czrk.getRkid());
				pInfo.setRkzjbh(czrk.getGmsfhm());
				pInfo.setCreateUser(SecurityUtils.getUserId());
				pInfo.setCreateTime(new Date());
				pInfo.setRklx("1");
				syRkglCzrkPicService.addPic(pInfo);
			}else{
				picInfo.setPic(imgFile);
				picInfo.setCreateUser(SecurityUtils.getUserId());
				picInfo.setCreateTime(date);
				picInfo.setRkid(czrk.getRkid());
				syRkglPicMapper.updateByPrimaryKeySelective(picInfo);
			}
	
		}
		
}



	@Override
	public List<String> loadCzrkFjByJzwId(String jzwId) {
		if(StringUtils.hasText(jzwId)){
			return syFwCzrkMapper.selectCzrkFjByJzwId(jzwId);
		}
		return null;
	}




	@Override
	public SyFwCzrk validateCzrk(String sfzh, String jzwfjid) {
		// TODO Auto-generated method stub
		if(sfzh==null||"".equals(sfzh)){
			return null;
		}
		if(jzwfjid==null||"".equals(jzwfjid)){
			return null;
		}
		SyFwCzrk czrk=syFwCzrkMapper.selectFwCzrkByfjbhAndZjhm(sfzh, jzwfjid);
		return czrk;
		
	}

	public KJSONMSG loadFwCzrkAccInfoApp(String jzwfjid) {
		SyRkglCzrk czrk=new SyRkglCzrk();
		KJSONMSG obj = new KJSONMSG();
		Jzwfj jzwfj=jzwfjMapper.selectByPrimaryKey(jzwfjid);
		if(jzwfj==null){
			obj = new KJSONMSG(300,"未查询到房间数据");
			return obj;
		}
		String jzwjgId=jzwfj.getJzwjgid();
		Jzwjg jzwjg=jzwjgMapper.selectByPrimaryKey(jzwjgId);
		if(jzwjg==null){
			obj = new KJSONMSG(300,"未查询到建筑物结构数据");
			return obj;
		}
		String jzwjbxxid=jzwjg.getJzwid();
		Jzwjbxx jzwjbxx=jzwjbxxMapper.selectByPrimaryKey(jzwjbxxid);
		if(jzwjbxx==null){
			obj = new KJSONMSG(300,"未查询到建筑物数据");
			return obj;
		}
		//暂时处理
		Mlph mlph=mlphMapper.selectMlphByJzwId(jzwjbxxid);
		if(mlph==null){
			obj = new KJSONMSG(300,"根据建该房屋所属建筑物未查询到门楼牌号信息，请联系相关人员维护该建筑物信息");
			return obj;
		}
		czrk.setMlph(mlph.getMlph());
		
//		czrk.setHid(uuid);//户ID
//		czrk.setRkbh(uuid);//人口编号
		czrk.setJzwfjid(jzwfjid);
		czrk.setJzwid(jzwjbxxid);
		czrk.setDzbm(jzwjbxx.getDzbm());//地址编码
		czrk.setZbx(jzwjbxx.getGpsX());//PGIS坐标X(经度)
		czrk.setZby(jzwjbxx.getGpsY());//PGIS坐标Y(纬度)
		System.out.println(jzwjbxx.getDzbm());
		 List<Map<String,String>> xzqy=xzqhMapper.selectXzqyMapByDzbm(jzwjbxx.getSszdyjxzqy_dzbm());
		 if(xzqy==null||xzqy.size()<=0){
			 obj = new KJSONMSG(300,"建筑物所属行政区域代码为空，请联系相关人员维护该建筑物信息");
			 return obj;
		 }
		 Map<String,String> map1=xzqy.get(0);
		 czrk.setSsxq_mc(map1.get("MC"));
		 String qudm=map1.get("QUDM");
		 if(qudm==null||qudm.length()<6){
			 obj = new KJSONMSG(300,"建筑物所属行政区域数据错误，请联系相关人员维护该建筑物信息");
			 return obj;
		 }
		 if(qudm!=null&&qudm.length()>=6){
			 String xzqhdm=qudm.substring(0,6);
			 Xzqh xzqh=xzqhMapper.selectXzqhBydm(xzqhdm);
			 czrk.setSsxq(xzqh.getDzbm());//省市县（区）
			 czrk.setSsxq_mc(xzqh.getXzqhmc());
//			 czrk.setXzjd(xzqh.getXzqhdm());//乡镇街道
//			 czrk.setXzjd_mc(xzqh.getXzqhmc());
			
		 }
		 Jlx jlx=jlxMapper.selectByPrimaryKey(jzwjbxx.getSsjlxxq_dzbm());
			if(jlx==null){
				obj = new KJSONMSG(300,"建筑物所属街路巷小区数据错误，请联系相关人员维护该建筑物信息");
				return obj;
			}
			czrk.setJlxc(jlx.getDzbm());//所属街路巷(小区)
			czrk.setJlxc_mc(jlx.getJlxxqmc());
			
			Jzwdy jzwdy=jzwdyMapper.selectByPrimaryKey(jzwfj.getJzwdyid());
			String dymc="";
			if(jzwdy!=null){
				dymc=jzwdy.getDymc();
			}
			czrk.setMlxz((""+jzwjbxx.getDzmc())+dymc+jzwfj.getFjmc());//门楼详细地址
			 String jwqdm=jzwjbxx.getZaglssjwzrqdm();
			 if(jwqdm==null||jwqdm.length()<12){
				 obj = new KJSONMSG(300,"建筑物所属警务区数据错误，请联系相关人员维护该建筑物信息");
				 return obj;
			 }
			 Jwq jwq= jwqMapper.selectByJwqbh(jwqdm);
			 if(jwq==null){
				 obj = new KJSONMSG(300,"建筑物所属警务区数据为空，请联系相关人员维护该建筑物信息");
				 return obj;
			 }
			 if(jwqdm!=null&&jwqdm.length()>=12){
				 czrk.setJwzrqdm(jwqdm);
				 czrk.setJwzrq_mc(jwq.getJwqmc());
				
				 String pcsOrgCode=jwqdm.substring(0, 9)+"000";
				 Organization psc_org =organizationMapper.selectOrgByCode(pcsOrgCode);
				 if(psc_org==null){
					 obj = new KJSONMSG(300,"建筑物所属警务区-派出所数据为空，请联系相关人员维护该建筑物信息");
					 return obj;
				 }
				 czrk.setSspcs(psc_org.getOrgna_code());
				 czrk.setPcs_mc(psc_org.getOrgna_name());
			 }
			 Map<String,String> map=new HashMap<String,String>();
			 map.put("ssxq", czrk.getSsxq()==null ?"":czrk.getSsxq());
			 map.put("ssxq_mc", czrk.getSsxq_mc()==null ?"":czrk.getSsxq_mc());
			 map.put("jlxc", czrk.getJlxc()==null ?"":czrk.getJlxc());
			 map.put("jlxc_mc", czrk.getJlxc_mc()==null ?"":czrk.getJlxc_mc());
			 map.put("mlph", czrk.getMlph()==null ?"":czrk.getMlph());
			 map.put("mlxz", czrk.getMlxz()==null ?"":czrk.getMlxz());
			 map.put("pcs_mc", czrk.getPcs_mc()==null ?"":czrk.getPcs_mc());
			 map.put("sspcs", czrk.getSspcs()==null ?"":czrk.getSspcs());
			 map.put("mlph", czrk.getMlph()==null ?"":czrk.getMlph());
			 map.put("jwzrqdm", czrk.getJwzrqdm()==null ?"":czrk.getJwzrqdm());
			 map.put("jwzrq_mc", czrk.getJwzrq_mc()==null ?"":czrk.getJwzrq_mc());
			 map.put("dzbm", czrk.getDzbm()==null ?"":czrk.getDzbm());
			 obj = new KJSONMSG(200,"数据加载成功", map);
		return obj;
	}

	@Override
	public long loadTodayworkCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		KAssert.hasText(map, "参数不能为空");
		return syRkglCzrkMapper.selectSyCzrkWorkCount(map);
	}
	@Override
	public List<Map<String, Object>> loadTodayWorkDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		return syRkglCzrkMapper.selectSyCzrkWorkDetail(map);
	}
	@Override
	public long loadDayCZRKWorkCount(Map<String, Object> workInfo) {
		// TODO Auto-generated method stub
		KAssert.hasText(workInfo, "参数错误");
		KAssert.hasText(workInfo, "参数错误");
		return syFwCzrkMapper.loadCZRKCount(workInfo);
	}
	@Override
	public List<Map<String, Object>> loadFwCzrkList(Map<String, Object> workInfo) {
		// TODO Auto-generated method stub
		KAssert.hasText(workInfo, "参数错误");
		return syFwCzrkMapper.loadFwCzrkList(workInfo);
	}

}
