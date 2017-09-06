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
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.base.util.SubSpringBeanUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
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
import com.kingmon.project.psam.jzw.mapper.JzwlcMapper;
import com.kingmon.project.psam.jzw.model.Jzwdy;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.jzw.model.Jzwjg;
import com.kingmon.project.psam.jzw.model.Jzwlc;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.sy.mapper.SyFwLdrkMapper;
import com.kingmon.project.psam.sy.mapper.SyRkglLdrkdjbMapper;
import com.kingmon.project.psam.sy.mapper.SyRkglLdrkdjbPicMapper;
import com.kingmon.project.psam.sy.mapper.SyRkglCzrkPicMapper;
import com.kingmon.project.psam.sy.model.SyFwLdrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjbPic;
import com.kingmon.project.psam.sy.model.SyRkglCzrkPic;
import com.kingmon.project.psam.sy.service.ISyFwLdrkService;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkPicService;
import com.kingmon.project.psam.sy.service.ISyRkglLdrkdjbPicService;
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
public class SyFwLdrkServiceImpl extends BaseService implements ISyFwLdrkService {
	@Autowired
	private SyFwLdrkMapper syFwLdrkMapper;
	
	@Autowired
	private SyRkglLdrkdjbMapper syRkglLdrkdjbMapper;
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
	private SyRkglLdrkdjbPicMapper syRkglLdrkdjbPicMapper;
	
	@Autowired
	private  MlphMapper mlphMapper;

