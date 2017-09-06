package com.kingmon.project.psam.jzw;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.project.psam.jzw.mapper.JzwdyMapper;
import com.kingmon.project.psam.jzw.mapper.JzwfjMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
import com.kingmon.project.psam.jzw.mapper.JzwlcMapper;
import com.kingmon.project.psam.jzw.model.Jzwdy;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.model.Jzwjg;
import com.kingmon.project.psam.jzw.model.Jzwlc;
import com.kingmon.project.psam.jzw.view.JzwFjShowInPage;

public class JzwJgNewUtil {
	
	public static  Jzwdy genDyByxh(short dyxh,List<Jzwdy> dylist){
		if(dylist==null||dylist.size()==0){
			return null;
		}
		for(Jzwdy dy:dylist){
			try{
				if(dyxh==Short.parseShort(dy.getDyxh())){
					return dy;
				}
			}catch(NumberFormatException e){}
			
		}
		return null;
	}
	
	public static  List<Jzwlc> genLcListByJzwdy(String dyid,List<Jzwlc> lclist){
		List<Jzwlc> lcListInDy=Lists.newArrayList();
		if(lclist==null||lclist.size()==0){
			return lcListInDy;
		}
		for(Jzwlc lc:lclist){
			if((""+dyid).equals(lc.getJzwdyid())){
//				lcListInDy.add(lc);
				if(Short.parseShort(lc.getLcxh())>0){
					lcListInDy.add(lc);
				}
			}
		}
		return lcListInDy;
	}
	public static  List<Jzwlc> genDxLcList(List<Jzwlc> lclist){
		List<Jzwlc> dx_lcList=Lists.newArrayList();
		if(lclist==null||lclist.size()==0){
			return dx_lcList;
		}
		for(Jzwlc lc:lclist){
			if(Short.parseShort(lc.getLcxh())<0){
				dx_lcList.add(lc);
			}
		}
		return dx_lcList;
	}
	public static  Jzwlc genLcByxh(short lcxh,List<Jzwlc> lclist){
		if(lclist==null||lclist.size()==0){
			return null;
		}
		for(Jzwlc lc:lclist){
			try{
				if(lcxh==Short.parseShort(lc.getLcxh())){
					return lc;
				}
			}catch(NumberFormatException e){}
			
		}
		return null;
	}
	/**
	 * 地下楼层不随单元分布，，，，获取
	 * @param lcxh
	 * @param dylist
	 * @param lcjglist
	 * @return
	 */
	public static  Jzwlc genDxLcByxh(short lcxh,List<Jzwdy> dylist,List<Jzwlc> lcjglist){
		if(lcjglist==null||lcjglist.size()==0){
			return null;
		}
		for(Jzwdy jzwdy:dylist){
			List<Jzwlc> lcList=genLcListByJzwdy(jzwdy.getJzwdyid(), lcjglist);
			for(Jzwlc jzwlc:lcList){
				try{
					if(lcxh==Short.parseShort(jzwlc.getLcxh())){
						return jzwlc;
					}
				}catch(NumberFormatException e){}
			}
		}
		return null;
	}

