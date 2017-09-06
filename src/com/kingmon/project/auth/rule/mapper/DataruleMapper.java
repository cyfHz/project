package com.kingmon.project.auth.rule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.auth.rule.model.Datarule;
@KMapper
public interface DataruleMapper {
	
   // int deleteByPrimaryKey(String rule_id);
    int insertSelective(Datarule record);
    Datarule selectByPrimaryKey(String rule_id);
    int updateByPrimaryKeySelective(Datarule record);
    
    List<String> selectVlausByRuleCodeAndUserId(@Param("rule_code")String rule_code,@Param("appuser_id")String appuser_id);
    
//    List<String> selectVlausByRuleCodeAndRoleId(@Param("rule_code")String rule_code,@Param("role_id")String role_id);

}