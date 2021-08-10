package com.scnu.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
//@MapperScan("com.scnu.order.mapper")
//@EnableDiscoveryClient  //nacos注册
//@EnableFeignClients     //服务调用
public class MainConfig {
}
