package com.github.jnoee.xo.pay.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.pay.yee.YeePay;
import com.github.jnoee.xo.pay.yee.YeePayProperties;

@Configuration
@EnableConfigurationProperties(YeePayProperties.class)
public class PayAutoConfiguration {
  @Bean
  public YeePay yeePay() {
    return new YeePay();
  }
}
