package com.kingmon.base.exception.auth;

/**
 * 
 * @Description: 未授权
 * @author zhaohuatai
 * @eamil z_huatai@qq.com
 * @date 2015年9月30日 下午3:07:12
 */
public class UnauthorizedException extends AuthorizationException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
