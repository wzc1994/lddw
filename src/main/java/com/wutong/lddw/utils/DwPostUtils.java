package com.wutong.lddw.utils;

import com.wutong.lddw.context.ParamHandler;
import com.wutong.lddw.context.SessionParam;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DwPostUtils {

    private final static String BASE_URL = "https://zone4.ledou.qq.com/fcgi-bin/petpk";

    public static HttpClientResult execute(String paramsStr){
        String url = getUrl();
        Map<String, String> headers = getHeaders();
        Map<String, String> params = getParams(paramsStr);
        try {
            return HttpClientUtils.doPost(url, headers, params);
        } catch (Exception e) {
            log.error("执行POST请求出错", e);
            return new HttpClientResult(-1);
        }
    }

    private static String getUrl(){
        return BASE_URL + "?uid=" + ParamHandler.get().getUid() + "&cmd=sns";
    }

    private static Map<String, String> getHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("charset", "utf-8");
        headers.put("Accept-Encoding", "gzip");
        headers.put("referer", "https://servicewechat.com/wxc7bdffeaa050ca4c/158/page-frame.html");
        headers.put("content-type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 7.1.1; Mi Note 3 Build/NMF26X; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/64.0.3282.137 Mobile Safari/537.36 MicroMessenger/7.0.3.1400(0x27000334) Process/appbrand2 NetType/WIFI Language/zh_CN");
        headers.put("Host", "zone4.ledou.qq.com");
        headers.put("Connection", "Keep-Alive");
        return headers;
    }

    private static Map<String, String> getParams(String paramsStr){
        Map<String, String> params = new HashMap<>();
        String[] paramStrArr = paramsStr.split("&");
        Arrays.stream(paramStrArr).forEach(paramStr -> {
            String[] temp = paramStr.split("=");
            params.put(temp[0], temp[1]);
        });
        SessionParam sessionParam = ParamHandler.get();
        params.put("uid", sessionParam.getUid());
        params.put("h5openid", sessionParam.getH5openid());
        params.put("h5token", sessionParam.getH5token());
        return params;
    }

}
