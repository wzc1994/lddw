package com.wutong.lddw.api.faction.escort.pojo;

import lombok.Data;

import java.net.URLDecoder;

@Data
public class Tran {

    private String index;

    private String uid;

    private Long begin_time;

    private String type;

    private String quality;

    private String name;

    private Long fight_power;

    private String level;

    public void setName(String name) {
        try {
            this.name = URLDecoder.decode(name, "UTF-8");
        } catch (Exception e) {
            this.name = null;
        }
    }

}
