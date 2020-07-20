package com.wutong.lddw.service.impl;

import com.wutong.lddw.api.activity.cyc.CycApi;
import com.wutong.lddw.api.activity.cyc.pojo.Choice;
import com.wutong.lddw.api.activity.cyc.pojo.CycResult;
import com.wutong.lddw.consts.ModuleConsts;
import com.wutong.lddw.context.LogHandler;
import com.wutong.lddw.service.AbstractDwService;
import com.wutong.lddw.service.IDwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CycService extends AbstractDwService implements IDwService {

    public CycService(){
        this.module.setId(ModuleConsts.CYC);
        this.module.setName("猜一猜");
    }

    @Autowired
    private CycApi cycApi;

    @Override
    public void dw(){
        CycResult result = cycApi.look();
        this.answer(result);
    }

    private void answer(CycResult result){
        if (CollectionUtils.isEmpty(result.getChoices())) {
            this.getLogger().log(this.createLog(result));
            return;
        }
        int answerId = this.getAnswerId(result);
        CycResult answerResult = cycApi.choose(answerId);
        this.getLogger().log(this.createLog(answerResult));
        this.answer(answerResult);
    }

    private int getAnswerId(CycResult cycResult){
        String answer = cycResult.getAnswer();
        List<Choice> choices = cycResult.getChoices();
        Choice correct = choices.stream().filter(choice -> answer.equals(choice.getId())).findFirst().get();
        return choices.indexOf(correct);
    }

    private String createLog(CycResult answerResult){
        if (CollectionUtils.isEmpty(answerResult.getChoices())) {
            String log = "今日已经猜完所有图片！总分：%s。";
            return String.format(log, answerResult.getToday_score());
        } else {
            String log = "第%s题%s。总分：%s。";
            return String.format(log, Integer.parseInt(answerResult.getCur_pics()) - 1, answerResult.getMsg(), answerResult.getToday_score());
        }
    }

}
