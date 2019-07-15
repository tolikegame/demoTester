package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.tool.RomanToInteger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestControllerTest {
    //MockMvc
    protected MockMvc mockMvc;
    // Json序列化工具
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected WebApplicationContext wac;

    /**
     * 使用 SpyBean 結合Mockito 模擬返回值
     */
    @SpyBean
    RomanToInteger romanToInteger;

    @Before
    public void init(){
        //初始化MockMvc
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity()) //套用Spring Security 設定
                .build();
    }

    @Test
    @WithMockUser(username = "admin")
    public void testGet() throws Exception {

        /**
         * 建立一個 get 請求並設定參數symbol為X
         *
         */
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/api/switch").param("symbol","X");
        //透過 MockMvc 調用 Controller 返回response
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //檢查response 的狀態碼與 MediaType 與 Json 數值
        Assert.assertEquals(response.getStatus(),200);
        Assert.assertEquals(response.getContentType(), MediaType.APPLICATION_JSON_UTF8.toString());
        int value = objectMapper.readTree(response.getContentAsString()).get("result").asInt();
        Assert.assertEquals(value,10);
    }

    @Test
    @WithMockUser(username = "admin")
    public void testPost() throws Exception {
        Map<String,String> body = new HashMap<>();
        body.put("symbol","V");
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/api/switch").contentType(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(body));
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assert.assertEquals(response.getStatus(),200);
        Assert.assertEquals(response.getContentType(), MediaType.APPLICATION_JSON_UTF8.toString());
        int value = objectMapper.readTree(response.getContentAsString()).get("result").asInt();
        Assert.assertEquals(value,5);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testWhenThrowNull() throws Exception {
        //透過 Mocktio stub 物件
        Mockito.when(romanToInteger.romanToInt(Mockito.anyString())).thenThrow(new NullPointerException());
        //調用api 測試
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/api/injection").param("symbol","X");
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //檢查返還結果
        Assert.assertEquals(response.getStatus(),500);
        //檢查方法調用狀況
        Mockito.verify(romanToInteger,Mockito.atLeastOnce()).romanToInt(Mockito.anyString());
    }
}
