package com.hc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.domain.Dept;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptControllerTest {

    @Autowired
    private WebApplicationContext wac;

    //mock对象：用来模拟网络请求
    private MockMvc mockMvc;

    @Before
    public void setup() {
        //mock对象初始化，指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    //测试方法：运行测试方法时不需要启动服务
    @Test
    public void saveDept() throws Exception {
        System.out.println(mockMvc);
        Dept dept = new Dept(10, "ACCOUNTING", "CHICAGO");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dept);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.request(HttpMethod.POST, "/mock/dept")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MvcResult result = mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.dname").value("ACCOUNTING"))
                .andDo(print())
                .andReturn();

        String res = result.getResponse().getContentAsString();
        log.info(res);
    }

    @Test
    public void allDept() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/mock/allDept")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].dname").value("ACCOUNTING"))
                .andDo(print())
                .andReturn();

        String res = result.getResponse().getContentAsString();
        log.info(res);
    }

    @Test
    public void allDept2() throws Exception {
        MockHttpServletRequestBuilder builder = get("/mock/allDept2")
                .contentType(MediaType.APPLICATION_JSON);
        String result = mockMvc.perform(builder)//构造一个请求
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Result === " + result);
    }


    @Test
    public void selectByDeptno() throws Exception {
        mockMvc.perform(get("/mock/dept/10"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteByDeptno() throws Exception {//delete
        mockMvc.perform(delete("/mock/dept/10"))
                .andReturn();
    }

    /////////////////////////////////////////////////////
    @Test
    public void fun1() throws Exception {
        mockMvc.perform((get("/mock/fun1")))
                .andExpect(MockMvcResultMatchers.content().string("hello"))
                .andExpect(status().isOk()) //添加执行完成后的断言
                .andDo(print());//添加一个结果处理器，此处表示输出整个响应的结果信息
    }

    @Test
    public void selectByDeptno2() throws Exception {
        mockMvc.perform((get("/mock/dept2")
                .param("deptno", "30"))) //添加传值请求
                .andExpect(status().isOk()) //添加执行完成后的断言
                .andDo(print());//添加一个结果处理器，此处表示输出整个响应的结果信息
    }

    @Test
    public void fun4() throws Exception {//post
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dname", "sales");
        params.add("loc", "china");
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/mock/fun4")
                .params(params))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Result === " + mvcResult);
    }

    @Test
    public void fun5() throws Exception {//put
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dname", "sales");
        params.add("loc", "china");
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/mock/fun5")
                .params(params))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Result === " + mvcResult);
    }

    @Test
    public void fun6() throws Exception {//patch
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dname", "sales");
        params.add("loc", "china");
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/mock/fun6")
                .params(params))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Result === " + mvcResult);
    }

}
