package com.sinlff.server.controller;

import cn.hutool.json.JSONUtil;
import com.sinlff.server.domain.User;
import com.sinlff.server.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @description:<p>
 * 用户
 * @author: 王盛武 @date: 2021-09-06 14:01:14
 * @since 1.0.0
 * @see
 */
@RestController
@Slf4j
public class TempController {
    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    private void init(){
        System.out.println("TempController初始化");
    }

    @RequestMapping(value = "/test1")
    public String test1() {
        log.info("55555555555555");
        return "OK";
    }

    @RequestMapping(value = "/test2")
    public User test2() {
        User user=userMapper.selectById(1L);
        log.info(JSONUtil.toJsonStr(user));
        log.info("111111");
        return user;
    }

}