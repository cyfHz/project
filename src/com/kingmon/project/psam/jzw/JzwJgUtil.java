package com.kingmon.project.psam.jzw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.psam.jzw.model.Jzwdy;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.model.Jzwlc;
import com.kingmon.project.psam.jzw.view.JzwFjShowInPage;

public class JzwJgUtil { 
	/** 
	 * 
	 * @param jzwjgid : 结构id
	 * @param dyxh ： 单元序号
	 * @param dymc ： 单元名称
	 * @param lcs ： 楼层数
	 * @param dyms ： 单元门数
	 * @return
	 */
	public static Jzwdy constructJzwDy(String jzwjgid,String dyxh,String dymc,short lcs,short dyms){
		Jzwdy jzwdy=new Jzwdy();
		jzwdy.setJzwdyid(UUIDUtil.uuid());
		jzwdy.setJzwjgid(jzwjgid);
		jzwdy.setDyxh(dyxh);//单元序号
		jzwdy.setDymc(dymc);//单元名称-
		jzwdy.setLcs(lcs);//楼层数
		jzwdy.setDyms(dyms);// 单元门数/房间数
		jzwdy.setZhgxrq(new Date());//最后更新日期-DATE
		return jzwdy;
	}
	/**
	 * 
	 * @param jzwjgid : 结构id
	 * @param jzwdyid : 所属单元id
	 * @param lcxh ：楼层序号
	 * @param lcmc ：楼层名称
	 * @return
	 */
	public static Jzwlc constructJzwLc(String jzwjgid,String jzwdyid,String lcxh,String lcmc){
		Jzwlc jzwlc=new Jzwlc();
		jzwlc.setJzwdyid(jzwdyid);
		jzwlc.setJzwjgid(jzwjgid);//建筑物结构ID
		jzwlc.setJzwlcid(UUIDUtil.uuid());//建筑物楼层ID
		jzwlc.setLcxh(lcxh);//序号
		jzwlc.setLcmc(lcmc);//名称-
		jzwlc.setZhgxrq(new Date());//最后更新日期-DATE
		return jzwlc;
	}
	
	public  static Jzwfj constructJzwFj(String jzwjgid,String jzwdyid,String jzwlcid,
			String fjxh,String fjmc,
			JzwFjShowInPage fjInPage){
		Date date=new Date();
		Jzwfj jzwfj=new Jzwfj();
		jzwfj.setJzwfjid(UUIDUtil.uuid());
		jzwfj.setJzwjgid(jzwjgid);
		jzwfj.setJzwdyid(jzwdyid);//单元
		jzwfj.setJzwlcid(jzwlcid);//建筑物楼层ID
		jzwfj.setFjxh(fjxh);
		jzwfj.setFjmc(fjmc);
		jzwfj.setZhgxrq(date);//最后更新日期-DATE
		jzwfj.setCreatedby(SecurityUtils.getUserId());//创建人
		jzwfj.setCreated(date);//创建时间-Date
		
		jzwfj.setShowInfo(fjInPage.getConstructStr());
		jzwfj.setShowMode("1");
		
//		jzwfj.setDeltag("0");
//		jzwfj.setDeltime(date);
//		jzwfj.setDeluser("");
		
	
		return jzwfj;
	}
	
	
	public static int getLeft(Jzwfj fj){
		String showInfo=fj.getShowInfo();
		String[] arrays=showInfo.split(",");
		String left=arrays[0];
		return Integer.parseInt(left);
	}
	public  static int getRight(Jzwfj fj){
		String showInfo=fj.getShowInfo();
		String[] arrays=showInfo.split(",");
		String left=arrays[0];
		String width =arrays[2];
		int llleft=Integer.parseInt(left);
		int lllwidth=Integer.parseInt(width);
		return llleft+lllwidth;
	}
	public  static int getTop(Jzwfj fj){
		String showInfo=fj.getShowInfo();
		String[] arrays=showInfo.split(",");
		String top =arrays[1];
		return Integer.parseInt(top);
	}
	public  static int getWidth(Jzwfj fj){
		String showInfo=fj.getShowInfo();
		String[] arrays=showInfo.split(",");
		String width =arrays[2];
		return Integer.parseInt(width);
	}
	public  static int getHeight(Jzwfj fj){
		String showInfo=fj.getShowInfo();
		String[] arrays=showInfo.split(",");
		String height =arrays[3];
		return Integer.parseInt(height);
	}
	/**
	 * 处理单元名称/楼层名称/房间名称
	 * @param original  原字符串
	 * @param width     单元号/楼层号/房间号  位数
	 * @param postfix   单元号/楼层号/房间号  后缀
	 * @return String   返回处理了位数和后缀的字符串
	 */
	public  static  String processWithAndPostfix(String original,int width,String postfix){
		original=original==null?"":original;
		while (original.length()<width) {
			original="0"+original;
		}
		original += postfix;
		return original;
	}
	/**
	 * 00x 三位保证排序正确
	 * @param original
	 * @param width
	 * @return
	 */
	public  static  String processDyLcXh(String original,int width){
		original=original==null?"":original;
		while (original.length()<width) {
			original="0"+original;
		}
		return original;
	}
	public static void main(String[] args){
		
		//System.out.println(processDyLcXh(null,3));
		

		List<Short> lcxhList=Lists.newArrayList();
		String[] xx=new String[]{"001","003","005"};
		for(String str:xx){
			lcxhList.add(Short.parseShort(str));
		}
		System.out.println(lcxhList);
		short i=3;
		Short taegerXh=new Short(i);
		System.out.println(lcxhList.contains(taegerXh));
		
	}
	