	@Autowired
	private ISyRkglLdrkdjbPicService syRkglLdrkdjbPicService;
	@Override
	public SyRkglLdrkdjb loadLdrkAccInfo(String jzwfjid) {
		// TODO Auto-generated method stub

		SyRkglLdrkdjb ldrk=new SyRkglLdrkdjb();
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
		Mlph mlph=mlphMapper.selectMlphByJzwId(jzwjbxxid);
		KAssert.notNull(mlph, "根据建该房屋所属建筑物未查询到门楼牌号信息，请联系相关人员维护该建筑物信息");
		ldrk.setLh(mlph.getMlph());
		String uuid=UUIDUtil.uuid();
		ldrk.setFh(jzwfj.getFjmc());//房间名称
//		ldrk.setLh(jzwjbxx.getJzwmc());//楼号
		ldrk.setJzwid(jzwjbxxid);
		ldrk.setDzbm(jzwjbxx.getDzbm());
		ldrk.setFjbm(jzwfjid);
//		ldrk.setLdid(uuid);
//		ldrk.setXlh(uuid);//自动生成的编码
		List<Map<String,String>> xzqy=xzqhMapper.selectXzqyMapByDzbm(jzwjbxx.getSszdyjxzqy_dzbm());
		 if(xzqy==null||xzqy.size()<=0){
			 throw new ServiceLogicalException("建筑物所属行政区域代码为空，请联系相关人员维护该建筑物信息");
		 }
		 Map<String,String> map=xzqy.get(0);
		 ldrk.setDzSsxq_mc(map.get("MC"));
		 String qudm=map.get("QUDM");
		 if(qudm==null||qudm.length()<6){
			 throw new ServiceLogicalException("建筑物所属行政区域数据错误，请联系相关人员维护该建筑物信息");
		 }
		 if(qudm!=null&&qudm.length()>=6){
			 String xzqhdm=qudm.substring(0,6);
			 Xzqh xzqh=xzqhMapper.selectXzqhBydm(xzqhdm);
			 ldrk.setDzSsxq(xzqh.getDzbm());//省市县（区）
			 ldrk.setDzSsxq_mc(xzqh.getXzqhmc());
//			 ldrk.setDzXzjd(xzqh.getXzqhdm());//乡镇街道
//			 ldrk.setXzjd(xzqh.getXzqhmc());
		 }
		 Jlx jlx=jlxMapper.selectByPrimaryKey(jzwjbxx.getSsjlxxq_dzbm());
		 if(jlx==null){
				throw new ServiceLogicalException("建筑物所属街路巷小区数据错误，请联系相关人员维护该建筑物信息");
			}
		 ldrk.setJlxxq(jlx.getJlxxqmc());
//		 ldrk.setDzbm(uuid);
			Jzwdy jzwdy=jzwdyMapper.selectByPrimaryKey(jzwfj.getJzwdyid());
			String dymc="";
			if(jzwdy!=null){
				dymc=jzwdy.getDymc();
			}
		  ldrk.setDyh(jzwdy.getDymc());
		  ldrk.setXxdz((""+jzwjbxx.getDzmc())+dymc+jzwfj.getFjmc());
		 
		  String jwqdm=jzwjbxx.getZaglssjwzrqdm();
			 if(jwqdm==null||jwqdm.length()<12){
				 throw new ServiceLogicalException("建筑物所属警务区数据错误，请联系相关人员维护该建筑物信息");
			 }
			 Jwq jwq= jwqMapper.selectByJwqbh(jwqdm);
			 if(jwq==null){
				 throw new ServiceLogicalException("建筑物所属警务区数据为空，请联系相关人员维护该建筑物信息");
			 }
			 if(jwqdm!=null&&jwqdm.length()>=12){
				 ldrk.setSsjwq(jwqdm);
				 ldrk.setSsjwq_mc(jwq.getJwqmc());
				
				 String pcsOrgCode=jwqdm.substring(0, 9)+"000";
				 Organization psc_org =organizationMapper.selectOrgByCode(pcsOrgCode);
				 if(psc_org==null){
					 throw new ServiceLogicalException("建筑物所属警务区-派出所数据为空，请联系相关人员维护该建筑物信息");
				 }
				 ldrk.setSspcs(psc_org.getOrgna_code());
				 ldrk.setSspcs_mc(psc_org.getOrgna_name());
			 }
		return ldrk;
	}

	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public List<String> loadLdrkFjByJzwId(String jzwId) {
		if(StringUtils.hasText(jzwId)){
			return syFwLdrkMapper.selectLdrkFjByJzwId(jzwId);
		}
		return null;
	}
    @Transactional(rollbackFor = Exception.class)
	@Override
	public void saveSyFwLdrkAccInfo(SyRkglLdrkdjb ldrk,byte[] imgFile) {
		// TODO Auto-generated method stub
		KAssert.hasText(ldrk.getXm(), "姓名不能为空");
		KAssert.hasText(ldrk.getSfzh(), "身份证号不能为空");
		if(!IDCardUtil.validateCard(ldrk.getSfzh())){
			AlertSLEUtil.Error("身份证号有误，请重新输入");
		}
		
		KAssert.hasText(ldrk.getXl(), "学历不能为空");
		KAssert.hasText(ldrk.getMz(), "民族不能为空");
		KAssert.hasText(ldrk.getZzmm(), "政治面貌不能为空");
		KAssert.hasText(ldrk.getFh(), "房号不能为空");
		KAssert.hasText(ldrk.getXxdz(), "详细地址不能为空");
		SyFwLdrk fwldrk=syFwLdrkMapper.selectFwLdrkByfjbhAndZjhm(ldrk.getSfzh(), ldrk.getFjbm());
		if(fwldrk!=null){
			throw new ServiceLogicalException("该信息已经采集完成！");
		}
		Date date=new Date();
		SyRkglLdrkdjb glldrk=syRkglLdrkdjbMapper.selectglldrkById(ldrk.getSfzh());
		SyRkglLdrkdjbPic picInfo=syRkglLdrkdjbPicService.selectpicInfoByZjhm(ldrk.getSfzh());
		if(glldrk==null){//第一次采集流动人口
			String uuID=UUIDUtil.uuid();
			ldrk.setLdid(uuID);
			ldrk.setXlh(uuID);
			ldrk.setId(uuID);
			ldrk.setDeltag("0");//删除标记
			ldrk.setZxbz("0");//注销标志（0未注销1注销）
			ldrk.setDjr(SecurityUtils.getUserId());
			ldrk.setDjdw(SecurityUtils.getUserOrgId());
			ldrk.setDjrmc(SecurityUtils.getLoginname());
			ldrk.setDjdwmc(SecurityUtils.getUserOrgName());
			ldrk.setDjsj(date);
			ldrk.setLrfs("0");
			ldrk.setZxzt("0");
			SyFwLdrk syfwldrk=new SyFwLdrk();
			syfwldrk.setId(uuID);
			syfwldrk.setRyid(uuID);
			syfwldrk.setFjbh(ldrk.getFjbm());
			syfwldrk.setDjr(SecurityUtils.getUserId());
			syfwldrk.setDjdw(SecurityUtils.getUserOrgId());
			syfwldrk.setDjrmc(SecurityUtils.getLoginname());
			syfwldrk.setDjdwmc(SecurityUtils.getUserOrgName());
			syfwldrk.setDjsj(date);
			syfwldrk.setLrfs(ldrk.getLrfs());//录入方式(后台系统0,移动终端1)-CHAR(1)
			syfwldrk.setDeltag("0");// 删除标记（0-未删除，1-已删除）-CHAR(1)
			syfwldrk.setJzwid(ldrk.getJzwid());
			syfwldrk.setZjbh(ldrk.getSfzh());
			
			syfwldrk.setMacAddress(ldrk.getMacAddress());
			syfwldrk.setImei(ldrk.getImeiNum());
			syfwldrk.setTf(ldrk.getTfCardNum());
			syfwldrk.setSim(ldrk.getSimNum());
			
			syFwLdrkMapper.insertSelective(syfwldrk);
			syRkglLdrkdjbMapper.insertSelective(ldrk);
			
		}else{//数据库中有该人员数据，流动人口再次采集
			//流动人员管理表存储
		    SubSpringBeanUtil.copy(ldrk, glldrk, true);
			glldrk.setXgr(SecurityUtils.getUserId());
			glldrk.setXgdw(SecurityUtils.getUserOrgId());
			glldrk.setXgrmc(SecurityUtils.getLoginname());
			glldrk.setXgdwmc(SecurityUtils.getUserOrgName());
			glldrk.setGxsj(new Date());
			//流动人口中间表
			SyFwLdrk syfwldrk=new SyFwLdrk();
			String uuID=UUIDUtil.uuid();
			syfwldrk.setId(uuID);
//			syfwldrk.setRyid(glldrk.getId());
			syfwldrk.setRyid(glldrk.getLdid());
			
			syfwldrk.setFjbh(ldrk.getFjbm());
			syfwldrk.setDjr(SecurityUtils.getUserId());
			syfwldrk.setDjdw(SecurityUtils.getUserOrgId());
			syfwldrk.setDjrmc(SecurityUtils.getLoginname());
			syfwldrk.setDjdwmc(SecurityUtils.getUserOrgName());
			syfwldrk.setDjsj(date);
			syfwldrk.setLrfs(ldrk.getLrfs());//录入方式(后台系统0,移动终端1)-CHAR(1)
			syfwldrk.setDeltag("0");// 删除标记（0-未删除，1-已删除）-CHAR(1)
			syfwldrk.setJzwid(ldrk.getJzwid());
			syfwldrk.setZjbh(ldrk.getSfzh());
			
			syfwldrk.setMacAddress(ldrk.getMacAddress());
			syfwldrk.setImei(ldrk.getImeiNum());
			syfwldrk.setTf(ldrk.getTfCardNum());
			syfwldrk.setSim(ldrk.getSimNum());
			
			syFwLdrkMapper.insertSelective(syfwldrk);
			syRkglLdrkdjbMapper.updateByPrimaryKeySelective(glldrk);
		}
		//处理照片
				if(imgFile==null||imgFile.length==0){
					if(picInfo==null){
					//	AlertSLEUtil.Error("请上传照片");
					}else{
//							do nothing
						picInfo.setRklx("2");
						picInfo.setRkid(ldrk.getLdid());
						syRkglLdrkdjbPicMapper.updateByPrimaryKeySelective(picInfo);
					}
				}else{
					if(picInfo==null){
						SyRkglLdrkdjbPic pInfo=new SyRkglLdrkdjbPic();
						pInfo.setId(UUIDUtil.uuid());
						pInfo.setPic(imgFile);
						pInfo.setRkid(ldrk.getLdid());
						pInfo.setRkzjbh(ldrk.getSfzh());
						pInfo.setCreateUser(SecurityUtils.getUserId());
						pInfo.setCreateTime(date);
						pInfo.setRklx("2");
						syRkglLdrkdjbPicService.addPic(pInfo);
					}else{
						picInfo.setPic(imgFile);
						picInfo.setCreateUser(SecurityUtils.getUserId());
						picInfo.setCreateTime(date);
						picInfo.setRkid(ldrk.getLdid());
						syRkglLdrkdjbPicMapper.updateByPrimaryKeySelective(picInfo);
					}
			
				}

	}

