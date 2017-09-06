package com.kingmon.project.common.dictionary.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.kingmon.base.common.KMapper;
import com.kingmon.project.common.dictionary.model.Dictionary;

@KMapper
public interface DictionaryMapper {
	
	public List<Map<String, Object>> getDictByCode(String code);
	
	public Dictionary selectDictByTypeAndCode(@Param("typeCode") String typeCode ,@Param("dicCode") String dicCode);
	
	public List<Map<String, Object>>  loadByTypeCodeBy(@Param("code")String code,@Param("subCode")String subCode,@Param("sub")String sub);
}