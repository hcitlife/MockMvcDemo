package com.hc;

import com.hc.controller.DeptController2;
import com.hc.domain.Dept;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptController2Test {

    //mock对象：用来模拟网络请求
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DeptController2())
                .alwaysDo(print())  //全局配置
                //默认每次执行请求后都做的动作
                .alwaysExpect(MockMvcResultMatchers.status().isOk()) //默认每次执行后进行验证的断言
                .build();
    }

    //测试普通控制器
    @Test
    public void selectByDeptno() throws Exception {
        System.out.println(mockMvc);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/mock2/dept/10"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dept"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("dept/detail"))
                .andDo(print())
                .andReturn();

        String res = result.getResponse().getContentAsString();
        log.info(res);
    }

    //得到MvcResult自定义验证
    @Test
    public void selectByDeptno2() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/mock2/dept/{deptno}", 10))//执行请求
                .andReturn(); //返回MvcResult
        Assert.assertNotNull(result.getModelAndView().getModel().get("dept")); //自定义断言
    }

    //验证请求参数绑定到模型数据及Flash属性
    @Test
    public void saveDept() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/mock2/dept")
                .param("deptno", "50")
                .param("dname", "zhang")
                .param("loc", "san");
        mockMvc.perform(builder) //执行传递参数的POST请求(也可以post("/user?name=zhang"))
                .andExpect(MockMvcResultMatchers.handler().handlerType(DeptController2.class)) //验证执行的控制器类型
                .andExpect(MockMvcResultMatchers.handler().methodName("saveDept")) //验证执行的控制器方法名
                .andExpect(MockMvcResultMatchers.model().hasNoErrors()) //验证页面没有错误
                .andExpect(MockMvcResultMatchers.flash().attributeExists("dept")) //验证存在flash属性
                .andExpect(MockMvcResultMatchers.view().name("redirect:/dept/list/"))
                .andDo(print()); //验证视图
    }

    //上传测试
    @Test
    public void fileTest() throws Exception {
        MockMultipartFile mmf = new MockMultipartFile("fileName", "aim.xlsx", "application/ms-excel", new FileInputStream(new File("E:/ABCD.xlsx")));
        MockMultipartHttpServletRequestBuilder buider = MockMvcRequestBuilders.fileUpload("/mock2/fileUpload").
                file(mmf);

        ResultActions resultActions = mockMvc.perform(buider);
        MvcResult mvcResult = resultActions.andDo(MockMvcResultHandlers.print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("==========结果为：==========\n" + result + "\n");
    }

}