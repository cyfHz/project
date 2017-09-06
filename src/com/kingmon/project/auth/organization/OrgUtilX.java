package com.kingmon.project.auth.organization;

import java.util.Arrays;
import java.util.List;
import org.springframework.util.StringUtils;
public class OrgUtilX {
//	
//	public static void main(String[] af){
//		List<String> list=Arrays.asList(new String[]{"370100000000","370100000010","370200000000","370300000000"});
//		List<String> listfj=Arrays.asList(new String[]{"370101000000","370102000000","370103000000"});
//		List<String> listpcs=Arrays.asList(new String[]{"370101001000","370101002000","370101003000"});
//		System.out.println(OrgUtilX.genOrgCode("37","sj",null));
//		System.out.println(OrgUtilX.genOrgCode("3701","fj",null));
//		System.out.println(OrgUtilX.genOrgCode("370101","pcs",null));
//		
//		
//	}
	public static String genOrgCode(String tragetCode,String type,List<String> codeList){
		if(!StringUtils.hasText(tragetCode)){
			return null;
		}
		String lastCode="";
		switch (type) {
		case "sj":
			lastCode="00000000";
			if(codeList==null||codeList.isEmpty()){
				return tragetCode+"01"+lastCode;
			}
			for(int x=0;x<10;x++){
				for(int y=1;y<10;y++){
						String str=tragetCode+x+y+lastCode;
						if(!codeList.contains(str)){
							return str;
						}
				}
			}
			break;
		case "fj":
			lastCode="000000";
			if(codeList==null||codeList.isEmpty()){
				return tragetCode+"01"+lastCode;
			}
			for(int x=0;x<10;x++){
				for(int y=1;y<10;y++){
						String str=tragetCode+x+y+lastCode;
						if(!codeList.contains(str)){
							return str;
						}
				}
			}
			break;
		case "pcs":
			lastCode="000";
			if(codeList==null||codeList.isEmpty()){
				return tragetCode+"001"+lastCode;
			}
			for(int x=0;x<10;x++){
				for(int y=0;y<10;y++){
					for(int z=1;z<10;z++){
						String str=tragetCode+x+y+z+lastCode;
						if(!codeList.contains(str)){
							return str;
						}
					}
				}
			}
			break;
		default:
			return null;
		}
		return null;
	}

	public static void main(String[] af){
		List<String> list=Arrays.asList(new String[]{"370100000000","370100000010","370200000000","370300000000"});
		String orgCodex="371010110000";
		System.out.println(checkOrg(orgCodex)+list);
	}
	public static String checkOrg(String orgCode){
		if(orgCode==null||orgCode.isEmpty()){
			return null;
		}
		int index=orgCode.length()-1;
		char x=orgCode.charAt(index);
		while(index>=0&&x=='0'){
			index--;
			x=orgCode.charAt(index);
		}
		switch (index+1) {
		case 2://370000000000
			return "st";
		case 3:
		case 4:
			return "sj";//370100000000 371000000000 
		case 5:
		case 6:
			return "fj"; //3701 01 000000 3701 10 000000
		case 7:
		case 8:
		case 9://3713 22 590000
			return "pcs";
		case 10://20160218 修改直属局情况，为10位
			return "pcs";
		case 11:
		case 12:
			return "jwq";
		default:
			return null;
		}
	}
	public static int getOrgLevel(String orgCode){
		if(orgCode==null||orgCode.isEmpty()){
			return -1;
		}
		int index=orgCode.length()-1;
		char x=orgCode.charAt(index);
		while(index>=0&&x=='0'){
			index--;
			x=orgCode.charAt(index);
		}
		return index+1;
	}
	public static String getOrgLevlName(String type){
		if(type==null||type.isEmpty()){
			return null;
		}
		switch (type) {
		case "st":
			return "省厅";
		case "sj":
			return "市局";
		case "fj":
			return "分局";
		case "pcs":
			return "派出所";
		case "jwq":
			return "警务区";
		default:
			return null;
		}
	}
	
}
