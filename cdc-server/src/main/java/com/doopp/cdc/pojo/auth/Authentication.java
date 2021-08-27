package com.doopp.youlin.pojo.auth;

//import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.DigestUtils;

import java.util.Base64;
import java.util.UUID;

@Data
public class Authentication {

    @ApiModelProperty(value="Authentication ", example="OTQwNjA4NTc3OTg5NjQ0MjE3LDE1ODc3MzI0MDksYWM4YTZ..")
    private String authentication;

    @ApiModelProperty(value="APP 开发忽略", example="https://....")
    private String nextUrl;

    @JsonIgnore
    private String appId;

    @JsonIgnore
    private String nonce;

    @JsonIgnore
    private String sign;

    @JsonIgnore
    private int timestamp;

    private Authentication() {
    }

    public Authentication(String appId, String appSecret) {

        this.appId = appId;

        this.timestamp = (int)(System.currentTimeMillis()/1000);

        this.nonce = UUID.randomUUID().toString();

        this.sign = DigestUtils.md5DigestAsHex((this.appId
                + "," + appSecret
                + "," + this.nonce
                + "," + this.timestamp).getBytes());

        this.authentication = Base64.getEncoder().encodeToString((this.appId
                + "," + this.timestamp
                + "," + this.nonce
                + "," + this.sign).getBytes());
    }
}
