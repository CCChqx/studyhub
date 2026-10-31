package com.studyhub.config;

/*
*  @Bean 把 Sm2Scheduler 注册成 Scheduler
* */

import com.studyhub.review.Scheduler;
import com.studyhub.review.Sm2Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewConfig {

    @Bean
    public Scheduler scheduler(){
        return new Sm2Scheduler();
    }
}
