package com.kingmon.project.psam.sqjcwh.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PinyinUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.psam.sqjcwh.mapper.SqjcwhMapper;
import com.kingmon.project.psam.sqjcwh.model.Sqjcwh;
import com.kingmon.project.psam.sqjcwh.service.ISqjcwhService;
import com.kingmon.project.psam.util.QydmData;
import com.kingmon.project.psam.xzjd.mapper.XzjdMapper;
import com.kingmon.project.psam.xzjd.model.Xzjd;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.util.Pinyin4jUtil;

@Service
public class SqjcwhServiceImpl extends BaseService implements ISqjcwhService {
	
	@Autowired
	private SqjcwhMapper sqjcwhMapper;
	@Autowired
	private XzjdMapper xzjdMapper;
	@Autowired
	private XzqhMapper xzqhMapper;
	/**
	 * @param sqjcwh
	 * 数据校验
	 */
	private void validate(Sqjcwh sqjcwh){
		KAssert.notNull(sqjcwh.getSqjcwhdm(), "社区居村委会代码不能为空");
		KAssert.notNull(sqjcwh.getSqjcwhmc(), "社区居村委会名称不能为空");
		KAssert.notNull(sqjcwh.getDzyslxdm(), "地址元素类型代码不能为空");
		KAssert.notNull(sqjcwh.getBmjc(), "别名简称不能为空");
		KAssert.notNull(sqjcwh.getDycxsxdm(), "地域城乡属性不能为空");
	}
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadSqjcwhDataSet(ParamObject po) {
		 StringBuffer sql=new StringBuffer("");
		 sql.
		 append("SELECT tmp.mc as SJXZQY_DZBM, ")//xz.XZJDMC as SJXZQY_DZBM,
		.append("x.DZBM,")
		.append("x.SQJCWHDM,")
		.append("x.SQJCWHMC,")
		.append("x.BMJC,")
		.append("x.SYZTDM,")
		.append("x.DZYSLXDM,")
		.append("x.DYCXSXDM,")
		.append("x.SLRQ,")
		.append("x.CXSJ,")
		.append("x.GXSJ,")
		.append("x.QYRQ,")
		.append("x.TYRQ,")
		.append("x.CXYY,")
		.append("x.ZJF,")
		.append("x.DJSJ,")
		.append("x.CXBJ ")
//		.append("x.SJXZQY_DZBM ")
		.append("@from DZ_SQJCWH  x ")
		.append("left join ")
		.append(" ( ")
		.append("  select xz.dzbm as dm , xz.xzjddm as qudm, xz.xzjdmc as mc from DZ_XZJD xz ")
		.append("  UNION")
		.append("  SELECT qh.dzbm as dm ,qh.xzqhdm as qudm, qh.xzqhmc as mc FROM DZ_XZQH qh ")
		.append("  UNION")
		.append("  select s.dzbm as dm ,s.sqjcwhdm as qudm, s.sqjcwhmc as mc from  dz_sqjcwh s ")
		.append("  ) tmp on x.SJXZQY_DZBM=tmp.dm")
//		.append("left join DZ_XZJD xz  on xz.DZBM=x.SJXZQY_DZBM ")
//		.append("left join DZ_XZQH qh  on qh.DZBM=x.SJXZQY_DZBM ")
		.append(" where 1=1 ");
		//使用状态代码为停用或报废是不显示
/*	    sql.append(" and x.SYZTDM !='").append(KConstants.SYTZDM_STOP).append("'");
		sql.append(" and x.SYZTDM !='").append(KConstants.SYTZDM_STOPUSE).append("'");;
*/		 
		 
		String sqjcwhdm = (String) po.getWebParam("sqjcwhdm");
		if (!SubApStrUtil.isEmptyAfterTrimE(sqjcwhdm)) {
			sql.append("and x.SQJCWHDM like:sqjcwhdm ");
			po.addSQLParam("sqjcwhdm", "%"+sqjcwhdm+"%");
		}
		String sqjcwhmc = (String) po.getWebParam("sqjcwhmc");
		if (!SubApStrUtil.isEmptyAfterTrimE(sqjcwhmc)) {
			sql.append("and (x.SQJCWHMC like:sqjcwhmc or lower(x.zjf) like lower(:sqjcwhmc))");
			po.addSQLParam("sqjcwhmc", "%"+sqjcwhmc+"%"); 	
		}
		String syztdm = (String) po.getWebParam("syztdm");
		if (!SubApStrUtil.isEmptyAfterTrimE(syztdm)) {
			sql.append(" and x.SYZTDM=:syztdm ");
			po.addSQLParam("syztdm", syztdm);
		}
		String sjxzqy_dzbm = (String) po.getWebParam("sjxzqy_dzbm");
		if (!SubApStrUtil.isEmptyAfterTrimE(sjxzqy_dzbm)) {
//			sql.append(" and x.SJXZQY_DZBM=:sjxzqy_dzbm ");
//			po.addSQLParam("sjxzqy_dzbm", sjxzqy_dzbm);
			List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(sjxzqy_dzbm);
			String qydm=QydmData.getQydm(map);
			qydm+="%";
			sql.append(" and tmp.qudm like :qydm ");//and (xz.xzjddm like :qydm or qh.xzqhdm like :qydm)
			po.addSQLParam("qydm", qydm);
		}
		//代码根本对不起来，所以用社区居村委会 dm like 匹配不合适。
//		String xzqh=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
//		sql.append(" and x.SQJCWHDM like:xzjddm_data_auth ");
//		po.addSQLParam("xzjddm_data_auth", ""+xzqh+"%");
		
		//String xzqh=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
		String xzqh=SecurityUtils.getUserLevelStr();//-zht-20160219
		if(xzqh.length()>6){ xzqh = xzqh.substring(0,6); }
		//此处乡镇街道
		sql.append(" and tmp.qudm like :xzjddm_data_auth ");//and (xz.XZJDDM like:xzjddm_data_auth or qh.xzqhdm like:xzjddm_data_auth)
		po.addSQLParam("xzjddm_data_auth", ""+xzqh+"%");		
		
		if (po.hasOrder()) {
			sql.append(" order by ").append("x.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}else{
			sql.append(" order by x.SQJCWHDM");
		}

		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addSqjcwh(Sqjcwh sqjcwh) {
//		validate(sqjcwh);
		//KAssert.notNull(sqjcwh.getSqjcwhdm(), "社区居村委会代码不能为空");
		KAssert.notNull(sqjcwh.getSqjcwhmc(), "社区居村委会名称不能为空");
		KAssert.notNull(sqjcwh.getDzyslxdm(), "地址元素类型代码不能为空");
		KAssert.notNull(sqjcwh.getBmjc(), "别名简称不能为空");
		KAssert.notNull(sqjcwh.getDycxsxdm(), "地域城乡属性不能为空");
		KAssert.notNull(sqjcwh.getSjxzqy_dzbm(), "上级行政区域不能为空");
		
		String sjxzqy_dzbm=sqjcwh.getSjxzqy_dzbm();
		Xzjd xzjd=xzjdMapper.selectByPrimaryKeyL(sjxzqy_dzbm);
		KAssert.notNull(xzjd, "未查询到所属乡镇街道");
		String xzjdDm=xzjd.getXzjddm();
		String sqjcwhdm="";
		List<String> listMap=sqjcwhMapper.selectSqjcwhdmListByXzjdDzbm(sjxzqy_dzbm);
		if(listMap==null||listMap.size()==0){
			sqjcwhdm=xzjdDm+"001";
		}else{
			sqjcwhdm=getRbm(xzjdDm,listMap);
		}
		sqjcwh.setSqjcwhdm(sqjcwhdm);
		
		//上级社区居村委会
		String sjsqjcwh_dzbm=sqjcwh.getSjsqjcwh_dzbm();
		if(StringUtils.hasText(sjsqjcwh_dzbm)){
			String ss_sqjcwh_dm=sqjcwhMapper.selectSqjcwhdmByDzbm(sjsqjcwh_dzbm);
			if(ss_sqjcwh_dm==null||ss_sqjcwh_dm.isEmpty()){
				AlertSLEUtil.Error("未查询到上级社区居村委会");
			}
			if(!ss_sqjcwh_dm.startsWith(xzjdDm)){
				AlertSLEUtil.Error("选取上级社区居村委会,不在所属最低一级行政区域内");
			}
		}
//		long count=sqjcwhMapper.queryCount(KConstants.GENERATED_UUID,sqjcwh.getSqjcwhdm());
//		if(count>0){
//			AlertSLEUtil.Error("社区居村委会代码已存在");
//		}
		String sql = "select count(1) from DZ_SQJCWH x where x.SQJCWHMC=:mc and x.SJXZQY_DZBM=:sjdm";
		long count2 = jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC()
					.addSQLParam("sjdm", sqjcwh.getSjxzqy_dzbm())
					.addSQLParam("mc", sqjcwh.getSqjcwhmc()));
		if (count2 > 0) {
			AlertSLEUtil.Error("该社区居村委会名称已存在");
		}
		/**
		 * 地址编码、登记人、登记单位、登记时间、撤销标记、助记符
		 */
		Date date = new Date();
		sqjcwh.setDzbm(UUIDUtil.uuid());
		sqjcwh.setDjr(SecurityUtils.getSessionUser().getUserId());
		sqjcwh.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());
		sqjcwh.setDjsj(date);
		sqjcwh.setCxbj(KConstants.CXBJ_0);
		sqjcwh.setZjf(PinyinUtil.toSzm(sqjcwh.getBmjc()));//Pinyin4jUtil.convertToSpell(sqjcwh.getBmjc())
		//如果使用状态为停用，则设置停用日期
		if (KConstants.SYTZDM_STOPUSE.equals(sqjcwh.getSyztdm())) {
			sqjcwh.setTyrq(date);
		}
		sqjcwhMapper.insertSelective(sqjcwh);
	}
	private String getRbm(String xzqhdm,List<String> listMap) {
		for(int x=0;x<10;x++){
			for(int y=0;y<10;y++){
				for( int z=1;z<10;z++){
					String str=xzqhdm+x+y+z;
					if(!listMap.contains(str)){
						return str;
					}
				}
			}
		}
		return null;
	}
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public Sqjcwh getSqjcwhById(String dbzm) {
		Sqjcwh sqjcwh=sqjcwhMapper.selectByPrimaryKeyL(dbzm);
		return sqjcwh;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateSqjcwh(Sqjcwh sqjcwh) {
		validate(sqjcwh);
		Map map = sqjcwhMapper.selectDetailByPrimaryKey(sqjcwh.getDzbm());
		//启用日期不能小于设立日期，停用日期不能小于启用日期
		Date slrq = sqjcwh.getSlrq();
		Date qyrq = (Date) map.get("QYRQ");
		Date tyrq = (Date) map.get("TYRQ");
		if (qyrq!=null&&slrq!=null) {
			if(slrq.after(qyrq)){
				AlertSLEUtil.Error("设立日期不能大于启用日期");
			}
		}
		if (tyrq!=null&&slrq!=null) {
			if(slrq.after(tyrq)){
				AlertSLEUtil.Error("设立日期不能大于停用日期");
			}
		}
		String sjxzqy_dzbm=sqjcwh.getSjxzqy_dzbm();
		Xzjd xzjd=xzjdMapper.selectByPrimaryKeyL(sjxzqy_dzbm);
		KAssert.notNull(xzjd, "未查询到所属乡镇街道");
		String xzjdDm=xzjd.getXzjddm();
		//上级社区居村委会
				String sjsqjcwh_dzbm=sqjcwh.getSjsqjcwh_dzbm();
				if(StringUtils.hasText(sjsqjcwh_dzbm)){
					String ss_sqjcwh_dm=sqjcwhMapper.selectSqjcwhdmByDzbm(sjsqjcwh_dzbm);
					if(ss_sqjcwh_dm==null||ss_sqjcwh_dm.isEmpty()){
						AlertSLEUtil.Error("未查询到上级社区居村委会");
					}
					if(!ss_sqjcwh_dm.startsWith(xzjdDm)){
						AlertSLEUtil.Error("选取上级社区居村委会,不在所属最低一级行政区域内");
					}
				}
				
		long count=sqjcwhMapper.queryCount(sqjcwh.getDzbm(),sqjcwh.getSqjcwhdm());
		if(count>0){
			AlertSLEUtil.Error("社区居村委会代码已存在");
		}
		String sql = "select count(1) from DZ_SQJCWH x where x.DZBM!=:dzbm and x.SQJCWHMC=:mc and x.SJXZQY_DZBM=:sjdm";
		long count2 = jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC()
					.addSQLParam("dzbm", sqjcwh.getDzbm())
					.addSQLParam("sjdm", sqjcwh.getSjxzqy_dzbm())
					.addSQLParam("mc", sqjcwh.getSqjcwhmc()));
		if (count2 > 0) {
			AlertSLEUtil.Error("该社区居村委会名称已存在"+count2);
		}
		/**
		 * 修改人、修改单位、更新时间
		 */
		Date date = new Date();
		sqjcwh.setXgr(SecurityUtils.getSessionUser().getUserId());
		sqjcwh.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		sqjcwh.setGxsj(date);
		
		sqjcwh.setZjf(PinyinUtil.toSzm(sqjcwh.getBmjc()));
		
		if (KConstants.SYTZDM_STOPUSE.equals(sqjcwh.getSyztdm())) {
			sqjcwh.setTyrq(date);
		}
		sqjcwhMapper.updateByPrimaryKeySelective(sqjcwh);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void revokeSqjcwh(String dzbm, String cxyy) {
		/**
		 * 撤销标记、使用状态、撤销人、撤销单位、撤销原因、撤销时间、修改人、修改单位、更新时间
		 */
		Sqjcwh sqjcwh=getSqjcwhById(dzbm);
		sqjcwh.setCxbj(KConstants.CXBJ_1);
		sqjcwh.setSyztdm(KConstants.SYTZDM_STOPUSE);
		sqjcwh.setCxr(SecurityUtils.getSessionUser().getUserId());
		sqjcwh.setCxdw(SecurityUtils.getSessionUser().getOrganizationId());
		sqjcwh.setCxyy(cxyy);
		sqjcwh.setCxsj(new Date());
		sqjcwh.setXgr(SecurityUtils.getSessionUser().getUserId());
		sqjcwh.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		sqjcwh.setGxsj(new Date());
		sqjcwh.setTyrq(new Date());//使用状态为 停用，设置停用日期20160712
		sqjcwhMapper.revokeSqjcwh(sqjcwh);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void activateSqjcwh(String dzbm) {
		/**
		 * 撤销标记、使用状态、启用时间、修改人、修改单位、更新时间
		 */
		Sqjcwh sqjcwh=getSqjcwhById(dzbm);
		sqjcwh.setCxbj(KConstants.CXBJ_0);
		sqjcwh.setSyztdm(KConstants.SYTZDM_INUSE);
		sqjcwh.setQyrq(new Date());
		sqjcwh.setXgr(SecurityUtils.getSessionUser().getUserId());
		sqjcwh.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		sqjcwh.setGxsj(new Date());
		sqjcwhMapper.activateSqjcwh(sqjcwh);
	}

	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public Map selectDetailByPrimaryKey(String dzbm) {
		if(dzbm==null){
			return null;
		}
		return sqjcwhMapper.selectDetailByPrimaryKey(dzbm);
	}
	
}
