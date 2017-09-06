package com.kingmon.project.auth.rule.mapper;

import java.util.List;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.auth.rule.model.Widgetrule;
@KMapper
public interface WidgetruleMapper {
	
//    int deleteByPrimaryKey(String rule_id);
    int insertSelective(Widgetrule record);
    
    Widgetrule selectByPrimaryKey(String rule_id);
    
    int updateByPrimaryKeySelective(Widgetrule record);
    
    /**
     *   w.rule_id,<br>
	    w.rule_values,<br>
	    w.rule_code,<br>
	    w.rule_desc<br>
     * @param APPUSER_ID<br>
     * @return
     */
    List<Widgetrule> selectWidgetRuleListByUserId(String appuser_id);
    /**
     *  w.rule_code,<br>
     * @param appuser_id
     * @return
     */
    List<String> selectWidgetRuleCodeListByUserId(String appuser_id);
    
    
//    List<String> selectWidgetRuleValueListByUserId(String appuser_id);
}