	/**
	 * 验证单元序号/楼层序号 是否符合规范，如果值为空或者不能转化为数字则校验不通过
	 * @param list 建筑物 楼层序号集合 lcxhList 或者 单元序号集合 dyxhList
	 * @param type   jzwlc 或者 jzwdy
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static  String validate_Lc_Dy_XH_DtaType(List list,String type){
		String value = "";
		if ("jzwlc".equals(type)) {
			if(list==null||list.size()==0){
				return "未查询到楼层数据，请在建筑物维护部分进行维护。";
			}
			String lcxh = null;
			String lcxh_error = "";
			for (int i = 0; i < list.size(); i++) {
				try {
					Jzwlc jzwlc = (Jzwlc) list.get(i);
					lcxh = jzwlc.getLcxh();
					if (lcxh == null||"".equals(lcxh.trim())) {
						return "存在楼层序号未填写的楼层，请进行修改。";
					}
					Integer.parseInt(lcxh.trim());
				} catch (Exception e) {
					lcxh_error += "["+lcxh+"]";
				}
			}
			if (!("".equals(lcxh_error))) {
				value = "存在楼层序号不符合规范，请进行修改。<br/>错误楼层序号："+lcxh_error+"<br/><br/>";
			}
		}else if("jzwdy".equals(type)){
			if(list==null||list.size()==0){
				return "未查询到单元数据，请在建筑物维护部分进行维护。";
			}
			String dyxh = null;
			String dyxh_error = "";
			for (int i = 0; i < list.size(); i++) {
				try {
					Jzwdy jzwdy = (Jzwdy) list.get(i);
					dyxh = jzwdy.getDyxh();
					if (dyxh == null||"".equals(dyxh.trim())) {
						return "存在单元序号未填写的楼层，请进行修改。";
					}
					Integer.parseInt(dyxh.trim());
				} catch (Exception e) {
					dyxh_error += "["+dyxh+"]";
				}
			}
			if (!("".equals(dyxh_error))) {
				value = "存在单元序号不符合规范，请进行修改。<br/>错误单元序号："+dyxh_error+"<br/><br/>";
			}
		}
		
		return value;
	}
	
	/*
	 dylcfjRules=[
		{"lcto":"3","mcfjs":"2","dyIndex":"1","lcfrom":"1"},
		{"lcto":"4","mcfjs":"3","dyIndex":"2","lcfrom":"1"},
		{"lcto":"5","mcfjs":"2","dyIndex":"3","lcfrom":"1"},
		{"lcto":"4","mcfjs":"3","dyIndex":"1","lcfrom":"4"},
		{"lcto":"6","mcfjs":"2","dyIndex":"2","lcfrom":"5"}], 
		
		map : <dyIndex,{[lcfrom,lcto],[lcfrom,lcto]....}>
	 */
	public static boolean validate(Map<Integer, List<int[]>> map){
		
		//1.校验数组元素交叉
		 for (Map.Entry<Integer, List<int[]>> entry : map.entrySet()) {
			 List<int[]> list  = new ArrayList<int[]>(entry.getValue().size());
			 list = entry.getValue();
			 if(!isIntersect(list)){
				 return false;
			 }
			//2.检验数字元素连续
			
			 if(!sortAndCheckContinuity(list)){
				 return false;
			 }
		 }
		 
		return true;
	}
	
	//校验数组元素交叉
	public static boolean isIntersect(List<int[]> list){
		
		for (int i = 0; i < list.size(); i++) {
			int[] array = list.get(i);
			if(!check(list,array,i)){
				return false;
			}
		}
		return true;
	}
	/**
	 * 检查数组元素是否有交叉
	 * @param list
	 * @param array
	 * @param index
	 * @return
	 */
	public static boolean check(List<int[]> list,int[] array,int index){
		
		int from = array[0];
		int to = array[1];
		for (int i = 0;(i!=index)&&i < list.size(); i++) {
			int[] array2 = list.get(i);
			int from2 = array2[0];
			int to2 = array2[1];
			if (from>=from2&&from<=to2) {
				return false;
			}
			if (to>=from2&&to<=to2) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 将list排序，并检查是否连续
	 * @param list
	 * @return
	 */
	public static boolean sortAndCheckContinuity(List<int[]> list){
		 Collections.sort(list,new Comparator<int[]>() {
				@Override
				public int compare(int[] a1, int[] a2) {
					return a1[0]-a2[0];
				}
			 });
		for (int i = 0; i < list.size()-1; i++) {
			 int[] array1 = list.get(i);
			 int[] array2 = list.get(i+1);
			 if ((array2[0]-array1[1])!=1) {
				return false;
			}
		 }
		 return true;
	}
	
	
//	public static void main(String[] args){
//		List<Short> lcxhList=Lists.newArrayList();
//		String[] xx=new String[]{"001","003","005"};
//		for(String str:xx){
//			lcxhList.add(Short.parseShort(str));
//		}
//		System.out.println(lcxhList);
//	}
}
