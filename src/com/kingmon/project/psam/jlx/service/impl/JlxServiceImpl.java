package com.kingmon.project.psam.jlx.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.PinyinUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.psam.jlx.mapper.JlxMapper;
import com.kingmon.project.psam.jlx.model.Jlx;
import com.kingmon.project.psam.jlx.service.IJlxService;
import com.kingmon.project.psam.shpz.service.IShpzService;
import com.kingmon.project.psam.shpz.utils.ShpzUtil;
import com.kingmon.project.psam.sqjcwh.mapper.SqjcwhMapper;
import com.kingmon.project.psam.util.QydmData;
import com.kingmon.project.psam.xzjd.mapper.XzjdMapper;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.util.Pinyin4jUtil;

@Service
public class JlxServiceImpl extends BaseService implements IJlxService {
	@Autowired
	private JlxMapper jlxMapper;

	@Autowired
	private XzjdMapper xzjdMapper;

	@Autowired
	private XzqhMapper xzqhMapper;
	@Autowired
	private SqjcwhMapper sqjcwhMapper;

	@Autowired
	private IShpzService shpzService;

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public DataSet loadJlxDataSet(ParamObject po) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT ")
				.append(" tmp.dm as SSZDYJXZQY_DZBM,")
				.append(" tmp.mc as SSZDYJXZQY_MC,")
				.append(" x.DZBM,")
				.append(" x.JLXXQDM,")
				.append(" x.JLXXQMC,")
				.append(" x.BMJC, ")
				.append(" x.SYZTDM, ")
				.append(" x.DZYSLXDM, ")
				.append(" x.SLRQ, ")
				.append(" x.CXSJ, ")
				.append("x.GXSJ, ")
				.append("x.QYRQ, ")
				.append("x.TYRQ, ")
				.append("x.CXYY, ")
				.append("x.ZJF, ")
				.append("x.CXBJ, ")
				.append("x.SPZT, ")
				.append("x.DJR, ")
				.append("x.DJDW, ")
				.append("x.DJSJ, ")
				.append("x.XGR, ")
				.append("x.XGDW, ")
				.append("x.CXR, ")
				.append("x.CXDW, ")
				.append("x.SHPZDM, ")
				.append("x.SSJLXXQ_DZBM, ")
				.append("x.JLXXQMC as SSJLXXQ_MC ")
				.append(" @from DZ_JLX  x ")
				.append(" left join ")
				.append(" DZ_JLX  x1 ")
				.append(" on x.SSJLXXQ_DZBM=x1.DZBM ")
				.append(" left join ")
				// **************A****************
				// .append(" (  ")
				// .append("  select s.dzbm as dm ,s.sqjcwhdm as qudm,s.sqjcwhmc as mc from dz_sqjcwh s ")
				// .append("  UNION ")
				// .append("  SELECT x.dzbm as dm ,x.xzjddm as qudm,x.xzjdmc as mc FROM dz_xzjd x  ")
				// .append("  UNION ")
				// .append("  SELECT z.dzbm as dm ,z.xzqhdm as qudm,z.xzqhmc as mc FROM dz_xzqh z  ")
				// .append("  )  tmp on x.sszdyjxzqy_dzbm=tmp.dm ")
				// **************B****************
				// .append(" (  ")
				// .append("  select s.dzbm as dm ,s.sqjcwhmc as mc from dz_sqjcwh s,DZ_JLX j1 where s.dzbm= j1.sszdyjxzqy_dzbm ")
				// .append("  UNION ")
				// .append("  SELECT x.dzbm as dm ,x.xzjdmc as mc FROM dz_xzjd x,DZ_JLX j2 where x.dzbm=j2.sszdyjxzqy_dzbm  ")
				// .append("  UNION ")
				// .append("  SELECT z.dzbm as dm ,z.xzqhmc as mc FROM dz_xzqh z,DZ_JLX j3 where z.dzbm=j3.sszdyjxzqy_dzbm  ")
				// .append("  ) ")

