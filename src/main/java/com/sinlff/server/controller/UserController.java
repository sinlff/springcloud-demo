package com.sinlff.server.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinlff.server.domain.User;
import com.sinlff.server.mapper.UserMapper;
import com.sinlff.server.service.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RestController
@RefreshScope
public class UserController {
    @Value("${wsw1:none}")
    private String wsw1;

    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    private void init(){
        log.info("UserController初始化, wsw1={}", wsw1);
//        DiscoveryClient a;
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
        log.debug("logTest debug log");
        log.info("logTest info log");
        log.warn("logTest warn log");
        log.error("logTest error log");

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

        log.debug("updateLogLeve debug log");
        log.info("updateLogLeve info log");
        log.warn("updateLogLeve warn log");
        log.error("updateLogLeve error log");
        return logger.getLevel().toString();
    }

}