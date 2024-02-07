package com.sinlff.server.controller;

import boot.ApplicationTest;
import cn.hutool.json.JSONUtil;
import com.sinlff.server.domain.User;
import com.sinlff.server.test.BaseMvcTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootTest(classes = {ApplicationTest.class})
@SpringJUnitWebConfig
//@WebAppConfiguration
public class UserControllerTest extends BaseMvcTest {
    @Autowired private UserController userController;
    @Autowired private MockMvc mockMvc;

    @PostConstruct
    private void init() {
        log.info("UserControllerTest初始化");
    }

    @Test
    public void userList() throws Exception {
        User req=new User();
        req.setId(1L);
        req.setName("nam1");
        req.setAge(2);
        req.setEmail("admin@qq.com");

        String url="/usercenter/user/userList.do";
        String reqJsonPretty=JSONUtil.toJsonPrettyStr(req);
        String resJson = mockMvc.perform(MockMvcRequestBuilders.post(url)
//                 .headers(httpHeaders)//设置公共请求头
                 .contentType(MediaType.APPLICATION_JSON)//请求内容类型
                 .accept(MediaType.APPLICATION_JSON)//响应期望内容类型
                 .characterEncoding("utf-8")
                 .content(reqJsonPretty)//报文体
                 ).andExpect(MockMvcResultMatchers.status().isOk())//断言状态码200
//                 .andDo(MockMvcResultHandlers.print())//打印全部mockMVC报文日志
                 .andReturn().getResponse().getContentAsString();

        String resJsonPretty= JSONUtil.toJsonPrettyStr(resJson);
        log.info("\n请求报文\n{}\n响应报文\n{}",reqJsonPretty,resJsonPretty);
    }

}