package com.kingmon.base.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.kingmon.base.common.KConstants;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.authUtil.WidgetRuleUtil;

public class WidgetTag extends ConditionalTagSupport{

	private String widgetRulevalue;
	
	public WidgetTag() {
		init();
	}
	@Override
	protected boolean condition() throws JspTagException {
		
		if(StringUtils.hasText(KConstants.authIgnorUser)&&KConstants.authIgnorUser.equals(SecurityUtils.getLoginname())){
			return true;
		}
		
		if(WidgetRuleUtil.isUserHaveWidgetRuleCode(widgetRulevalue,  SecurityUtils.getUserId())){
			return true;
		}
		return false;
//		return true;
	}

	public void release() {
		super.release();
		init();
	}

	private void init() {
		this.widgetRulevalue = null;
	}
	public String getWidgetRulevalue() {
		return widgetRulevalue;
	}
	public void setWidgetRulevalue(String widgetRulevalue) {
		this.widgetRulevalue = widgetRulevalue;
	}
		
}
