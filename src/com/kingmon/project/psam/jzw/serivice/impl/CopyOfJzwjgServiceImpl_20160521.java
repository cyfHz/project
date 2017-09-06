//package com.kingmon.project.psam.jzw.serivice.impl;
//
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.kingmon.base.common.KConstants;
//import com.kingmon.base.data.DataSet;
//import com.kingmon.base.data.KJSONMSG;
//import com.kingmon.base.data.ParamObject;
//import com.kingmon.base.exception.ServiceLogicalException;
//import com.kingmon.base.service.BaseService;
//import com.kingmon.base.util.AlertSLEUtil;
//import com.kingmon.base.util.KAssert;
//import com.kingmon.base.util.PaginationUtil;
//import com.kingmon.common.authUtil.SecurityUtils;
//import com.kingmon.project.psam.jzw.JzwJgNewUtil;
//import com.kingmon.project.psam.jzw.JzwJgUtil;
//import com.kingmon.project.psam.jzw.mapper.JzwdyMapper;
//import com.kingmon.project.psam.jzw.mapper.JzwfjMapper;
//import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
//import com.kingmon.project.psam.jzw.mapper.JzwlcMapper;
//import com.kingmon.project.psam.jzw.model.Jzwdy;
//import com.kingmon.project.psam.jzw.model.Jzwfj;
//import com.kingmon.project.psam.jzw.model.Jzwjg;
//import com.kingmon.project.psam.jzw.model.Jzwlc;
//import com.kingmon.project.psam.jzw.serivice.IJzwjgService;
//import com.kingmon.project.psam.jzw.view.JzwFjShowInPage;
////@Service
//public class CopyOfJzwjgServiceImpl_20160521 extends BaseService implements IJzwjgService{
//
//	@Autowired
//	private JzwjgMapper jzwjgMapper;
//	@Autowired
//	private JzwfjMapper jzwfjMapper;
//	@Autowired
//	private JzwlcMapper jzwlcMapper;
//	@Autowired
//	private JzwdyMapper jzwdyMapper;
//	
//	@Transactional(rollbackFor=Exception.class,readOnly=true)
//	@Override
//	public Jzwjg findJustJzwjgByJzwId(String jzwid) {
//		if(!StringUtils.hasText(jzwid)){
//			return null;
//		}
//		return jzwjgMapper.selectByJzwId(jzwid);
//	}
//	
//	@Transactional(rollbackFor=Exception.class,readOnly=true)
//	@Override
//	public String findJustJzwIdByJgid(String jzwjgId) {
//		if(StringUtils.hasText(jzwjgId)){
//			return jzwjgMapper.selectJzwidByJzwjgid(jzwjgId);
//		}
//		return null;
//	}
//	
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public Jzwjg findValidateAndBuildJzwjgById(String jzwjgId) {
//		if(!StringUtils.hasText(jzwjgId)){
//			AlertSLEUtil.Error("请选择建筑物");
//		}
//		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgId);
//		KAssert.notNull(jzwjg,"未查询到建筑物结构");
//		
//		List<Jzwdy> sortedJzwdyListx=jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgId);
//		List<Jzwlc> sortedJzwlcListx=jzwlcMapper.selectSortedJzwlcByJzwJgid(jzwjgId);
//		List<Jzwfj> sortedJzwfjListx=jzwfjMapper.selectSortedFjByJzwjgId(jzwjgId);
//		
//		KJSONMSG mgs=JzwJgNewUtil.validateJzwjg(jzwjg, sortedJzwdyListx, sortedJzwlcListx, sortedJzwfjListx);
//		if(mgs.getStatusCode()!=200){
//			AlertSLEUtil.Error(mgs.getMessage());
//		}
//		//校验通过，但是此时还没有更新Isvalid字段，则进行更新  Isvalid=1，
//		if(jzwjg.getIsvalid()==null||!(KConstants.ISVALID_JZWJG_YES.equals(jzwjg.getIsvalid()))){
//			jzwjg.setIsvalid(KConstants.ISVALID_JZWJG_YES);
//			jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
//		}
//		
//		//校验通过，但是此时还没有生成 房间坐标，此时要进行坐标生成
//		if (jzwjg.getIsbuild()==null||!(KConstants.ISBUILD_JZWJG.equals(jzwjg.getIsbuild()))) {
//			JzwJgNewUtil.initBuildJzwfjZB( jzwjg,sortedJzwdyListx,sortedJzwlcListx,sortedJzwfjListx, jzwfjMapper,jzwlcMapper,jzwdyMapper,jzwjgMapper);
//			jzwjg.setIsbuild(KConstants.ISBUILD_JZWJG);
//		}
//		return jzwjg;
//		
//	}
//
//	/*
//	{"jzwjg":{"dys":"4","lcs":"10","mdyms":"2","dxcs":"3","dxmcms":"2"},
//	"jzwId":"ed28d485-a5f4-4b17-9efb-eae1e6ba243c",
//	"prefixRules":[{"from":"1","to":"3","value":"A"},
//				   {"from":"4","to":"6","value":"B"}],
//	"postfixAndWidthRule":{"postfix_dy":"单元","postfix_lc":"层","postfix_fj":"室",
//						   "width_dy":"2","width_lc":"3","width_fj":"2"},
//	"type":"1"}
//	*/
//	//type:地下室 : 0：无地下室 1：规则分单元地下室    2：不规则地下室
//	//0：无地下室:     view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"type":"0"};
//	//1：规则分单元地下室:view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"type":"1"};
//	//2：地下房间不规则排列:view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"dxlc":dxlc,"type":"2"};
//	
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public void addAnddBuildJzwjg(Map<?,?> view) {
//		boolean isRegular = Boolean.parseBoolean((String)(view.get("isRegular")));//单元楼层房间是否规则分布
//		if (isRegular) {
//			addJzwjgRegular(view);
//		}else{
//			addJzwjgNoRegular(view);
//		}
//	}
//	@Transactional(rollbackFor = Exception.class)
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void addJzwjgRegular(Map view) {
//		String jzwId = (String) (view.get("jzwId"));// 建筑物Id
//		KAssert.notNull(jzwId, "请选择建筑物");
//		validateAddJzwJg(view);// 数据校验
//		Long t1 = System.currentTimeMillis();
//		Date date = new Date();
//		// 单元/楼层/房间 后缀 ,提取单元号/楼层号/房间号 位数--->postfix_dy,postfix_lc,postfix_fj,width_dy,width_lc,width_fj
//		Map<String, Object> postfixAndWidthRule = (Map<String, Object>) view.get("postfixAndWidthRule");
//
//		postfixAndWidthRule = (Map<String, Object>) (postfixAndWidthRule == null ? Maps.newHashMap() : postfixAndWidthRule);
//
//		Map<String, Object> jzwjgMap = (Map<String, Object>) view.get("jzwjg");// 建筑物结构View
//
//		List<Map<String, Object>> prefixRules = (List<Map<String, Object>>) view.get("prefixRules");
//		prefixRules=(prefixRules==null?new ArrayList<Map<String, Object>>():prefixRules);
//		String type = (String) view.get("type");// 0：无地下室 1：规则分单元地下室 2：不规则地下室
//
//		String postfix_dy = (String) (postfixAndWidthRule.get("postfix_dy"));// 单元后缀--单元名称用到
//		postfix_dy = (postfix_dy == null ? "" : postfix_dy);
//
//		String postfix_lc = (String) (postfixAndWidthRule.get("postfix_lc"));// 楼层后缀--楼层名称用到
//		postfix_lc = (postfix_lc == null ? "" : postfix_lc);
//
//		String postfix_fj = (String) (postfixAndWidthRule.get("postfix_fj"));// 房间后缀--房间名称用到
//		postfix_fj = (postfix_fj == null ? "" : postfix_fj);
//
//		int width_dy = Integer.parseInt((String) (postfixAndWidthRule.get("width_dy")));// 单元号位数--单元名称用到
//		int width_lc = Integer.parseInt((String) (postfixAndWidthRule.get("width_lc")));// 楼层号位数--楼层名称用到
//		int width_fj = Integer.parseInt((String) (postfixAndWidthRule.get("width_fj")));// 房间号位数--房间名称用到
//
//		String dys_ = (String) (jzwjgMap.get("dys"));// 建筑物结构-单元数
//		String lcs_ = (String) (jzwjgMap.get("lcs"));// 建筑物结构-楼层数
//		String mdyms = (String) (jzwjgMap.get("mdyms"));// 建筑物结构-单元门数
//
//		String dxcs = (String) (jzwjgMap.get("dxcs"));// 建筑物结构-地下层数
//		String dxmcms = (String) (jzwjgMap.get("dxmcms"));// 建筑物结构-地下每单元门数
//		dxcs = (dxcs == null || "".equals(dxcs)) ? "0" : dxcs;
//		dxmcms = (dxmcms == null || "".equals(dxmcms)) ? "0" : dxmcms;
//
//		short dys = Short.MIN_VALUE, lcs = Short.MIN_VALUE, fjs = Short.MIN_VALUE, dxcsx = Short.MIN_VALUE, dxmcmsx = Short.MIN_VALUE;
//		try {
//			dys = Short.parseShort(dys_);
//			if (dys < 0) {
//				AlertSLEUtil.Error("单元数不能小于0");
//			}
//			lcs = Short.parseShort(lcs_);
//			if (lcs < 0) {
//				AlertSLEUtil.Error("楼层数不能小于0");
//			}
//			fjs = Short.parseShort(mdyms);
//			if (fjs < 0) {
//				AlertSLEUtil.Error("单元门数不能小于0");
//			}
//			dxcsx = Short.parseShort(dxcs);
//			if (dxcsx < 0) {
//				AlertSLEUtil.Error("地下楼层数不能小于0");
//			}
//			if (dxcsx > 100) {
//				AlertSLEUtil.Error("地下楼层数不能大于100");
//			}
//			dxmcmsx = Short.parseShort(dxmcms);
//			if (dxmcmsx < 0) {
//				AlertSLEUtil.Error("地下单元门数不能小于0");
//			}
//		} catch (Exception e) {
//			throw new ServiceLogicalException("数据转化错误，请检查楼层数、单元数、地下层数、地下每层门数等是否是数字");
//		}
//		// 构造/建筑物结构model
//		Jzwjg jzwjg = new Jzwjg(jzwId, jzwId, jzwId, dys, lcs, fjs, dxcsx, dxmcmsx, "1", "1", date, date, "1", "1");
//		jzwjg.setDxType(type);//地下室分布类型s
//		jzwjgMapper.insertSelective(jzwjg);
//		// ------------------------------------创建建筑物结构开始-------------------------------------------------------------------------------
//		ArrayList<Jzwfj> jzwfjList = Lists.newArrayList();
//		// 作为不规则地下楼层及房间的统一单元
//		String dxJzwdyId = "";
//
//		// 添加地上楼层房间
//		for (int d = 0; d < dys; d++) {
//			// 处理单元名称 长度+后缀
//			String dymc = JzwJgUtil.processWithAndPostfix((d + 1) + "", width_dy, postfix_dy);
////			Jzwdy jzwdy = JzwJgUtil.constructJzwDy(jzwjg.getJzwjgid(), JzwJgUtil.processDyLcXh(("" + (d + 1)), 3), dymc, (short)(lcs+dxcsx), fjs);
//			Jzwdy jzwdy = JzwJgUtil.constructJzwDy(jzwjg.getJzwjgid(), JzwJgUtil.processDyLcXh(("" + (d + 1)), 3), dymc, (short)(lcs), fjs);
//			jzwdyMapper.insertSelective(jzwdy);
//			if (d == 0) {// 处理 地下房间不依附单元的情况，地下房间，全部默认在第一单元
//				dxJzwdyId = jzwdy.getJzwdyid();
//			}
//
//			for (int c = 0; c < lcs; c++) {
//				// 处理楼层名称 长度+后缀
//				String lcmc = JzwJgUtil.processWithAndPostfix((c + 1) + "", width_lc, postfix_lc);
//				Jzwlc jzwlc = JzwJgUtil.constructJzwLc(jzwjg.getJzwjgid(), jzwdy.getJzwdyid(), "" + (c + 1), lcmc);
//				jzwlcMapper.insertSelective(jzwlc);
//
//				for (int f = 0; f < fjs; f++) {
//					// 构造房间
//					int width = KConstants.jzwWidth;// 120;
//					int height = KConstants.jzwHeight;// 70;
//					int left = ((fjs * d) + f) * width;
//					int top = (lcs - (c + 1)) * height;
//					// 处理房间名称 长度+后缀
//					String fjmc = (c + 1) + ((f + 1) < 10 ? ("0" + (f + 1)) : ("" + (f + 1)));
//					fjmc = JzwJgUtil.processWithAndPostfix(fjmc, width_fj, postfix_fj);
//
//					// 房间名称添加前缀
//					int size = prefixRules.size();
//					for (int i = 0; i < size; i++) {
//						Map<String, Object> map = prefixRules.get(i);
//						int from = Integer.MIN_VALUE;
//						int to = Integer.MIN_VALUE;
//						try {
//							from = Integer.parseInt((String) map.get("from"));
//							if (from < 0) {
//								AlertSLEUtil.Error("房间号前缀起始楼层不能小于0");
//							}
//							to = Integer.parseInt((String) map.get("to"));
//							if (to < 0) {
//								AlertSLEUtil.Error("房间号前缀结束楼层不能小于0");
//							}
//						} catch (Exception e) {
//							throw new ServiceLogicalException("数据转化错误，请检查房间号前缀起始、结束楼层是否是数字");
//						}
//						String value = (String) map.get("value");// 前缀
//						if ((c + 1 >= from) && (c + 1 <= to)) {
//							fjmc = value + fjmc;
//						}
//					}
//					// 不再带有单元楼层前缀
//					String fjxh = (c + 1) + ((f + 1) < 10 ? ("0" + (f + 1)) : ("" + (f + 1)));
//					JzwFjShowInPage page = new JzwFjShowInPage(1, left, top, width, height);
//					Jzwfj jzwfj = JzwJgUtil.constructJzwFj(jzwjg.getJzwjgid(), jzwdy.getJzwdyid(), jzwlc.getJzwlcid(), fjxh, fjmc, page);
//					jzwfj.setCreatedby(SecurityUtils.getUserId());
//					jzwfjList.add(jzwfj);
//				}
//			}
//			// 添加规则分单元地下室
//			if ("1".equals(type)) {
//				int ls_totalH = lcs * KConstants.jzwHeight;
//				for (int c = 0; c < dxcsx; c++) {// 遍历地下层数
//					// 处理楼层名称 长度+后缀
//					String lcmc = "-" + JzwJgUtil.processWithAndPostfix((c + 1) + "", width_lc, postfix_lc);
//
//					Jzwlc jzwlc = JzwJgUtil.constructJzwLc(jzwjg.getJzwjgid(), jzwdy.getJzwdyid(), "-" + (c + 1), lcmc);
//
//					jzwlcMapper.insertSelective(jzwlc);
//
//					for (int f = 0; f < dxmcmsx; f++) {// 地下每层门数（规则情况下与楼上每层门数相同）
//
//						int dx_width = KConstants.jzwWidth;// 120;
//						int dx_height = KConstants.jzwHeight;// 70;
//						int dx_left = ((dxmcmsx * d) + f) * dx_width;
//						int dx_top = c * dx_height + ls_totalH;
//						// 处理房间名称 长度+后缀
//						String fjmc = "-" + (c + 1) + ((f + 1) < 10 ? ("0" + (f + 1)) : ("" + (f + 1)));
//						fjmc = JzwJgUtil.processWithAndPostfix(fjmc, width_fj, postfix_fj);
//						// fjmc=dymc+lcmc+fjmc;
//
//						// String fjxh=(d+1)+"-(-"+(c+1)+")-"+(c+1)+"0"+(f+1);
//						String fjxh = "-" + (c + 1) + ((f + 1) < 10 ? ("0" + (f + 1)) : ("" + (f + 1)));
//
//						JzwFjShowInPage page = new JzwFjShowInPage(1, dx_left, dx_top, dx_width, dx_height);
//						Jzwfj jzwfj = JzwJgUtil.constructJzwFj(jzwjg.getJzwjgid(), jzwdy.getJzwdyid(), jzwlc.getJzwlcid(), fjxh, fjmc, page);
//						jzwfj.setCreatedby(SecurityUtils.getUserId());
//						jzwfjList.add(jzwfj);
//					}
//				}
//			}
//		}
//
//		// 添加不规则分单元地下室
//		if ("2".equals(type)) {
//
//			List<Map<String, Object>> dxlc = (List<Map<String, Object>>) view.get("dxlc");
//			KAssert.notEmpty(jzwjgMap, "请填写地下楼层结构数据");
//
//			int ls_totalW = dys * fjs * KConstants.jzwWidth;// 该建筑物总宽度
//			int ls_totalH = lcs * KConstants.jzwHeight;
//			int dxlcs = Integer.MIN_VALUE;
//			int mcms = Integer.MIN_VALUE;
//			// 设置所属建筑物结构dxcs为dxlc.size()
//			jzwjg.setDxcs(Short.parseShort(dxlc.size() + ""));
////			type
//			jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
//
//			for (int i = 0; i < dxlc.size(); i++) {
//
//				Map<String, Object> map = dxlc.get(i);
//				try {
//					dxlcs = dxlc.size();
//					// dxlcs=Integer.parseInt(String.valueOf(map.get("dxlcs"))); if(dxlcs<0){AlertSLEUtil.Error("地下楼层数不能小于0");}
//					mcms = Integer.parseInt(String.valueOf(map.get("mcms")));
//					if (mcms < 0) {
//						AlertSLEUtil.Error("地下每层房间数不能小于0");
//					}
//				} catch (Exception e) {
//					throw new ServiceLogicalException("数据转化错误，请检查地下楼层数、地下每层房间数 是否数字");
//				}
//
//				int one_width = 0;// 每个房间宽度
//				int one_width_sp = 0;// 不能整除的情况
//				one_width = ls_totalW / mcms;// 设置该层每间地下室宽度
//				if (ls_totalW % mcms == 0) {// 能整除
//					one_width = ls_totalW / mcms;// 设置该层每间地下室宽度
//				} else {
//					one_width = (ls_totalW - ls_totalW % mcms) / mcms;
//					one_width_sp = one_width + ls_totalW % mcms;// 最后一个房间宽度加上余数
//				}
//				// 处理楼层名称 长度+后缀
//				String dxlcmc = "-" + JzwJgUtil.processWithAndPostfix((i + 1) + "", width_lc, postfix_lc);
//				Jzwlc jzwlc = JzwJgUtil.constructJzwLc(jzwjg.getJzwjgid(), dxJzwdyId, "-" + (i + 1), dxlcmc);
//				jzwlcMapper.insertSelective(jzwlc);
//
//				for (int j = 0; j < mcms; j++) {// 地下楼层向下排列
//					int width = 0;
//					if ((one_width_sp != 0) && (j == mcms - 1)) {// 如果ls_totalW/mcms不能整除的话，最后一个房间宽度加上余数
//						width = one_width_sp;
//					} else {
//						width = one_width;
//					}
//					int height = KConstants.jzwHeight;// 70;
//					int top = i * height + ls_totalH;// top值等于楼上总高度加上地下层的高度
//					int left = j * one_width;//
//					int dxlcIndex = i + 1;
//
//					String fjmc = "-" + dxlcIndex + ((j + 1) < 10 ? ("0" + (j + 1)) : ("" + (j + 1)));
//					fjmc = JzwJgUtil.processWithAndPostfix(fjmc, width_fj, postfix_fj);
//					String fjxh = "-" + dxlcIndex + ((j + 1) < 10 ? ("0" + (j + 1)) : ("" + (j + 1)));
//
//					JzwFjShowInPage page = new JzwFjShowInPage(1, left, top, width, height);
//					Jzwfj jzwfj = JzwJgUtil.constructJzwFj(jzwjg.getJzwjgid(), dxJzwdyId, jzwlc.getJzwlcid(), fjxh, fjmc, page);
//					jzwfjList.add(jzwfj);
//				}
//			}
//		}
//		if (jzwfjList != null && jzwfjList.size() > 0) {
//			jzwfjMapper.batchInsertJzwfj(jzwfjList);
//		}
//
//		Long t2 = System.currentTimeMillis();
//		System.out.println("起始：" + t1);
//		System.out.println("结束：" + t2);
//		System.out.println("执行耗时：" + (t2 - t1) / 1000);
//	}
//	
//	@Transactional(rollbackFor=Exception.class)
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void addJzwjgNoRegular(Map view){
//		// 记得修改。。。。* ### // jzwId="0fdbf914-0841-4061-9ee7-d838e65650c5";
//		String jzwId = (String) (view.get("jzwId"));// 建筑物Id
//		KAssert.notNull(jzwId, "请选择建筑物");
//
//		validateAddJzwJg(view);// 数据校验
//		Long t1 = System.currentTimeMillis();
//		Date date = new Date();
//		// 单元/楼层/房间 后缀 ,提取单元号/楼层号/房间号 位数--->postfix_dy,postfix_lc,postfix_fj,width_dy,width_lc,width_fj
//		Map<String, Object> postfixAndWidthRule = (Map<String, Object>) view.get("postfixAndWidthRule");
//
//		postfixAndWidthRule = (Map<String, Object>) (postfixAndWidthRule == null ? Maps.newHashMap() : postfixAndWidthRule);
//
//		Map<String, Object> jzwjgMap = (Map<String, Object>) view.get("jzwjg");// 建筑物结构View
//
//		List<Map<String, Object>> prefixRules = (List<Map<String, Object>>) view.get("prefixRules");
//		List<Map<String, Object>> dylcfjRules = (List<Map<String, Object>>) view.get("dylcfjRules");
//
//		String type = (String) view.get("type");// 0：无地下室 1：规则分单元地下室 2：不规则地下室
//
//		String postfix_dy = (String) (postfixAndWidthRule.get("postfix_dy"));// 单元后缀--单元名称用到
//		postfix_dy = (postfix_dy == null ? "" : postfix_dy);
//
//		String postfix_lc = (String) (postfixAndWidthRule.get("postfix_lc"));// 楼层后缀--楼层名称用到
//		postfix_lc = (postfix_lc == null ? "" : postfix_lc);
//
//		String postfix_fj = (String) (postfixAndWidthRule.get("postfix_fj"));// 房间后缀--房间名称用到
//		postfix_fj = (postfix_fj == null ? "" : postfix_fj);
//
//		int width_dy = Integer.parseInt((String) (postfixAndWidthRule.get("width_dy")));// 单元号位数--单元名称用到
//		int width_lc = Integer.parseInt((String) (postfixAndWidthRule.get("width_lc")));// 楼层号位数--楼层名称用到
//		int width_fj = Integer.parseInt((String) (postfixAndWidthRule.get("width_fj")));// 房间号位数--房间名称用到
//
//		String dys_ = (String) (jzwjgMap.get("dys"));// 建筑物结构-单元数
//		
//		String dxcs = (String) (jzwjgMap.get("dxcs"));// 建筑物结构-地下层数
//		String dxmcms = (String) (jzwjgMap.get("dxmcms"));// 建筑物结构-地下每单元门数
//		dxcs = (dxcs == null || "".equals(dxcs)) ? "0" : dxcs;
//		dxmcms = (dxmcms == null || "".equals(dxmcms)) ? "0" : dxmcms;
//
//		short dys = Short.MIN_VALUE, lcs = Short.parseShort("0"), fjs = Short.parseShort("0"), dxcsx = Short.MIN_VALUE, dxmcmsx = Short.MIN_VALUE;
//		try {
//			dys = Short.parseShort(dys_);
//			if (dys < 0) {
//				AlertSLEUtil.Error("单元数不能小于0");
//			}
//			dxcsx = Short.parseShort(dxcs);
//			if (dxcsx < 0) {
//				AlertSLEUtil.Error("地下楼层数不能小于0");
//			}
//			if (dxcsx > 100) {
//				AlertSLEUtil.Error("地下楼层数不能大于100");
//			}
//			dxmcmsx = Short.parseShort(dxmcms);
//			if (dxmcmsx < 0) {
//				AlertSLEUtil.Error("地下单元门数不能小于0");
//			}
//		} catch (Exception e) {
//			throw new ServiceLogicalException("数据转化错误，请检查楼层数、单元数、地下层数、地下每层门数等是否是数字");
//		}
//		// 构造/建筑物结构model
//		Jzwjg jzwjg = new Jzwjg(jzwId, jzwId, jzwId, dys, lcs, fjs, dxcsx, dxmcmsx, "1", "1", date, date, "1", "1");
//		jzwjgMapper.insertSelective(jzwjg);
//		// ------------------------------------创建建筑物结构开始-------------------------------------------------------------------------------
//		ArrayList<Jzwfj> jzwfjList = Lists.newArrayList();
//		// 作为不规则地下楼层及房间的统一单元
//		String dxJzwdyId = "";
//
//		Map<Integer, List<int[]>> dylcfjMap = Maps.newHashMap();
//		// 建筑物最大层数
//		int lcMax = 0;
//		// 单元及其对应的最大宽度 格式：<dyIndex, widthMax>
//		Map<Integer, Integer> dyWidthMaxMap = Maps.newHashMap();
//		int leftWidthSum = 0;
//		for (int i = 0; i < dylcfjRules.size(); i++) {
//			Map<String, Object> map = dylcfjRules.get(i);
//			int dyIndex = Integer.MIN_VALUE;
//			int lcfrom = Integer.MIN_VALUE;
//			int lcto = Integer.MIN_VALUE;
//			int mcfjs = Integer.MIN_VALUE;
//			try {
//				dyIndex = Integer.parseInt((String) map.get("dyIndex"));
//				if (dyIndex < 0) {
//					AlertSLEUtil.Error("单元号不能小于0");
//				}
//				lcfrom = Integer.parseInt((String) map.get("lcfrom"));
//				if (lcfrom < 0) {
//					AlertSLEUtil.Error("起始楼层号不能小于0");
//				}
//				lcto = Integer.parseInt((String) map.get("lcto"));
//				if (lcto < 0) {
//					AlertSLEUtil.Error("结束楼层号不能小于0");
//				}
//				mcfjs = Integer.parseInt((String) map.get("mcfjs"));
//				if (mcfjs < 0) {
//					AlertSLEUtil.Error("每层房间数不能小于0");
//				}
//			} catch (Exception e) {
//				throw new ServiceLogicalException("数据转化错误，请检查单元楼层房间规则是否是数字");
//			}
//			if (dylcfjMap.get(dyIndex) == null) {
//				List<int[]> list2 = new ArrayList<int[]>();
//				list2.add(new int[] { lcfrom, lcto });
//				dylcfjMap.put(dyIndex, list2);
//			} else {
//				dylcfjMap.get(dyIndex).add(new int[] { lcfrom, lcto });
//			}
//			// 找出最高楼层
//			if (lcto > lcMax) {
//				lcMax = lcto;
//			}
//			if (dyWidthMaxMap.get(dyIndex) == null) {
//				dyWidthMaxMap.put(dyIndex, mcfjs);
//			} else {
//				Integer oldFjs = dyWidthMaxMap.get(dyIndex);
//				dyWidthMaxMap.put(dyIndex, mcfjs > oldFjs ? mcfjs : oldFjs);
//			}
//		}
//		KAssert.isTrue(JzwJgUtil.validate(dylcfjMap), "数据转化错误，楼层数存在交叉或者不连续情况");
//		
//		// 添加地上楼层房间
//		for (int d = 0; d < dys; d++) {
//			// 处理单元名称 长度+后缀
//			String dymc = JzwJgUtil.processWithAndPostfix((d + 1) + "", width_dy, postfix_dy);
//			Jzwdy jzwdy = JzwJgUtil.constructJzwDy(jzwjg.getJzwjgid(), JzwJgUtil.processDyLcXh(("" + (d + 1)), 3), dymc, Short.parseShort(dyWidthMaxMap.get(d+1)+""), fjs);
//			jzwdyMapper.insertSelective(jzwdy);
//			if (d == 0) {// 处理 地下房间不依附单元的情况，地下房间，全部默认在第一单元
//				dxJzwdyId = jzwdy.getJzwdyid();
//			}
//
//			for (int i = 0; i < dylcfjRules.size(); i++) {
//				Map<String, Object> map = dylcfjRules.get(i);
//				int dyIndex = Integer.MIN_VALUE;
//				int lcfrom = Integer.MIN_VALUE;
//				int lcto = Integer.MIN_VALUE;
//				int mcfjs = Integer.MIN_VALUE;
//				dyIndex = Integer.parseInt((String) map.get("dyIndex"));
//				lcfrom = Integer.parseInt((String) map.get("lcfrom"));
//				lcto = Integer.parseInt((String) map.get("lcto"));
//				mcfjs = Integer.parseInt((String) map.get("mcfjs"));
//				// 如果当前单元号处与 规则定义的的单元范围内，则对其根据相应规则生成房间
//				if ((d + 1) == dyIndex) {
//					int widthMax = dyWidthMaxMap.get(dyIndex);
//					int one_width = 0;// 每个房间宽度
//					int one_width_sp = 0;// 不能整除的情况
//					if ((widthMax * KConstants.jzwWidth) % mcfjs == 0) {// 能整除
//						one_width = (widthMax * KConstants.jzwWidth) / mcfjs;// 设置该层每间地下室宽度
//					} else {
//						one_width = ((widthMax * KConstants.jzwWidth) - ((widthMax * KConstants.jzwWidth) % mcfjs)) / mcfjs;
//						one_width_sp = one_width + (widthMax * KConstants.jzwWidth) % mcfjs;// 最后一个房间宽度加上余数
//					}
//					for (int j = lcfrom; j <= lcto; j++) {
//						String lcmc = JzwJgUtil.processWithAndPostfix("" + j, width_lc, postfix_lc);
//						Jzwlc jzwlc = JzwJgUtil.constructJzwLc(jzwjg.getJzwjgid(), jzwdy.getJzwdyid(), "" + j, lcmc);
//						jzwlcMapper.insertSelective(jzwlc);
//						for (int m = 0; m < mcfjs; m++) {
//							// 构造房间
//							int height = KConstants.jzwHeight;// 70;
//
//							int top = (lcMax - j) * height;
//							int width = one_width;
//							if (((widthMax * KConstants.jzwWidth) % mcfjs != 0) && (m == mcfjs - 1)) {
//								width = one_width_sp;
//							}
//							int left = leftWidthSum + m * width;
//							// 处理房间名称 长度+后缀
//							String fjmc = j + ((m + 1) < 10 ? ("0" + (m + 1)) : ("" + (m + 1)));
//							fjmc = JzwJgUtil.processWithAndPostfix(fjmc, width_fj, postfix_fj);
//
//							// 房间名称添加前缀
//							for (int k = 0; k < prefixRules.size(); k++) {
//								Map<String, Object> pmap = prefixRules.get(k);
//								int from = Integer.MIN_VALUE;
//								int to = Integer.MIN_VALUE;
//								try {
//									from = Integer.parseInt((String) pmap.get("from"));
//									if (from < 0) {
//										AlertSLEUtil.Error("房间号前缀起始楼层不能小于0");
//									}
//									to = Integer.parseInt((String) pmap.get("to"));
//									if (to < 0) {
//										AlertSLEUtil.Error("房间号前缀结束楼层不能小于0");
//									}
//								} catch (Exception e) {
//									throw new ServiceLogicalException("数据转化错误，请检查房间号前缀起始、结束楼层是否是数字");
//								}
//								String value = (String) pmap.get("value");// 前缀
//								if ((j >= from) && (j <= to)) {
//									fjmc = value + fjmc;
//								}
//							}
//							// 不再带有单元楼层前缀
//							String fjxh = j + ((m + 1) < 10 ? ("0" + (m + 1)) : ("" + (m + 1)));
//							JzwFjShowInPage page = new JzwFjShowInPage(1, left, top, width, height);
//							Jzwfj jzwfj = JzwJgUtil.constructJzwFj(jzwjg.getJzwjgid(), jzwdy.getJzwdyid(), jzwlc.getJzwlcid(), fjxh, fjmc, page);
//							jzwfj.setCreatedby(SecurityUtils.getUserId());
//							jzwfjList.add(jzwfj);
//						}
//					}
//				}
//			}
//			leftWidthSum += dyWidthMaxMap.get(d + 1) * KConstants.jzwWidth;
//		}
//		// 添加不规则分单元地下室
//		if ("2".equals(type)) {
//
//			List<Map<String, Object>> dxlc = (List<Map<String, Object>>) view.get("dxlc");
//			KAssert.notEmpty(jzwjgMap, "请填写地下楼层结构数据");
//
//			int ls_totalW =  leftWidthSum;// 该建筑物总宽度
//			int ls_totalH =  lcMax * KConstants.jzwHeight;
//			int dxlcs = Integer.MIN_VALUE;
//			int mcms = Integer.MIN_VALUE;
//			// 设置所属建筑物结构dxcs为dxlc.size()
//			jzwjg.setDxcs(Short.parseShort(dxlc.size() + ""));
//
//			jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
//
//			for (int i = 0; i < dxlc.size(); i++) {
//
//				Map<String, Object> map = dxlc.get(i);
//				try {
//					dxlcs = dxlc.size();
//					// dxlcs=Integer.parseInt(String.valueOf(map.get("dxlcs"))); if(dxlcs<0){AlertSLEUtil.Error("地下楼层数不能小于0");}
//					mcms = Integer.parseInt(String.valueOf(map.get("mcms")));
//					if (mcms < 0) {
//						AlertSLEUtil.Error("地下每层房间数不能小于0");
//					}
//				} catch (Exception e) {
//					throw new ServiceLogicalException("数据转化错误，请检查地下楼层数、地下每层房间数 是否数字");
//				}
//
//				int one_width = 0;// 每个房间宽度
//				int one_width_sp = 0;// 不能整除的情况
//				one_width = ls_totalW / mcms;// 设置该层每间地下室宽度
//				if (ls_totalW % mcms == 0) {// 能整除
//					one_width = ls_totalW / mcms;// 设置该层每间地下室宽度
//				} else {
//					one_width = (ls_totalW - ls_totalW % mcms) / mcms;
//					one_width_sp = one_width + ls_totalW % mcms;// 最后一个房间宽度加上余数
//				}
//				// 处理楼层名称 长度+后缀
//				String dxlcmc = "-" + JzwJgUtil.processWithAndPostfix((i + 1) + "", width_lc, postfix_lc);
//				Jzwlc jzwlc = JzwJgUtil.constructJzwLc(jzwjg.getJzwjgid(), dxJzwdyId, "-" + (i + 1), dxlcmc);
//				jzwlcMapper.insertSelective(jzwlc);
//
//				for (int j = 0; j < mcms; j++) {// 地下楼层向下排列
//					int width = 0;
//					if ((one_width_sp != 0) && (j == mcms - 1)) {// 如果ls_totalW/mcms不能整除的话，最后一个房间宽度加上余数
//						width = one_width_sp;
//					} else {
//						width = one_width;
//					}
//					int height = KConstants.jzwHeight;// 70;
//					int top = i * height + ls_totalH;// top值等于楼上总高度加上地下层的高度
//					int left = j * one_width;//
//					int dxlcIndex = i + 1;
//
//					String fjmc = "-" + dxlcIndex + ((j + 1) < 10 ? ("0" + (j + 1)) : ("" + (j + 1)));
//					fjmc = JzwJgUtil.processWithAndPostfix(fjmc, width_fj, postfix_fj);
//					// fjmc=dxlcmc+fjmc;
//					String fjxh = "-" + dxlcIndex + ((j + 1) < 10 ? ("0" + (j + 1)) : ("" + (j + 1)));
//					// String fjxh=1+"-(-"+(i+1)+")-"+(i+1)+"0"+(j+1);
//
//					JzwFjShowInPage page = new JzwFjShowInPage(1, left, top, width, height);
//					Jzwfj jzwfj = JzwJgUtil.constructJzwFj(jzwjg.getJzwjgid(), dxJzwdyId, jzwlc.getJzwlcid(), fjxh, fjmc, page);
//					jzwfjList.add(jzwfj);
//				}
//			}
//		}
//		if (jzwfjList != null && jzwfjList.size() > 0) {
//			jzwfjMapper.batchInsertJzwfj(jzwfjList);
//		}
//
//		Long t2 = System.currentTimeMillis();
//		System.out.println("起始：" + t1);
//		System.out.println("结束：" + t2);
//		System.out.println("执行耗时：" + (t2 - t1) / 1000);
//	}
//	/**
//	 * {
//			"jzwjg":{"dys":"4","lcs":"10","mdyms":"2","dxcs":"3","dxmcms":"2"},
//			"jzwId":"ed28d485-a5f4-4b17-9efb-eae1e6ba243c",
//			"prefixRules":[{"from":"1","to":"3","value":"A"},
//						   {"from":"4","to":"6","value":"B"}],
//			"postfixAndWidthRule":{"postfix_dy":"单元","postfix_lc":"层","postfix_fj":"室",
//								   "width_dy":"2","width_lc":"3","width_fj":"2"},
//			"type":"1"}
//			type:地下室 : 0：无地下室 1：规则分单元地下室    2：不规则地下室
//	 * @param view
//	 */
//	@SuppressWarnings({"rawtypes", "unchecked" })
//	private void validateAddJzwJg(Map view){
//		Map<String, Object> postfixAndWidthRule = (Map<String, Object>)view.get("postfixAndWidthRule");
//		
//		KAssert.notEmpty(postfixAndWidthRule, "数据有误 ，单元号、楼层号、房间号 位数，不能为空，请检查。");
//		
//		Map<String, Object> jzwjgMap = (Map<String, Object>)view.get("jzwjg");
//		KAssert.notEmpty(jzwjgMap, "数据有误,请检查数据。");
//		
//		String jzwId = (String)view.get("jzwId");//建筑物Id
//		KAssert.hasText(jzwId, "数据有误,请重新打开页面填写数据。");
//		
//		String width_dy = (String)postfixAndWidthRule.get("width_dy");//单元号位数
//		String width_lc =(String)postfixAndWidthRule.get("width_lc");//楼层号位数
//		String width_fj =(String)postfixAndWidthRule.get("width_fj");//房间号位数
//		
//		KAssert.hasText(width_dy, "数据有误,请填写单元号位数。");
//		KAssert.hasText(width_lc, "数据有误,请填写楼层号位数。");
//		KAssert.hasText(width_fj, "数据有误,请填写房间号位数。");
//		
//		try{
//			Integer int_width_dy=Integer.parseInt(width_dy); if(int_width_dy<0){AlertSLEUtil.Error("单元号位数不能小于0");}
//			Integer int_width_lc=Integer.parseInt(width_lc); if(int_width_lc<0){AlertSLEUtil.Error("楼层号位数不能小于0");}
//			Integer int_width_fj=Integer.parseInt(width_fj); if(int_width_fj<0){AlertSLEUtil.Error("房间号位数不能小于0");} 
//			
//		}catch(NumberFormatException e){
//			throw new ServiceLogicalException("数据转化错误，请检查单元号位数、楼层号位数、房间号位数数等数是否是数字");
//		}
//		Integer int_width_fj=Integer.parseInt(width_fj); 
//		if(int_width_fj>100){
//			AlertSLEUtil.Error("房间号位数不能大于100");
//		}
//		boolean isRegular = Boolean.parseBoolean((String)(view.get("isRegular")));//单元楼层房间是否规则分布
//		
//		String dys_ =(String) jzwjgMap.get("dys");
//		String lcs_ = (String)jzwjgMap.get("lcs");
//		String mdyms = (String) jzwjgMap.get("mdyms");
//		KAssert.hasText(dys_, "数据有误,请填写单元数。");
//		if(isRegular){
//			KAssert.hasText(lcs_, "数据有误,请填写楼层数。");
//			KAssert.hasText(mdyms, "数据有误,请填写每单元门数。");
//		}
//	}
//	
//
//
//	/**
//	 * //var parm={"refJzwFjId":targetIdArray[0] ,"jzwId":jzwjgId,"addfjmc":addfjxh,"addfjxh":addfjxh};
//	 * true
//	 * @param refFjId
//	 * @param jzwjgid
//	 * @param isUpDown true:楼顶添加   false： 地下添加
//	 * @return
//	 */
//	@Transactional(rollbackFor=Exception.class,readOnly=true)
//	@Override
//	public KJSONMSG canAdd(String refFjId,String jzwjgid,boolean isUpDown){
//		Jzwfj refFj=jzwfjMapper.selectByPrimaryKey(refFjId);
//		int refFjtop=JzwJgUtil.getTop(refFj);
//		int refFjleft=JzwJgUtil.getLeft(refFj);
//		int refFjright=JzwJgUtil.getRight(refFj);
//		
//		List<Jzwfj> fjList= jzwfjMapper.selectFjByJzwjgId( jzwjgid);
//		for(Jzwfj fj :fjList){
//			int targetTopp=JzwJgUtil.getTop(fj);
//			int targetLeft=JzwJgUtil.getLeft(fj);
//			int targetRight=JzwJgUtil.getRight(fj);
//			//楼顶                                                                                                              
//			if(isUpDown&&targetTopp<refFjtop&&(targetTopp+KConstants.jzwHeight)>(refFjtop-KConstants.jzwHeight)){
//				if(targetLeft>=refFjleft&&targetLeft<refFjright){
//					return new KJSONMSG(300, "该房间之上已经存在房间，不可添加", false);
//				}
//				if(targetRight>refFjleft&&targetRight<=refFjright){
//					return new KJSONMSG(300, "该房间之上已经存在房间，不可添加", false);
//				}
//				if(targetLeft<=refFjleft&&targetRight>=refFjright){
//					return new KJSONMSG(300, "该房间之上已经存在房间，不可添加", false);
//				}
//			}
//			 //地下 	  (isUpDown&&targetTopp<refFjtop)||(!isUpDown&&targetTopp>refFjtop)
//			if(!isUpDown&&targetTopp>refFjtop&&targetTopp<(refFjtop+2*KConstants.jzwHeight)){
//				if(targetLeft>=refFjleft&&targetLeft<refFjright){
//					return new KJSONMSG(300, "该房间之下已经存在房间，不可添加", false);
//				}
//				if(targetRight>refFjleft&&targetRight<=refFjright){
//					return new KJSONMSG(300, "该房间之下已经存在房间，不可添加", false);
//				}
//				if(targetLeft<=refFjleft&&targetRight>=refFjright){
//					return new KJSONMSG(300, "该房间之下已经存在房间，不可添加", false);
//				}
//			}
//		}
//		return new KJSONMSG(200, "", true);
//	}
//	
////	@Transactional(rollbackFor=Exception.class,readOnly=true)
////	//@Override
////	public KJSONMSG canAdd(String refFjId,String jzwjgid,String direction){
////		Jzwfj refFj=jzwfjMapper.selectByPrimaryKey(refFjId);
////		String jzwDyId=refFj.getJzwdyid();
////		
////		List<Jzwfj> fjListFromDy=jzwfjMapper.selectJzwfjByJzwDyId(jzwDyId);
////		
////		int refFjtop=JzwJgUtil.getTop(refFj);
////		int refFjleft=JzwJgUtil.getLeft(refFj);
////		int refFjright=JzwJgUtil.getRight(refFj);
////		
////		List<Jzwfj> fjList= jzwfjMapper.selectFjByJzwjgId( jzwjgid);
////		for(Jzwfj fj :fjList){
////			int targetTopp=JzwJgUtil.getTop(fj);
////			int targetLeft=JzwJgUtil.getLeft(fj);
////			int targetRight=JzwJgUtil.getRight(fj);
////			//楼顶                                                                                                              
////			if(isUpDown&&targetTopp<refFjtop&&(targetTopp+KConstants.jzwHeight)>(refFjtop-KConstants.jzwHeight)){
////				if(targetLeft>=refFjleft&&targetLeft<refFjright){
////					return new KJSONMSG(300, "该房间之上已经存在房间，不可添加", false);
////				}
////				if(targetRight>refFjleft&&targetRight<=refFjright){
////					return new KJSONMSG(300, "该房间之上已经存在房间，不可添加", false);
////				}
////				if(targetLeft<=refFjleft&&targetRight>=refFjright){
////					return new KJSONMSG(300, "该房间之上已经存在房间，不可添加", false);
////				}
////			}
////			 //地下 	  (isUpDown&&targetTopp<refFjtop)||(!isUpDown&&targetTopp>refFjtop)
////			if(!isUpDown&&targetTopp>refFjtop&&targetTopp<(refFjtop+2*KConstants.jzwHeight)){
////				if(targetLeft>=refFjleft&&targetLeft<refFjright){
////					return new KJSONMSG(300, "该房间之下已经存在房间，不可添加", false);
////				}
////				if(targetRight>refFjleft&&targetRight<=refFjright){
////					return new KJSONMSG(300, "该房间之下已经存在房间，不可添加", false);
////				}
////				if(targetLeft<=refFjleft&&targetRight>=refFjright){
////					return new KJSONMSG(300, "该房间之下已经存在房间，不可添加", false);
////				}
////			}
////		}
////		return new KJSONMSG(200, "", true);
////	}
//	
//
////----------------------validate--------------------------------------------------------------------------------------------------
//	
//	@Transactional(rollbackFor=Exception.class,readOnly=true)
//	@Override
//	public KJSONMSG validateJzwjgByJzwId(String jzwId){
//		Jzwjg jg=jzwjgMapper.selectByJzwId(jzwId);
//		if(jg==null){
//			return new KJSONMSG(301, "未查询到该建筑物结构");
//		}
//		return validateJzwjg(jg.getJzwjgid());
//	}
//	
//	@Transactional(rollbackFor=Exception.class,readOnly=true)
//	@Override
//	public KJSONMSG validateJzwjg(String jzwjgId) {
//		if(jzwjgId==null||jzwjgId.isEmpty()){
//			return new KJSONMSG(300, "建筑物结构错误：查询参数错误");
//		}
//		Jzwjg jzwjg=jzwjgMapper.selectByPrimaryKey(jzwjgId);
//		if(jzwjg==null){
//			return new KJSONMSG(300, "建筑物结构错误：未查询到该建筑物结构数据");
//		}
//		Short lcs=jzwjg.getLcs();//楼层数
//		Short dys=jzwjg.getDys();//单元数/行数
//		Short mdyms=jzwjg.getMdyms();//每层门数/房间数
//		
//		String sflcxt=jzwjg.getSflcxt();// 是否单元楼层相同 1:相同，0：不同
//		String sfmsxt=jzwjg.getSfmsxt();//  是否门数相同 1:相同，0：不同
//		
//		Short dxcs=jzwjg.getDxcs();//  地下层数
//		Short dxmcms=jzwjg.getDxmcms();//地下每层门数/房间数
//		
//		if(dys==null||dys.shortValue()<=0){
//			return new KJSONMSG(300, "建筑物结构错误：未指定单元数");
//		}
//		if(lcs==null||lcs.shortValue()<=0){
//			return new KJSONMSG(300, "建筑物结构错误：未指定最大楼层数");
//		}
//		if(mdyms==null||mdyms.shortValue()<=0){
//			return new KJSONMSG(300, "建筑物结构错误：未指定每层门数");
//		}
//
//		List<Jzwdy> dylist= jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgId);
//		List<Jzwlc> lclist= jzwlcMapper.selectSortedJzwlcByJzwJgid(jzwjgId);
//		List<Jzwfj> fjlist= jzwfjMapper.selectFjByJzwjgId(jzwjgId);
//		
//		KJSONMSG mgs=JzwJgNewUtil.validateJzwjg(jzwjg, dylist, lclist, fjlist);
//		return mgs;
//		
//	}
//	//params={"jg_dys":jg_dys,"jg_lcs":jg_lcs,"jg_mdyms":jg_mdyms,"jg_dxcs":jg_dxmcms};
//	@Override
//	@Transactional(rollbackFor=Exception.class)
//	public void updateJzwjg(Map<?, ?> view){
//		String jzwjgid=(String) view.get("jzwjgid");
//		
//		String jg_dys=(String)view.get("jg_dys");
//		String jg_lcs=(String)view.get("jg_lcs");
//		
//		String jg_mdyms=(String)view.get("jg_mdyms");
//		
//		String jg_dxcs=(String)view.get("jg_dxcs");
//		String jg_dxmcms=(String)view.get("jg_dxmcms");
//		
//	
//		KAssert.bigThanZero(jg_dys, "单元数必须为大于零的数字");
//		KAssert.bigThanZero(jg_lcs, "楼层数必须为大于零的数字");
//		KAssert.bigThanZero(jg_mdyms, "每层门数必须为大于零的数字");
//		KAssert.bigThan(jg_dxcs,-1, "地下楼层数必须为大于等于零的数字");
//		KAssert.bigThan(jg_dxmcms,-1, "地下每层门数必须为大于等于零的数字");
//		
//		Jzwjg jzwjg=new Jzwjg();
//		jzwjg.setJzwjgid(jzwjgid);
//		jzwjg.setDys(Short.parseShort(jg_dys));
//		jzwjg.setLcs(Short.parseShort(jg_lcs));
//		jzwjg.setMdyms(Short.parseShort(jg_mdyms));
//		jzwjg.setDxcs(Short.parseShort(jg_dxcs));
//		jzwjg.setDxmcms(Short.parseShort(jg_dxmcms));
//		jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
//		jzwjgMapper.resetJzwjg(jzwjg);
//		
//	}
//	
////----------------------------------------------------------------------------------------------------------------------------------
//	@Transactional(rollbackFor = Exception.class,readOnly=true)
//	@Override
//	public DataSet loadJzwjgDyLcInfo(Map<String,String> params) {
//		PaginationUtil.initPageAndSort(params);  
//		String jzwjgid = params.get("jzwjgid");
//		if(jzwjgid==null||jzwjgid.isEmpty()){
//			return DataSet.newDs();
//		}
//		params.put("jzwjgid", jzwjgid);
//		return new DataSet(jzwjgMapper.selectJzwjgDyLcCount(params), jzwjgMapper.selectJzwjgDyLcInfo(params));
//
//	}
//	//{xh=001, jgtype=dy, mc=1单元, id=a2021891-a4f0-4949-b962-ebfa60f49d77}
//	@SuppressWarnings("rawtypes")
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public void updateJzwjgDyLcXh_20160521(Map view) {
//		KAssert.notNull(view, "未提交数据");
//		String xh=(String) view.get("xh");
//		String jgtype=(String) view.get("jgtype");
//		String mc=(String) view.get("mc");
//		String id=(String) view.get("id");
//		KAssert.notNull(xh, "序号为空，请检查");
//		KAssert.notNull(jgtype, "未知类型");
//		KAssert.notNull(mc, "名称为空，请检查");
//		String xhxxx="";
//		try{
//			if(Integer.parseInt(xh)<10){
//				xhxxx="00"+xh;
//			}else{
//				xhxxx="0"+xh;
//			}
//		}catch(Exception e){
//			AlertSLEUtil.Error("序号只能填写数字，请检查");
//		}
//		Date date = new Date();
//		String jzwjgId="";
//		if("dy".equals(jgtype)){
//			Jzwdy dy = jzwdyMapper.selectByPrimaryKey(id);
//			dy.setDyxh(xhxxx);
//			dy.setDymc(mc);
//			dy.setZhgxrq(date);	
//			jzwjgId = dy.getJzwjgid();
//			Jzwjg jg=jzwjgMapper.selectByPrimaryKey(jzwjgId);
//			if(Short.parseShort(xh)>jg.getDys()){
//				AlertSLEUtil.Error("该单元序号【"+xh+"】大于建筑物结构指定的单元数:【"+jg.getDys()+"】");
//			}
//			if(Short.parseShort(xh)<0){
//				AlertSLEUtil.Error("该单元序号不能小于零");
//			}
//			List<Jzwdy> dyList=jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgId);
//			if(dyList!=null&&dyList.size()>0){
//				for(Jzwdy dyx:dyList){
//					short xhx=Short.parseShort(dyx.getDyxh());
//					if(Short.parseShort(xh)==xhx&&!(id.equals(dyx.getJzwdyid()))){
//						AlertSLEUtil.Error("该单元序号已经存在");
//					}
//				}
//			}
//			jzwdyMapper.updateByPrimaryKeySelective(dy);
//		}else if("lc".equals(jgtype)){
//			Jzwlc lc = jzwlcMapper.selectByPrimaryKey(id);
//			lc.setLcxh(xh);
//			lc.setLcmc(mc);
//			lc.setZhgxrq(date);
//			jzwjgId = lc.getJzwjgid();
//			Jzwjg jg=jzwjgMapper.selectByPrimaryKey(jzwjgId);
//			if(Short.parseShort(xh)>jg.getLcs()){
//				AlertSLEUtil.Error("该楼层序号【"+xh+"】大于建筑物结构指定的楼层数:【"+jg.getLcs()+"】");
//			}
//			if(Short.parseShort(xh)<0){
//				if(Short.parseShort(xh)<(jg.getDxcs()==null?0:jg.getDxcs())){
//					AlertSLEUtil.Error("该楼层序号【"+xh+"】大于建筑物结构指定的地下楼层数:【"+jg.getLcs()+"】");
//				}
//			}
//			List<Jzwlc> lcList=jzwlcMapper.selectSortedJzwlcByJzwdyid(lc.getJzwdyid());
//			if(lcList!=null&&lcList.size()>0){
//				for(Jzwlc lcx:lcList){
//					short lcxhx=Short.parseShort(lcx.getLcxh());
//					if(Short.parseShort(xh)==lcxhx&&!(id.equals(lcx.getJzwlcid()))){
//						AlertSLEUtil.Error("该单元内，该楼层序号已经存在");
//					}
//				}
//			}
//			jzwlcMapper.updateByPrimaryKeySelective(lc);
//		}else if("fj".equals(jgtype)){
//			Jzwfj fj = jzwfjMapper.selectByPrimaryKey(id);
//			fj.setFjxh(xh);
//			fj.setFjmc(mc);
//			fj.setZhgxrq(date);
//			jzwjgId = fj.getJzwjgid();
//			List<Jzwfj> fgList=jzwfjMapper.selectSortedJzwfjByJzwlcId(fj.getJzwlcid());
//			if(fgList!=null&&fgList.size()>0){
//				for(Jzwfj fgx:fgList){
//					short fjxhx=Short.parseShort(fgx.getFjxh());
//					if(Short.parseShort(xh)==fjxhx&&!(id.equals(fgx.getJzwfjid()))){
//						AlertSLEUtil.Error("该楼层,房间序号已经存在");
//					}
//				}
//			}
//			jzwfjMapper.updateByPrimaryKeySelective(fj);
//		}
//	}
//	//{xh=001, jgtype=dy, mc=1单元, id=a2021891-a4f0-4949-b962-ebfa60f49d77}
//	@Deprecated
//	@SuppressWarnings("rawtypes")
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public void updateJzwjgDyLcXh(Map view) {
//		KAssert.notNull(view, "未提交数据");
//		String xh=(String) view.get("xh");
//		String jgtype=(String) view.get("jgtype");
//		String mc=(String) view.get("mc");
//		String id=(String) view.get("id");
//		KAssert.notNull(xh, "序号为空，请检查");
//		KAssert.notNull(jgtype, "未知类型");
//		KAssert.notNull(mc, "名称为空，请检查");
//	try{
//		Integer.parseInt(xh);
//		}catch(Exception e){
//			AlertSLEUtil.Error("序号只能填写数字，请检查");
//		}
//		Date date = new Date();
//		String jzwjgId="";
//		if("dy".equals(jgtype)){
//			Jzwdy dy = jzwdyMapper.selectByPrimaryKey(id);
//			try{
//				
//				String lcs=(String) view.get("lcs");
//				Long lcCount=jzwlcMapper.selectJzwlcCountByJzwDyid(dy.getJzwdyid());
//				short lcCountx=(lcCount==null?0:lcCount.shortValue());
//				if(lcCountx>Short.parseShort(lcs)){
//					AlertSLEUtil.Error("填写楼层数【"+lcs+"】小于该单元实际楼层数【"+lcCountx+"】，请检查");
//				}
//				dy.setLcs(Short.parseShort(lcs));;
//			}catch(NumberFormatException e){
//				AlertSLEUtil.Error("该单元楼层数只填写写数字，请检查");
//			}
//			
//			dy.setDyxh(xh);
//			dy.setDymc(mc);
//			dy.setZhgxrq(date);	
//			jzwjgId = dy.getJzwjgid();
//			jzwdyMapper.updateByPrimaryKeySelective(dy);
//		}else if("lc".equals(jgtype)){
//			Jzwlc lc = jzwlcMapper.selectByPrimaryKey(id);
//			lc.setLcxh(xh);
//			lc.setLcmc(mc);
//			lc.setZhgxrq(date);
//			jzwjgId = lc.getJzwjgid();
//			jzwlcMapper.updateByPrimaryKeySelective(lc);
//		}
//		//更新建筑物结构ISBUILD字段和ISVALID字段为null
//		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgId);
//		if(jzwjg.getIsbuild()!=null||jzwjg.getIsvalid()!=null){
//			if (jzwjg!=null) {
//				jzwjgMapper.resetJzwjg(jzwjg);
//			}
//		}
//	}
//	//view {"jzwjgid":jzwjgid,"dy_xh":dy_xh,"dy_mc":dy_mc,"dy_lcs":dy_lcs,"dy_ms":dy_ms};
//	@Override
//	@Transactional(rollbackFor=Exception.class)
//	public void addJzwjgDy_20160521(Map<?, ?> view) {
//		String jzwjgid=(String) view.get("jzwjgid");
//		String dy_xh=(String)view.get("dy_xh");
//		String dy_mc=(String)view.get("dy_mc");
//		String dy_lcs=(String)view.get("dy_lcs");
//		String dy_ms=(String)view.get("dy_ms");
//		KAssert.hasText(jzwjgid, "未找到该建筑物结构数据");
//		KAssert.hasText(dy_xh, "单元序号不能为空");
//		//KAssert.hasText("dy_mc", "单元名称不能为空");
//		KAssert.hasText(dy_lcs, "单元名楼层数不能为空");
//		KAssert.hasText(dy_ms, "每层门数不能为空");
//		short lcs=0;short dyms=0;
//		try{
//			Integer.parseInt(dy_xh);
//		}catch(NumberFormatException e){
//			AlertSLEUtil.Error("单元序号必须为数字");
//		}
//		try{
//			if((lcs=Short.parseShort(dy_lcs))<=0){
//				AlertSLEUtil.Error("单元楼层数必须大于零");
//			}
//		}catch(Exception e){
//			AlertSLEUtil.Error("单元楼层数必须为数字");
//		}
//		try{
//			if((dyms=Short.parseShort(dy_ms))<=0){
//				AlertSLEUtil.Error("每层门数必须大于零");
//			}
//		}catch(Exception e){
//			AlertSLEUtil.Error("每层门数必须为数字");
//		}
//		
//		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgid);
//		short jgdys=jzwjg.getDys()==null?0:jzwjg.getDys().shortValue();
//		
//		Long dys_from_db_table=jzwdyMapper.selectJzwDyCountByJzwjgId(jzwjgid); 
//		short real_dys= (dys_from_db_table==null?0:dys_from_db_table.shortValue());
//		if(real_dys > jgdys-1){
//			AlertSLEUtil.Error("该建筑物：<br/>实际单元个数【"+real_dys+"】大于配置单元个数【"+jgdys+"】。<br/>不能进行添加，请预先修改配置");
//		}
//		short jgLcs=jzwjg.getLcs()==null?0:jzwjg.getLcs().shortValue();
//		if(lcs>jgLcs){
//			AlertSLEUtil.Error("该建筑物：<br/>添加单元的楼层数【"+lcs+"】，<br/>大于配置楼层数【"+jgLcs+"】，请预先修改配置");
//		}
//		short jgmdyms =jzwjg.getMdyms()==null?0:jzwjg.getMdyms().shortValue();
//		if(dyms>jgmdyms){
//			AlertSLEUtil.Error("该单元：<br/>每层门数【"+dyms+"】，<br/>大于配置门数【"+jgmdyms+"】，请预先修改配置");
//		}
//		
//		List<Jzwdy> dyList=jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgid);
//		if(dyList!=null&&dyList.size()>0){
//			for(Jzwdy dyx:dyList){
////				if(dy_xh.trim().equals(dyx.getDyxh())){
////					AlertSLEUtil.Error("该单元序号：【"+dy_xh+"】已经存在");
////				}
//				try{
//					short dy_xh_from_param=Short.parseShort(dy_xh);
//					short dyx_from_db=Short.parseShort(dyx.getDyxh());
//					if(dy_xh_from_param<=0){
//						AlertSLEUtil.Error("单元序号必须大于零");
//					}
//					if(dy_xh_from_param==dyx_from_db){
//						AlertSLEUtil.Error("该单元序号：【"+dy_xh+"】已经存在");
//					}
//					if(dy_xh_from_param<dyx_from_db){
//						AlertSLEUtil.Error("只能添加最大序号单元");
//					}
//				}catch(NumberFormatException e){
//					if(dyx.getDyxh()==null||dyx.getDyxh().isEmpty()){
//						AlertSLEUtil.Error("该建筑物结构中，存在单元序号为空的数据，不符合规范，<br/>请首先修改后进行本操作。");
//					}
//					AlertSLEUtil.Error("该建筑物结构中，单元序号：【"+dyx.getDyxh()+"】,不是数字，不符合规范，<br/>请首先修改后进行本操作。");
//				}
//			}
//		}
//		int d=dyList.size();
//		
//		Jzwdy jzwdy=JzwJgUtil.constructJzwDy(jzwjgid, dy_xh, dy_mc, lcs, dyms);
//		String jzwjgdyid=jzwdy.getJzwdyid();
//		List<Jzwlc> lcList=Lists.newArrayList();
//		List<Jzwfj> fjList=Lists.newArrayList();
//		for(int c=0;c<lcs;c++){
//			Jzwlc lc=JzwJgUtil.constructJzwLc(jzwjgid, jzwjgdyid, ""+(c+1), (c+1)+"层");
//			lcList.add(lc);
//			//生成房间：
//			for(int f=0;f<dyms;f++){
//				int width = KConstants.jzwWidth;// 120;
//				int height = KConstants.jzwHeight;// 70;
//				int left = ((dyms * d) + f) * width;
//				int top = (lcs - (c + 1)) * height;
//				
//				String fjxh = (c+ 1) + ((f + 1) < 10 ? ("0" + (f + 1)) : ("" + (f + 1)));
//				String fjmc=fjxh+"室";
//				JzwFjShowInPage fjInPage=new JzwFjShowInPage(1, left, top, width, height);
//				Jzwfj fj=JzwJgUtil.constructJzwFj(jzwjgid, jzwdy.getJzwdyid(), lc.getJzwlcid(), fjxh, fjmc, fjInPage);
//				fjList.add(fj);
//			}
//	
//			
//		}
//		
//		jzwdyMapper.insertSelective(jzwdy);
//		jzwlcMapper.batchInsertJzwlc(lcList);
//		jzwfjMapper.batchInsertJzwfj(fjList);
//
//		
//	}
//	
//	//view {"jzwjgid":jzwjgid,"dy_xh":dy_xh,"dy_mc":dy_mc,"dy_lcs":dy_lcs,"dy_ms":dy_ms};
//	@Override
//	@Transactional(rollbackFor=Exception.class)
//	public void addJzwjgDy(Map<?, ?> view) {
//		String jzwjgid=(String) view.get("jzwjgid");
//		String dy_xh=(String)view.get("dy_xh");
//		String dy_mc=(String)view.get("dy_mc");
//		String dy_lcs=(String)view.get("dy_lcs");
//		String dy_ms=(String)view.get("dy_ms");
//		KAssert.hasText(jzwjgid, "未找到该建筑物结构数据");
//		KAssert.hasText(dy_xh, "单元序号不能为空");
//		//KAssert.hasText("dy_mc", "单元名称不能为空");
//		KAssert.hasText(dy_lcs, "单元名楼层数不能为空");
//		KAssert.hasText(dy_ms, "每层门数不能为空");
//		short lcs=0;short dyms=0;
//		try{
//			Integer.parseInt(dy_xh);
//		}catch(NumberFormatException e){
//			AlertSLEUtil.Error("单元序号必须为数字");
//		}
//		try{
//			if((lcs=Short.parseShort(dy_lcs))<=0){
//				AlertSLEUtil.Error("单元楼层数必须大于零");
//			}
//		}catch(Exception e){
//			AlertSLEUtil.Error("单元楼层数必须为数字");
//		}
//		try{
//			if((dyms=Short.parseShort(dy_ms))<=0){
//				AlertSLEUtil.Error("每层门数必须大于零");
//			}
//		}catch(Exception e){
//			AlertSLEUtil.Error("每层门数必须为数字");
//		}
//		
//		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgid);
//		short jgdys=jzwjg.getDys()==null?0:jzwjg.getDys().shortValue();
//		
//		Long dys_from_db_table=jzwdyMapper.selectJzwDyCountByJzwjgId(jzwjgid); 
//		short real_dys= (dys_from_db_table==null?0:dys_from_db_table.shortValue());
//		if(real_dys > jgdys-1){
//			AlertSLEUtil.Error("该建筑物：<br/>实际单元个数【"+real_dys+"】大于配置单元个数【"+jgdys+"】。<br/>不能进行添加，请预先修改配置");
//		}
//		short jgLcs=jzwjg.getLcs()==null?0:jzwjg.getLcs().shortValue();
//		if(lcs>jgLcs){
//			AlertSLEUtil.Error("该建筑物：<br/>添加单元的楼层数【"+lcs+"】，<br/>大于配置楼层数【"+jgLcs+"】，请预先修改配置");
//		}
//		short jgmdyms =jzwjg.getMdyms()==null?0:jzwjg.getMdyms().shortValue();
//		if(dyms>jgmdyms){
//			AlertSLEUtil.Error("该单元：<br/>每层门数【"+dyms+"】，<br/>大于配置门数【"+jgmdyms+"】，请预先修改配置");
//		}
//		
//		List<Jzwdy> dyList=jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgid);
//		if(dyList!=null&&dyList.size()>0){
//			for(Jzwdy dyx:dyList){
////				if(dy_xh.trim().equals(dyx.getDyxh())){
////					AlertSLEUtil.Error("该单元序号：【"+dy_xh+"】已经存在");
////				}
//				try{
//					short dy_xh_from_param=Short.parseShort(dy_xh);
//					short dyx_from_db=Short.parseShort(dyx.getDyxh());
//					if(dy_xh_from_param<=0){
//						AlertSLEUtil.Error("单元序号必须大于零");
//					}
//					if(dy_xh_from_param==dyx_from_db){
//						AlertSLEUtil.Error("该单元序号：【"+dy_xh+"】已经存在");
//					}
//				}catch(NumberFormatException e){
//					if(dyx.getDyxh()==null||dyx.getDyxh().isEmpty()){
//						AlertSLEUtil.Error("该建筑物结构中，存在单元序号为空的数据，不符合规范，<br/>请首先修改后进行本操作。");
//					}
//					AlertSLEUtil.Error("该建筑物结构中，单元序号：【"+dyx.getDyxh()+"】,不是数字，不符合规范，<br/>请首先修改后进行本操作。");
//				}
//			}
//		}
//		Jzwdy jzwdy=JzwJgUtil.constructJzwDy(jzwjgid, dy_xh, dy_mc, lcs, dyms);
//		String jzwjgdyid=jzwdy.getJzwdyid();
//		List<Jzwlc> lcList=Lists.newArrayList();
//		for(int i=1;i<=lcs;i++){
//			Jzwlc lc=JzwJgUtil.constructJzwLc(jzwjgid, jzwjgdyid, ""+i, i+"楼");
//			lcList.add(lc);
//		}
//		jzwdyMapper.insertSelective(jzwdy);
//		jzwlcMapper.batchInsertJzwlc(lcList);
//		
//
//		if(jzwjg.getIsbuild()!=null||jzwjg.getIsvalid()!=null){
//			jzwjg.setIsbuild(null);
//			jzwjg.setIsvalid(null);
//			jzwjgMapper.resetJzwjg(jzwjg);
//		}
//		
////		Long dys=jzwdyMapper.selectJzwDyCountByJzwjgId(jzwjgid);
////		short dysx=(dys==null?0:dys.shortValue());
////		Long maxLcs=jzwlcMapper.selectMaxJzwlcCountGroupByJzwDyid(jzwjgid);
////		short maxLcsx=(maxLcs==null?0:maxLcs.shortValue());
////		
////		jzwjg.setDys(dysx>jzwjg.getDys()?dysx:jzwjg.getDys());
////		jzwjg.setLcs(maxLcsx>jzwjg.getLcs()?maxLcsx:jzwjg.getLcs());
////		jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
//		
//	}
//	@Override
//	@Transactional(rollbackFor=Exception.class)
//	public void delJzwjgDy(String jzwjgid,String dyid) {
//		KAssert.hasText(dyid, "请选择要删除的单元");
//		Long count=jzwfjMapper.selectJzwfjCountByJzwDyId(dyid);
//		if(count!=null&&count>0){
//			AlertSLEUtil.Error("该单元下已经存在房间，不允许删除");
//		}
//		//List<Jzwlc> lcList=jzwlcMapper.selectJzwlcByJzwdyid(dyid);
//		String del_lc_sql="delete from DZ_JZWLC where JZWDYID=:jzwdyid";
//		jdbcBaseDao.jdbcDelete(del_lc_sql, ParamObject.new_NP_NC().addSQLParam("jzwdyid", dyid));
//		String del_dy_sql="delete from DZ_JZWDY where JZWDYID=:jzwdyid";
//		jdbcBaseDao.jdbcDelete(del_dy_sql, ParamObject.new_NP_NC().addSQLParam("jzwdyid", dyid));
//		
//		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgid);
//		if(jzwjg.getIsbuild()!=null||jzwjg.getIsvalid()!=null){
//			if (jzwjg!=null) {
//				jzwjg.setIsbuild(null);
//				jzwjg.setIsvalid(null);
//				jzwjgMapper.resetJzwjg(jzwjg);
//			}
//		}
//		
//		//Long dys=jzwdyMapper.selectJzwDyCountByJzwjgId(jzwjgid);
//		//short dysx=(dys==null?0:dys.shortValue());
//		
//		//Long maxLcs=jzwlcMapper.selectMaxJzwlcCountGroupByJzwDyid(jzwjgid);
//		//short maxLcsx=(maxLcs==null?0:maxLcs.shortValue());
//		
//		//jzwjg.setDys(dysx>jzwjg.getDys()?dysx:jzwjg.getDys());
//		//jzwjg.setLcs(maxLcsx);
//		
//		//jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
//	}
//	
//	//{"jzwjgid":jzwjgid,"jzwjgDyid":jzwjgDyid,"lc_xh":lc_xh,"lc_mc":lc_mc};
//	public void validateLc(Map<?, ?> view){
//		
//	}
////{"jzwjgid":jzwjgid,"jzwjgDyid":jzwjgDyid,"lc_xh":lc_xh,"lc_mc":lc_mc};
////	可以添加地下楼层？？？？？
//	@Override
//	@Transactional(rollbackFor=Exception.class)
//	public void addJzwjgLc(Map<?, ?> view) {
//		
//		String jzwjgid=(String) view.get("jzwjgid");
//		String jzwjgDyid=(String)view.get("jzwjgDyid");
//		String lc_xh=(String)view.get("lc_xh");
//		String lc_mc=(String)view.get("lc_mc");
//		KAssert.hasText(jzwjgid, "未找到该建筑物结构数据");
//		KAssert.hasText(jzwjgDyid, "未找到该楼层所属单元数据");
//		KAssert.hasText(lc_xh, "楼层序号不能为空");
//		//KAssert.hasText(lc_mc, "楼层名称不能为空");
//		try{
//			if(Short.parseShort(lc_xh)==0){
//				AlertSLEUtil.Error("楼层序号必须不等于零");
//			}
//		}catch(NumberFormatException e){
//			AlertSLEUtil.Error("楼层序号必须为数字");
//		}
//		
//		
//		short max_lc_xhx=Short.MIN_VALUE;
//		short min_lc_xhx=Short.MAX_VALUE;
//		List<Jzwlc> lcList=jzwlcMapper.selectSortedJzwlcByJzwdyid(jzwjgDyid);
//		Jzwdy dy=jzwdyMapper.selectByPrimaryKey(jzwjgDyid);
//		if(dy==null){AlertSLEUtil.Error("未找到该单元信息");}
//		Short dy_lcsx=dy.getLcs();
//		short dy_lcsxx=(dy_lcsx==null?0:dy_lcsx.shortValue());
//		if(lcList.size()+1>dy_lcsxx){
//			AlertSLEUtil.Error("该单元配置楼层数【"+dy_lcsxx+"】小于实际楼层数【"+lcList.size()+"】，请修改后添加");
//		}
//		
//		if(lcList!=null&&lcList.size()>0){
//			for(Jzwlc lcx:lcList){
//				try{
//					if(( Short.parseShort(lcx.getLcxh()))==0){
//						AlertSLEUtil.Error("该单元内，存在楼层序号：【"+(lcx.getLcxh())+"】不符合规范，<br/>请首先修改后进行本操作。");
//					}
//					
//					if(max_lc_xhx<Short.parseShort(lcx.getLcxh())){
//						max_lc_xhx=Short.parseShort(lcx.getLcxh());
//					}
//					if(min_lc_xhx>Short.parseShort(lcx.getLcxh())){
//						min_lc_xhx=Short.parseShort(lcx.getLcxh());
//					}
//					short lcxh_from_param=Short.parseShort(lc_xh);
//					short lcxh_from_db=Short.parseShort(lcx.getLcxh());
//					
//					if(lcxh_from_param==lcxh_from_db){
//						AlertSLEUtil.Error("该单元序号：【"+lcxh_from_db+"】已经存在");
//					}
//					
//				}catch(NumberFormatException e){
//					if(lcx.getLcxh()==null||lcx.getLcxh().isEmpty()){
//						AlertSLEUtil.Error("该单元内，存在楼层序号为空的数据，不符合规范，<br/>请首先修改后进行本操作。");
//					}
//					AlertSLEUtil.Error("该单元内，存在楼层序号：【"+lcx.getLcxh()+"】,不是数字，不符合规范，<br/>请首先修改后进行本操作。");
//				}
//				
////				if(lc_xh.trim().equals(lcx.getLcxh())){
////					AlertSLEUtil.Error("该单元内，楼层序号：【"+lc_xh+"】已经存在");
////				}
//			}
//		}
//		
//		//
//		if(max_lc_xhx>0&&max_lc_xhx+1<Short.parseShort(lc_xh)){
//			AlertSLEUtil.Error("您输入的楼层序号：【"+lc_xh+"】,与该单元内最大楼层序号：【"+max_lc_xhx+"】，存在不连续情况，请重新输入。");
//		}
//		if(min_lc_xhx<0&&min_lc_xhx-1>Short.parseShort(lc_xh)){
//			AlertSLEUtil.Error("您输入的楼层序号：【"+lc_xh+"】,与该单元内最大楼层序号：【"+max_lc_xhx+"】，存在不连续情况，请重新输入。");
//		}
//		
//		Jzwlc lc=JzwJgUtil.constructJzwLc(jzwjgid,jzwjgDyid, lc_xh, lc_mc);
//		jzwlcMapper.insertSelective(lc);
//		
//		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgid);
//		
//		if(jzwjg.getIsbuild()!=null||jzwjg.getIsvalid()!=null){
//			if (jzwjg!=null) {
//				jzwjg.setIsbuild(null);
//				jzwjg.setIsvalid(null);
//				jzwjgMapper.resetJzwjg(jzwjg);
//			}
//		}
//		
//		Long maxLcs=jzwlcMapper.selectMaxJzwlcCountGroupByJzwDyid(jzwjgid);
//		short maxLcsx=(maxLcs==null?0:maxLcs.shortValue());
//		short lcsxx=jzwjg.getLcs()==null?0:jzwjg.getLcs().shortValue();
//		
//		if(maxLcsx>lcsxx){
//			AlertSLEUtil.Error("该建筑物：<br/>添加单元的楼层数【"+maxLcsx+"】，<br/>大于配置楼层数【"+lcsxx+"】，请预先修改配置");
//		}
//		
//	}
//	//{"jzwjgid":jzwjgid,"jzwjgDyid":ss_dyid,"max_lc_xh":max_lc_xh,"default_lc_mc":default_lc_mc};
//	@Override
//	@Transactional(rollbackFor=Exception.class)
//	public void batchAddJzwjgLc(Map<String, Object> view) {
//		String jzwjgid=(String) view.get("jzwjgid");
//		String jzwjgDyid=(String)view.get("jzwjgDyid");
//		String max_lc_xh=(String)view.get("max_lc_xh");
//		String default_lc_mc=(String)view.get("default_lc_mc");
//		KAssert.hasText(jzwjgid, "未找到该建筑物结构数据");
//		KAssert.hasText(jzwjgDyid, "未找到该楼层所属单元数据");
//		KAssert.hasText(max_lc_xh, "最大楼层序号不能为空");
//		KAssert.hasText(default_lc_mc, "默认名称后缀不能为空");
//		short max_lc_xhx=0;
//		try{
//			if(( max_lc_xhx=Short.parseShort(max_lc_xh))<=0){
//				AlertSLEUtil.Error("最大楼层数据数必须大于零");
//			}
//		}catch(Exception e){
//			AlertSLEUtil.Error("最大楼层数必须为数字");
//		}
//		
//		List<Jzwlc> lcList=jzwlcMapper.selectSortedJzwlcByJzwdyid(jzwjgDyid);
//		List<Short> lcxhList=Lists.newArrayList();
//		if(lcList!=null&&lcList.size()>0){
//			for(Jzwlc lc:lcList){
//				try{
//					if(Short.parseShort(lc.getLcxh().trim())<=0){
//						AlertSLEUtil.Error("该单元中存在楼层序号为零的数据，请修改该数据，然后进行批量添加工作");
//					}
//				}catch(Exception e){
//					AlertSLEUtil.Error("该单元中存在楼层序号不是数字的数据，请修改该数据，然后进行批量添加工作");
//				}
//				lcxhList.add(Short.parseShort(lc.getLcxh().trim()));
//			}
//		}
//		short max=Collections.max(lcxhList);
//		short min=Collections.min(lcxhList);
//		if(max>max_lc_xhx+1){
//			AlertSLEUtil.Error("系统已存在的该单元楼层数据中,<br/>最大楼层序号：【"+max+"】，<br/>不在您输入的最大楼层序号:【1-"+max_lc_xhx+"】范围内，<br/>请重新输入最大值，或修改原有楼层序号最大值");
//		}
//		List<Jzwlc> lcListForNewAdd=Lists.newArrayList();
//		for(short i=1;i<=max_lc_xhx;i++){
//			Short taegerXh=new Short(i);
//			if(lcxhList.contains(taegerXh)){
//				continue;
//			}
//			Jzwlc lc=JzwJgUtil.constructJzwLc(jzwjgid, jzwjgDyid, ""+i, i+"楼");
//			lcListForNewAdd.add(lc);
//		}
//		if(lcListForNewAdd!=null&&lcListForNewAdd.size()>0){
//			jzwlcMapper.batchInsertJzwlc(lcListForNewAdd);
//		}
//		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgid);
//		if(jzwjg.getIsbuild()!=null||jzwjg.getIsvalid()!=null){
//			if (jzwjg!=null) {
//				jzwjg.setIsbuild(null);
//				jzwjg.setIsvalid(null);
//				jzwjgMapper.resetJzwjg(jzwjg);
//			}
//		}
//		Long maxLcs=jzwlcMapper.selectMaxJzwlcCountGroupByJzwDyid(jzwjgid);
//		short maxLcsx=(maxLcs==null?0:maxLcs.shortValue());
//		short lcsxx=jzwjg.getLcs()==null?0:jzwjg.getLcs().shortValue();
////		jzwjg.setLcs(maxLcsx);
////		jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
//		
//		if(maxLcsx>lcsxx){
//			AlertSLEUtil.Error("该建筑物：<br/>添加单元的楼层数【"+maxLcsx+"】，<br/>大于配置楼层数【"+lcsxx+"】，请预先修改配置");
//		}
//		
//	}
//	@Override
//	@Transactional(rollbackFor=Exception.class)
//	public void delJzwjgLc(String jzwjgid,String lcid) {
//		KAssert.hasText(lcid, "请选择要删除的 数据");
//		Long count=jzwfjMapper.selectJzwfjCountByJzwLcId(lcid);
//		if(count!=null&&count>0){
//			AlertSLEUtil.Error("该楼层下已经存在房间，不允许删除");
//		}
//		String del_lc_sql="delete from DZ_JZWLC where JZWLCID=:jzwlcid";
//		jdbcBaseDao.jdbcDelete(del_lc_sql, ParamObject.new_NP_NC().addSQLParam("jzwlcid", lcid));
//		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgid);
//		if(jzwjg.getIsbuild()!=null||jzwjg.getIsvalid()!=null){
//			if (jzwjg!=null) {
//				jzwjg.setIsbuild(null);
//				jzwjg.setIsvalid(null);
//				jzwjgMapper.resetJzwjg(jzwjg);
//			}
//		}
//		//Long maxLcs=jzwlcMapper.selectMaxJzwlcCountGroupByJzwDyid(jzwjgid);
//		//short maxLcsx=(maxLcs==null?0:maxLcs.shortValue());
//		//jzwjg.setLcs(maxLcsx);
//		//jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
//	}
//
//
////------------------------------------------------------------------------------------------------------------
////----------------以下函数禁用------------------------------------------------------------------------------------------\
//	
//	
//		/**
//		 * 遍历建筑物中所有单元楼层房间，为房间生成坐标
//		 * @param jzwjg
//		 *20160505 按照建筑物结构配置进行生成
//		 */
//		@Transactional(rollbackFor=Exception.class)
//		private void initBuildJzwfjZB(String jzwjgId){
//			Jzwjg jzwjg =jzwjgMapper.selectByPrimaryKey(jzwjgId);
//			if(jzwjg.getIsbuild()!=null&&jzwjg.getIsbuild().equals(KConstants.ISBUILD_JZWJG)){
//				AlertSLEUtil.Error("该建筑物已经生成房间坐标数据，不能进行本操作");
//			}
//			List<Jzwdy> jzwdyList=jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgId);
//			List<Jzwlc> jzwlcListx=jzwlcMapper.selectSortedJzwlcByJzwJgid(jzwjgId);
//			List<Jzwfj> jzwfjListx=jzwfjMapper.selectSortedFjByJzwjgId(jzwjgId);
//			List<Jzwfj> targetJzwfjList = Lists.newArrayList();
//			//遍历单元
//			for (int i = 0; i < jzwdyList.size(); i++) {
//						Jzwdy jzwdy = jzwdyList.get(i);
////						int dyms = jzwdy.getDyms();//单元门数
//						//查出该单元下的楼层
//						List<Jzwlc> jzwlcList = JzwJgNewUtil.genLcListByJzwdy(jzwdy.getJzwdyid(), jzwlcListx);
//						//遍历该单元的楼层
//						for (int j = 0; j < jzwlcList.size(); j++) {
//							Jzwlc jzwlc = jzwlcList.get(j);
//							//查出该楼层下的房间
//							List<Jzwfj> jzwfjList = JzwJgNewUtil.genFjListByJzwLc(jzwlc.getJzwlcid(), jzwfjListx);
//							
//							if(jzwfjList==null){
//								jzwfjList=Collections.emptyList();
//							}
//							//遍历该单元该层所有房间
//							for (int k = 0; k < jzwfjList.size(); k++) {
//								Date date= new Date();
//								Jzwfj jzwfj = jzwfjList.get(k);
//								int width=KConstants.jzwWidth;//120;
//								int height=KConstants.jzwHeight;//70;
//								int left=((jzwfjList.size()*i)+k)*width;
//								int top=(jzwlcList.size()-(j+1))*height;
//								
//								String showInfo = ""+left+","+top+","+width+","+height;
//								jzwfj.setShowInfo(showInfo);
//								jzwfj.setZhgxrq(date);
//								jzwfj.setUpdated(date);
//								jzwfj.setUpdatedby(SecurityUtils.getSessionUser().getUserId());
//								jzwfj.setShowMode("1");
//								targetJzwfjList.add(jzwfj);
//							}
//						}
//					}
//			if(!CollectionUtils.isEmpty(targetJzwfjList) ){
//				jzwfjMapper.batchUpdateJzwfj(targetJzwfjList);
//			}
//			//建筑物结构生成标志位设为已生成状态
//			Jzwjg jzwjgx =new Jzwjg();
//			jzwjgx.setJzwjgid(jzwjgId);
//			jzwjgx.setIsbuild(KConstants.ISBUILD_JZWJG);
//			jzwjgMapper.updateByPrimaryKeySelective(jzwjgx);
//			
//		}
//	/**
//	 * 遍历建筑物中所有单元楼层房间，为房间生成坐标
//	 * @param jzwjg
//	 */
//	@Transactional(rollbackFor=Exception.class,readOnly=true)
//	private void buildJzwfjZB_bak(String jzwjgId){
//		List<Jzwdy> jzwdyList = jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgId);
//		List<Jzwfj> targetJzwfjList = Lists.newArrayList();
//		//遍历单元
//		for (int i = 0; i < jzwdyList.size(); i++) {
//			Jzwdy jzwdy = jzwdyList.get(i);
////			int dyms = jzwdy.getDyms();//单元门数
//			//查出该单元下的楼层
//			List<Jzwlc> jzwlcList = jzwlcMapper.selectSortedJzwlcByJzwdyid(jzwdy.getJzwdyid());
//			//遍历该单元的楼层
//			for (int j = 0; j < jzwlcList.size(); j++) {
//				Jzwlc jzwlc = jzwlcList.get(j);
//				//查出该楼层下的房间
//				List<Jzwfj> jzwfjList = jzwfjMapper.selectSortedJzwfjByJzwlcId(jzwlc.getJzwlcid());
//				if(jzwfjList==null){
//					jzwfjList=Collections.emptyList();
//				}
//				//遍历该单元该层所有房间
//				for (int k = 0; k < jzwfjList.size(); k++) {
//					Date date= new Date();
//					Jzwfj jzwfj = jzwfjList.get(k);
//					int width=KConstants.jzwWidth;//120;
//					int height=KConstants.jzwHeight;//70;
////					int left=((dyms*i)+k)*width;
//					int left=((jzwfjList.size()*i)+k)*width;
//					int top=(jzwlcList.size()-(j+1))*height;
//					//left=240;
//					String showInfo = ""+left+","+top+","+width+","+height;
//					jzwfj.setShowInfo(showInfo);
//					jzwfj.setZhgxrq(date);
//					jzwfj.setUpdated(date);
//					jzwfj.setUpdatedby(SecurityUtils.getSessionUser().getUserId());
//					jzwfj.setShowMode("1");
//					targetJzwfjList.add(jzwfj);
////					jzwfjMapper.updateJzwfj(jzwfj);
//				}
//			}
//		}
//		if(!CollectionUtils.isEmpty(targetJzwfjList) ){
//			jzwfjMapper.batchUpdateJzwfj(targetJzwfjList);
//		}
//		
//	}
//
//	@Override
//	public void updateJzwjgDyLcXh(Map<?, ?> view) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateJzwjgDyLcXh_20160521(Map<?, ?> view) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delJzwjgDy_20160521(String jzwjgid, String dyid) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delJzwjgfj(String jzwjgid, String fjid) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//
//	
//	
////	@Transactional(rollbackFor=Exception.class)
////	public Jzwjg findAndBuildJzwjgById_bak(String jzwjgId) {
////		if(!StringUtils.hasText(jzwjgId)){
////			return null;
////		}
////		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgId);
////		List<Jzwlc> lcxhList = jzwlcMapper.selectDistinctLcxhByJzwJgid(jzwjgId);
////		KAssert.notEmpty(lcxhList, "该建筑物没有【楼层】数据，请在建筑物维护部分进行维护。");
////		
////		List<Jzwdy> dyxhList = jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgId);
////		KAssert.notEmpty(dyxhList, "该建筑物没有【单元】数据，请在建筑物维护部分进行维护。");
////		
////		String validate_lcxh ="";
////		String validate_dyxh ="";
////		//如果单元序号、楼层序号 未进行校验  或者 未通过检验，则进行检验
////		if (!(KConstants.ISVALID_JZWJG_YES.equals(jzwjg.getIsvalid()))) {
////			validate_lcxh = JzwJgUtil.validate_Lc_Dy_XH_DtaType(lcxhList,"jzwlc");
////			validate_dyxh = JzwJgUtil.validate_Lc_Dy_XH_DtaType(dyxhList,"jzwdy");
////		}
////		//检验不通过
////		if (!("".equals(validate_lcxh))||!("".equals(validate_dyxh))) {
////			jzwjg.setIsvalid(KConstants.ISVALID_JZWJG_NO);//检验不通过
////			AlertSLEUtil.FormatError(validate_lcxh+validate_dyxh);
////			//---检验不通过。往下代码不会执行
////		}
////		//---检验通过
////		//单元序号 楼层序号校验通过后要进行生成房间坐标操作 
////		//如果建筑物中房间未生成坐标，则要进行生成操作                                        1
////		if (jzwjg.getIsbuild()==null||!(KConstants.ISBUILD_JZWJG.equals(jzwjg.getIsbuild()))) {
////			initBuildJzwfjZB(jzwjgId);//生成房间
////		}
////		
////		//检验通过，才会执行--校验通过->此时没有Isvalid填值，此时 进行填值，如果已经填值，则不进行填值
////		//如果建筑物结构ISVALID字段为NULL或者为未通过状态，则将其改为通过状态
////		if (!(KConstants.ISVALID_JZWJG_YES.equals(jzwjg.getIsvalid()))) {
////			jzwjg.setIsvalid(KConstants.ISVALID_JZWJG_YES);
////			jzwjgMapper.updateByPrimaryKeySelective(jzwjg);//检验通过
////		}
////		return jzwjg;
////		
////	}
//	
//}
////只要经过合并拆分，之后，长宽，不等于 120,70，这种房间，不要重新生成坐标，用原先的坐标
////// 后续添加楼层，单元后，重新生成建筑物结构，房间坐标
////int heightx=JzwJgUtil.getHeight(jzwfj);
////int widthx=JzwJgUtil.getWidth(jzwfj);
////if(KConstants.jzwHeight!=heightx||KConstants.jzwWidth!=widthx){
////	continue;
////}