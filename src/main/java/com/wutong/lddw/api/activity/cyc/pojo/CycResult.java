package com.wutong.lddw.api.activity.cyc.pojo;

import com.wutong.lddw.api.BaseResult;
import lombok.Data;

import java.util.List;

@Data
public class CycResult extends BaseResult {

    private String cur_pics;

    private String answer;

    private String answer_level;

    private String today_score;

    private List<Choice> choices;

}
