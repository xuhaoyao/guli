package com.scnu.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.scnu")
@MapperScan("com.scnu.statistics.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling   //定时任务
public class DailyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class,args);
    }
}
