package com.sinlff.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//@EnableEurekaServer
@SpringBootApplication(
        scanBasePackages={
        },
        exclude = {
        }
)
@ComponentScan(
        basePackages = {
                "com.sinlff.server"
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
                    "com.sinlff.server.config.MyMetaObjectHandler",
                    "com.sinlff.server.config.MybatisPlusConfig"
                })
        }
)
@Slf4j
public class Application {

    public static void main(String[] args){
        SpringApplication.run(com.sinlff.server.Application.class, args);
        log.info("Application启动完成");

//        Mysq
    }
}