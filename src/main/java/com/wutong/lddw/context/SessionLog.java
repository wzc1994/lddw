package com.wutong.lddw.context;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SessionLog {

    private String module;

    private String moduleName;

    private List<String> logs = new ArrayList<>();

    public void log(String log){
        logs.add(log);
    }

}
