package com.doopp.cdc.pojo.auth;

import lombok.Data;

import java.util.UUID;

@Data
public class UserToken {

    private String userToken;

    private int expire;

    public UserToken() {
        this.userToken = UUID.randomUUID().toString();
        this.expire = (int)(System.currentTimeMillis()*2);
    }
}
