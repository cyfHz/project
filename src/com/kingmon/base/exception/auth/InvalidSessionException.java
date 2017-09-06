package com.kingmon.base.exception.auth;

/**
 * 
 * @Description: TODO(session 不合法) 
 * @author zhaohuatai
 * @eamil z_huatai@qq.com
 * @date 2015年9月30日 下午3:05:54
 * @since  v2.0
 */
public class InvalidSessionException extends SessionException{

    public InvalidSessionException() {
        super();
    }

    public InvalidSessionException(String message) {
        super(message);
    }

    public InvalidSessionException(Throwable cause) {
        super(cause);
    }

    public InvalidSessionException(String message, Throwable cause) {
        super(message, cause);
    }


}
