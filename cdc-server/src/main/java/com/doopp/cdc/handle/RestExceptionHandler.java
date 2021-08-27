package com.doopp.cdc.handle;

import com.doopp.cdc.message.MyError;
import com.doopp.cdc.message.MyException;
import com.doopp.cdc.message.MyResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public MyResponse<?> missingRequestParameter(HttpServletResponse response, MissingServletRequestParameterException ex){
        return MyResponse.no(500, ex.getMessage());
    }

    @ExceptionHandler({ServletRequestBindingException.class})
    @ResponseBody
    public MyResponse<?> requestBindingExceptionRequest(HttpServletResponse response, ServletRequestBindingException ex){
        ex.printStackTrace();
        return MyResponse.no(500, ex.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public MyResponse<?> requestMethodNotSupported(HttpServletResponse response, HttpRequestMethodNotSupportedException ex){
        return MyResponse.no(500, ex.getMessage());
    }

    @ExceptionHandler({SQLSyntaxErrorException.class})
    @ResponseBody
    public MyResponse<?> sqlSyntaxError(HttpServletResponse response, SQLSyntaxErrorException ex){
        return MyResponse.no(500, MyError.FAIL.message());
    }

    @ExceptionHandler({DuplicateKeyException.class})
    @ResponseBody
    public MyResponse<?> duplicateKey(HttpServletResponse response, DuplicateKeyException ex){
        return MyResponse.no(MyError.DUPLICATE_KEY);
    }

    @ExceptionHandler({MyException.class})
    @ResponseBody
    public MyResponse<?> myException(HttpServletResponse response, MyException ex){
        return MyResponse.no(ex.getCode(), ex.getMessage());
    }
}
