package com.wutong.lddw.api.faction.query.pojo;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Data
public class Member {

    private String uid;

    private String name;

    private String level;

    private String sex;

    private Long fight_power;

    private String vip_level;

    public void setName(String name){
        try {
            this.name = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            this.name = null;
        }
    }

}
