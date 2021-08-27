package com.doopp.youlin.api.controller;

import com.doopp.youlin.message.MyResponse;
import com.doopp.youlin.pojo.auth.AuthToken;
import com.doopp.youlin.pojo.auth.Authentication;
import com.doopp.youlin.pojo.auth.UserToken;
import com.doopp.youlin.api.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "Auth")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "获取 Auth 认证需要的 Authentication",
            notes="获取到 Authentication 用户 Auth 服登陆，注册",
            httpMethod = "GET"
    )
    @GetMapping("/authentication")
    public MyResponse<Authentication> authentication() {
        return MyResponse.ok(authService.getAuthentication());
    }

    @ApiOperation(value = "通过 AuthToken 登陆",
            notes="通过 AuthToken 信息登陆，并获得 User-Token",
            httpMethod = "POST"
    )
    @PostMapping("/login")
    public MyResponse<UserToken> login(@RequestBody AuthToken authToken) throws IOException {
        UserToken userToken = authService.authUserLogin(authToken);
        return MyResponse.ok(userToken);
    }
}
