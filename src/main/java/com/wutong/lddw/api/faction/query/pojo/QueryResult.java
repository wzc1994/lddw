package com.wutong.lddw.api.faction.query.pojo;

import com.wutong.lddw.api.BaseResult;
import lombok.Data;

import java.util.List;

@Data
public class QueryResult extends BaseResult {

    private List<Member> members;

}
