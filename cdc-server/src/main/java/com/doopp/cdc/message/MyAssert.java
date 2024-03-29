package com.doopp.cdc.message;

import com.doopp.cdc.util.VerifyUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

public abstract class MyAssert {

    public static void notNull(@Nullable Object object, MyError myError) {
        if (object == null) {
            throw new MyException(myError);
        }
    }

    public static void notEmpty(@Nullable Object object, MyError myError) {
        if (ObjectUtils.isEmpty(object)) {
            throw new MyException(myError);
        }
    }

    private final static String r = "[a-zA-Z0-9`~!@#\\$%\\^&\\*\\(\\)-_=\\+\\[\\{\\]\\}\\\\\\|;:'\",<\\.>/\\?]{8,26}";
    public static void isPassword(String password, MyError myError) {
        if (!Pattern.matches(r, password)) {
            throw new MyException(myError);
        }
    }

    public static void needTrue(boolean state, MyError myError) {
        if (!state) {
            throw new MyException(myError);
        }
    }

    public static void isEmail(String email, MyError myError) {
        if (!VerifyUtil.isEmail(email)) {
            throw new MyException(myError);
        }
    }
}
