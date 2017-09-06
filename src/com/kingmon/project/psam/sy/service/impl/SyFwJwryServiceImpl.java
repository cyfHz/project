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
import com.kingmon.project.psam.sy.mapper.SyFwJwryMapper;
import com.kingmon.project.psam.sy.mapper.SyFwjbxxMapper;
import com.kingmon.project.psam.sy.mapper.SyjwryMapper;
import com.kingmon.project.psam.sy.mapper.SyjwryPicMapper;
import com.kingmon.project.psam.sy.model.SyFwJwry;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.model.SyjwryPic;
import com.kingmon.project.psam.sy.service.ISyFwJwryService;
import com.kingmon.project.psam.sy.service.ISyjwryPicService;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.psam.xzqh.model.Xzqh;
/**
 * 
* @ClassName :SyFwJwryServiceImpl     
* @Description :   
* @createTime :2015年12月20日  上午9:38:14   
* @author ：zhaohuatai   
* @version :1.0
 */
@Service
public class SyFwJwryServiceImpl extends BaseService implements ISyFwJwryService {
	@Autowired
	private SyFwJwryMapper syFwJwryMapper;
	@Autowired
	private SyjwryMapper syjwryMapper;
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
	private  OrganizationMapper organizationMapper;
	@Autowired
	private SyjwryPicMapper syjwryPicMapper;
	@Autowired
	private ISyjwryPicService  syjwryPicService;
	@Autowired
	private SyFwjbxxMapper  fwjbxxmapper;
	
	@Override
	public Syjwry loadSyjwryAccInfo(String jzwfjid) {
		// TODO Auto-generated method stub
//		Syjwry jwry=syjwryMapper.selectSyjwryByjzwfjId(jzwfjid);
//		if(jwry==null){
//			jwry=new Syjwry();
//		}
		Syjwry jwry=new Syjwry();
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
		SyFwjbxx fwjbxx=fwjbxxmapper.selectYFwjbxxByFjbm(jzwfjid);
		if(fwjbxx!=null){
			if(fwjbxx.getFzxm()!=null && !"".equals(fwjbxx.getFzxm())){
				jwry.setFwfzxm(fwjbxx.getFzxm());
			}
			if(fwjbxx.getFzsfzhm()!=null && !"".equals(fwjbxx.getFzsfzhm())){
				jwry.setFzsfzh(fwjbxx.getFzsfzhm());
			}
			
		}
		
		String uuid=UUIDUtil.uuid();
		jwry.setFjh(jzwfj.getFjmc());//房间名称
//		jwry.setJwryid(uuid);
		jwry.setJzwid(jzwjbxxid);
		jwry.setDzbm(jzwjbxx.getDzbm());
		jwry.setFjbm(jzwfjid);
	
		List<Map<String,String>> xzqy=xzqhMapper.selectXzqyMapByDzbm(jzwjbxx.getSszdyjxzqy_dzbm());
		 if(xzqy==null||xzqy.size()<=0){
			 throw new ServiceLogicalException("建筑物所属行政区域代码为空，请联系相关人员维护该建筑物信息");
		 }
		 Map<String,String> map=xzqy.get(0);
		 jwry.setSsxqdm_mc(map.get("MC"));
		 String qudm=map.get("QUDM");
		 if(qudm==null||qudm.length()<6){
			 throw new ServiceLogicalException("建筑物所属行政区域数据错误，请联系相关人员维护该建筑物信息");
		 }
		 if(qudm!=null&&qudm.length()>=6){
			 String xzqhdm=qudm.substring(0,6);
			 Xzqh xzqh=xzqhMapper.selectXzqhBydm(xzqhdm);
			 jwry.setSsxqdm(xzqh.getDzbm());//省市县（区）
			 jwry.setSsxqdm_mc(xzqh.getXzqhmc());
		 }
		 Jlx jlx=jlxMapper.selectByPrimaryKey(jzwjbxx.getSsjlxxq_dzbm());
		 if(jlx==null){
				throw new ServiceLogicalException("建筑物所属街路巷小区数据错误，请联系相关人员维护该建筑物信息");
			}
		 jwry.setSsjlxxqDzbm(jlx.getJlxxqdm());
		 jwry.setSsjlxxqDzbm_mc(jlx.getJlxxqmc());
		 jwry.setDzbm(uuid);
		 
		 Jzwdy jzwdy=jzwdyMapper.selectByPrimaryKey(jzwfj.getJzwdyid());
			String dymc="";
			if(jzwdy!=null){
				dymc=jzwdy.getDymc();
			}
			jwry.setXxdz((""+jzwjbxx.getDzmc())+dymc+jzwfj.getFjmc());
			 String jwqdm=jzwjbxx.getZaglssjwzrqdm();
			 if(jwqdm==null||jwqdm.length()<12){
				 throw new ServiceLogicalException("建筑物所属警务区数据错误，请联系相关人员维护该建筑物信息");
			 }
			 Jwq jwq= jwqMapper.selectByJwqbh(jwqdm);
			 if(jwq==null){
				 throw new ServiceLogicalException("建筑物所属警务区数据为空，请联系相关人员维护该建筑物信息");
			 }
			 if(jwqdm!=null&&jwqdm.length()>=12){
				 jwry.setJwzrq(jwqdm);
				 jwry.setJwzrq_mc(jwq.getJwqmc());
				
				 String pcsOrgCode=jwqdm.substring(0, 9)+"000";
				 Organization psc_org =organizationMapper.selectOrgByCode(pcsOrgCode);
				 if(psc_org==null){
					 throw new ServiceLogicalException("建筑物所属警务区-派出所数据为空，请联系相关人员维护该建筑物信息");
				 }
				 jwry.setSdpcs(psc_org.getOrgna_code());
				 jwry.setSdpcs_mc(psc_org.getOrgna_name());
			 }
		return jwry;
	}

