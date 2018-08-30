package com.github.jnoee.xo.web.jackson;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jnoee.xo.config.BaseAutoConfiguration;
import com.github.jnoee.xo.web.config.WebAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableWebMvc
@ContextConfiguration(classes = {BaseAutoConfiguration.class, JacksonAutoConfiguration.class,
    WebAutoConfiguration.class})
public class IEnumModuleTest {
  @Autowired
  private ObjectMapper mapper;

  @Test
  public void test() throws Exception {
    TestBean bean = new TestBean();
    String json = mapper.writeValueAsString(bean);
    Assert.assertEquals("{\"num\":\"1\"}", json);
  }
}
