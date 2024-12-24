package com.tongji.backend.entity.dto;

import org.springframework.http.HttpStatus;

//响应封装类
public class ResponseMessage<T> {

    private Integer code;
    private String message;
    private T data;

    public ResponseMessage(Integer code,String message,  T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    //接口请求成功
    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(HttpStatus.OK.value(),"success",  data);
    }

    public static <T> ResponseMessage<T> successmsg(String msg) {
        return new ResponseMessage<>(HttpStatus.OK.value(),  msg,null);
    }

    public static <T> ResponseMessage<T> success(String message, T data) {
        return new ResponseMessage<>(HttpStatus.OK.value(),message,  data);
    }

    //接口请求失败
    public static <T> ResponseMessage<T> error(String message) {
        return new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),message,  null);
    }

    public static <T> ResponseMessage<T> error(String message, T data) {
        return new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),message,  data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
