package com.sinlff.server.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinlff.server.domain.User;
import com.sinlff.server.logger.LoggerService;
import com.sinlff.server.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    private void init(){
        System.out.println("TempController初始化");
    }

    @RequestMapping(value = "/test/selectList")
    public List<User> userList() {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        List<User> userList=userMapper.selectList(queryWrapper);
        log.info("userMapper.selectList={}",JSONUtil.toJsonStr(userList));
        return userList;
    }

    @RequestMapping(value = "/test/selectById")
    public User selectById() {
        User user=userMapper.selectById(1L);
        log.info(JSONUtil.toJsonStr(user));
        log.info("111111");
        return user;
    }

    @Autowired private LoggerService loggerService;
    @RequestMapping(value = "/log/logTest")
    public String logTest() {
        log.debug("UserController debug log");
        log.info("UserController info log");
        log.warn("UserController warn log");
        log.error("UserController error log");

        loggerService.printLog();
        return "OK";
    }
    @RequestMapping(value = "/log/updateLogLevel")
    public String updateLogLeve(String level, String packageName) {
        if(StringUtils.isEmpty(level)){
            throw new RuntimeException("level不能为空");
        }
        if(StringUtils.isEmpty(packageName)){
            throw new RuntimeException("packageName不能为空");
        }

        ch.qos.logback.classic.LoggerContext loggerContext =(ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger= loggerContext.getLogger(packageName);

        level=level.toLowerCase();
        logger.setLevel(ch.qos.logback.classic.Level.toLevel(level));
        log.info("updateLogLevel packageName={},level={}", packageName,level);

        loggerService.printLog();
        return logger.getLevel().toString();
    }

}