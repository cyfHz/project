package com.kingmon.base.exception.auth;

/**
 *  
 * @Description: TODO(authorization 授权异常) 
 * @author zhaohuatai
 * @eamil z_huatai@qq.com
 * @date 2015年9月30日 上午9:59:14
 */
public class AuthorizationException extends AuthException {

	public AuthorizationException() {
		super();
	}

	public AuthorizationException(String message) {
		super(message);
	}

	public AuthorizationException(Throwable cause) {
		super(cause);
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}
}