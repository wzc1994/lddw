package com.wutong.lddw.service.impl;

import com.wutong.lddw.api.faction.escort.EscortApi;
import com.wutong.lddw.api.faction.escort.pojo.EscortResult;
import com.wutong.lddw.api.faction.escort.pojo.Tran;
import com.wutong.lddw.api.faction.escort.pojo.UserBcInfo;
import com.wutong.lddw.api.faction.query.QueryApi;
import com.wutong.lddw.api.faction.query.pojo.QueryResult;
import com.wutong.lddw.context.ParamHandler;
import com.wutong.lddw.context.SessionParam;
import com.wutong.lddw.pojo.TimerRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BcService {

    private static final Logger logger = LoggerFactory.getLogger(BcService.class);

    // 每个成员押镖信息
    private Map<String, UserBcInfo> userBcInfoMap = new HashMap<>();
    // 镖车总况
    private Map<String, Tran> tranMap = new HashMap<>();

    private TimerRun timerRun = new TimerRun();

    // 上次开始时间（用于判断是不是隔天操作，如果是隔天操作，清空上一天的押镖情况）
    private LocalDateTime lastStartTime;


    @Autowired
    QueryApi queryApi;

    @Autowired
    EscortApi escortApi;

    public void start(){
        SessionParam sessionParam = ParamHandler.get();
        if (timerRun.isRun()) {
            logger.info("监控正在执行中，无需重复开启！操作者：{}。", sessionParam.getUid());
            return;
        }
        logger.info("开始监控！操作者：{}", sessionParam.getUid());
        if (lastStartTime == null || !this.isSameDay(LocalDateTime.now(), lastStartTime)) {
            logger.info("新的一天开始了，让我们忘掉昨天的不愉快吧~");
            this.clear();
        }
        this.lastStartTime = LocalDateTime.now();
        if (CollectionUtils.isEmpty(userBcInfoMap)) {
            this.initUserBcInfoData();
        }
        fillUserBcInfoData();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger.info("心跳...蹦~~~蹦~~~蹦~~~");
                timerRun.setRun(true);
                if (!isOpenTime()) {
                    timer.cancel();
                    timerRun.setRun(false);
                    timerRun.setMsg("镖局每日13点-14点，21点-22点开放护送");
                    logger.info("监控停止！原因：镖局每日13点-14点，21点-22点开放护送。");
                }
                ParamHandler.set(sessionParam);
                String msg = fillUserBcInfoData();
                if (!"ok".equals(msg)) {
                    timer.cancel();
                    timerRun.setRun(false);
                    timerRun.setMsg(msg);
                    logger.info("监控停止！原因：{}。", msg);
                }
            }
        }, 10000, 10000);
    }

    public List<UserBcInfo> data(){
        return userBcInfoMap.values().stream().sorted(Comparator.comparing(UserBcInfo::getFight_power).reversed()).collect(Collectors.toList());
    }

    public TimerRun timerRun(){
        return timerRun;
    }

    private void initUserBcInfoData(){
        QueryResult queryResult = queryApi.query();
        queryResult.getMembers().forEach(member -> {
            UserBcInfo userBcInfo = new UserBcInfo();
            userBcInfo.setUid(member.getUid());
            userBcInfo.setName(member.getName());
            userBcInfo.setFight_power(member.getFight_power());
            userBcInfoMap.put(member.getUid(), userBcInfo);
        });
    }

    private String fillUserBcInfoData() {
        EscortResult escortResult = escortApi.search();
        if (!escortResult.isOK()) {
            return escortResult.getMsg();
        }
        escortResult.getEscort_info().getTrans().forEach(tran -> {
            boolean isSameDay = this.isSameDay(LocalDateTime.now(), this.toLocalDateTime(tran.getBegin_time() * 1000L));
            if (isSameDay && !tranMap.containsKey(tran.getIndex())) {
                UserBcInfo userBcInfo = userBcInfoMap.get(tran.getUid());
                userBcInfo.addTran(tran);
                tranMap.put(tran.getIndex(), tran);
            }
        });
        return "ok";
    }

    private void clear(){
        this.userBcInfoMap = new HashMap<>();
        this.tranMap = new HashMap<>();
        this.timerRun = new TimerRun();
    }

    public boolean isOpenTime(){
        // 时间点
        LocalDateTime time_12_40 = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 40));
        LocalDateTime time_14_20 = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 20));
        LocalDateTime time_20_40 = LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 40));
        LocalDateTime time_22_20 = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 20));
        return (time_12_40.isBefore(LocalDateTime.now()) && time_14_20.isAfter(LocalDateTime.now())) ||
                (time_20_40.isBefore(LocalDateTime.now()) && time_22_20.isAfter(LocalDateTime.now()));
    }

    private boolean isSameDay(LocalDateTime a, LocalDateTime b){
        return a.getDayOfYear() == b.getDayOfYear();
    }

    private LocalDateTime toLocalDateTime(long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("Asia/Shanghai"));
    }

    public static void main(String[] args){
        LocalDateTime last = LocalDateTime.now().plusHours(30);
        LocalDateTime cur = LocalDateTime.now();
        System.out.println(last.getDayOfYear());
        System.out.println(cur.getDayOfYear());
        System.out.println(last.getDayOfYear() == cur.getDayOfYear());

        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(1595252679000L), ZoneId.of("Asia/Shanghai"));
        System.out.println(time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
