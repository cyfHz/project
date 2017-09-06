package com.dragonsoft.commons.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dragonsoft.adapter.service.QueryAdapterSend;
import com.dragonsoft.pci.exception.InvokeServiceException;
import com.google.common.collect.Maps;
import com.itextpdf.text.log.SysoCounter;
import com.kingmon.common.authUtil.SecurityUtils;

public class RkQueryUtil {
	
	private static final String strItem="QueryQGRK";//QueryZtry
	
	public static void main(String[] args) {
		String csrq = "111aa";
		csrq=csrq.toUpperCase();
		System.out.println(csrq);
		
		/*Map<String,String> map=Maps.newHashMap();
		String condition = "sfzh='371083198107072039'";
		String name = "姜勇";
		String userCardId = "371083198107072039";
		String orgId = "370402750000";
		try {
			QueryAdapterSend adapter = new QueryAdapterSend();
			String ss = adapter.sendQuery(strItem, condition, userCardId, name, orgId);
			System.out.println(ss);
		} catch (InvokeServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  */
		
	}
	public static Map<String,String> queryCzrk(String sfzh){
		Map<String,String>  resMap = Maps.newHashMap();
		if(sfzh==null||sfzh.trim().isEmpty()){
			return resMap;
		}
		sfzh=sfzh.toUpperCase();
		String condition="sfzh='"+sfzh+"'";
		String strReturns=null;
		try {
			QueryAdapterSend adapter = new QueryAdapterSend();
			String userCardId = SecurityUtils.getUserSfzh();
			String userName=SecurityUtils.getUserName();
			String userDept=SecurityUtils.getUserOrgId();
			if((condition != null && !condition.isEmpty()) && (userCardId != null && !userCardId.isEmpty()) && (userName != null && !userName.isEmpty()) && (userDept != null && !userDept.isEmpty())){
				strReturns = adapter.sendQuery(strItem, condition, userCardId, userName, userDept);
			}else{
				return resMap;
			}
			if(strReturns == null ||strReturns.trim().isEmpty()){
				return resMap;
			}else{
				resMap = parsseXmlStr(strReturns);
			}
		} catch (InvokeServiceException e) {
			e.printStackTrace();
			return resMap;
		}
		
		return resMap;
	}

	
	private static Map<String,String> parsseXmlStr(String xml){
		Map<String,String> map=new HashMap<String,String>();
		Document doc=null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
			return map;
		}
		
		
		List<?> list = doc.selectNodes("RBSPMessage/Method/Items/Item/Value/Row");
		if (list != null && list.size() > 2) {
			Element stateElement = (Element)list.get(0);
			String state = ((Element)stateElement.elements().get(0)).getText();			
			if (state.equalsIgnoreCase("000")) {
				Element fieldElement = (Element)list.get(1);
				if(fieldElement==null||!fieldElement.hasContent()){
					return map;
				}
				Element dataElement = (Element)list.get(2);
				if(dataElement==null||!dataElement.hasContent()){
					return map;
				}
				int filedSzie=fieldElement.elements().size();
				int dataSzie=dataElement.elements().size();
				if(filedSzie==0||dataSzie==0||dataSzie!=filedSzie){
					return map;
				}
				Element fields = (Element)list.get(1);
				Element datas = (Element)list.get(2);
				List<?> fieldList = fields.elements();
				List<?> dataList = datas.elements();
				for(int j = 0; j < fieldList.size(); j++){
					Element fieldE = (Element)fieldList.get(j);
					Element dataE = (Element)dataList.get(j);
					map.put(fieldE.getText(), (""+dataE.getText()).trim());
				}
			}
		
		}
		return map;
	}
}
