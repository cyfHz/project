package com.kingmon.project.psam.sy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.DBTimeUtil;
import com.kingmon.base.util.IDCardUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.DataRuleUtil;
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
import com.kingmon.project.psam.sy.mapper.SyFwCzrkMapper;
import com.kingmon.project.psam.sy.mapper.SyFwJwryMapper;
import com.kingmon.project.psam.sy.mapper.SyFwLdrkMapper;
import com.kingmon.project.psam.sy.mapper.SyFwjbxxMapper;
import com.kingmon.project.psam.sy.mapper.SyRkglCzrkMapper;
import com.kingmon.project.psam.sy.mapper.SyRkglLdrkdjbMapper;
import com.kingmon.project.psam.sy.mapper.SyjwryMapper;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.service.ISyFwjbxxService;
import com.kingmon.project.psam.util.QydmData;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.psam.xzqh.model.Xzqh;
/**
 * 
* @ClassName :SYFwjbxxServiceImpl     
* @Description :   
* @createTime :2015年12月20日  上午9:38:10   
* @author ：zhaohuatai   
* @version :1.0
 */
@Service
public class SyFwjbxxServiceImpl extends BaseService implements ISyFwjbxxService {
	@Autowired
	private SyFwjbxxMapper fwjbxxMapper;

	@Autowired
	private JzwfjMapper jzwfjMapper;
	
	@Autowired
	private JzwdyMapper jzwdyMapper;

	
	@Autowired
	private JzwjgMapper jzwjgMapper;
	@Autowired
	private JzwjbxxMapper jzwjbxxMapper;
	@Autowired
	private XzqhMapper xzqhMapper;
	@Autowired
	private JlxMapper jlxMapper;
	
	@Autowired
	private JwqMapper jwqMapper;
	@Autowired
	private  OrganizationMapper organizationMapper;
	@Value("${dev.data.process}")
	private String devDataProcess;
	
	@Autowired
	private SyFwCzrkMapper syFwCzrkMapper;
	
	@Autowired
	private SyFwLdrkMapper syFwLdrkMapper;
	
	@Autowired
	private SyFwJwryMapper syFwJwryMapper;
	
	@Autowired
	private SyRkglCzrkMapper syRkglCzrkMapper;
	@Autowired
	private SyRkglLdrkdjbMapper syRkglLdrkdjbMapper;
	@Autowired
	private SyjwryMapper syjwryMapper;
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadSYFwjbxxDataSet(Map<String, String> params) {
//		if(KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)){
//			return DataSet.newDs();
//		}else{
	
			PaginationUtil.initPageAndSort(params);
			String fzxm = params.get("fzxm");
			if(fzxm!=null && !fzxm.isEmpty()){
				params.put("fzxm", "%"+fzxm+"%");
			}else{
				params.remove("fzxm");
			}
			String fzsfzhm=params.get("fzsfzhm");
			if(fzsfzhm!=null && !fzsfzhm.isEmpty()){
				params.put("fzsfzhm", "%"+fzsfzhm+"%");
			}else{
				params.remove("fzsfzhm");
			}
			String fwcqzh=params.get("fwcqzh");
			if(fwcqzh!=null && !fwcqzh.isEmpty()){
				params.put("fwcqzh", "%"+fwcqzh+"%");
			}else{
				params.remove("fwcqzh");
			}
			
			String ssjlxxq_dzbm=params.get("ssjlxxq_dzbm");
			if(ssjlxxq_dzbm!=null && !"".equals(ssjlxxq_dzbm)){
				params.put("ssjlxxq_dzbm",ssjlxxq_dzbm);
			}else{
				params.remove("ssjlxxq_dzbm");
			}
			
			String sszdyjxzqy_dzbm=params.get("sszdyjxzqy_dzbm");
			if(sszdyjxzqy_dzbm!=null && !"".equals(sszdyjxzqy_dzbm)){
//				params.put("sszdyjxzqy_dzbm",sszdyjxzqy_dzbm);
				List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(sszdyjxzqy_dzbm);
				String qydm=QydmData.getQydm(map);
				qydm+="%";
				params.put("qydm", qydm);
				params.remove("sszdyjxzqy_dzbm");
			}else{
				params.remove("sszdyjxzqy_dzbm");
			}
			
//			if(sszdyjxzqy_dzbm!=null && !"".equals(sszdyjxzqy_dzbm)){
//				params.remove("sszdyjxzqy_dzbm");
//				//params.put("sszdyjxzqy_dzbm",sszdyjxzqy_dzbm);
//				List<Map<String,String>> list=xzqhMapper.selectXzqyMapByDzbm(sszdyjxzqy_dzbm);
//				if(list!=null&&!list.isEmpty()){
//					String qudm=list.get(0).get("QUDM");
//					if(qudm!=null&&qudm.length()>6){
//						qudm=qudm.substring(0, 6);
//						params.put("sszdyjxzqy_dmxxx",qudm+"%");
//					}
//				}
//			}else{
//				params.remove("sszdyjxzqy_dzbm");
//				params.remove("sszdyjxzqy_dm");
//			}
			String sszdyjxzqy_type=params.get("sszdyjxzqy_type");
			if(sszdyjxzqy_type!=null && !"".equals(sszdyjxzqy_type)){
				params.put("sszdyjxzqy_type",sszdyjxzqy_type);
			}else{
				params.remove("sszdyjxzqy_type");
			}
			
			//String xzqh=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
			String xzqh=SecurityUtils.getUserLevelStr();//-zht-20160219
			if(xzqh.length()>6){ xzqh = xzqh.substring(0,6); }
			params.put("xzjddm_data_auth",""+xzqh+"%");
			
			return new DataSet(fwjbxxMapper.selectSYFwjbxxCount(params), fwjbxxMapper.selectSYFwjbxxList(params));
//		}
	}

