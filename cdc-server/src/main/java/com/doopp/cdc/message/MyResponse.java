package com.doopp.youlin.message;

import lombok.Data;

@Data
public class MyResponse<T> {

    private int code = 0;

    private String msg = "";

    private T data;

    public static <D> MyResponse<D> ok(D data) {
        MyResponse<D> myResponse = new MyResponse<D>();
        myResponse.setData(data);
        return myResponse;
    }

    public static MyResponse<?> no(int errorCode, String msg) {
        MyResponse<?> myResponse = new MyResponse<>();
        myResponse.setMsg(msg);
        myResponse.setCode(errorCode);
        return myResponse;
    }

    public static MyResponse<?> no(MyError myError) {
        MyResponse<?> myResponse = new MyResponse<>();
        myResponse.setMsg(myError.message());
        myResponse.setCode(myError.code());
        return myResponse;
    }
}
