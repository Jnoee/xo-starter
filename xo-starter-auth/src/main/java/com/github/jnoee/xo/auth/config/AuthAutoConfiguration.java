package com.github.jnoee.xo.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.auth.AuthHelper;
import com.github.jnoee.xo.auth.handle.AuthErrorController;

/**
 * 组件配置。
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthAutoConfiguration {
  @Bean
  AuthHelper authHelper() {
    return new AuthHelper();
  }

  @Bean
  AuthErrorController authErrorController() {
    return new AuthErrorController();
  }
}
