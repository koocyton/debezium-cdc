package com.doopp.youlin.api.service.impl;

import com.doopp.youlin.component.HttpClient;
import com.doopp.youlin.dao.UserDao;
import com.doopp.youlin.mapper.UserMapper;
import com.doopp.youlin.message.MyError;
import com.doopp.youlin.message.MyException;
import com.doopp.youlin.pojo.auth.AuthToken;
import com.doopp.youlin.pojo.auth.AuthUser;
import com.doopp.youlin.pojo.auth.Authentication;
import com.doopp.youlin.pojo.auth.UserToken;
import com.doopp.youlin.pojo.entity.User;
import com.doopp.youlin.server.util.ShardedJedisHelper;
import com.doopp.youlin.api.service.AuthService;
import com.doopp.youlin.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Value("${auth.app_id}")
    private String appId;

    @Value("${auth.app_secret}")
    private String appSecret;

    @Value("${auth.next_url}")
    private String nextUrl;

    @Value("${auth.api_server}")
    private String authApiServer;

    @Resource
    private HttpClient httpClient;

    @Resource
    private UserDao userDao;

    @Resource
    private IdWorker idWorker;

    @Resource(name = "sessionCache")
    private ShardedJedisHelper sessionCache;

    @Override
    public Authentication getAuthentication() {
        Authentication authentication = new Authentication(appId, appSecret);
        authentication.setNextUrl(nextUrl);
        return authentication;
    }

    public User authUserRegister(String authToken) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Auth-Token", authToken);
        headerMap.put("Authentication", "Base " + getAuthentication().getAuthentication());
        AuthUser authUser = httpClient.restGet( authApiServer + "/api/user/me",
                headerMap,
                AuthUser.class
        );
        // 返回的用户信息，数据不对
        if (authUser==null || authUser.getOpenId()==null) {
            throw new MyException(MyError.SERVER_ERROR);
        }
        // 注册 auth user 到 用户表
        synchronized (authUser.getOpenId()) {
            User user = userDao.selectByOpenId(authUser.getOpenId());
            if (user == null) {
                user = UserMapper.INSTANCE.user(authUser);
                user.setId(idWorker.nextId());
                userDao.create(user);
            }
            return user;
        }
    }

    @Override
    public UserToken authUserLogin(AuthToken authToken) {
        // 检查签名
        if (!authToken.sign(appSecret).equals(authToken.getSign())) {
            throw new MyException(MyError.UNSAFE_REQUEST);
        }
        // 判断是否超时
        int currentTimestamp = (int)(System.currentTimeMillis()/1000);
        if (authToken.getExpire()<currentTimestamp) {
            throw new MyException(MyError.EXPIRE_TIME);
        }
        // 不能重复使用签名
        // if (dataCacheRedis.get(authToken.getNonce())!=null) {
        //    throw new StandardException(StatusCode.TOKEN_CANNOT_REUSED);
        // }
        // 标记 nonce 已被使用过，并缓存一天的时间
        // dataCacheRedis.setex(authToken.getNonce(), 86400, authToken.getNonce());
        // 去请求 Auth 上的用户信息
        User user = authUserRegister(authToken.getToken());
        // create token
        UserToken userToken = new UserToken();
        sessionCache.set(userToken.getUserToken().getBytes(), user);
        return userToken;
    }

    @Override
    public User sessionUser(String userToken) {
        return sessionCache.get(userToken.getBytes(), User.class);
    }

    // update auth user
    @Override
    public AuthUser updateAuthUser(Long userId, Object postObject) {
        return null;
    }
}
