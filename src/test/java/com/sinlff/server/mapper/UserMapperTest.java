package com.sinlff.server.mapper;

import boot.ApplicationMapperTest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinlff.server.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@SpringBootTest(classes = {ApplicationMapperTest.class})
public class UserMapperTest {
    @Autowired private UserMapper userMapper;

    @PostConstruct
    private void init() {
        log.info("UserMapperTest初始化");
    }

    @Test
    public void userList() throws Exception {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        List<User> userList=userMapper.selectList(queryWrapper);
        log.info(JSONUtil.toJsonStr(userList));
    }

}