	public static  Jzwdy getDyByid(String jzwdyid,List<Jzwdy> dylist){
		if(dylist==null||dylist.size()==0||jzwdyid==null){
			return null;
		}
		for(Jzwdy dy:dylist){
			if(jzwdyid.equals(dy.getJzwdyid())){
				return dy;
			}
		}
		return null;
	}
	public static  Jzwlc getLcByid(String jzwlcid,List<Jzwlc> lclist){
		if(lclist==null||lclist.size()==0||jzwlcid==null){
			return null;
		}
		for(Jzwlc lc:lclist){
			if(jzwlcid.equals(lc.getJzwlcid())){
				return lc;
			}
		}
		return null;
	}
	
	
	public static  List<Jzwfj> genFjListByJzwLc(String lcid,List<Jzwfj> fjlist){
		List<Jzwfj> fjListInLc=Lists.newArrayList();
		if(fjlist==null||fjlist.size()==0){
			return fjListInLc;
		}
		for(Jzwfj fj:fjlist){
			if((""+lcid).equals(fj.getJzwlcid())){
				fjListInLc.add(fj);
			}
		}
		return fjListInLc;
	}
	public static Jzwfj getFjByFjxh(short fjxh,List<Jzwfj> fjlist){
		if(fjlist==null||fjlist.size()==0){
			return null;
		}
		for(Jzwfj fj:fjlist){
			try{
				if(fjxh==Short.parseShort(fj.getFjxh())){
					return fj;
				}
			}catch(NumberFormatException e){}
		}
		return null;
	}
	public static  List<Jzwfj> genFjListByJzwDy(String dyid,List<Jzwfj> fjlist){
		List<Jzwfj> fjListInLc=Lists.newArrayList();
		if(fjlist==null||fjlist.size()==0){
			return fjListInLc;
		}
		for(Jzwfj fj:fjlist){
			if((""+dyid).equals(fj.getJzwdyid())){
				fjListInLc.add(fj);
			}
		}
		return fjListInLc;
	}
	
	
	
	
	public static KJSONMSG validateJzwjg(Jzwjg jzwjg,List<Jzwdy> dylist,List<Jzwlc> lclist,List<Jzwfj> fjlist) {
		if(jzwjg==null){
			return new KJSONMSG(300, "建筑物结构错误：未查询到该建筑物结构数据");
		}
		
		Short lcs=jzwjg.getLcs();//楼层数
		Short dys=jzwjg.getDys();//单元数/行数
		Short mdyms=jzwjg.getMdyms();//每层门数/房间数
		
		String sflcxt=jzwjg.getSflcxt();// 是否单元楼层相同 1:相同，0：不同
		String sfmsxt=jzwjg.getSfmsxt();//  是否门数相同 1:相同，0：不同、
		
		Short dxcs=jzwjg.getDxcs();//  地下层数
		Short dxmcms=jzwjg.getDxmcms();//地下每层门数/房间数
		
		if(dys==null||dys.shortValue()<=0){
			return new KJSONMSG(300, "建筑物结构错误：未指定单元数");
		}
		if(lcs==null||lcs.shortValue()<=0){
			return new KJSONMSG(300, "建筑物结构错误：未指定楼层数");
		}
		if(mdyms==null||mdyms.shortValue()<=0){
			return new KJSONMSG(300, "建筑物结构错误：未指定每层门数");
		}
	if(dylist==null||dylist.size()!=dys.shortValue()){
		return new KJSONMSG(300, "建筑物结构错误：<br>  实际单元个数:【"+(dylist.size())+"】不等于指定单元数:【"+dys+"】");
	}
//	if(lclist==null||lclist.size()!=lcs.shortValue()){
//		return new KJSONMSG(300, "建筑物结构错误：<br>  实际楼层个数:【"+lclist.size()+"】不等于指定楼层数:【"+lcs+"】");
//	}
//-----------------dy---------------------------------------------------------------------------------------------------	
	for(Jzwdy dy:dylist){
		String dy_mc=dy.getDymc();
		String dy_xh=dy.getDyxh();
		Short dy_lcs=dy.getLcs();
		Short dy_dyms=dy.getDyms();
		String dyid=dy.getJzwdyid();
//		if(dy_mc==null||dy_mc.trim().isEmpty()){
//			return new KJSONMSG(300, "存在单元名称为空的数据项");
//		}
		if(dy_xh==null||dy_xh.trim().isEmpty()){
			return new KJSONMSG(300, "建筑物结构错误：存在单元序号为空的数据项");
		}
		try {
			int dyxh=Integer.parseInt(dy_xh.trim());
			
			if(dyxh>dys){
				return new KJSONMSG(300, "建筑物结构错误：存在错误单元序号【"+dy_xh+"】大于指定单元数：【"+dys+"】");
			}
		} catch (Exception e) {
			return new KJSONMSG(300, "建筑物结构错误：存在单元序号【"+dy_xh+"】不是数字，不符合规范");
		}
		
		if(dy_dyms==null||dy_dyms.shortValue()<=0){
			return new KJSONMSG(300, "建筑物结构错误：存在序号【"+dy_xh+"】的单元：该单元未指定单元每层户数");
		}
		
		//2016-05-14去掉
		if(dy_lcs==null||dy_lcs.shortValue()<=0){
			//return new KJSONMSG(300, "建筑物结构错误：存在序号【"+dy_xh+"】的单元：该单元未指定楼层数");
		}
		//2016-05-14去掉
		if(dy_lcs>lcs){
			//return new KJSONMSG(300, "建筑物结构错误：存在序号【"+dy_xh+"】的单元：该单元指定楼层数:【"+dy_lcs+"】大于该建筑指定最大楼层数：【"+lcs+"】");
		}
		
		
		List<Jzwlc> dy_lcList=JzwJgNewUtil.genLcListByJzwdy(dyid,lclist);
		
		if(dy_lcList==null||dy_lcList.size()!=dy_lcs.shortValue()){
			String msg=(String) ("建筑物结构错误： 单元：【"+dy_xh+"】， 指定楼层数：【"+dy_lcs+"】 不等于实际楼层【"+(dy_lcList==null?0:dy_lcList.size())+"】");
			//return new KJSONMSG(300,msg );
		}
		if(dy_lcList==null||dy_lcList.size()>lcs){
			String msg=(String) ("建筑物结构错误： 单元：【"+dy_xh+"】，实际楼层【"+(dy_lcList==null?0:dy_lcList.size())+"】大于 指定楼层数：【"+lcs+"】");
			return new KJSONMSG(300,msg );
		}
//-----------------lc---------------------------------------------------------------------------------------------------		
		for(Jzwlc dylc: dy_lcList){
			String lc_mc=dylc.getLcmc();
			String lc_xh= dylc.getLcxh();
			String lcid=dylc.getJzwlcid();
//			if(lc_mc==null||lc_mc.trim().isEmpty()){
//				return new KJSONMSG(300, "存在楼层名称为空的数据项");
//			}
			if (lc_xh == null||lc_xh.trim().isEmpty()) {
				return new KJSONMSG(300, "建筑物结构错误： 单元序号：【"+dy_xh+"】,存在楼层序号为空数据项");
			}
			try {
				int lcxh= Integer.parseInt(lc_xh.trim());
				
				if(lcxh>dy_lcs){
					//2016-05-14去掉
					//return new KJSONMSG(300, "建筑物结构错误：存在楼层序号【"+lc_xh+"】大于该单元内指定楼层数：【"+dy_lcs+"】");
				}
				
				if(lcxh>lcs){
					return new KJSONMSG(300, "建筑物结构错误：存在楼层序号【"+lc_xh+"】大于该建筑物最大楼层数：【"+dy_lcs+"】");
				}
				
			} catch (Exception e) {
				return new KJSONMSG(300, "建筑物结构错误：单元序号：【"+dy_xh+"】，存在楼层序号【"+lc_xh+"】不是数字，不符合规范");
			}
//-----------------fj---------------------------------------------------------------------------------------------------
			List<Jzwfj> lc_fjList= JzwJgNewUtil.genFjListByJzwLc(lcid,fjlist);
			if(lc_fjList==null||lc_fjList.isEmpty()){
				//return new KJSONMSG(300, "建筑物结构错误：单元序号：【"+dy_xh+"】,楼层序号：【"+dy_xh+"】，内没有房间数据");
				continue;
			}
			//2016-05-31去掉
			//if(lc_fjList.size()>dy_dyms){
				//return new KJSONMSG(300, "建筑物结构错误：单元序号：【"+dy_xh+"】，楼层序号【"+lc_xh+"】,实际房间数：【"+lc_fjList.size()+"】,大于该单元配置每层房间数：【"+dy_dyms+"】");
			//}
			
			
			for(Jzwfj lc_fj:lc_fjList){
				String lc_fjxh=lc_fj.getFjxh();
				String lc_fjmc= lc_fj.getFjmc();
//				if(lc_mc==null||lc_mc.trim().isEmpty()){
//					return new KJSONMSG(300, "单元序号："+dy_xh+",楼层序号："+lc_xh+"， 存在房间序名称为空数据项");
//				}
				if(lc_fjxh==null||lc_fjxh.trim().isEmpty()){
					return new KJSONMSG(300, "建筑物结构错误：单元序号：【"+dy_xh+"】,楼层序号：【"+lc_xh+"】， 存在房间序号为空数据项");
				}
				try {
					Integer.parseInt(lc_fjxh.trim());
				} catch (Exception e) {
					return new KJSONMSG(300, "建筑物结构错误：单元序号：【"+dy_xh+"】,楼层序号：【"+lc_xh+"】，存在房间序号【"+lc_fjxh+"】不是数字，不符合规范");
				}
			}
		}
	}
	//单独校验是否有顺序
		try{
			vaildateContinuity( jzwjg,dylist,lclist);
		}catch(ServiceLogicalException e){
			return  new KJSONMSG(300,e.getMessage());
		}
		return  new KJSONMSG(200, "数据校验通过");
	}
	private static void vaildateContinuity(Jzwjg jzwjg,List<Jzwdy> sortedJzwdyListx,List<Jzwlc> sortedJzwlcListx){
		
		for(int i=0;i<sortedJzwdyListx.size()-1;i++){
			Jzwdy jzwdy1=sortedJzwdyListx.get(i);
			Jzwdy jzwdy2=sortedJzwdyListx.get(i+1);
			String dyxh1=jzwdy1.getDyxh();
			Short dyxh1_s=Short.parseShort(dyxh1);
			
			String dyxh2=jzwdy2.getDyxh();
			Short dyxh2_s=Short.parseShort(dyxh2);
			if(dyxh1_s+1!=dyxh2_s){
				AlertSLEUtil.Error("单元序号:【"+dyxh1+"】-【"+dyxh2+"】存在不连续数据，请维护该数据");
			}
			List<Jzwlc> jzwlcList=genLcListByJzwdy(jzwdy1.getJzwdyid(), sortedJzwlcListx);
			for(int j=0;j<jzwlcList.size()-1;j++){
				Jzwlc jzwlc1=jzwlcList.get(j);
				Jzwlc jzwlc2=jzwlcList.get(j+1);
				
				String lcxh1=jzwlc1.getLcxh();
				Short lcxh1_s=Short.parseShort(lcxh1);
				
				String lcxh2=jzwlc2.getLcxh();
				Short lcxh2_s=Short.parseShort(lcxh2);
				if(lcxh1_s+1!=lcxh2_s){
					AlertSLEUtil.Error("单元:【"+dyxh1+"】，中存在楼层序号：【"+lcxh1+"】-【"+lcxh2+"】存在不连续数据，请维护该数据");
				}
			}
		}
	}
	private static String getDxLcType(
			Jzwjg jzwjg,
			List<Jzwdy> sortedJzwdyListx,
			List<Jzwlc> sortedJzwlcListx,
			List<Jzwfj> sortedJzwfjListx){
		List<String> list=Arrays.asList(new String[]{"1","2"});
		return (jzwjg==null||!list.contains(jzwjg.getDxType()))?"1":jzwjg.getDxType();
	}
	