				// **************C****************
				// .append(" ( ")
				// .append("  select s.dzbm as dm ,s.sqjcwhmc as mc from DZ_JLX j1, dz_sqjcwh s left join DZ_XZJD xz  on xz.DZBM=s.SJXZQY_DZBM")
				// .append("    where s.dzbm= j1.sszdyjxzqy_dzbm   and xz.XZJDDM like '"+xzqh+"%'")
				// .append("   UNION")
				// .append("   SELECT x.dzbm as dm ,x.xzjdmc as mc FROM DZ_JLX j2,dz_xzjd x where x.dzbm=j2.sszdyjxzqy_dzbm  and  x.xzjddm like '"+xzqh+"%'")
				// .append("   UNION")
				// .append("   SELECT z.dzbm as dm ,z.xzqhmc as mc FROM DZ_JLX j3, dz_xzqh z  where z.dzbm=j3.sszdyjxzqy_dzbm and z.xzqhdm like '"+xzqh+"%'")
				// .append("  ) tmp on x.sszdyjxzqy_dzbm=tmp.dm")
				// **************D****************
				// .append(" ( ")
				// .append("  select s.dzbm as dm ,s.sqjcwhdm as qudm, s.sqjcwhmc as mc from DZ_JLX j1, dz_sqjcwh s where s.dzbm= j1.sszdyjxzqy_dzbm  and s.sqjcwhdm like '"+xzqh+"%' ")
				// .append("  UNION")
				// .append("  SELECT x.dzbm as dm ,x.xzjddm as qudm, x.xzjdmc as mc FROM DZ_JLX j2,dz_xzjd x where x.dzbm=j2.sszdyjxzqy_dzbm  and  x.xzjddm like '"+xzqh+"%'")
				// .append("  UNION")
				// .append("  SELECT z.dzbm as dm ,z.xzqhdm as qudm, z.xzqhmc as mc FROM DZ_JLX j3, dz_xzqh z  where z.dzbm=j3.sszdyjxzqy_dzbm and z.xzqhdm like '"+xzqh+"%'")
				// .append("  ) tmp on x.sszdyjxzqy_dzbm=tmp.dm")
				// **************E****************
				// .append(" ( ")
				// .append("  select s.dzbm as dm ,s.sqjcwhdm as qudm, s.sqjcwhmc as mc from DZ_JLX j1, dz_sqjcwh s where s.dzbm= j1.sszdyjxzqy_dzbm and s.sqjcwhdm like '"+xzqh+"%'")
				// .append("  UNION")
				// .append("  SELECT x.dzbm as dm ,x.xzjddm as qudm, x.xzjdmc as mc FROM DZ_JLX j2,dz_xzjd x where x.dzbm=j2.sszdyjxzqy_dzbm  and  x.xzjddm like '"+xzqh+"%'")
				// .append("  UNION")
				// .append("  SELECT z.dzbm as dm ,z.xzqhdm as qudm, z.xzqhmc as mc FROM DZ_JLX j3, dz_xzqh z  where z.dzbm=j3.sszdyjxzqy_dzbm and z.xzqhdm like '"+xzqh+"%'")
				// .append("  ) tmp on x.sszdyjxzqy_dzbm=tmp.dm")
				// **************F****************
				.append(" ( ")
				.append("  select s.dzbm as dm ,s.sqjcwhdm as qudm, s.sqjcwhmc as mc from DZ_JLX j1, dz_sqjcwh s where s.dzbm= j1.sszdyjxzqy_dzbm ")
				.append("  UNION")
				.append("  SELECT x.dzbm as dm ,x.xzjddm as qudm, x.xzjdmc as mc FROM DZ_JLX j2,dz_xzjd x where x.dzbm=j2.sszdyjxzqy_dzbm  ")
				.append("  UNION")
				.append("  SELECT z.dzbm as dm ,z.xzqhdm as qudm, z.xzqhmc as mc FROM DZ_JLX j3, dz_xzqh z  where z.dzbm=j3.sszdyjxzqy_dzbm ")
				.append("  ) tmp on x.sszdyjxzqy_dzbm=tmp.dm")

				.append(" where 1=1 ");

		// sql.append(" and tmp.qudm like:xzjddm_data_auth ");
		// po.addSQLParam("xzjddm_data_auth", ""+xzqh+"%");
		
		//String xzqh = DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
		String xzqh =SecurityUtils.getUserLevelStr();//-zht-20160219
		if (xzqh.length() > 6) {
			xzqh = xzqh.substring(0, 6);
		}
		//sql.append(" and instr( tmp.qudm ,'" + xzqh + "',1)=1 ");
		sql.append(" and tmp.qudm like '"+xzqh+"%' ");
		
		String jlxxqdm = (String) po.getWebParam("jlxxqdm");
		if (!SubApStrUtil.isEmptyAfterTrimE(jlxxqdm)) {
			sql.append("and x.JLXXQDM like:jlxxqdm ");
			po.addSQLParam("jlxxqdm", "%" + jlxxqdm + "%");
		}
		String jlxxqmc = (String) po.getWebParam("jlxxqmc");
		if (!SubApStrUtil.isEmptyAfterTrimE(jlxxqmc)) {
			sql.append("and (x.JLXXQMC like:jlxxqmc or lower(x.zjf) like lower(:jlxxqmc))");
			po.addSQLParam("jlxxqmc", "%" + jlxxqmc + "%");
		}
		String syztdm = (String) po.getWebParam("syztdm");
		if (!SubApStrUtil.isEmptyAfterTrimE(syztdm)) {
			sql.append(" and x.SYZTDM=:syztdm ");
			po.addSQLParam("syztdm", syztdm);
		}
		String SSJLXXQ_MC = (String) po.getWebParam("ssjlxxq_mc");
		if (!SubApStrUtil.isEmptyAfterTrimE(SSJLXXQ_MC)) {
			sql.append(" and x.JLXXQMC like :ssjlxxq_mc ");
			po.addSQLParam("ssjlxxq_mc", "%" + SSJLXXQ_MC + "%");
		}

		String sszdyjxzqy_dzbm = (String) po.getWebParam("sszdyjxzqy_dzbm");
		if (!SubApStrUtil.isEmptyAfterTrimE(sszdyjxzqy_dzbm)) {
//			sql.append(" and x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm ");
//			po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
			List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(sszdyjxzqy_dzbm);
			String qydm=QydmData.getQydm(map);
			qydm+="%";
			sql.append(" and tmp.qudm like :qydm ");
			po.addSQLParam("qydm", qydm);
		}

