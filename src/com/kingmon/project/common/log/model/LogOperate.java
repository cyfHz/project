package com.kingmon.project.common.log.model;

import java.io.Serializable;
import java.util.Date;

import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
/**
 * 操作日志
 * app_log_operate
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:53:55
 */
public class LogOperate implements Serializable{
	
	
	public LogOperate() {
		super();
	}
	public LogOperate(SessionUser sessionUser,AuthWidgetRule authWidgetRule) {
		this.setId(UUIDUtil.uuid()); //操作日志id
		this.setOperate_def(authWidgetRule.desc());//操作的功能模块描述
		this.setOperate_time(new Date()); //操作时间
		this.setOrgna_id(sessionUser.getOrganizationId());//组织机构id
		this.setTable_name(authWidgetRule.refTable());//相关表
		this.setUser_id(sessionUser.getUserId());////用户id
		this.setUser_loginname(sessionUser.getLoginname());//登陆名称
		this.setUser_name(sessionUser.getName());//用户名称
		this.setOperate_type(authWidgetRule.crudType());//操作类型
	}
	public LogOperate(SessionUser sessionUser,AuthWidgetRule authWidgetRule,String log_text) {
		this.setId(UUIDUtil.uuid()); //操作日志id
		this.setOperate_def(authWidgetRule.desc());//操作的功能模块描述
		this.setOperate_time(new Date()); //操作时间
		this.setOrgna_id(sessionUser.getOrganizationId());//组织机构id
		this.setTable_name(authWidgetRule.refTable());//相关表
		this.setUser_id(sessionUser.getUserId());////用户id
		this.setUser_loginname(sessionUser.getLoginname());//登陆名称
		this.setUser_name(sessionUser.getName());//用户名称
		this.setOperate_type(authWidgetRule.crudType());//操作类型
		this.log_text = log_text;
	}
	public LogOperate(String id, String user_id, String user_loginname, String user_name, String orgna_id,
			Date operate_time, String operate_type, String table_name, String operate_def, String log_text) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.user_loginname = user_loginname;
		this.user_name = user_name;
		this.orgna_id = orgna_id;
		this.operate_time = operate_time;
		this.operate_type = operate_type;
		this.table_name = table_name;
		this.operate_def = operate_def;
		this.log_text = log_text;
	}

	/**
	 * 主键 -VARCHAR2(36)
	 */
    private String id;

    /**
     * 用户id -VARCHAR2(36)
     */
    private String user_id;

    /**
     * 登录名 -VARCHAR2(50)
     */
    private String user_loginname;

    /**
     * 用户名 -VARCHAR2(50)
     */
    private String user_name;

    /**
     * 组织机构id -VARCHAR2(36)
     */
    private String orgna_id;

    /**
     * 操作时间 -DATE
     */
    private Date operate_time;

    /**
     * #?
     * 操作类型 -VARCHAR2(10)
     */
    private String operate_type;

    /**
     * 涉及的数据表-VARCHAR2(50)
     */
    private String table_name;

    /**
     * 操作的功能模块描述 -VARCHAR2(200)
     */
    private String operate_def;

    /**
     * 日志内容 -CLOB
     */
    private String log_text;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_loginname
	 */
	public String getUser_loginname() {
		return user_loginname;
	}

	/**
	 * @param user_loginname the user_loginname to set
	 */
	public void setUser_loginname(String user_loginname) {
		this.user_loginname = user_loginname;
	}

	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return the orgna_id
	 */
	public String getOrgna_id() {
		return orgna_id;
	}

	/**
	 * @param orgna_id the orgna_id to set
	 */
	public void setOrgna_id(String orgna_id) {
		this.orgna_id = orgna_id;
	}

	/**
	 * @return the operate_time
	 */
	public Date getOperate_time() {
		return operate_time;
	}

	/**
	 * @param operate_time the operate_time to set
	 */
	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}

	/**
	 * @return the operate_type
	 */
	public String getOperate_type() {
		return operate_type;
	}

	/**
	 * @param operate_type the operate_type to set
	 */
	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}

	/**
	 * @return the table_name
	 */
	public String getTable_name() {
		return table_name;
	}

	/**
	 * @param table_name the table_name to set
	 */
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	/**
	 * @return the operate_def
	 */
	public String getOperate_def() {
		return operate_def;
	}

	/**
	 * @param operate_def the operate_def to set
	 */
	public void setOperate_def(String operate_def) {
		this.operate_def = operate_def;
	}

	/**
	 * @return the log_text
	 */
	public String getLog_text() {
		return log_text;
	}

	/**
	 * @param log_text the log_text to set
	 */
	public void setLog_text(String log_text) {
		this.log_text = log_text;
	}

}