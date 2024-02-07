package com.sinlff.server.test;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

@Slf4j
@DSTransactional
//@ContextConfiguration(classes={MyMvcAutoConfig.class})
public class BaseMvcTest<T> {

    protected String testToken="";

    protected void addHttpHeaders(HttpHeaders httpHeaders){
        httpHeaders.set("agent", "junit");
    }

}