package com.wutong.lddw.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class HttpClientResult implements Serializable {

    public HttpClientResult(){

    }

    public HttpClientResult(int code){
        this.code = code;
    }

    public HttpClientResult(int code, String content){
        this.code = code;
        this.content = content;
    }

    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应数据
     */
    private String content;

}
