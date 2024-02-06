package com.sinlff.server.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 填充公共字段
 *
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @PostConstruct
    private void init(){
        log.info("MyMetaObjectHandler初始化");
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill...");
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill...");
        this.setFieldValByName("updateTime",  new Date(), metaObject);
    }

}