		if (po.hasOrder()) {
			sql.append(" order by ").append("x.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}else{
			sql.append(" order by x.JLXXQDM");
		}

		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public Jlx getJlxById(String dbzm) {
		if (dbzm != null && dbzm.length() > 0) {
			return jlxMapper.selectByPrimaryKeyL(dbzm);
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public Jlx getSimpleJlxById(String dbzm) {
		if (dbzm != null && dbzm.length() > 0) {
			return jlxMapper.selectByPrimaryKey(dbzm);
		}
		return null;
	}

	/**
	 * @param jlx
	 *            数据校验
	 */
	private void validate(Jlx jlx) {
		KAssert.notNull(jlx.getJlxxqdm(), "街路巷（小区）代码不能为空");
		KAssert.notNull(jlx.getJlxxqmc(), "街路巷（小区）名称不能为空");
		KAssert.notNull(jlx.getDzyslxdm(), "地址元素类型代码不能为空");
		KAssert.notNull(jlx.getBmjc(), "别名简称不能为空");
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateJlx(Jlx jlx) {
		String spzt = jlx.getSpzt();
		int userLevel = SecurityUtils.getUserLevel();//zht-20160219
		if(spzt!=null&&KConstants.SPZT_SUCCESS.equals(spzt)){
			if(userLevel>6){
				AlertSLEUtil.Error("不能对该街路巷小区进行编辑操作");
			}
		}
		//当前 没有街路巷小区代码  把地址编码赋 给代码
		if(jlx.getJlxxqdm()==null||jlx.getJlxxqdm().isEmpty()){
			jlx.setJlxxqdm(jlx.getDzbm());
		}
		validate(jlx);
		
		Map map = jlxMapper.selectDetailByPrimaryKey(jlx.getDzbm());
		// 启用日期不能小于设立日期，停用日期不能小于启用日期
		Date slrq = jlx.getSlrq();
		Date qyrq = (Date) map.get("QYRQ");
		Date tyrq = (Date) map.get("TYRQ");
		if (qyrq != null && slrq != null) {
			if (slrq.after(qyrq)) {
				AlertSLEUtil.Error("设立日期不能大于启用日期");
			}
		}
		if (tyrq != null && slrq != null) {
			if (slrq.after(tyrq)) {
				AlertSLEUtil.Error("设立日期不能大于停用日期");
			}
		}
		long count = jlxMapper.queryCount(jlx.getDzbm(), jlx.getJlxxqdm());
		if (count > 0) {
			AlertSLEUtil.Error("街路巷（小区）已存在");
		}
		String sql = "select count(1) from DZ_JLX x where x.DZBM!=:dzbm and x.JLXXQMC=:mc and x.SSZDYJXZQY_DZBM=:sjdm";
		long count2 = jdbcBaseDao.jdbcQueryCount(sql,
									ParamObject.new_NP_NC()
									.addSQLParam("dzbm", jlx.getDzbm())
									.addSQLParam("sjdm", jlx.getSszdyjxzqy_dzbm())
									.addSQLParam("mc", jlx.getJlxxqmc()));
		if (count2 > 0) {
			AlertSLEUtil.Error("该街路巷（小区）名称已存在");
		}
		/**
		 * 修改人、修改单位、更新时间
		 */
		jlx.setXgr(SecurityUtils.getSessionUser().getUserId());
		jlx.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		jlx.setGxsj(new Date());

		jlx.setZjf(PinyinUtil.toSzm(jlx.getBmjc()));
		
		if (KConstants.SYTZDM_STOPUSE.equals(jlx.getSyztdm())) {
			jlx.setTyrq(new Date());
		}
		jlxMapper.updateByPrimaryKeySelective(jlx);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addJlx(Jlx jlx) {
		String uuid=UUIDUtil.uuid();//街路巷小区 代码 可跟主键 地址编码 一样
		jlx.setJlxxqdm(uuid);//街路巷小区代码
		validate(jlx);
		long count = jlxMapper.queryCount("add", jlx.getJlxxqdm());
		if (count > 0) {
			AlertSLEUtil.Error("街路巷（小区）代码已存在");
		}
		String sql = "select count(1) from DZ_JLX x where x.JLXXQMC=:mc and x.SSZDYJXZQY_DZBM=:sjdm";
		long count2 = jdbcBaseDao.jdbcQueryCount(sql,
						ParamObject.new_NP_NC()
						.addSQLParam("sjdm", jlx.getSszdyjxzqy_dzbm())
						.addSQLParam("mc", jlx.getJlxxqmc()));
		if (count2 > 0) {
			AlertSLEUtil.Error("该街路巷（小区）名称已存在");
		}
		/**
		 * 地址编码、登记人、登记单位、登记时间、撤销标记、助记符、审批状态、审核配置代码、设立日期、启用日期
		 */
		jlx.setDzbm(uuid);
		
		jlx.setDjr(SecurityUtils.getUserId());
		jlx.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());
		jlx.setDjsj(new Date());
		jlx.setCxbj(KConstants.CXBJ_0);
		jlx.setZjf(PinyinUtil.toSzm(jlx.getBmjc()));//Pinyin4jUtil.convertToSpell(jlx.getBmjc())
		jlx.setSpzt(KConstants.SPZT_INIT);// 未审核
		jlx.setSlrq(new Date());
		jlx.setQyrq(new Date());
		if (KConstants.SYTZDM_STOPUSE.equals(jlx.getSyztdm())) {
			jlx.setTyrq(new Date());
		}
		jlxMapper.insertSelective(jlx);
		String qydm = jlxMapper.selectQhdmBydzbm(jlx.getDzbm());
		if(qydm==null||qydm.length()<6){
			AlertSLEUtil.Error("该街路巷所属最低一级行政区划代码错误,请联系管理员！");
		}
		String dsdm = qydm.substring(0, 4) + "00";// 2.根据所属行政区域代码找出其所属地市代码 比如 370100
		// 根据所属地市找出其审核配置
		String pzdm=shpzService.findShpzDm("1", dsdm);
		if (pzdm == null) {
			AlertSLEUtil.Error("未进行审核配置，请配置过后再添加街路巷小区");
		}
		jlx.setShpzdm(pzdm);
		String spzt=ShpzUtil.getSpzt(pzdm, SecurityUtils.getUserLevel());//获取审批状态
		jlx.setSpzt(spzt);
		jlxMapper.updateByPrimaryKeySelective(jlx);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public void activateJlx(String dzbm) {
		/**
		 * 还需修改人和修改单位
		 */
		Jlx jlx = getSimpleJlxById(dzbm);
		jlx.setCxbj(KConstants.CXBJ_0);
		jlx.setSyztdm(KConstants.SYTZDM_INUSE);
		jlx.setQyrq(new Date());
		jlx.setXgr(SecurityUtils.getSessionUser().getUserId());
		jlx.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		jlx.setGxsj(new Date());
		jlxMapper.activateJlx(jlx);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void revokeJlx(String dzbm, String cxyy) {
		/**
		 * 撤销标记、使用状态、撤销人、撤销单位、撤销原因、撤销时间、修改人、修改单位、更新时间
		 */
		Jlx jlx = getSimpleJlxById(dzbm);
		jlx.setCxbj(KConstants.CXBJ_1);
		jlx.setSyztdm(KConstants.SYTZDM_STOPUSE);
		jlx.setCxr(SecurityUtils.getSessionUser().getUserId());
		jlx.setCxdw(SecurityUtils.getSessionUser().getOrganizationId());
		jlx.setCxyy(cxyy);
		jlx.setCxsj(new Date());
		jlx.setXgr(SecurityUtils.getSessionUser().getUserId());
		jlx.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		jlx.setGxsj(new Date());
		jlx.setTyrq(new Date());//使用状态为 停用，设置停用日期20160712
		jlxMapper.revokeJlx(jlx);
	}

	// ---------------------------------------------
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public DataSet loadJlxBMMCDataSet(Map<String, String> params) {
		if (params == null) {
			params = java.util.Collections.emptyMap();
		}
		PaginationUtil.initPageAndSort(params);
		String xzqhmc = params.get("JLXXQMC");
		if (xzqhmc != null && !xzqhmc.isEmpty()) {
			params.put("JLXXQMC", "%" + xzqhmc + "%");
		} else {
			params.put("JLXXQMC", null);
		}
		String SSZDYJXZQY_DZBM = params.get("SSZDYJXZQY_DZBM");
		if (SSZDYJXZQY_DZBM != null && !SSZDYJXZQY_DZBM.isEmpty()) {
			params.put("SSZDYJXZQY_DZBM", SSZDYJXZQY_DZBM);
		} else {
			String xzqh=SecurityUtils.getUserLevelStr();
			xzqh=xzqh+"00000000";
			if(xzqh.length()>6){ 
				xzqh = xzqh.substring(0,6); 
			}
			String sszdyjxzqy_dzbm=this.xzqhMapper.selectDzbmByDm(xzqh);//查询最低一级行政区划地址编码
			params.put("SSZDYJXZQY_DZBM", sszdyjxzqy_dzbm);
		}
		return new DataSet(jlxMapper.selectJlxBMMCCount(params), jlxMapper.selectJlxBMMCList(params));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void applyUseJlx(String dzbm) {
		String qydm = jlxMapper.selectQhdmBydzbm(dzbm);
		String dsdm = qydm.substring(0, 4) + "00";
		String pzdm=shpzService.findShpzDm("1", dsdm);
		if (pzdm == null) {
			AlertSLEUtil.Error("未进行审核配置，请配置过后再添加街路巷小区");
		}
		// 审核状态为“未审核”“区县审核通过”“审核通过”的不能再次申请。
		// 审核状态为 “区县审核不通过”“市局审核不通过”的可以再次申请。
		Jlx jlx = jlxMapper.selectByPrimaryKey(dzbm);
		String spzt = jlx.getSpzt();
		if (spzt == null|| KConstants.SPZT_INIT.equals(spzt)|| KConstants.SPZT_QXSH_YES.equals(spzt) || KConstants.SPZT_SUCCESS.equals(spzt)) {
			AlertSLEUtil.Error("不需要再次申请。");
		}

//		if ("0".equals(pzdm)) {// 再次申请时 如果配置为不需要审核，则直接将审核状态改为审核通过
//		// Jlx jlx = jlxMapper.selectByPrimaryKey(dzbm);
//			jlx.setSpzt(KConstants.SPZT_SUCCESS);
//			jlx.setShpzdm(pzdm);
//			jlxMapper.updateByPrimaryKeySelective(jlx);
//		} else {
//			jlxMapper.applyUseJlx(KConstants.SPZT_INIT,pzdm, dzbm);
//		}
		//2016-03-30 zht
		jlxMapper.applyUseJlx(KConstants.SPZT_INIT,pzdm, dzbm);
	}
	/**
	 * 街路巷小区审核
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void reviewJlx(String dzbm, String spzt) {
		Jlx jlx = jlxMapper.selectByPrimaryKey(dzbm);
		String qydm = jlxMapper.selectQhdmBydzbm(dzbm);
		if(qydm==null||qydm.length()<6){
			AlertSLEUtil.Error("该街路巷所属最低一级行政区划代码错误,请联系管理员！");
		}
		String dsdm = qydm.substring(0, 4) + "00";
		String pzdm=shpzService.findShpzDm("1", dsdm);
		if (pzdm == null) {
			AlertSLEUtil.Error("未进行审核配置，请先配置");
		}
		jlx.setShpzdm(pzdm);// 审核配置代码
		String setSpzt=ShpzUtil.getShResult(pzdm, SecurityUtils.getUserLevel(), spzt, jlx.getSpzt());
		jlx.setSpzt(setSpzt);
		jlxMapper.updateByPrimaryKeySelective(jlx);
	}

	@Override
	public DataSet loadJlxAjaxData(ParamObject po, String sszdyjxzqy_dzbm, String sszdyjxzqy_xtype, String isLoadFromSuperXzqy) {

//		return loadJlxAjaxDataX(po, sszdyjxzqy_dzbm, sszdyjxzqy_xtype, isLoadFromSuperXzqy);
		return loadJlxAjaxDataXX(po, sszdyjxzqy_dzbm, sszdyjxzqy_xtype, isLoadFromSuperXzqy);
	}

	/**
	 * 
	 * @param paramObject
	 * @param sszdyjxzqy_dzbm
	 *            所属最低一级行政区域代码
	 * @param sszdyjxzqy_xtype
	 *            sszdyjxzqy_dzbm 是什么类型 xzqh， xzjd， sqjcwh
	 * @param isLoadFromSuperXzqy
	 *            是否从sszdyjxzqy_dzbm 0：只有本级节点； 1:本节点 + 上级节点 ，两个节点的数据，； 2:本级节点，上级节点一直到市级节点
	 * @return
	 */
	private DataSet loadJlxAjaxDataX(ParamObject po, String sszdyjxzqy_dzbm, String sszdyjxzqy_xtype, String isLoadFromSuperXzqy) {

		if (isLoadFromSuperXzqy == null || isLoadFromSuperXzqy.length() == 0) {
			isLoadFromSuperXzqy = "2";
		}
		if (sszdyjxzqy_dzbm == null || sszdyjxzqy_dzbm.length() == 0) {
			return DataSet.newDs();
		}
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT ").append(" x.DZBM,").append(" x.JLXXQDM,").append(" x.JLXXQMC,").append(" x.SYZTDM, ").append(" x.ZJF ").append(" @from DZ_JLX  x ")
				.append(" where 1=1 and x.syztdm=10 ");
		if ("0".equals(isLoadFromSuperXzqy)) {
			sql.append(" and x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm ");
			po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);

		} else if ("1".equals(isLoadFromSuperXzqy)) {
			String sj_xzqu_dzbm = "" + get_sj_xzqu_dzbm(sszdyjxzqy_xtype, sszdyjxzqy_dzbm);
			sql.append(" and (x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm or  x.SSZDYJXZQY_DZBM=:sj_xzqu_dzbm)");
			po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
			po.addSQLParam("sj_xzqu_dzbm", sj_xzqu_dzbm);

		} else if ("2".equals(isLoadFromSuperXzqy)) {

			List<String> sj_xzqu_dzbm_upto_shi_list = get_sj_xzqu_dzbm_upto_shi_list(sszdyjxzqy_xtype, sszdyjxzqy_dzbm);
			StringBuffer sb = new StringBuffer("");
			if (sj_xzqu_dzbm_upto_shi_list != null && sj_xzqu_dzbm_upto_shi_list.size() > 0) {
				int index = 0;
				for (String sjdzbm : sj_xzqu_dzbm_upto_shi_list) {
					String paramName = "param_" + index;
					sb.append("  or  x.SSZDYJXZQY_DZBM=:" + paramName + " ");
					po.addSQLParam(paramName, sjdzbm);
					index++;
				}
				sql.append(" and (x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm " + sb.toString() + " )");
				po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
			} else {
				sql.append(" and (x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm  )");
				po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
			}
		} else {
			return DataSet.newDs();
		}

		String jlxxqdm = (String) po.getWebParam("jlxxqdm");
		if (!SubApStrUtil.isEmptyAfterTrimE(jlxxqdm)) {
			sql.append("and JLXXQDM like:jlxxqdm ");
			po.addSQLParam("jlxxqdm", "%" + jlxxqdm + "%");
		}
		String jlxxqmc = (String) po.getWebParam("jlxxqmc");
		if (!SubApStrUtil.isEmptyAfterTrimE(jlxxqmc)) {
			sql.append("and (JLXXQMC like:jlxxqmc or lower(zjf) like lower(:jlxxqmc))");
			po.addSQLParam("jlxxqmc", "%" + jlxxqmc + "%");
		}

		if (po.hasOrder()) {
			sql.append(" order by ").append("x.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	// 获取更上一级的数据
	private String get_sj_xzqu_dzbm(String sszdyjxzqy_xtype, String sszdyjxzqy_dzbm) {
		if ("SQJCWH".equals(sszdyjxzqy_xtype) || "sqjcwh".equals(sszdyjxzqy_xtype)) {
			String szjd_Dzbm = sqjcwhMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
			if (szjd_Dzbm != null && !szjd_Dzbm.isEmpty()) {
				return szjd_Dzbm;
			}
		} else if ("XZJD".equals(sszdyjxzqy_xtype) || "xzjd".equals(sszdyjxzqy_xtype)) {
			String xzqh_xian_Dzbm = xzjdMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
			if (xzqh_xian_Dzbm != null && !xzqh_xian_Dzbm.isEmpty()) {
				return xzqh_xian_Dzbm;
			}
		} else if ("XZQH".equals(sszdyjxzqy_xtype) || "xzqh".equals(sszdyjxzqy_xtype)) {
			String sj_xzqh_dzbm = xzqhMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
			if (sj_xzqh_dzbm != null && !sj_xzqh_dzbm.isEmpty()) {
				return sj_xzqh_dzbm;
			}
		}
		return null;
	}

	private List<String> get_sj_xzqu_dzbm_upto_shi_list(String sszdyjxzqy_xtype, String sszdyjxzqy_dzbm) {
		List<String> dzbm = Lists.newArrayList();
		if ("SQJCWH".equals(sszdyjxzqy_xtype) || "sqjcwh".equals(sszdyjxzqy_xtype)) {
			// dzbm.add(sszdyjxzqy_dzbm);// 最第一级 自身（村委会）
			String szjd_Dzbm = sqjcwhMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
			if (szjd_Dzbm != null && !szjd_Dzbm.isEmpty()) {
				dzbm.add(szjd_Dzbm);// 乡镇街道
				String xzqh_xian_Dzbm = xzjdMapper.selectSjxzqyDzbmByKey(szjd_Dzbm);
				if (xzqh_xian_Dzbm != null && !xzqh_xian_Dzbm.isEmpty()) {
					dzbm.add(xzqh_xian_Dzbm);// 区县
					String xzqh_shi_Dzbm = xzqhMapper.selectSjxzqyDzbmByKey(xzqh_xian_Dzbm);
					if (xzqh_shi_Dzbm != null && !xzqh_shi_Dzbm.isEmpty()) {
						dzbm.add(xzqh_shi_Dzbm);// 市
					}
				}
			}
		} else if ("XZJD".equals(sszdyjxzqy_xtype) || "xzjd".equals(sszdyjxzqy_xtype)) {
			// dzbm.add(sszdyjxzqy_dzbm);
			String xzqh_xian_Dzbm = xzjdMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
			if (xzqh_xian_Dzbm != null && !xzqh_xian_Dzbm.isEmpty()) {
				dzbm.add(xzqh_xian_Dzbm);
				String xzqh_shi_Dzbm = xzqhMapper.selectSjxzqyDzbmByKey(xzqh_xian_Dzbm);
				if (xzqh_shi_Dzbm != null && !xzqh_shi_Dzbm.isEmpty()) {
					dzbm.add(xzqh_shi_Dzbm);
				}

			}
		} else if ("XZQH".equals(sszdyjxzqy_xtype) || "xzqh".equals(sszdyjxzqy_xtype)) {

			String dzdm = xzqhMapper.selectXzqhdmById(sszdyjxzqy_dzbm);
			String xx = SubApStrUtil.removeLastChars(dzdm, "0");
			if (xx.length() == 6) {// dzdm == 县区
				dzbm.add(sszdyjxzqy_dzbm);
				// String xzqh_shi_Dzbm= xzqhMapper.selectXzqhdmById(sszdyjxzqy_dzbm);
				String xzqh_shi_Dzbm = xzqhMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
				if (xzqh_shi_Dzbm != null && !xzqh_shi_Dzbm.isEmpty()) {
					dzbm.add(xzqh_shi_Dzbm);
				}
			} else if (xx.length() == 4) {// dzdm == 市

				// dzbm.add(sszdyjxzqy_dzbm);

			} else if (xx.length() == 2) {// dzdm== 省
				// dzbm.add(sszdyjxzqy_dzbm);
			}
		}

		return dzbm;
	}

	
	/**
	 * 
	 * @param paramObject
	 * @param sszdyjxzqy_dzbm
	 *            所属最低一级行政区域代码
	 * @param sszdyjxzqy_xtype
	 *            sszdyjxzqy_dzbm 是什么类型 xzqh， xzjd， sqjcwh
	 * @param isLoadFromSuperXzqy
	 *            是否从sszdyjxzqy_dzbm 0：只有本级节点； 1:本节点 + 上级节点 ，两个节点的数据，； 2:本级节点，上级节点一直到市级节点
	 * @return
	 */
	private DataSet loadJlxAjaxDataXX(ParamObject po, String sszdyjxzqy_dzbm, String sszdyjxzqy_xtype, String isLoadFromSuperXzqy) {

		if (isLoadFromSuperXzqy == null || isLoadFromSuperXzqy.length() == 0) {
			isLoadFromSuperXzqy = "2";
		}
		if (sszdyjxzqy_dzbm == null || sszdyjxzqy_dzbm.length() == 0) {
			return DataSet.newDs();
		}
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT ").append(" x.DZBM,").append(" x.JLXXQDM,")
		   .append(" tmp.mc as MC, ").append(" x.SSZDYJXZQY_DZBM,")
		   .append(" x.JLXXQMC,").append(" x.SYZTDM, ").append(" x.ZJF ").append(" @from DZ_JLX  x ")
				.append(" left join ")
//				.append(" ( ")
//				.append("  select s.dzbm as dm ,s.sqjcwhdm as qudm, s.sqjcwhmc as mc from DZ_JLX j1, dz_sqjcwh s where s.dzbm= j1.sszdyjxzqy_dzbm ")
//				.append("  UNION")
//				.append("  SELECT x.dzbm as dm ,x.xzjddm as qudm, x.xzjdmc as mc FROM DZ_JLX j2,dz_xzjd x where x.dzbm=j2.sszdyjxzqy_dzbm  ")
//				.append("  UNION")
//				.append("  SELECT z.dzbm as dm ,z.xzqhdm as qudm, z.xzqhmc as mc FROM DZ_JLX j3, dz_xzqh z  where z.dzbm=j3.sszdyjxzqy_dzbm ")
//				.append("  ) tmp on x.sszdyjxzqy_dzbm=tmp.dm")
				.append(" ( ")
				.append("  select s.dzbm as dm ,s.sqjcwhdm as qudm, s.sqjcwhmc as mc from  dz_sqjcwh s  ")
				.append("  UNION")
				.append("  SELECT x.dzbm as dm ,x.xzjddm as qudm, x.xzjdmc as mc FROM dz_xzjd x  ")
				.append("  UNION")
				.append("  SELECT z.dzbm as dm ,z.xzqhdm as qudm, z.xzqhmc as mc FROM  dz_xzqh z   ")
				.append("  ) tmp on x.sszdyjxzqy_dzbm=tmp.dm")
		        .append(" where 1=1 and x.syztdm=10 ");
		if ("0".equals(isLoadFromSuperXzqy)) {
			sql.append(" and x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm ");
			po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);

		} else if ("1".equals(isLoadFromSuperXzqy)) {
			String sj_xzqu_dzbm = "" + get_sj_xzqu_dzbm(sszdyjxzqy_xtype, sszdyjxzqy_dzbm);
			sql.append(" and (x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm or  x.SSZDYJXZQY_DZBM=:sj_xzqu_dzbm)");
			po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
			po.addSQLParam("sj_xzqu_dzbm", sj_xzqu_dzbm);

		} else if ("2".equals(isLoadFromSuperXzqy)) {

			List<String> sj_xzqu_dzbm_upto_shi_list = get_sj_xzqu_dzbm_upto_shi_list2(sszdyjxzqy_xtype, sszdyjxzqy_dzbm);
			StringBuffer sb = new StringBuffer("");
			if (sj_xzqu_dzbm_upto_shi_list != null && sj_xzqu_dzbm_upto_shi_list.size() > 0) {
				int index = 0;
				for (String sjdzbm : sj_xzqu_dzbm_upto_shi_list) {
					String paramName = "param_" + index;
					sb.append("  or  x.SSZDYJXZQY_DZBM=:" + paramName + " ");
					po.addSQLParam(paramName, sjdzbm);
					index++;
				}
				//根据最低一级行政区划地址编码查询代码
				String dzdm = xzqhMapper.selectXzqhdmById(sszdyjxzqy_dzbm);
				String xx="";
				if(dzdm!=null&& !dzdm.isEmpty()){
					 xx= SubApStrUtil.removeLastChars(dzdm, "0");
					if(xx.length()==3){
						xx=xx+"0";
					}
				}
				sql.append(" and (x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm  or tmp.qudm like :xx " + sb.toString() + " )");
				po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
				if(xx!=null&& !xx.isEmpty()){
					po.addSQLParam("xx", xx+"%");
				}else{
					po.addSQLParam("xx", xx);
				}
			} else {
				sql.append(" and (x.SSZDYJXZQY_DZBM=:sszdyjxzqy_dzbm  )");
				po.addSQLParam("sszdyjxzqy_dzbm", sszdyjxzqy_dzbm);
			}
		} else {
			return DataSet.newDs();
		}

		String jlxxqdm = (String) po.getWebParam("jlxxqdm");
		if (!SubApStrUtil.isEmptyAfterTrimE(jlxxqdm)) {
			sql.append("and JLXXQDM like:jlxxqdm ");
			po.addSQLParam("jlxxqdm", "%" + jlxxqdm + "%");
		}
		String jlxxqmc = (String) po.getWebParam("jlxxqmc");
		if (!SubApStrUtil.isEmptyAfterTrimE(jlxxqmc)) {
			sql.append("and (JLXXQMC like:jlxxqmc or lower(zjf) like lower(:jlxxqmc))");
			po.addSQLParam("jlxxqmc", "%" + jlxxqmc + "%");
		}

		if (po.hasOrder()) {
			sql.append(" order by ").append("x.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	private List<String> get_sj_xzqu_dzbm_upto_shi_list2(String sszdyjxzqy_xtype, String sszdyjxzqy_dzbm) {
		List<String> dzbm = Lists.newArrayList();
		if ("SQJCWH".equals(sszdyjxzqy_xtype) || "sqjcwh".equals(sszdyjxzqy_xtype)) {
			// dzbm.add(sszdyjxzqy_dzbm);// 最第一级 自身（村委会）
			String szjd_Dzbm = sqjcwhMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
			if (szjd_Dzbm != null && !szjd_Dzbm.isEmpty()) {
				dzbm.add(szjd_Dzbm);// 乡镇街道
				String xzqh_xian_Dzbm = xzjdMapper.selectSjxzqyDzbmByKey(szjd_Dzbm);
				if (xzqh_xian_Dzbm != null && !xzqh_xian_Dzbm.isEmpty()) {
					dzbm.add(xzqh_xian_Dzbm);// 区县
					String xzqh_shi_Dzbm = xzqhMapper.selectSjxzqyDzbmByKey(xzqh_xian_Dzbm);
					if (xzqh_shi_Dzbm != null && !xzqh_shi_Dzbm.isEmpty()) {
						dzbm.add(xzqh_shi_Dzbm);// 市
					}
				}
			}
		} else if ("XZJD".equals(sszdyjxzqy_xtype) || "xzjd".equals(sszdyjxzqy_xtype)) {
			// dzbm.add(sszdyjxzqy_dzbm);
			String xzqh_xian_Dzbm = xzjdMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
			if (xzqh_xian_Dzbm != null && !xzqh_xian_Dzbm.isEmpty()) {
				dzbm.add(xzqh_xian_Dzbm);
				String xzqh_shi_Dzbm = xzqhMapper.selectSjxzqyDzbmByKey(xzqh_xian_Dzbm);
				if (xzqh_shi_Dzbm != null && !xzqh_shi_Dzbm.isEmpty()) {
					dzbm.add(xzqh_shi_Dzbm);
				}

			}
		} else if ("XZQH".equals(sszdyjxzqy_xtype) || "xzqh".equals(sszdyjxzqy_xtype)) {

			String dzdm = xzqhMapper.selectXzqhdmById(sszdyjxzqy_dzbm);
			String xx = SubApStrUtil.removeLastChars(dzdm, "0");
			if (xx.length() == 6) {// dzdm == 县区
				dzbm.add(sszdyjxzqy_dzbm);
				// String xzqh_shi_Dzbm= xzqhMapper.selectXzqhdmById(sszdyjxzqy_dzbm);
				String xzqh_shi_Dzbm = xzqhMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
				if (xzqh_shi_Dzbm != null && !xzqh_shi_Dzbm.isEmpty()) {
					dzbm.add(xzqh_shi_Dzbm);
					String xzqh_shangji_Dzbm=xzqhMapper.selectSjxzqyDzbmByKey(xzqh_shi_Dzbm);
					if(xzqh_shangji_Dzbm!= null && !xzqh_shangji_Dzbm.isEmpty()){
						dzbm.add(xzqh_shangji_Dzbm);
					}
				}
			} else if (xx.length() == 4) {// dzdm == 市

				dzbm.add(sszdyjxzqy_dzbm);
				String xzqh_shi_Dzbm = xzqhMapper.selectSjxzqyDzbmByKey(sszdyjxzqy_dzbm);
				if (xzqh_shi_Dzbm != null && !xzqh_shi_Dzbm.isEmpty()) {
					dzbm.add(xzqh_shi_Dzbm);
				}
			} else if (xx.length() == 2) {// dzdm== 省
				// dzbm.add(sszdyjxzqy_dzbm);
			}
		}

		return dzbm;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public Map selectDetailByPrimaryKey(String dzbm) {
		if (dzbm == null) {
			return null;
		}
		return jlxMapper.selectDetailByPrimaryKey(dzbm);
	}

	public String getJlxSszdyjxzqyType(String jlx_sszdyjxzqy_dzbm) {
		if (!org.springframework.util.StringUtils.hasText(jlx_sszdyjxzqy_dzbm)) {
			return null;
		}
		String sql = "select 1 from dz_xzqh where dzbm=:sszdyjxzqy_dzbm";
		List<?> list = jdbcBaseDao.jdbcQueryMapList(sql, ParamObject.new_NP_NC().addSQLParam("sszdyjxzqy_dzbm", jlx_sszdyjxzqy_dzbm));
		if (list != null && list.size() > 0) {
			return "xzqh";
		}
		sql = "select 1 from dz_xzjd where dzbm=:sszdyjxzqy_dzbm";
		list = jdbcBaseDao.jdbcQueryMapList(sql, ParamObject.new_NP_NC().addSQLParam("sszdyjxzqy_dzbm", jlx_sszdyjxzqy_dzbm));
		if (list != null && list.size() > 0) {
			return "xzjd";
		}
		sql = "select 1 from DZ_SQJCWH where dzbm=:sszdyjxzqy_dzbm";
		list = jdbcBaseDao.jdbcQueryMapList(sql, ParamObject.new_NP_NC().addSQLParam("sszdyjxzqy_dzbm", jlx_sszdyjxzqy_dzbm));
		if (list != null && list.size() > 0) {
			return "sqjcwh";
		}
		return null;
	}
	
	public List<Map<String,String>>selectXzqyMapByDzbm(String dzbm){
		if(dzbm==null||dzbm.isEmpty()){
			String xzqh=SecurityUtils.getUserLevelStr();
			xzqh=xzqh+"00000000";
			if(xzqh.length()>6){ 
				xzqh = xzqh.substring(0,6); 
			}
			dzbm=xzqhMapper.selectDzbmByDm(xzqh);
		}
		List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(dzbm);
		return map;
	}
	
	public Map<String,Object> selectXzqhmcBySszdyjxzqyDzbm(String dzbm){
		if(dzbm==null||dzbm.isEmpty()){
			return null;
		}
		Map<String,Object> map=jlxMapper.selectZdyjxzqyByDzbm(dzbm);
		if(map.size()==0||map==null){
			return null;
		}
//		String name=(String)map.get("SSZDYJXZQY_DZBM");
		return map;
	}
}
