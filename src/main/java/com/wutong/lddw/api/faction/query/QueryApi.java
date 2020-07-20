package com.wutong.lddw.api.faction.query;

import com.alibaba.fastjson.JSONObject;
import com.wutong.lddw.api.faction.query.pojo.QueryResult;
import com.wutong.lddw.utils.DwPostUtils;
import com.wutong.lddw.utils.HttpClientResult;
import org.springframework.stereotype.Service;

@Service
public class QueryApi {

    public QueryResult query(){
        String param = "cmd=faction&op=query&need_member=1&need_feeds=1&uin=null&skey=null&pf=wx2&from=0";
        HttpClientResult result = DwPostUtils.execute(param);
        String content = result.getContent();
        return JSONObject.parseObject(content).toJavaObject(QueryResult.class);
    }

}
