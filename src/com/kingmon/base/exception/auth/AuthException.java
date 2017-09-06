package com.kingmon.base.exception.auth;

/**
 * 
 * @Description: TODO(授权/认证异常---父类) 
 * @author zhaohuatai
 * @eamil z_huatai@qq.com
 * @date 2015年9月30日 下午3:05:18
 */
public class AuthException extends RuntimeException {
	
    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }
    
    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