	public  static void initBuildJzwfjZB(
			Jzwjg jzwjg,
			List<Jzwdy> sortedJzwdyListx,
			List<Jzwlc> sortedJzwlcListx,
			List<Jzwfj> sortedJzwfjListx,
			
			JzwfjMapper jzwfjMapper,
			JzwlcMapper jzwlcMapper,
			JzwdyMapper jzwdyMapper,
			JzwjgMapper jzwjgMapper
			){
		if(jzwjg.getIsbuild()!=null&&jzwjg.getIsbuild().equals(KConstants.ISBUILD_JZWJG)){
			AlertSLEUtil.Error("该建筑物已经生成房间坐标数据，不能进行本操作");
		}
		String dxLcType=getDxLcType( jzwjg,sortedJzwdyListx,sortedJzwlcListx,sortedJzwfjListx);
		String jzwjgid=jzwjg.getJzwjgid();
		List<Jzwfj> targetJzwfjList = Lists.newArrayList();
		List<Jzwfj> targetJzwfjListIfNewConstruct = Lists.newArrayList();
		
		Short dys=jzwjg.getDys();
		Short lcs=jzwjg.getLcs();
		Short mdyms=jzwjg.getMdyms();
		Short fjs=mdyms;
		Jzwdy dzwdy_first=null;//第一个单元
		for (short d = 0; d < dys; d++) {
			Jzwdy jzwdy =genDyByxh((short)(d+1),sortedJzwdyListx);
//			Jzwdy jzwdy = sortedJzwdyListx.get(d);
			if(jzwdy==null){
				String dyxh=JzwJgUtil.processDyLcXh(("" + (d + 1)), 3);
				String dymc = dyxh+"单元";
				jzwdy = JzwJgUtil.constructJzwDy(jzwjg.getJzwjgid(), JzwJgUtil.processDyLcXh(("" + (d + 1)), 3), dymc, (short)(lcs), mdyms);
				jzwdyMapper.insertSelective(jzwdy);
			}
			if(d==0){
				dzwdy_first=jzwdy;
			}
			String jzwdyid=jzwdy.getJzwdyid();
			
			
			List<Jzwlc> jzwlcList = JzwJgNewUtil.genLcListByJzwdy(jzwdy.getJzwdyid(), sortedJzwlcListx);
			for (short c = 0; c < lcs; c++) {
				Jzwlc jzwlc = genLcByxh((short)(c+1),jzwlcList);//直接利用序号匹配
				//Jzwlc jzwlc = jzwlcList.get(c);// 根据查询顺序？？？？
				
				if(jzwlc==null){
					String lcxh=""+(c+1);
					jzwlc=JzwJgUtil.constructJzwLc(jzwjgid, jzwdyid, lcxh, lcxh+"层");	
					jzwlcMapper.insertSelective(jzwlc);
				}
				if(Integer.parseInt(jzwlc.getLcxh())<0){//地下楼层，单独处理
					continue;
				}
				List<Jzwfj> jzwfjList = JzwJgNewUtil.genFjListByJzwLc(jzwlc.getJzwlcid(), sortedJzwfjListx);
//				if(jzwfjList==null||jzwfjList.size()<=0){
//					AlertSLEUtil.Error("该建筑物当前没有房间！");
//				}
				for(short f = 0; f < mdyms; f++){
					int width = KConstants.jzwWidth;// 120;
					int height = KConstants.jzwHeight;// 70;
					int left = ((mdyms * d) + f) * width;
					int top = (lcs - (c + 1)) * height;
					JzwFjShowInPage fjInPage = new JzwFjShowInPage(1, left, top, width, height);
					
					String fjxh = (c + 1) + ((f + 1) < 10 ? ("0" + (f + 1)) : ("" + (f + 1)));
					
					Jzwfj jzwfj=getFjByFjxh(Short.parseShort(fjxh),jzwfjList);
					
					if(jzwfj==null){
						String fjmc = fjxh+"室";
						jzwfj=JzwJgUtil.constructJzwFj(jzwjgid, jzwdyid, jzwlc.getJzwlcid(), fjxh, fjmc, fjInPage);
						targetJzwfjListIfNewConstruct.add(jzwfj);
					}else{
						String fjId=jzwfj.getJzwfjid();
						jzwfj=JzwJgUtil.constructJzwFj(jzwjgid, jzwdyid, jzwlc.getJzwlcid(), jzwfj.getFjxh(), jzwfj.getFjmc(), fjInPage);
						jzwfj.setJzwfjid(fjId);
					}
					targetJzwfjList.add(jzwfj);
				}
			}
			//地下楼层随单元分布-------------------------S---------------------
			
			if("1".equals(dxLcType)){
				Short dxcs=jzwjg.getDxcs();
				dxcs=dxcs==null?0:dxcs;
				Short dxmcms=jzwjg.getDxmcms();
				dxmcms=dxmcms==null?0:dxmcms;
				int ls_totalH = lcs * KConstants.jzwHeight;
				for (int c = 0; c < dxcs; c++) {// 遍历地下层数
					String lcxh="-"+(c+1);
					Jzwlc dxlc=genLcByxh(Short.parseShort(lcxh), sortedJzwlcListx);
					if(dxlc==null){
						String lcmc = lcxh+"层";
						dxlc=JzwJgUtil.constructJzwLc( jzwjgid,jzwdy.getJzwdyid(), lcxh, lcmc);	
						jzwlcMapper.insertSelective(dxlc);
					}
					List<Jzwfj> dx_jzwfjList = JzwJgNewUtil.genFjListByJzwLc(dxlc.getJzwlcid(), sortedJzwfjListx);
					for (int f = 0; f < dxmcms; f++) {// 地下每层门数（规则情况下与楼上每层门数相同）
						int dx_width = KConstants.jzwWidth;// 120;
						int dx_height = KConstants.jzwHeight;// 70;
						int dx_left = ((dxmcms * d) + f) * dx_width;
						int dx_top = c * dx_height + ls_totalH;
						String fjxh = "-" + (c + 1) + ((f + 1) < 10 ? ("0" + (f + 1)) : ("" + (f + 1)));
						Jzwfj dx_jzwfj=getFjByFjxh(Short.parseShort(fjxh),dx_jzwfjList);
						JzwFjShowInPage fjInPage = new JzwFjShowInPage(1, dx_left, dx_top, dx_width, dx_height);
						if(dx_jzwfj==null){
							String fjmc = fjxh+"室";
							dx_jzwfj = JzwJgUtil.constructJzwFj(jzwjg.getJzwjgid(), jzwdy.getJzwdyid(), dxlc.getJzwlcid(), fjxh, fjmc, fjInPage);
							targetJzwfjListIfNewConstruct.add(dx_jzwfj);
						}else{
							String fjId=dx_jzwfj.getJzwfjid();
							dx_jzwfj=JzwJgUtil.constructJzwFj(jzwjgid, jzwdyid, dx_jzwfj.getJzwlcid(), dx_jzwfj.getFjxh(), dx_jzwfj.getFjmc(), fjInPage);
							dx_jzwfj.setJzwfjid(fjId);
						}
						targetJzwfjList.add(dx_jzwfj);
					}
				}
			}
			
			//地下楼层随单元分布-------------------------E---------------------
		}
		
		// 添加不规则分单元地下室
		if ("2".equals(dxLcType)) {
			
			Short dxcs=jzwjg.getDxcs();
			
			
			List<Jzwlc> dx_jzwlcList = JzwJgNewUtil.genDxLcList(sortedJzwlcListx);
			
			int ls_totalW = dys * fjs * KConstants.jzwWidth;// 该建筑物总宽度
			int ls_totalH = lcs * KConstants.jzwHeight;
			for(int i = 0; i < dxcs; i++){
				Short dxmcms=jzwjg.getDxmcms();
				String lcxh="-"+(i+1);
				Jzwlc dxlc=genLcByxh(Short.parseShort(lcxh), dx_jzwlcList);
				if(dxlc==null){
					String lcmc = lcxh+"层";
					dxlc=JzwJgUtil.constructJzwLc(dzwdy_first.getJzwdyid(), jzwjgid, lcxh, lcmc);	
					jzwlcMapper.insertSelective(dxlc);
				}
				List<Jzwfj> dx_jzwfjList = JzwJgNewUtil.genFjListByJzwLc(dxlc.getJzwlcid(), sortedJzwfjListx);
				dxmcms=(short) (dxmcms==0?dx_jzwfjList.size():dxmcms);
				
				int one_width = 0;// 每个房间宽度
				int one_width_sp = 0;// 不能整除的情况
				one_width = ls_totalW / dxmcms;// 设置该层每间地下室宽度
				if (ls_totalW % dxmcms == 0) {// 能整除
					one_width = ls_totalW / dxmcms;// 设置该层每间地下室宽度
				} else {
					one_width = (ls_totalW - ls_totalW % dxmcms) / dxmcms;
					one_width_sp = one_width + ls_totalW % dxmcms;// 最后一个房间宽度加上余数
				}
			
				for (int j = 0; j < dxmcms; j++) {// 地下楼层向下排列
					int width = 0;
					if ((one_width_sp != 0) && (j == dxmcms - 1)) {// 如果ls_totalW/mcms不能整除的话，最后一个房间宽度加上余数
						width = one_width_sp;
					} else {
						width = one_width;
					}
					int height = KConstants.jzwHeight;// 70;
					int top = i * height + ls_totalH;// top值等于楼上总高度加上地下层的高度
					int left = j * one_width;//
					int dxlcIndex = i + 1;
					String fjxh = "-" + dxlcIndex + ((j + 1) < 10 ? ("0" + (j + 1)) : ("" + (j + 1)));
					
					Jzwfj dx_jzwfj=getFjByFjxh(Short.parseShort(fjxh),dx_jzwfjList);
					JzwFjShowInPage fjInPage = new JzwFjShowInPage(1, left, top, width, height);
					if(dx_jzwfj==null){
						String fjmc = fjxh+"室";
						dx_jzwfj = JzwJgUtil.constructJzwFj(jzwjg.getJzwjgid(), dzwdy_first.getJzwdyid(), dxlc.getJzwlcid(), fjxh, fjmc, fjInPage);
						targetJzwfjListIfNewConstruct.add(dx_jzwfj);
					}else{
						String fjId=dx_jzwfj.getJzwfjid();
						dx_jzwfj=JzwJgUtil.constructJzwFj(jzwjgid, dzwdy_first.getJzwdyid(), dx_jzwfj.getJzwlcid(), dx_jzwfj.getFjxh(), dx_jzwfj.getFjmc(), fjInPage);
						dx_jzwfj.setJzwfjid(fjId);
					}
					targetJzwfjList.add(dx_jzwfj);
				}
			}
			
		}
		
		jzwfjMapper.batchUpdateJzwfj(targetJzwfjList);
		if(targetJzwfjListIfNewConstruct!=null&&!targetJzwfjListIfNewConstruct.isEmpty()){
			jzwfjMapper.batchInsertJzwfj(targetJzwfjListIfNewConstruct);//缺失房间
		}
		
		Jzwjg Jzwjgxx=new Jzwjg(jzwjgid,  KConstants.ISBUILD_JZWJG);
		Jzwjgxx.setDxType(dxLcType);
		jzwjgMapper.updateByPrimaryKeySelective(Jzwjgxx);
		
	}
	//------------------

	
}
