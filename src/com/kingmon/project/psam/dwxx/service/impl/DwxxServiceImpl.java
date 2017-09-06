package com.kingmon.project.psam.dwxx.service.impl;

import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.DBTimeUtil;
import com.kingmon.base.util.IDCardUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.psam.dwxx.mapper.DwxxFwMapper;
import com.kingmon.project.psam.dwxx.mapper.DwxxMapper;
import com.kingmon.project.psam.dwxx.model.Dwxx;
import com.kingmon.project.psam.dwxx.model.DwxxFw;
import com.kingmon.project.psam.dwxx.service.IDwxxService;
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
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.psam.xzqh.model.Xzqh;

@Service
public class DwxxServiceImpl extends BaseService implements IDwxxService {

	@Autowired
	private DwxxMapper dwxxMapper;
	@Autowired
	private JzwfjMapper jzwfjMapper;
	@Autowired
	private JzwjgMapper jzwjgMapper;
	@Autowired
	private JzwjbxxMapper jzwjbxxMapper;
	@Autowired
	private  OrganizationMapper organizationMapper;
	@Autowired
	private  MlphMapper mlphMapper;
	@Autowired
	private XzqhMapper xzqhMapper;
	@Autowired
	private JlxMapper jlxMapper;
	@Autowired
	private JzwdyMapper jzwdyMapper;
	@Autowired
	private JwqMapper jwqMapper;
	@Autowired
	private DwxxFwMapper dwxxFwMapper;
	
	
	@Override
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public Dwxx loadFwDwxxAccInfo(String jzwfjid){
		Dwxx dwxx=dwxxMapper.selectDwxxForFjid(jzwfjid);
		
		//没有信息则初始化
		if(dwxx==null){
			dwxx=new Dwxx();
			 String uuid=UUIDUtil.uuid();
			 dwxx.setZagldwbm(uuid);//单位编码
		}
		
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
		
		dwxx.setFjid(jzwfjid);//建筑物房间id(jzwjbxx)
		dwxx.setJzwid(jzwjbxxid);//建筑物id
		
		
		 List<Map<String,String>> xzqy=xzqhMapper.selectXzqyMapByDzbm(jzwjbxx.getSszdyjxzqy_dzbm());
		 if(xzqy==null||xzqy.size()<=0){
			 throw new ServiceLogicalException("建筑物所属行政区域代码为空，请联系相关人员维护该建筑物信息");
		 }
		 Map<String,String> map=xzqy.get(0);
		 dwxx.setSsxq_mc(map.get("MC"));//省市区 名称
		 String qudm=map.get("QUDM");
		 if(qudm==null||qudm.length()<6){
			 throw new ServiceLogicalException("建筑物所属行政区域数据错误，请联系相关人员维护该建筑物信息");
		 }
		 if(qudm!=null&&qudm.length()>=6){
			 String xzqhdm=qudm.substring(0,6);
			 Xzqh xzqh=xzqhMapper.selectXzqhBydm(xzqhdm);
			 
			 dwxx.setSsxq_mc(xzqh.getXzqhmc());//省市区 名称
			 dwxx.setDwdz_ssxqdm(xzqh.getDzbm());//省市县（区）
			 
			 String sjbm=qudm.substring(0,4)+"00";
			 dwxx.setSssjbm(sjbm);//所属市局 编码
			 dwxx.setSsfjbm(xzqhdm);//所属分局编码
		 }
		 Jlx jlx=jlxMapper.selectByPrimaryKey(jzwjbxx.getSsjlxxq_dzbm());
			if(jlx==null){
				throw new ServiceLogicalException("建筑物所属街路巷小区数据错误，请联系相关人员维护该建筑物信息");
			}
			
			Jzwdy jzwdy=jzwdyMapper.selectByPrimaryKey(jzwfj.getJzwdyid());
			String dymc="";
			if(jzwdy!=null){
				dymc=jzwdy.getDymc();
			}
			dwxx.setDwdz_qhnxxdz((""+jzwjbxx.getDzmc())+dymc+jzwfj.getFjmc());//区划内详细地址

			String jwqdm=jzwjbxx.getZaglssjwzrqdm();
			 if(jwqdm==null||jwqdm.length()<12){
				 throw new ServiceLogicalException("建筑物所属警务区数据错误，请联系相关人员维护该建筑物信息");
			 }
			 Jwq jwq= jwqMapper.selectByJwqbh(jwqdm);
			 if(jwq==null){
				 throw new ServiceLogicalException("建筑物所属警务区数据为空，请联系相关人员维护该建筑物信息");
			 }
			 if(jwqdm!=null&&jwqdm.length()>=12){
				 dwxx.setSsjwqbm(jwqdm);//所属警务区编码
				 dwxx.setSsjwqmc(jwq.getJwqmc());//所属警务区名称
				 
				
				 String pcsOrgCode=jwqdm.substring(0, 9)+"000";
				 Organization psc_org =organizationMapper.selectOrgByCode(pcsOrgCode);
				 if(psc_org==null){
					 throw new ServiceLogicalException("建筑物所属警务区-派出所数据为空，请联系相关人员维护该建筑物信息");
				 }
				 
				 dwxx.setSspcsbm(psc_org.getOrgna_code());//所属派出所编码
				 dwxx.setSspcsmc(psc_org.getOrgna_name());//所属派出所名称
				 
			 }
			 
		return dwxx;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveDwjbxxAccInfo(Dwxx dwxx){
		
		dwxx.setDeltag("0");//删除标记
		dwxx.setDjdw_gajgjgdm(SecurityUtils.getUserOrgCode());//登记单位代码
		dwxx.setDjdw_gajgmc(SecurityUtils.getUserOrgName());//登记单位名
		
		if(dwxx.getFddbr_gmsfhm()!=null && !dwxx.getFddbr_gmsfhm().isEmpty()){
			boolean isv=IDCardUtil.validateCard(dwxx.getFddbr_gmsfhm());
			KAssert.isTrue(isv, "法人身份证号格式不正确"); 
		}
		if(dwxx.getBwfzr_gmsfhm()!=null && !dwxx.getBwfzr_gmsfhm().isEmpty()){
			boolean isv=IDCardUtil.validateCard(dwxx.getBwfzr_gmsfhm());
			KAssert.isTrue(isv, "保卫负责人身份证号格式不正确"); 
		}
		
		String zagldwbm=dwxx.getZagldwbm();
		long count=dwxxMapper.selectCountByDwxxKey(zagldwbm);
		
		DwxxFw dwxxFw=dwxxFwMapper.selectDwxxFwForDwId(zagldwbm);//查询中间表
		
		if(count>0){
			dwxx.setGxsj(DBTimeUtil.getDBTime());//更新时间
			
			if(dwxxFw!=null){//更新中间表
				dwxxFw.setGxsj(DBTimeUtil.getDBTime());
				dwxxFwMapper.updateByPrimaryKeySelective(dwxxFw);
			}else{//添加
				DwxxFw dwxxfw = new DwxxFw();//单位房屋 中间表
				String uuid=UUIDUtil.uuid();
				dwxxfw.setId(uuid);
				dwxxfw.setDeltag("0");//
				dwxxfw.setDjdw(SecurityUtils.getUserOrgId());
				dwxxfw.setDjdwmc(SecurityUtils.getUserOrgName());
				dwxxfw.setDjr(SecurityUtils.getUserId());
				dwxxfw.setDjrmc(SecurityUtils.getUserName());
				dwxxfw.setDjsj(DBTimeUtil.getDBTime());
				dwxxfw.setFjid(dwxx.getFjid());
				dwxxfw.setJzwid(dwxx.getJzwid());
				dwxxfw.setDwid(zagldwbm);
				dwxxFwMapper.insertSelective(dwxxfw);
			}
			dwxxMapper.updateByPrimaryKeySelective(dwxx);
		}else{
			DwxxFw dwxxfw = new DwxxFw();//单位房屋 中间表
			String uuid=UUIDUtil.uuid();
			dwxxfw.setId(uuid);
			dwxxfw.setDeltag("0");//
			dwxxfw.setDjdw(SecurityUtils.getUserOrgId());
			dwxxfw.setDjdwmc(SecurityUtils.getUserOrgName());
			dwxxfw.setDjr(SecurityUtils.getUserId());
			dwxxfw.setDjrmc(SecurityUtils.getUserName());
			dwxxfw.setDjsj(DBTimeUtil.getDBTime());
			dwxxfw.setFjid(dwxx.getFjid());
			dwxxfw.setJzwid(dwxx.getJzwid());
			dwxxfw.setDwid(zagldwbm);
			
			dwxxMapper.insertSelective(dwxx);
			dwxxFwMapper.insertSelective(dwxxfw);
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,readOnly=true)
    public DataSet loadJzwFwDwxxForFjid(Map<String,String> params){
		String fjid=params.get("jzwfjid");
		if(fjid==null || fjid.isEmpty()){
    		 AlertSLEUtil.Error("当前房屋没有采集基本信息");
    	}
    	PaginationUtil.initPageAndSort(params);
    	DataSet ds=new DataSet(dwxxMapper.CountJzwFwDwxxForFjid(params), dwxxMapper.loadJzwFwDwxxForFjid(params));
    	return ds;
    	
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public DataSet loadJzwFwLsDwxxForFjid(Map<String,String> params){
		String fjid=params.get("jzwfjid");
		if(fjid==null){
    		return null;
    	}
    	PaginationUtil.initPageAndSort(params);
    	DataSet ds=new DataSet(dwxxMapper.CountJzwFwLsDwxxForFjid(params), dwxxMapper.loadJzwFwLsDwxxForFjid(params));
    	return ds;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void revokeFwDwxx(Map<String,Object> params){
		String dwid=(String)params.get("dwid");
		String fjbm=(String)params.get("fjbm");
		if(dwid==null || dwid.isEmpty()){
			AlertSLEUtil.Error("单位信息不存在,无法删除！");
		}
		if(fjbm==null || fjbm.isEmpty()){
			AlertSLEUtil.Error("房间编号！");
		}
		params.put("deltag", 1);//删除标记
		params.put("deluser",SecurityUtils.getUserId());//删除人
		params.put("deltime", DBTimeUtil.getDBTime());//删除时间
		
		params.put("gxsj", DBTimeUtil.getDBTime());//更新时间
		dwxxFwMapper.revokeFwDwxx(params);
	}
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public Dwxx selectDwXxById(String dwid){
		if(dwid==null||dwid.isEmpty()){
			return null;
		}
		return dwxxMapper.selectDwXxById(dwid);
	}
}
