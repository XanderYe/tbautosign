package cn.xanderye.tbautosign.schedule;

import cn.xanderye.tbautosign.service.TBInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Xander on 2018-11-09.
 */
@Component
@Slf4j
public class ScheduledTask {
    @Autowired
    private TBInfoService tbInfoService;
    int cronCount = 0;

    @Scheduled(cron = "30 0 0 * * ?")
    public void reset() {
        tbInfoService.resetTBInfos();
        cronCount=0;
        log.info("重置贴吧签到状态");
    }

    @Scheduled(cron = "0 0 1,2,3 * * ?")
    public void tbAutoSign() {
        tbInfoService.sign();
        log.info("===initialDelay: 第{}次执行贴吧自动签到", ++cronCount);
    }
}
