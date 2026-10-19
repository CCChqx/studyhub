package com.studyhub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.studyhub.mapper")
@EnableScheduling
public class StudyhubApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyhubApplication.class, args);
    }
}
