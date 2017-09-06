package com.kingmon.base.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.util.StringUtils;

import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.authUtil.WidgetRuleUtil;

public class AuthLinkButton extends SimpleTagSupport{
	private String id;
	private String iconCls;
	private String onclick;
	private String plain;
	private String text;
	
	private String targetUrl;
	
	private String roleCode;
	
	private String widgetRuleCode;
	
	private Boolean hiddenIfNoPerm;
	
	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
			String treeStr=this.createLinkButton();
			if(treeStr!=null&&!"".equals(treeStr)){
				 getJspContext().getOut().write(treeStr.toString());
			}
		}
		private String createLinkButton() {
			//Subject subject=SecurityUtils.getSubject();
			//subject.hasRole(""+roleCode);
			boolean hasPermission=true;
			
			StringBuffer strBuffer=new StringBuffer("");
			strBuffer.append("<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\"  ");
			if(id!=null&&!"".equals(id)){
				strBuffer.append(" id=\""+id+"\" ");
			}
			if(plain!=null&&"true".equals(plain)){
				strBuffer.append(" plain=\"true\" ");
			}
			if(iconCls!=null&&!"".equals(iconCls)){
				strBuffer.append(" iconCls=\""+iconCls+"\" ");
			}
			if(onclick!=null&&!"".equals(onclick)){
				strBuffer.append(" onclick=\""+onclick+"\" ");
			}
			/**/
			if(StringUtils.hasText(widgetRuleCode)){
				if(WidgetRuleUtil.isUserHaveWidgetRuleCode(widgetRuleCode,  SecurityUtils.getUserId())){
					hasPermission=true;
				}else{
					hasPermission=false;
				}
			}
//			hasPermission=true;
			
			//拥有角色即可---此处逻辑有错，去掉该处
//			if(!ZStrUtil.isEmpty(roleCode)){
//				if(!subject.hasRole(""+roleCode)){
//					hasPermission=false;
//				}
//			}
			
			if(!hasPermission){
				if(hiddenIfNoPerm!=null&&hiddenIfNoPerm){
					return "";
				}
				strBuffer.append("  data-options=\"disabled:true\" ");
				strBuffer.append(">");
				strBuffer.append("<font color=\"red\">["+text+"]</font>");
			}else{
				strBuffer.append(">");
				strBuffer.append(text);
			}
			strBuffer.append("</a>");
			return strBuffer.toString();
		}

		public String getIconCls() {
			return iconCls;
		}

		public void setIconCls(String iconCls) {
			this.iconCls = iconCls;
		}

		public String getOnclick() {
			return onclick;
		}

		public void setOnclick(String onclick) {
			this.onclick = onclick;
		}

		public String getPlain() {
			return plain;
		}

		public void setPlain(String plain) {
			this.plain = plain;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getRoleCode() {
			return roleCode;
		}
		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}
	
		public Boolean getHiddenIfNoPerm() {
			return hiddenIfNoPerm;
		}
		public void setHiddenIfNoPerm(Boolean hiddenIfNoPerm) {
			this.hiddenIfNoPerm = hiddenIfNoPerm;
		}
		public String getTargetUrl() {
			return targetUrl;
		}
		public void setTargetUrl(String targetUrl) {
			this.targetUrl = targetUrl;
		}
		public String getWidgetRuleCode() {
			return widgetRuleCode;
		}
		public void setWidgetRuleCode(String widgetRuleCode) {
			this.widgetRuleCode = widgetRuleCode;
		}
		
		
}
