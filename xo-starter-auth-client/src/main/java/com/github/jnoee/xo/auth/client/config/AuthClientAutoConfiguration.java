package com.github.jnoee.xo.auth.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.auth.client.AuthClientRealm;
import com.github.jnoee.xo.auth.config.ShiroConfigurationAware;

/**
 * 组件配置。
 */
@Configuration
@ConditionalOnWebApplication
public class AuthClientAutoConfiguration implements ShiroConfigurationAware {
  /**
   * 配置认证组件。
   * 
   * @param authService 认证服务
   * @return 返回认证组件。
   */
  @Override
  @Bean
  public AuthClientRealm authRealm() {
    return new AuthClientRealm();
  }
}
