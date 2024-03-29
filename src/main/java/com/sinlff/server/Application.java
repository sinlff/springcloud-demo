package com.sinlff.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@Slf4j
@EnableEurekaClient
@SpringBootApplication(
        exclude = {
                EurekaClientAutoConfiguration.class
        }
)
@ComponentScan(
        basePackages = {
                "com.sinlff.server"
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
//                    "com.sinlff.server.Application",
                })
        }
)
public class Application {

    public static void main(String[] args){
        SpringApplication.run(com.sinlff.server.Application.class, args);
        log.info("Application启动完成");
    }

}