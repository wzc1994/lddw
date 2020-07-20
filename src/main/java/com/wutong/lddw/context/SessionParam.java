package com.wutong.lddw.context;

import lombok.Data;

@Data
public class SessionParam {

    public SessionParam(String uid, String h5token, String h5openid){
        this.uid = uid;
        this.h5token = h5token;
        this.h5openid = h5openid;
    }

    private String uid;

    private String h5token;

    private String h5openid;

}
