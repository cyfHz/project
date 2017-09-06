package com.kingmon.project.common.log.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 错误日志表
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:45:01
 */
public class LogErrorClob implements Serializable{
	/**log_error_clob
	 * 主键-VARCHAR2(50)
	 */
    private String id;

    /**
     * 创建时间-DATE
     */
    private Date createtime;

    /**
     * 错误信息-CLOB
     */
    private String message;

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
	 * @return the createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}