	@Override
	public List<String> loadJwryFjByJzwId(String jzwId) {
		if(StringUtils.hasText(jzwId)){
			return syFwJwryMapper.selectJwryFjByJzwId(jzwId);
		}
		return null;
	}
    @Transactional(rollbackFor = Exception.class)
	@Override
	public void saveSyFwJwryAccInfo(Syjwry jwry,byte[] imgFile) {
		// TODO Auto-generated method stub
		KAssert.hasText(jwry.getYwm(), "英文名不能为空");
		KAssert.hasText(jwry.getYwx(), "英文姓不能为空");
		KAssert.hasText(jwry.getZjzl(), "证件种类不能为空");
		KAssert.hasText(jwry.getZjhm(), "证件号码不能为空");
		KAssert.hasText(jwry.getFjh(), "房间号不能为空");
		if("1".equals(jwry.getZjzl())){//身份证号 校验
			if(!IDCardUtil.validateCard(jwry.getZjhm())){
				AlertSLEUtil.Error("身份证号有误，请重新输入");
			}
		}
		KAssert.hasText(jwry.getFzsfzh(), "房主身份证号不能为空");
		if(!IDCardUtil.validateCard(jwry.getFzsfzh())){
			AlertSLEUtil.Error("房主身份证号有误，请重新输入");
		}
		
		SyFwJwry fwjwry=syFwJwryMapper.selectFwJwryByfjbhAndZjhm(jwry.getZjhm(),jwry.getFjbm());
		if(fwjwry!=null){
			throw new ServiceLogicalException("该信息已经采集完成！");
		}
		Date date=new Date();
		Syjwry syjwry=syjwryMapper.selectsyJwrybyzjhm(jwry.getZjhm());
		SyjwryPic picInfo = syjwryPicService.selectpicInfoByZjhm(jwry.getZjhm());
		if(syjwry==null){//第一次采集境外人员
			String uuID=UUIDUtil.uuid();
			jwry.setJwryid(uuID);
			jwry.setZxzt("0");//注销状态 (1已注销;0未注销)-VARCHAR2(2)
			jwry.setDeltag("0");//删除标记（0-未删除，1-已删除）
			jwry.setDjr(SecurityUtils.getUserId());
			jwry.setDjdw(SecurityUtils.getUserOrgId());
			jwry.setDjrmc(SecurityUtils.getLoginname());
			jwry.setDjdwmc(SecurityUtils.getUserOrgName());
			jwry.setDjsj(date);
			jwry.setLrfs("0");
			SyFwJwry fwJwry=new SyFwJwry();
	    	fwJwry.setId(uuID);
	    	fwJwry.setRyid(uuID);
	    	fwJwry.setFjbh(jwry.getFjbm());
	    	fwJwry.setDjr(SecurityUtils.getUserId());
	    	fwJwry.setDjdw(SecurityUtils.getUserOrgId());
	    	fwJwry.setDjrmc(SecurityUtils.getLoginname());
	    	fwJwry.setDjdwmc(SecurityUtils.getUserOrgName());
	    	fwJwry.setDjsj(date);
	    	fwJwry.setLrfs(jwry.getLrfs());//录入方式(后台系统0,移动终端1)-CHAR(1)
	    	fwJwry.setDeltag("0");// 删除标记（0-未删除，1-已删除）-CHAR(1)
	    	fwJwry.setJzwid(jwry.getJzwid());
	    	fwJwry.setZjbh(jwry.getZjhm());
	    	
	    	fwJwry.setMacAddress(jwry.getMacAddress());
	    	fwJwry.setImei(jwry.getImeiNum());
	    	fwJwry.setTf(jwry.getTfCardNum());
	    	fwJwry.setSim(jwry.getSimNum());
	    	
	    	syFwJwryMapper.insertSelective(fwJwry);
	    	syjwryMapper.insertSelective(jwry);
		}else{//数据库中有该人员数据，境外人员再次采集
			SubSpringBeanUtil.copy(jwry, syjwry, true);
			syjwry.setXgr(SecurityUtils.getUserId());
			syjwry.setXgdw(SecurityUtils.getUserOrgId());
			syjwry.setXgrmc(SecurityUtils.getLoginname());
			syjwry.setXgdwmc(SecurityUtils.getUserOrgName());
			syjwry.setGxsj(date);
			//境外人员中间表
			SyFwJwry fwJwry=new SyFwJwry();
	    	String uuID=UUIDUtil.uuid();
	    	fwJwry.setId(uuID);
	    	fwJwry.setRyid(syjwry.getJwryid());
	    	fwJwry.setFjbh(jwry.getFjbm());
	    	fwJwry.setDjr(SecurityUtils.getUserId());
	    	fwJwry.setDjdw(SecurityUtils.getUserOrgId());
	    	fwJwry.setDjrmc(SecurityUtils.getLoginname());
	    	fwJwry.setDjdwmc(SecurityUtils.getUserOrgName());
	    	fwJwry.setDjsj(date);
	    	fwJwry.setLrfs(jwry.getLrfs());//录入方式(后台系统0,移动终端1)-CHAR(1)
	    	fwJwry.setDeltag("0");// 删除标记（0-未删除，1-已删除）-CHAR(1)
	    	fwJwry.setJzwid(jwry.getJzwid());
	    	fwJwry.setZjbh(jwry.getZjhm());
	    	
	    	//TF sim等卡号
	    	fwJwry.setMacAddress(jwry.getMacAddress());
	    	fwJwry.setImei(jwry.getImeiNum());
	    	fwJwry.setTf(jwry.getTfCardNum());
	    	fwJwry.setSim(jwry.getSimNum());
	    	
	    	syFwJwryMapper.insertSelective(fwJwry);
	    	syjwryMapper.updateByPrimaryKeySelective(syjwry);
		}
		//处理照片
		if(imgFile==null||imgFile.length==0){
			if(picInfo==null){
			//	AlertSLEUtil.Error("请上传照片");
			}else{
//					do nothing
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
				syjwryPicMapper.insertSelective(pInfo);
			}else{
				picInfo.setPic(imgFile);
				picInfo.setCreateUser(SecurityUtils.getUserId());
				picInfo.setCreateTime(date);
				picInfo.setRkid(jwry.getJwryid());
				syjwryPicMapper.updateByPrimaryKeySelective(picInfo);
			}
	
		}
		
		
		//--------------------------------------------------------------------------------------
	}

