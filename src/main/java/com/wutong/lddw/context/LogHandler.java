package com.wutong.lddw.context;

import java.util.Map;

public class LogHandler {

    private static final ThreadLocal<Map<String, SessionLog>> threadLocal = new ThreadLocal<Map<String, SessionLog>>();

    public static void set(Map<String, SessionLog> sessinLogMap){
        threadLocal.set(sessinLogMap);
    }

    public static Map<String, SessionLog> get(){
        return threadLocal.get();
    }

    public static SessionLog getLog(String module){
        Map<String, SessionLog> sessinLogMap = get();
        SessionLog sessionLog = sessinLogMap.get(module);
        if (sessionLog == null) {
            sessionLog = new SessionLog();
            sessionLog.setModule(module);
            sessinLogMap.put(module, sessionLog);
        }
        return sessionLog;
    }

    public static void clear(){
        threadLocal.remove();
    }

}