	@Override
	public SyFwjbxx loadSYFwjbxxByFjbm(String fjbm) {
		if(!StringUtils.hasText(fjbm)){
			return null;
		}
		return fwjbxxMapper.selectYFwjbxxByFjbm(fjbm);
	}


	@Override
	public void updateFw(Map<String, Object> params) {
		params.put("xgr", SecurityUtils.getSessionUser().getUserId());
		params.put("xgrmc", SecurityUtils.getSessionUser().getLoginname());
		params.put("xgdw", SecurityUtils.getSessionUser().getOrganizationId());
		params.put("xgdwmc", SecurityUtils.getSessionUser().getOrganizationName());
		params.put("gxsj",new Date());
//		fwjbxxMapper.updateFw(params);
	
	}

	@Override
	public void addFw(Map<String, Object> params) {
		params.put("fwid", UUIDUtil.uuid());
		params.put("djr", SecurityUtils.getSessionUser().getUserId());
		params.put("djdw", SecurityUtils.getSessionUser().getOrganizationId());
		params.put("djrmc", SecurityUtils.getSessionUser().getLoginname());
		params.put("djdwmc", SecurityUtils.getSessionUser().getOrganizationName());
		params.put("djsj",new Date());
		//fwjbxxMapper.insertSelective(params);
	}

