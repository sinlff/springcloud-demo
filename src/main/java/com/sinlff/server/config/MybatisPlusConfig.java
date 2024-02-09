package com.sinlff.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class MybatisPlusConfig {

    @PostConstruct
    private void init(){
        log.info("MybatisPlusConfig初始化");
    }

    /*
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MyInnerInterceptor myInnerInterceptor) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        interceptor.addInnerInterceptor(myInnerInterceptor);
        return interceptor;
    }
    */

}