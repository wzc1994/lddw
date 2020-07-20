package com.wutong.lddw.context;

public class ParamHandler {

    private static final ThreadLocal<SessionParam> threadLocal = new ThreadLocal<SessionParam>();

    public static void set(SessionParam sessionParam){
        threadLocal.set(sessionParam);
    }

    public static SessionParam get(){
        return threadLocal.get();
    }

    public static void clear(){
        threadLocal.remove();
    }

}
