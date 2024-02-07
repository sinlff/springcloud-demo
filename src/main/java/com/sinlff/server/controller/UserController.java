package com.sinlff.server.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinlff.server.domain.User;
import com.sinlff.server.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    private void init(){
        System.out.println("TempController初始化");
    }

    @RequestMapping(value = "/userList")
    public List<User> userList() {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        List<User> userList=userMapper.selectList(queryWrapper);
        log.info(JSONUtil.toJsonStr(userList));
        log.info("111111");
        return userList;
    }

    @RequestMapping(value = "/test2")
    public User test2() {
        User user=userMapper.selectById(1L);
        log.info(JSONUtil.toJsonStr(user));
        log.info("111111");
        return user;
    }

}