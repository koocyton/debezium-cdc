package com.doopp.cdc.api.controller;

import com.doopp.cdc.message.MyResponse;
import com.doopp.cdc.pojo.auth.UserToken;
import com.doopp.cdc.api.service.AuthService;
import com.doopp.cdc.pojo.req.LoginReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public MyResponse<UserToken> login(@RequestBody LoginReq loginReq) throws IOException {
        UserToken userToken = authService.userLogin(loginReq);
        return MyResponse.ok(userToken);
    }

    @PostMapping("/logout")
    public MyResponse<String> logout(@RequestBody LoginReq loginReq) throws IOException {
        return MyResponse.ok(null);
    }
}
