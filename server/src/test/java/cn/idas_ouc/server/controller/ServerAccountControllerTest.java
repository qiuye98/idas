package cn.idas_ouc.server.controller;

import cn.idas_ouc.server.service.ServerAccountService;
import cn.idas_ouc.server.service.ServerService;
import cn.idas_ouc.server.service.ServerUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
// import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName: ServerAccountControllerTest
 * Package: cn.idas_ouc.server.controller
 *
 * @Author boxin
 * @Create 2023/9/20 15:26
 * @Version 1.0
 * @Description:
 */

// @RunWith(SpringRunner.class) @WebMvcTest(ServerAccountController.class) @SpringBootTest 不能启动
// @RunWith(SpringRunner.class) @WebMvcTest(ServerAccountController.class) 可以启动，但是无法注入 service
// @WebMvcTest(ServerAccountController.class) @SpringBootTest 无法启动
// @ExtendWith(SpringExtension.class)
// @ComponentScan(basePackages = "cn.idas_ouc")
@WebMvcTest(ServerAccountController.class)
class ServerAccountControllerTest {


    @MockBean
    private ServerAccountService serverAccountService;

    @MockBean
    private ServerUserService serverUserService;
    @MockBean
    private ServerService serverService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void useradd() throws Exception {
        System.out.println("测试");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/server/serveraccount/useradd")
                .param("userId", "1")
                .param("serverId", "31")
                .param("username", "hello");  // @RequestParam 获取变量。post请求也适用
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());



    }
}