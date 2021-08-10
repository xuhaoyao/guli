package com.scnu.edu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.scnu.edu.mapper")
@EnableTransactionManagement
public class MPConfig {

    /**
     * 当要更新一条记录的时候，希望这条记录没有被别人更新
     * 乐观锁实现方式：
     *
     * 取出记录时，获取当前version
     * 更新时，带上这个version
     * 执行更新时， set version = newVersion where version = oldVersion
     * 如果version不对，就更新失败
     */
    @Bean
    OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

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
