package com.kingmon.base.exception.auth;

/**
 * 
 * @Description: TODO(session过期) 
 * @author zhaohuatai
 * @eamil z_huatai@qq.com
 * @date 2015年9月30日 下午3:06:17
 */
public class SessionExpiredException extends InvalidSessionException{


    public SessionExpiredException() {
        super();
    }

    public SessionExpiredException(String message) {
        super(message);
    }

    public SessionExpiredException(Throwable cause) {
        super(cause);
    }
    public SessionExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

}
