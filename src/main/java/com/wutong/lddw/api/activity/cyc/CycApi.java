package com.wutong.lddw.api.activity.cyc;

import com.alibaba.fastjson.JSONObject;
import com.wutong.lddw.api.activity.cyc.pojo.CycResult;
import com.wutong.lddw.utils.HttpClientResult;
import com.wutong.lddw.utils.DwPostUtils;
import org.springframework.stereotype.Service;

@Service
public class CycApi {


    public CycResult look(){
        String param = "cmd=activity&aid=118&sub=1&uin=null&skey=null&pf=wx2&from=0";
        HttpClientResult result = DwPostUtils.execute(param);
        String content = result.getContent();
        return JSONObject.parseObject(content).toJavaObject(CycResult.class);
    }

    public CycResult choose(int answerId){
        String param = "cmd=activity&aid=118&sub=3&answer_id=" + answerId + "&uin=null&skey=null&pf=wx2&from=0";
        HttpClientResult result = DwPostUtils.execute(param);
        String content = result.getContent();
        return JSONObject.parseObject(content).toJavaObject(CycResult.class);
    }

}
