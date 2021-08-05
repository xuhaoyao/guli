package com.scnu.edu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.scnu.edu.mapper")
@EnableTransactionManagement
public class MPConfig {

    //注入分页拦截器,实现分页
    @Bean
    PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    //注入逻辑删除的组件
    @Bean
    ISqlInjector iSqlInjector(){
        return new LogicSqlInjector();
    }

}
