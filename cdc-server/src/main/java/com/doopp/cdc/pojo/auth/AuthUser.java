package com.doopp.youlin.pojo.auth;

import lombok.Data;

@Data
public class AuthUser {

    private Long openId;

    private String nickname;

    private String phone = "";

    private String email = "";

    private String portrait = "";

    private String country = "";

    private String city = "";

    private int age = 0;

    private String gender = "";

    private int credit = 0 ;
}
