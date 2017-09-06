package com.kingmon.project.webservice.auth;

import javax.jws.WebService;


@WebService
public interface AuthService{

    public Object login(String loginName,String password);
}
