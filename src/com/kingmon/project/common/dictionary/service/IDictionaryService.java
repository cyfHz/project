package com.kingmon.project.common.dictionary.service;


import java.util.List;
import java.util.Map;

import com.kingmon.project.common.dictionary.model.Dictionary;

public interface IDictionaryService {
	public String getDictByCode(String code);
	
	public Dictionary loadDictByTypeAndCode(String typeCode , String dicCode);

	public List<Map<String, Object>>  loadByTypeCode(String typeCode);
	
	public String  loadByTypeCodeBy(String code,String subCode);
	
	
	public Dictionary loadDictByTypeAndCodeForTag(String typeCode , String dicCode);
	
}
