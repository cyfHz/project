package com.kingmon.base.exception.auth;

/**
 * 
 * @Description: 未授权的客户端
 * @author zhaohuatai
 * @eamil z_huatai@qq.com
 * @date 2015年9月30日 下午3:07:23
 */
public class UnauthorizedHostException extends UnauthorizedException{


    private String host;

    public UnauthorizedHostException() {
        super();
    }

    public UnauthorizedHostException(String message) {
        super(message);
    }

    public UnauthorizedHostException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedHostException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getHost() {
        return this.host;
    }

    public void setHostAddress(String host) {
        this.host = host;
    }

}
