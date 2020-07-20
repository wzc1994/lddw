package com.wutong.lddw.service;

import com.wutong.lddw.context.LogHandler;
import com.wutong.lddw.context.SessionLog;
import com.wutong.lddw.pojo.Module;

public abstract class AbstractDwService {

    protected Module module = new Module();

    protected SessionLog getLogger(){
        SessionLog sessionLog = LogHandler.getLog(module.getId());
        sessionLog.setModuleName(module.getName());
        return sessionLog;
    }

}
