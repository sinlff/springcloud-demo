package com.sinlff.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class LoggerService {

    @PostConstruct
    private void init(){
        log.info("LoggerService初始化");
    }

    public void printLog(){
        log.debug("LoggerService debug log");
        log.info("LoggerService info log");
        log.warn("LoggerService warn log");
        log.error("LoggerService error log");
    }

}