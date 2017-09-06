package com.kingmon.project.common.dictionary.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.kingmon.base.service.BaseService;
import com.kingmon.project.common.dictionary.mapper.DictionaryMapper;
import com.kingmon.project.common.dictionary.model.Dictionary;
import com.kingmon.project.common.dictionary.service.IDictionaryService;
@Service
public class DictionaryServiceImpl extends BaseService implements IDictionaryService{
	@Autowired
	DictionaryMapper dictionaryMapper;
//	@Cacheable(value="defaultQueryResultCache",key="'DictionaryService_getDictByCode'+#code")
		public String getDictByCode(String code) {
			if(code==null||code.isEmpty()){
				return "[]";
			}
			List<Map<String, Object>> list = dictionaryMapper.getDictByCode(code);
			
			return JSON.toJSONString(list);
		}
	
	@Cacheable(value="defaultQueryResultCache",key="'DictionaryService_getDictByCode'+#typeCode")
	public List<Map<String, Object>> loadByTypeCode(String typeCode) {
		if(typeCode==null||typeCode.isEmpty()){
			return Collections.emptyList();
		}
		return dictionaryMapper.getDictByCode(typeCode);
	}
	@Override
	public Dictionary loadDictByTypeAndCode(String typeCode, String dicCode) {
		if(!StringUtils.hasText(typeCode)||!StringUtils.hasText(dicCode)){
			return null;
		}
		return dictionaryMapper.selectDictByTypeAndCode(typeCode, dicCode);
	}
	
	public String  loadByTypeCodeBy(String code,String subCode){
		String sub=subCode;
		if(code==null||code.isEmpty()){
			return "[]";
		}
		if(subCode==null||subCode.isEmpty()){
			return "[]";
		}else{
			subCode=""+subCode.substring(0, 1)+"%";
		}
//		"2%"
		List<Map<String, Object>> list = dictionaryMapper.loadByTypeCodeBy(code, subCode,sub);
		return JSON.toJSONString(list);
	}

	@Cacheable(value="defaultQueryResultCache" ,key="'DictionaryServiceImpl'+#typeCode+#dicCode")
	@Override
	public Dictionary loadDictByTypeAndCodeForTag(String typeCode, String dicCode) {
		if(!StringUtils.hasText(typeCode)||!StringUtils.hasText(dicCode)){
			return null;
		}
		return dictionaryMapper.selectDictByTypeAndCode(typeCode, dicCode);
	}
}