	@Override
	public SyFwjbxx getFwByfwid(String fwid) {
		SyFwjbxx fwjbxx = fwjbxxMapper.selectByPrimaryKey(fwid);
		return fwjbxx;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public SyFwjbxx loadFwjbxxAccInfo(String jzwfjid) {
		SyFwjbxx fwjbxx=fwjbxxMapper.selectYFwjbxxByFjbm(jzwfjid);
		if(fwjbxx==null){
			fwjbxx=new SyFwjbxx();
			 String uuid=UUIDUtil.uuid();
			 fwjbxx.setFwid(uuid);//房屋ID
			 fwjbxx.setFwbh(uuid);//房屋编号
		}
		//---------若没有数据，则初始化数据----------------------
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
		 
//			Mlph mlph=mlphMapper.selectMlphByJzwId(jzwjbxxid);
//			String ssxqdm=null;
//			if(mlph!=null){
//				ssxqdm=mlph.getSsxqdm();
//			}
		 //fwjbxx.setSsxqdm(jzwjbxx.getSszdyjxzqy_dzbm());//?//省市县（区）
		 List<Map<String,String>> xzqy=xzqhMapper.selectXzqyMapByDzbm(jzwjbxx.getSszdyjxzqy_dzbm());
		 if(xzqy==null||xzqy.size()<=0){
			 throw new ServiceLogicalException("建筑物所属行政区域代码为空，请联系相关人员维护该建筑物信息");
		 }
			 Map<String,String> map= xzqy.get(0);
			 fwjbxx.setSsxq_mc(map.get("MC"));
			 String qudm=map.get("QUDM");
			 if(qudm==null||qudm.length()<6){
				 throw new ServiceLogicalException("建筑物所属行政区域数据错误，请联系相关人员维护该建筑物信息");
			 }
			 if(qudm!=null&&qudm.length()>=6){
				 String xzqhdm=qudm.substring(0,6);
				 Xzqh xzqh=xzqhMapper.selectXzqhBydm(xzqhdm);
				fwjbxx.setSsxqdm(xzqh.getDzbm());//?//省市县（区）
				fwjbxx.setSsxq_mc(xzqh.getXzqhmc());
			 }
			
		 
		Jlx jlx=jlxMapper.selectByPrimaryKey(jzwjbxx.getSsjlxxq_dzbm());
			if(jlx==null){
				throw new ServiceLogicalException("建筑物所属街路巷小区数据错误，请联系相关人员维护该建筑物信息");
			}
			
		 fwjbxx.setSsjlxxq_dzbm(jlx.getDzbm()); //所属街路巷(小区)_地址编码
		 fwjbxx.setSsjlxxq_mc(jlx.getJlxxqmc());
		 
		 fwjbxx.setDzbm(jzwjbxxid);//地址编码
		 
		 fwjbxx.setFjbm(jzwfjid);//房间编码 
		 fwjbxx.setFjh(jzwfj.getFjxh());//房间号
		 
		Jzwdy jzwdy=jzwdyMapper.selectByPrimaryKey(jzwfj.getJzwdyid());
			String dymc="";
			if(jzwdy!=null){
				dymc=jzwdy.getDymc();
			}
			short dys=jzwjg.getDys();
			short lcs=jzwjg.getLcs();
			if(dys<=1||lcs<=1){//20160224-zht
				fwjbxx.setFwdz((""+jzwjbxx.getDzmc()));//房屋地址详细地址
			}else{
				fwjbxx.setFwdz((""+jzwjbxx.getDzmc())+dymc+jzwfj.getFjmc());//房屋地址详细地址
			}
		 String jwqdm=jzwjbxx.getZaglssjwzrqdm();
		 if(jwqdm==null||jwqdm.length()<12){
			 throw new ServiceLogicalException("建筑物所属警务区数据错误，请联系相关人员维护该建筑物信息");
		 }
		 Jwq jwq= jwqMapper.selectByJwqbh(jwqdm);
		 if(jwq==null){
			 throw new ServiceLogicalException("建筑物所属警务区数据为空，请联系相关人员维护该建筑物信息");
		 }
		 if(jwqdm!=null&&jwqdm.length()>=12){
			 fwjbxx.setJwzrq(jwqdm);
			 fwjbxx.setJwzrq_mc(jwq.getJwqmc());
			 
			
			 String pcsOrgCode=jwqdm.substring(0, 9)+"000";
			 Organization psc_org =organizationMapper.selectOrgByCode(pcsOrgCode);
			 if(psc_org==null){
				 throw new ServiceLogicalException("建筑物所属警务区-派出所数据为空，请联系相关人员维护该建筑物信息");
			 }
			 fwjbxx.setSdpcs(psc_org.getOrgna_code()); 
			 fwjbxx.setSdpcs_mc(psc_org.getOrgna_name());
		 }
		 
		return fwjbxx;
	}

	@Override
	public void saveFwjbxxAccInfo(SyFwjbxx syFwjbxx) {
		
		KAssert.hasText(syFwjbxx.getSsxqdm(), "所属省市区县不能为空");
		KAssert.hasText(syFwjbxx.getSsjlxxq_dzbm(), "所属街路巷(小区)不能为空");
		KAssert.hasText(syFwjbxx.getFjh(), "房间号不能为空");
		KAssert.hasText(syFwjbxx.getFjbm(), "房间编码不能为空");
		KAssert.hasText(syFwjbxx.getFwdz(), "房屋地址详细地址不能为空");
		
		KAssert.hasText(syFwjbxx.getFwlb(), "房屋类别不能为空");
		KAssert.hasText(syFwjbxx.getFwxz(), "房屋性质不能为空");
		KAssert.hasText(syFwjbxx.getFwyt(), "房屋用途不能为空");
		KAssert.hasText(syFwjbxx.getFwlx(), "房屋类型不能为空");
		
		KAssert.hasText(syFwjbxx.getHx(), "户型（*室*厅）不能为空");
//		KAssert.hasText(syFwjbxx.getFwjs(), "房屋间数不能为空");
//		KAssert.hasText(syFwjbxx.getFwmj(), "房屋面积(平米)不能为空");
		
		KAssert.hasText(syFwjbxx.getFwcqzh(), "房屋产权证号不能为空");
		KAssert.hasText(syFwjbxx.getFzxm(), "房主姓名不能为空");
		KAssert.hasText(syFwjbxx.getFzsfzhm(), "房主身份证号码不能为空");
//		KAssert.hasText(syFwjbxx.getFzlxdh(), "房主联系电话不能为空");
		KAssert.hasText(syFwjbxx.getSfczfw(), "是否出租房屋不能为空");
		//KAssert.hasText(syFwjbxx.getTgrxm(), "托管人姓名不能为空");
//		KAssert.hasText(syFwjbxx.getTgrxm(), "托管人姓名不能为空");
//		KAssert.hasText(syFwjbxx.getTgrsfzhm(), "托管人身份证号码不能为空");
//		KAssert.hasText(syFwjbxx.getTgrlxdh(), "托管人联系电话不能为空");
//		KAssert.hasText(syFwjbxx.getYfzgx(), "与房主关系不能为空");
		KAssert.hasText(syFwjbxx.getFwssdwbm(), "房屋所属单位编码不能为空");
//		KAssert.hasText(syFwjbxx.getFwssdwmc(), "房屋所属单位名称不能为空");
		
		KAssert.hasText(syFwjbxx.getSdpcs(), "属地派出所不能为空");
		KAssert.hasText(syFwjbxx.getJwzrq(), "警务责任区不能为空");
		
		
		syFwjbxx.setZxzt("0");//注销状态 (1已注销;0未注销)-VARCHAR2(2)
		syFwjbxx.setDeltag("0");//删除标记（0-未删除，1-已删除）
		syFwjbxx.setDjr(SecurityUtils.getUserId());
		syFwjbxx.setDjdw(SecurityUtils.getUserOrgId());
		syFwjbxx.setDjrmc(SecurityUtils.getLoginname());
		syFwjbxx.setDjdwmc(SecurityUtils.getUserOrgName());
		syFwjbxx.setDjsj(new Date());
		//-------------------------------------------
		//syFwjbxx.setSbid(sbid);//设备ID-VARCHAR2(50)
		syFwjbxx.setLrjs("0");//录入角色(民警0,社会积极力量1)-CHAR(1)
		//syFwjbxx.setLrfs("0");//录入方式(后台系统0,移动终端1))-CHAR(1)
		//syFwjbxx.setLrwl(lrwl);//录入网络(互联网E,公安网A)-CHAR(1)
		//------------------------
		//syFwjbxx.setQrCode(null);
//		syFwjbxx.setMacAddress(macAddress);
//		syFwjbxx.setTfCardNum(tfCardNum);
//		syFwjbxx.setImeiNum(imeiNum);
//		syFwjbxx.setSimNum(simNum);
//		syFwjbxx.setGpsX(gpsX);
//		syFwjbxx.setGpsY(gpsY);
		
		//syFwjbxx.setFwid(UUIDUtil.uuid());
		boolean isv=IDCardUtil.validateCard(syFwjbxx.getFzsfzhm());
		KAssert.isTrue(isv, "房主身份证号格式不正确");
		
		String fwid=syFwjbxx.getFwid();
		long count=fwjbxxMapper.selectCountByPrimaryKey(fwid);
		if(count>0){
			syFwjbxx.setXgr(SecurityUtils.getUserId());
			syFwjbxx.setXgdw(SecurityUtils.getUserOrgId());
			syFwjbxx.setXgrmc(SecurityUtils.getLoginname());
			syFwjbxx.setXgdwmc(SecurityUtils.getUserOrgName());
			syFwjbxx.setGxsj(new Date());
			fwjbxxMapper.updateByPrimaryKey(syFwjbxx);
		}else{
			fwjbxxMapper.insertSelective(syFwjbxx);
		}
		
		
	}

	@Override
	public void updateFwInfo(SyFwjbxx fw) {
		// TODO Auto-generated method stub
		fw.setXgr(SecurityUtils.getUserId());
		fw.setXgdw(SecurityUtils.getUserOrgId());
		fw.setXgdwmc(SecurityUtils.getUserOrgName());
		fw.setXgrmc(SecurityUtils.getLoginname());
		fw.setGxsj(new Date());
		fwjbxxMapper.updateByPrimaryKey(fw);
	}

	@Transactional(rollbackFor = Exception.class)
	public void revokeFwRyxx(Map<String, Object> params){
		
		String rylb=(String)params.get("rylb");
		if(rylb==null || rylb.isEmpty()){
			AlertSLEUtil.Error("人员类别有误,无法删除！");
		}
		params.put("deltag", 1);//删除标记
		params.put("deluser",SecurityUtils.getUserId());//删除人
		params.put("deltime", DBTimeUtil.getDBTime());//删除时间
		if(rylb.equals("czrk")){
			syFwCzrkMapper.revokeFwRyxx(params);
		}else if(rylb.equals("ldrk")){
			syFwLdrkMapper.revokeFwRyxx(params);
		}else if(rylb.equals("jwry")){
			syFwJwryMapper.revokeFwRyxx(params);
		}
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteFwRyxx(Map<String, Object> params){
		String rylb=(String)params.get("rylb");
		if(rylb==null || rylb.isEmpty()){
			new KJSONMSG(300,"人员类别有误,无法删除！");
		}
		
		params.put("deltag", 1);//删除标记
		params.put("deluser",SecurityUtils.getUserId());//删除人
		params.put("deltime", DBTimeUtil.getDBTime());//删除时间
		if(rylb.equals("czrk")){
			String sfzh=(String)params.get("sfzh");
			if(sfzh==null || sfzh.isEmpty()){
				new KJSONMSG(300,"身份证号为空！");
			}	
			SyRkglCzrk czrk=syRkglCzrkMapper.selectRkglCzrkById(sfzh);
			if(czrk==null){
				new KJSONMSG(300,"未查到该人员信息！");
			}
			params.put("rkid",czrk.getRkid());
			
			syFwCzrkMapper.revokeFwRyxx(params);
		}else if(rylb.equals("ldrk")){
			String sfzh=(String)params.get("sfzh");
			if(sfzh==null || sfzh.isEmpty()){
				new KJSONMSG(300,"身份证号为空！");
			}
			SyRkglLdrkdjb ldrk=syRkglLdrkdjbMapper.selectglldrkById(sfzh);
			if(ldrk==null){
				new KJSONMSG(300,"未查到该人员信息！");
			}
			params.put("rkid",ldrk.getLdid());
			
			syFwLdrkMapper.revokeFwRyxx(params);
		}else if(rylb.equals("jwry")){
			String sfzh=(String)params.get("sfzh");
			if(sfzh==null || sfzh.isEmpty()){
				new KJSONMSG(300,"身份证号为空！");
			}
			Syjwry jwry=syjwryMapper.selectsyJwrybyzjhm(sfzh);
			if(jwry==null){
				new KJSONMSG(300,"未查到该人员信息！");
			}
			params.put("rkid",jwry.getJwryid());
			
			syFwJwryMapper.revokeFwRyxx(params);
		}
	}
}
