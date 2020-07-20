package com.wutong.lddw.api;

import lombok.Data;

@Data
public class BaseResult {

    private String result;

    private String msg;

    public boolean isOK(){
        return "0".equals(result);
    }

}
