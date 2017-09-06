package com.kingmon.base.exception.auth;

/**
 * 
 * @Description: TODO(授权/认证异常) 
 * @author zhaohuatai
 * @eamil z_huatai@qq.com
 * @date 2015年9月30日 下午3:04:39
 */
public class AuthenticationException extends AuthException {

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
