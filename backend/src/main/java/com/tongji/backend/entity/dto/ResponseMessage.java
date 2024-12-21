package com.tongji.backend.entity.dto;

import org.springframework.http.HttpStatus;

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
