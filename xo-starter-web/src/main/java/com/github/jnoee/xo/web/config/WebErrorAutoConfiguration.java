package com.github.jnoee.xo.web.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jnoee.xo.web.handler.ErrorView;
import com.github.jnoee.xo.web.handler.WebErrorAttributes;
import com.github.jnoee.xo.web.handler.WebErrorController;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class WebErrorAutoConfiguration {
  /**
   * 配置ErrorAttributes组件。
   * 
   * @return 返回ErrorAttributes组件。
   */
  @Bean
  ErrorAttributes webErrorAttributes() {
    return new WebErrorAttributes();
  }

  /**
   * 配置异常处理组件。
   * 
   * @return 返回异常处理组件。
   */
  @Bean
  WebErrorController webErrorController() {
    return new WebErrorController();
  }

  /**
   * 配置异常视图。
   * 
   * @param objectMapper ObjectMapper
   * @param errorAttributes ErrorAttributes
   * @return 返回异常视图。
   */
  @Bean(name = "error")
  View errorView(ObjectMapper objectMapper, ErrorAttributes errorAttributes) {
    return new ErrorView(objectMapper, errorAttributes);
  }
}
