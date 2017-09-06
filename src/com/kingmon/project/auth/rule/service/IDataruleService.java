package com.kingmon.project.auth.rule.service;

import java.util.List;

public interface IDataruleService {


    /**
     *  
     * @param rule_code
     * @param appuser_id
     * @return
     */
    List<String>  loadValuesByRuleCodeAndUserId(String rule_code,String appuser_id);
    
//  List<String> loadValuesByRuleCodeAndRoleId(String rule_code,String role_id);
}
