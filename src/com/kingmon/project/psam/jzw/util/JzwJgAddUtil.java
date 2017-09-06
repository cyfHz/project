//package com.kingmon.project.psam.jzw.util;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.kingmon.base.common.KConstants;
//import com.kingmon.base.exception.ServiceLogicalException;
//import com.kingmon.base.util.AlertSLEUtil;
//import com.kingmon.base.util.KAssert;
//import com.kingmon.common.authUtil.SecurityUtils;
//import com.kingmon.project.psam.jzw.mapper.JzwdyMapper;
//import com.kingmon.project.psam.jzw.mapper.JzwfjMapper;
//import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
//import com.kingmon.project.psam.jzw.mapper.JzwlcMapper;
//import com.kingmon.project.psam.jzw.model.Jzwdy;
//import com.kingmon.project.psam.jzw.model.Jzwfj;
//import com.kingmon.project.psam.jzw.model.Jzwjg;
//import com.kingmon.project.psam.jzw.model.Jzwlc;
//import com.kingmon.project.psam.jzw.serivice.impl.JzwJgUtil;
//import com.kingmon.project.psam.jzw.view.JzwFjShowInPage;
//
//public class JzwJgAddUtil {
//	
//	
//
//	@SuppressWarnings("unchecked")
//	public void addJzwjgRegular(
//			Map<?, ?> view,
//			JzwjgMapper jzwjgMapper,
//			JzwdyMapper jzwdyMapper,
//			JzwlcMapper jzwlcMapper,
//			JzwfjMapper jzwfjMapper) {
//		
//		// 记得修改。。。。* ### // jzwId="0fdbf914-0841-4061-9ee7-d838e65650c5";
//		String jzwId = (String) (view.get("jzwId"));// 建筑物Id
//		KAssert.notNull(jzwId, "请选择建筑物");
//
//		JzwJgValiUtil.validateMap(view);// 数据校验
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
//			Jzwdy jzwdy = JzwJgUtil.constructJzwDy(jzwjg.getJzwjgid(), JzwJgUtil.processDyLcXh(("" + (d + 1)), 3), dymc, lcs, fjs);
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
//
//			jzwjgMapper.updateByPrimaryKey(jzwjg);
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
//	
//	@Transactional(rollbackFor=Exception.class)
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void addJzwjgNoRegular(
//			Map<?, ?> view,
//			JzwjgMapper jzwjgMapper,
//			JzwdyMapper jzwdyMapper,
//			JzwlcMapper jzwlcMapper,
//			JzwfjMapper jzwfjMapper){
//		String jzwId = (String) (view.get("jzwId"));// 建筑物Id
//		KAssert.notNull(jzwId, "请选择建筑物");
//
//		JzwJgValiUtil.validateMap(view);// 数据校验
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
//			jzwjgMapper.updateJzwjg(jzwjg);
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
//}
