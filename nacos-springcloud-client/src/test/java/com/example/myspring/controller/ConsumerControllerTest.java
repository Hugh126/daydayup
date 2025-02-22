package com.example.myspring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 随机端口
@AutoConfigureMockMvc // 启用 MockMvc
class ConsumerControllerTest {

    @LocalServerPort // 注入随机端口
    private int port;

    @Autowired
    private MockMvc mockMvc; // 注入 MockMvc


    @Test
    void consumer() throws Exception {
        // 拼接 URL
        String url = "http://localhost:" + port + "/consumer?name=abc";
        int i=0;
        while (i++ < 5) {
            // 发起请求并验证
            String nReq = String.format(" 第%d次请求", i);
            System.out.println(LocalDateTime.now().toString() + nReq);
            mockMvc.perform(MockMvcRequestBuilders.get(url))
                    .andExpect(MockMvcResultMatchers.status().isOk()) // 校验状态码为 200
                    .andDo(result -> {
                        // 打印返回结果
                        String response = result.getResponse().getContentAsString();
                        System.out.println( nReq + "[ RESP ]" + response);
                    });
        }

    }
}