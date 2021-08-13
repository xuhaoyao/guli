package com.scnu.statistics.schedule;

import com.scnu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    private DailyService dailyService;

    //每天凌晨1点执行任务:将前一天的网站数据进行统计
    @Scheduled(cron = "0 0 1 * * ?")
    public void staData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到两小时前的Date,即前一天
        Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 2);
        String day = sdf.format(date);
        dailyService.staData(day);
    }

}
