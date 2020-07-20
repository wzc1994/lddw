package com.wutong.lddw.controller;

import com.alibaba.fastjson.JSONObject;
import com.wutong.lddw.api.faction.escort.pojo.UserBcInfo;
import com.wutong.lddw.context.ParamHandler;
import com.wutong.lddw.context.SessionParam;
import com.wutong.lddw.service.impl.BcService;
import com.wutong.lddw.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/bc")
@Slf4j
public class BcController {

    private static final Logger logger = LoggerFactory.getLogger(BcController.class);

    @Autowired
    BcService bcService;

    @Value("${bc.admins}")
    String bcAdmins;

    @PostMapping("/start")
    @ResponseBody
    public Object start(){
        if (StringUtils.isBlank(bcAdmins) || !ArrayUtils.contains(bcAdmins.split(";"), ParamHandler.get().getUid())) {
            return ResponseUtils.fail("你没有开启镖车监控的权限");
        }
        if (!bcService.isOpenTime()) {
            return ResponseUtils.fail("镖局每日13点-14点，21点-22点开放护送");
        }
        try {
            bcService.start();
        } catch (Exception e) {
            logger.error("系统内部错误", e);
            return ResponseUtils.fail("系统内部错误");
        }
        return ResponseUtils.success(bcService.data());
    }

    @GetMapping("/data")
    @ResponseBody
    public Object data(){
        if (StringUtils.isBlank(bcAdmins) || !ArrayUtils.contains(bcAdmins.split(";"), ParamHandler.get().getUid())) {
            return ResponseUtils.fail("你没有查看镖车监控数据的权限");
        }
        try {
            JSONObject result = new JSONObject();
            result.put("userBcInfos", bcService.data());
            result.put("timerRun", bcService.timerRun());
            return ResponseUtils.success(result);
        } catch (Exception e) {
            logger.error("系统内部错误", e);
            return ResponseUtils.fail("系统内部错误");
        }
    }

}