	@Override
	public SyFwLdrk validateLdrk(String sfzh, String jzwfjid) {
		// TODO Auto-generated method stub
		if(sfzh==null||"".equals(sfzh)){
			return null;
		}
		if(jzwfjid==null||"".equals(jzwfjid)){
			return null;
		}
		return syFwLdrkMapper.selectFwLdrkByfjbhAndZjhm(sfzh, jzwfjid);
	}

	public KJSONMSG loadLdrkAccInfoApp(String jzwfjid){
		SyRkglLdrkdjb ldrk=new SyRkglLdrkdjb();
		Jzwfj jzwfj=jzwfjMapper.selectByPrimaryKey(jzwfjid);
		KJSONMSG obj = new KJSONMSG();
		if(jzwfj==null){
			obj=new KJSONMSG(300,"未查询到房间数据");
			return obj;
		}
		String jzwjgId=jzwfj.getJzwjgid();
		Jzwjg jzwjg=jzwjgMapper.selectByPrimaryKey(jzwjgId);
		if(jzwjg==null){
			obj=new KJSONMSG(300,"未查询到建筑物结构数据");
			return obj;
		}
		String jzwjbxxid=jzwjg.getJzwid();
		Jzwjbxx jzwjbxx=jzwjbxxMapper.selectByPrimaryKey(jzwjbxxid);
		if(jzwjbxx==null){
			obj=new KJSONMSG(300,"未查询到建筑物数据");
			return obj;
		}
		Mlph mlph=mlphMapper.selectMlphByJzwId(jzwjbxxid);
		if(mlph==null){
			obj=new KJSONMSG(300,"根据建该房屋所属建筑物未查询到门楼牌号信息，请联系相关人员维护该建筑物信息");
			return obj;
		}
//		KAssert.notNull(mlph, "根据建该房屋所属建筑物未查询到门楼牌号信息，请联系相关人员维护该建筑物信息");
		ldrk.setLh(mlph.getMlph());
		String uuid=UUIDUtil.uuid();
		ldrk.setFh(jzwfj.getFjmc());//房间名称
		ldrk.setJzwid(jzwjbxxid);
		ldrk.setDzbm(jzwjbxx.getDzbm());
		ldrk.setFjbm(jzwfjid);
		List<Map<String,String>> xzqy=xzqhMapper.selectXzqyMapByDzbm(jzwjbxx.getSszdyjxzqy_dzbm());
		 if(xzqy==null||xzqy.size()<=0){
			 obj=new KJSONMSG(300,"建筑物所属行政区域代码为空，请联系相关人员维护该建筑物信息");
			 return obj;
		 }
		 Map<String,String> map1=xzqy.get(0);
		 ldrk.setDzSsxq_mc(map1.get("MC"));
		 String qudm=map1.get("QUDM");
		 if(qudm==null||qudm.length()<6){
			 obj=new KJSONMSG(300,"建筑物所属行政区域数据错误，请联系相关人员维护该建筑物信息");
			 return obj;
		 }
		 if(qudm!=null&&qudm.length()>=6){
			 String xzqhdm=qudm.substring(0,6);
			 Xzqh xzqh=xzqhMapper.selectXzqhBydm(xzqhdm);
			 ldrk.setDzSsxq(xzqh.getDzbm());//省市县（区）
			 ldrk.setDzSsxq_mc(xzqh.getXzqhmc());
		 }
		 Jlx jlx=jlxMapper.selectByPrimaryKey(jzwjbxx.getSsjlxxq_dzbm());
		 if(jlx==null){
			 obj=new KJSONMSG(300,"建筑物所属街路巷小区数据错误，请联系相关人员维护该建筑物信息");
			 return obj;
			}
		 ldrk.setJlxxq(jlx.getJlxxqmc());
			Jzwdy jzwdy=jzwdyMapper.selectByPrimaryKey(jzwfj.getJzwdyid());
			String dymc="";
			if(jzwdy!=null){
				dymc=jzwdy.getDymc();
			}
		  ldrk.setDyh(jzwdy.getDymc());
		  ldrk.setXxdz((""+jzwjbxx.getDzmc())+dymc+jzwfj.getFjmc());
		  String jwqdm=jzwjbxx.getZaglssjwzrqdm();
			 if(jwqdm==null||jwqdm.length()<12){
				 obj=new KJSONMSG(300,"建筑物所属警务区数据错误，请联系相关人员维护该建筑物信息");
				 return obj;
			 }
			 Jwq jwq= jwqMapper.selectByJwqbh(jwqdm);
			 if(jwq==null){
				 obj=new KJSONMSG(300,"建筑物所属警务区数据为空，请联系相关人员维护该建筑物信息");
				 return obj;
			 }
			 if(jwqdm!=null&&jwqdm.length()>=12){
				 ldrk.setSsjwq(jwqdm);
				 ldrk.setSsjwq_mc(jwq.getJwqmc());
				 String pcsOrgCode=jwqdm.substring(0, 9)+"000";
				 Organization psc_org =organizationMapper.selectOrgByCode(pcsOrgCode);
				 if(psc_org==null){
					 obj=new KJSONMSG(300,"建筑物所属警务区-派出所数据为空，请联系相关人员维护该建筑物信息");
					 return obj;
				 }
				 ldrk.setSspcs(psc_org.getOrgna_code());
				 ldrk.setSspcs_mc(psc_org.getOrgna_name());
			 }
			 Map<String,String> map=new HashMap<String,String>();
			 map.put("dzSsxq", ldrk.getDzSsxq()==null ?"":ldrk.getDzSsxq());
			 map.put("dzSsxq_mc", ldrk.getDzSsxq_mc()==null ?"":ldrk.getDzSsxq_mc());
			 map.put("jlxxq", ldrk.getJlxxq()==null ?"":ldrk.getJlxxq());
			 map.put("dyh", ldrk.getDyh()==null ?"":ldrk.getDyh());
			 map.put("lh", ldrk.getLh()==null ?"":ldrk.getLh());
			 map.put("fh", ldrk.getFh()==null ?"":ldrk.getFh());
			 map.put("xxdz", ldrk.getXxdz()==null ?"":ldrk.getXxdz());
			 map.put("ssjwq_mc", ldrk.getSsjwq_mc()==null ?"":ldrk.getSsjwq_mc());
			 map.put("ssjwq", ldrk.getSsjwq()==null ?"":ldrk.getSsjwq());
			 map.put("sspcs", ldrk.getSspcs()==null ?"":ldrk.getSspcs());
			 map.put("sspcs_mc", ldrk.getSspcs_mc()==null ?"":ldrk.getSspcs_mc());
			 obj = new KJSONMSG(200,"数据加载成功", map);
		return obj;
	}

	@Override
	public long loadDayLdrkWorkCount(Map<String, Object> workInfo) {
		// TODO Auto-generated method stub
		KAssert.hasText(workInfo, "参数错误");
		return syFwLdrkMapper.loadFwLdrkCount(workInfo);
	}

	@Override
	public List<Map<String, Object>> loadFwLdrkList(Map<String, Object> workInfo) {
		// TODO Auto-generated method stub
		KAssert.hasText(workInfo, "参数错误");
		return syFwLdrkMapper.loadFwLdrkList(workInfo);
	}
}
