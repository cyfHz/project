//package com.kingmon.project.psam.fw.service.impl;
//
//import java.util.Date;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import com.kingmon.base.data.DataSet;
//import com.kingmon.base.service.BaseService;
//import com.kingmon.base.util.KAssert;
//import com.kingmon.base.util.PaginationUtil;
//import com.kingmon.base.util.UUIDUtil;
//import com.kingmon.common.authUtil.SecurityUtils;
//import com.kingmon.project.psam.fw.mapper.SYFwjbxxMapper;
//import com.kingmon.project.psam.fw.model.SYFwjbxx;
//import com.kingmon.project.psam.fw.service.ISYFwjbxxService;
//import com.kingmon.project.psam.xzjd.model.Xzjd;
//
//@Service
//public class SYFwjbxxServiceImpl extends BaseService implements ISYFwjbxxService {
//	@Autowired
//	private SYFwjbxxMapper fwjbxxMapper;
//
//	@Transactional(rollbackFor = Exception.class,readOnly=true)
//	@Override
//	public DataSet loadSYFwjbxxDataSet(Map<String, String> params) {
//		PaginationUtil.initPageAndSort(params);
//		// 所属街路巷(小区)地址编码
//		String dzbm = params.get("ssjlxxq_dzbm");
//		if (dzbm != null && !dzbm.isEmpty()) {
//			params.put("ssjlxxq_dzbm", "%" + dzbm + "%");
//		}else{
//			params.remove("ssjlxxq_dzbm");
//		}
//		// 房屋产权证号
//		String fwcqzh = params.get("fwcqzh");
//		if (fwcqzh != null && !fwcqzh.isEmpty()) {
//			params.put("fwcqzh", "%" + fwcqzh + "%");
//		}else{
//			params.remove("fwcqzh");
//		}
//		// 房屋类别
//		String fwlb = params.get("fwlb");
//		if (fwlb != null && !fwlb.isEmpty()) {
//			params.put("fwlb", fwlb);
//		}else{
//			params.remove("fwlb");
//		}
//		String ssjlxxq_dzbm=params.get("jlxdzbm");
//		if(ssjlxxq_dzbm!=null && !ssjlxxq_dzbm.isEmpty()){
//			params.put("ssjlxxq_dzbm", ssjlxxq_dzbm);
//		}else {
//			params.remove("ssjlxxq_dzbm");
//		}
//		
//		return new DataSet(fwjbxxMapper.selectSYFwjbxxCount(params), fwjbxxMapper.selectSYFwjbxxList(params));
//	}
//
//	@Override
//	public SYFwjbxx loadSYFwjbxxByFjbm(String fjbm) {
//		if(!StringUtils.hasText(fjbm)){
//			return null;
//		}
//		return fwjbxxMapper.selectYFwjbxxByFjbm(fjbm);
//	}
//    private void validateRecord(SYFwjbxx fw){
//    	KAssert.hasText(fw.getFwbh(), "房屋编号不能为空");
//       
//    }
//
//
//	@Override
//	public void updateFw(Map<String, Object> params) {
//		params.put("xgr", SecurityUtils.getSessionUser().getUserId());
//		params.put("xgrmc", SecurityUtils.getSessionUser().getLoginname());
//		params.put("xgdw", SecurityUtils.getSessionUser().getOrganizationId());
//		params.put("xgdwmc", SecurityUtils.getSessionUser().getOrganizationName());
//		params.put("gxsj",new Date());
//		fwjbxxMapper.updateFw(params);
//	
//	}
//
//	@Override
//	public void addFw(Map<String, Object> params) {
//		params.put("fwid", UUIDUtil.uuid());
//		params.put("djr", SecurityUtils.getSessionUser().getUserId());
//		params.put("djdw", SecurityUtils.getSessionUser().getOrganizationId());
//		params.put("djrmc", SecurityUtils.getSessionUser().getLoginname());
//		params.put("djdwmc", SecurityUtils.getSessionUser().getOrganizationName());
//		params.put("djsj",new Date());
//		fwjbxxMapper.insertSelective(params);
//	}
//
//	@Override
//	public SYFwjbxx getFwByfwid(String fwid) {
//		SYFwjbxx fwjbxx = fwjbxxMapper.selectByPrimaryKey(fwid);
//		return fwjbxx;
//	}
//
//}
