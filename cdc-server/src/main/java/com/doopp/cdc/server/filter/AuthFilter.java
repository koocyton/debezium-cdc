package com.doopp.youlin.server.filter;

import com.doopp.youlin.pojo.entity.User;
import com.doopp.youlin.api.service.AuthService;
import com.doopp.youlin.message.MyAssert;
import com.doopp.youlin.message.MyError;
import com.doopp.youlin.message.MyException;
import com.doopp.youlin.server.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final static List<Integer> ignoreLogErrorCodeList = new ArrayList<Integer>(){{
        add(513);
        add(501);
        add(519);
        add(520);
    }};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {

        // 不过滤的uri
        String[] notFilterUris = new String[]{
                "/api/login",
                "/api/logout",
                "/api/auth/authentication",
                "/api/auth/login"
        };

        // 请求的uri
        String uri = request.getRequestURI();

        // 是否过滤
        boolean doFilter = true;

        // 如果uri中包含不过滤的uri，则不进行过滤
        for (String notFilterUri : notFilterUris) {
            if (uri.indexOf(notFilterUri) == 0) {
                doFilter = false;
                break;
            }
        }

        try {
            request.getSession().setAttribute("SessionAdmin", null);
            if (doFilter) {
                userFilter(request);
            }
            filterChain.doFilter(request, response);
        }
        catch (MyException e) {
            // 忽略一些日志不用打
            if (!ignoreLogErrorCodeList.contains(e.getCode())) {
                log.info(e.getMessage(), e);
            }
            writeExceptionResponse(e.getCode(), response, e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage(), e);
            writeExceptionResponse(MyError.FAIL.code(), response, e.getMessage());
        }
    }

    private void userFilter(HttpServletRequest request) {
        String userToken = request.getHeader("User-Token");
        MyAssert.notEmpty(userToken, MyError.TOKEN_CHECK_FAILED);
        AuthService authService = ApplicationContextUtil.getBean("authService", AuthService.class);
        User user = authService.sessionUser(userToken);
        MyAssert.notEmpty(user, MyError.USER_NOT_FOUND);
        request.getSession().setAttribute("SessionAdmin", user);
    }

    private static void writeExceptionResponse(int errorCode, HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"code\":" + errorCode + ", \"msg\":\"" + errorMessage + "\", \"data\":null}");
    }
}
