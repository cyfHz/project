package com.kingmon.base.exception.auth;

/**
 * 
 * @Description: 未认证
 * @author zhaohuatai
 * @eamil z_huatai@qq.com
 * @date 2015年9月30日 下午3:06:39
 */
public class UnauthenticatedException  extends AuthorizationException {
    public UnauthenticatedException() {
        super();
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(Throwable cause) {
        super(cause);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
