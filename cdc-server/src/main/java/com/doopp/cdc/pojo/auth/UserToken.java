package com.doopp.youlin.pojo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserToken {

    @ApiModelProperty(value="用户 Token (head:User-Token)", example="7d92c7ac-3ecb-4bd6-8d49-bfe918f7aac2")
    private String userToken;

    @ApiModelProperty(value="过期时间", example="1587732143")
    private int expire;

    public UserToken() {
        this.userToken = UUID.randomUUID().toString();
        this.expire = (int)(System.currentTimeMillis()*2);
    }
}
