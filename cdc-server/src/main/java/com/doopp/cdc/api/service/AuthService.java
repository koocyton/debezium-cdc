package com.doopp.youlin.api.service;

import com.doopp.youlin.pojo.auth.AuthToken;
import com.doopp.youlin.pojo.auth.Authentication;
import com.doopp.youlin.pojo.auth.AuthUser;
import com.doopp.youlin.pojo.auth.UserToken;
import com.doopp.youlin.pojo.entity.User;

public interface AuthService {

    Authentication getAuthentication();

    UserToken authUserLogin(AuthToken authToken);

    User sessionUser(String userToken);

    AuthUser updateAuthUser(Long userId, Object postObject);
}
