package com.wutong.lddw.controller;

import com.wutong.lddw.consts.ModuleConsts;
import com.wutong.lddw.context.LogHandler;
import com.wutong.lddw.service.impl.CycService;
import com.wutong.lddw.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/cyc")
@Slf4j
public class CycController {

    @Autowired
    private CycService cycService;

    @RequestMapping()
    @ResponseBody
    public Object dw(){
        try {
            cycService.dw();
            return ResponseUtils.success(LogHandler.getLog(ModuleConsts.CYC));
        } catch (Exception e) {
            log.error("【猜一猜】代玩出错！", e);
            return ResponseUtils.fail(e.getMessage());
        }
    }

}
