package com.doopp.cdc.pojo.req;

import lombok.Data;
import org.springframework.util.DigestUtils;

@Data
public class LoginReq {

    private String account;

    private String password;

    public String hashPassword() {
        return DigestUtils.md5DigestAsHex(
                (this.account + " _ " + this.password).getBytes()
        );
    }
}
