package com.doopp.cdc.api.service.impl;

import com.doopp.cdc.message.MyAssert;
import com.doopp.cdc.message.MyError;
import com.doopp.cdc.message.MyException;
import com.doopp.cdc.pojo.auth.UserToken;
import com.doopp.cdc.api.service.AuthService;
import com.doopp.cdc.pojo.entity.User;
import com.doopp.cdc.pojo.req.LoginReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.pass}")
    private String adminPass;

    @Override
    public UserToken userLogin(LoginReq loginReq) {
        if (!loginReq.getAccount().equals(adminName)) {
            throw new MyException(MyError.ACCOUNT_NO_EXIST);
        }
        if (!loginReq.hashPassword().equals(adminPass)) {
            throw new MyException(MyError.PASSWORD_FAIL);
        }
        return new UserToken();
    }

    @Override
    public User sessionUser(String token) {
        return null;
    }
}
