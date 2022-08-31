package com.lishuai.highconcurrentsecondkill.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lishuai
 * @date 2022/8/22
 */
@Configuration
@EnableTransactionManagement//自动管理事务
@MapperScan("com.lishuai.highconcurrentsecondkill.mapper")
public class MyBatisPlusConfig {


    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

}
