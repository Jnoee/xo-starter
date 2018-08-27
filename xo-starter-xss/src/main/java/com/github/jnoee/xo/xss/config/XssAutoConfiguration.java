package com.github.jnoee.xo.xss.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.xss.filter.XssFilter;

@Configuration
public class XssAutoConfiguration {
  @Bean
  public FilterRegistrationBean<XssFilter> testFilterRegistration() {
    FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new XssFilter());
    registration.addUrlPatterns("/*");
    registration.setName("xssFilter");
    registration.setOrder(1);
    return registration;
  }
}
