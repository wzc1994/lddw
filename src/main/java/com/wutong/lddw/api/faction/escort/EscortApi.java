package com.wutong.lddw.api.faction.escort;

import com.alibaba.fastjson.JSONObject;
import com.wutong.lddw.api.faction.escort.pojo.EscortResult;
import com.wutong.lddw.utils.DwPostUtils;
import com.wutong.lddw.utils.HttpClientResult;
import org.springframework.stereotype.Service;

@Service
public class EscortApi {

    public EscortResult search(){
        String param = "cmd=faction&op=escort&uin=null&skey=null&pf=wx2&from=0";
        HttpClientResult result = DwPostUtils.execute(param);
        String content = result.getContent();
        return JSONObject.parseObject(content).toJavaObject(EscortResult.class);
    }

}
