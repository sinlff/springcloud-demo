package com.sinlff.server.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggerService {

    public void printLog(){
        log.debug("LoggerService debug log");
        log.info("LoggerService info log");
        log.warn("LoggerService warn log");
        log.error("LoggerService error log");
    }

}