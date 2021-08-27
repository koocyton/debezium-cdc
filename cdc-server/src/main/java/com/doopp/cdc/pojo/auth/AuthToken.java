package com.doopp.youlin.pojo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.DigestUtils;

@Data
public class AuthToken {

    @ApiModelProperty(value="Auth 返回的Token", example="218c3510-65a3-4635-b8ca-99abdb7f3467")
    private String token;

    //@ApiModelProperty(value="自动登陆的UserToken", example="62094da7-0571-4189-889d-c59ed68bbb83")
    //private String tempToken;

    @ApiModelProperty(value="请求参数的签名", example="ec228f0a7d58672f7f65b25444563859")
    private String sign;

    @ApiModelProperty(value="一次性KEY，避免被重复使用", example="f0a7d-5867-2f7f6-5b2e-c228-5444563859")
    private String nonce;

    @ApiModelProperty(value="过期时间", example="1587862377")
    private int expire;

    public String sign(String appSecret) {
        return DigestUtils.md5DigestAsHex((this.token
                + "," + this.expire
                + "," + this.nonce
                + "," + appSecret).getBytes());
    }
}
