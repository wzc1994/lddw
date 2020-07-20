package com.wutong.lddw.api.faction.escort.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserBcInfo {

    private String uid;

    private String name;

    private Long fight_power;

    private List<Tran> trans;

    public void addTran(Tran tran){
        if (trans == null) {
            trans = new ArrayList<>();
        }
        trans.add(tran);
    }

}
