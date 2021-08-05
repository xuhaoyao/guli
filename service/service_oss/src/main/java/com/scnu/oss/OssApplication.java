package com.scnu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

//此模块用不上数据库,不加入容器,否则因为在配置文件没有配置数据库信息会启动报错
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//测试swagger的时候,如果不改变包扫描规则,那么swagger打不开
@ComponentScan(basePackages = "com.scnu")
@EnableDiscoveryClient
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