	@Override
	public SyFwJwry validateJwry(String sfzh, String jzwfjid) {
		// TODO Auto-generated method stub
		if(sfzh==null||"".equals(sfzh)){
			return null;
		}
		if(jzwfjid==null||"".equals(jzwfjid)){
			return null;
		}
		return syFwJwryMapper.selectFwJwryByfjbhAndZjhm(sfzh, jzwfjid);
	}

	public KJSONMSG loadSyjwryAccInfoApp(String jzwfjid) {
		Syjwry jwry=new Syjwry();
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
		SyFwjbxx fwjbxx=fwjbxxmapper.selectYFwjbxxByFjbm(jzwfjid);
		if(fwjbxx!=null){
			if(fwjbxx.getFzxm()!=null && !"".equals(fwjbxx.getFzxm())){
				jwry.setFwfzxm(fwjbxx.getFzxm());
			}
			if(fwjbxx.getFzsfzhm()!=null && !"".equals(fwjbxx.getFzsfzhm())){
				jwry.setFzsfzh(fwjbxx.getFzsfzhm());
			}
		}
		String uuid=UUIDUtil.uuid();
		jwry.setFjh(jzwfj.getFjmc());//房间名称
//		jwry.setJwryid(uuid);
		jwry.setJzwid(jzwjbxxid);
		jwry.setDzbm(jzwjbxx.getDzbm());
		jwry.setFjbm(jzwfjid);
		List<Map<String,String>> xzqy=xzqhMapper.selectXzqyMapByDzbm(jzwjbxx.getSszdyjxzqy_dzbm());
		 if(xzqy==null||xzqy.size()<=0){
			 obj = new KJSONMSG(300,"建筑物所属行政区域代码为空，请联系相关人员维护该建筑物信息");
			 return obj;
		 }
		 Map<String,String> map1=xzqy.get(0);
		 jwry.setSsxqdm_mc(map1.get("MC"));
		 String qudm=map1.get("QUDM");
		 if(qudm==null||qudm.length()<6){
			 obj = new KJSONMSG(300,"建筑物所属行政区域数据错误，请联系相关人员维护该建筑物信息");
			 return obj;
		 }
		 if(qudm!=null&&qudm.length()>=6){
			 String xzqhdm=qudm.substring(0,6);
			 Xzqh xzqh=xzqhMapper.selectXzqhBydm(xzqhdm);
			 jwry.setSsxqdm(xzqh.getDzbm());//省市县（区）
			 jwry.setSsxqdm_mc(xzqh.getXzqhmc());
		 }
		 Jlx jlx=jlxMapper.selectByPrimaryKey(jzwjbxx.getSsjlxxq_dzbm());
		 if(jlx==null){
			 obj = new KJSONMSG(300,"建筑物所属街路巷小区数据错误，请联系相关人员维护该建筑物信息");
			 return obj;
			}
		 jwry.setSsjlxxqDzbm(jlx.getJlxxqdm());
		 jwry.setSsjlxxqDzbm_mc(jlx.getJlxxqmc());
		 jwry.setDzbm(uuid);
		 
		 Jzwdy jzwdy=jzwdyMapper.selectByPrimaryKey(jzwfj.getJzwdyid());
			String dymc="";
			if(jzwdy!=null){
				dymc=jzwdy.getDymc();
			}
			jwry.setXxdz((""+jzwjbxx.getDzmc())+dymc+jzwfj.getFjmc());
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
				 jwry.setJwzrq(jwqdm);
				 jwry.setJwzrq_mc(jwq.getJwqmc());
				
				 String pcsOrgCode=jwqdm.substring(0, 9)+"000";
				 Organization psc_org =organizationMapper.selectOrgByCode(pcsOrgCode);
				 if(psc_org==null){
					 obj = new KJSONMSG(300,"建筑物所属警务区-派出所数据为空，请联系相关人员维护该建筑物信息");
					 return obj;
				 }
				 jwry.setSdpcs(psc_org.getOrgna_code());
				 jwry.setSdpcs_mc(psc_org.getOrgna_name());
			 }
			 Map<String,String> map=new HashMap<String,String>();
			 map.put("ssxqdm", jwry.getSsxqdm()==null ?"":jwry.getSsxqdm());
			 map.put("ssxqdm_mc", jwry.getSsxqdm_mc()==null ?"":jwry.getSsxqdm_mc());
			 map.put("ssjlxxqDzbm", jwry.getSsjlxxqDzbm()==null ?"":jwry.getSsjlxxqDzbm());
			 map.put("ssjlxxqDzbm_mc", jwry.getSsjlxxqDzbm_mc()==null ?"":jwry.getSsjlxxqDzbm_mc());
			 map.put("fjh", jwry.getFjh()==null ?"": jwry.getFjh());
			 map.put("xxdz", jwry.getXxdz()==null ?"": jwry.getXxdz());
			 map.put("sdpcs", jwry.getSdpcs()==null ?"": jwry.getSdpcs());
			 map.put("sdpcs_mc", jwry.getSdpcs_mc()==null ?"": jwry.getSdpcs_mc());
			 map.put("jwzrq", jwry.getJwzrq()==null ?"": jwry.getJwzrq());
			 map.put("jwzrq_mc", jwry.getJwzrq_mc()==null ?"": jwry.getJwzrq_mc());
			 map.put("fzxm", jwry.getFwfzxm()==null ?"":jwry.getFwfzxm());
			 map.put("fzsfzh",jwry.getFzsfzh()==null ?"":jwry.getFzsfzh());
			 obj = new KJSONMSG(200,"数据加载成功", map);
		return obj;
	}

	@Override
	public long loadDayJwryWorkCount(Map<String, Object> workInfo) {
		// TODO Auto-generated method stub
		KAssert.hasText(workInfo, "参数错误");
		return syFwJwryMapper.loadFWJWryCount(workInfo);
	
	}

	@Override
	public List<Map<String, Object>> loadFwJwryList(Map<String, Object> workInfo) {
		// TODO Auto-generated method stub
		KAssert.hasText(workInfo, "参数错误");
		return syFwJwryMapper.loadFwJwryList(workInfo);
	}
}
