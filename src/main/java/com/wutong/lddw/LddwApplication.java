package com.wutong.lddw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@MapperScan({"com.thunisoft.demo.mapper"})
public class LddwApplication {

    public static void main(String[] args) {
        SpringApplication.run(LddwApplication.class, args);
    }

}
