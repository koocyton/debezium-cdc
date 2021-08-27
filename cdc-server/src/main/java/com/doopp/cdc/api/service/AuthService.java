package com.doopp.cdc.api.service;

import com.doopp.cdc.pojo.auth.UserToken;
import com.doopp.cdc.pojo.entity.User;
import com.doopp.cdc.pojo.req.LoginReq;

public interface AuthService {

    UserToken userLogin(LoginReq loginReq);

    User sessionUser(String token);
}
