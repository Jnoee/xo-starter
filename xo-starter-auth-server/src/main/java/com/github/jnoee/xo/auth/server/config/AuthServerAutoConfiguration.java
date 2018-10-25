package com.github.jnoee.xo.auth.server.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.auth.config.ShiroConfigurationAware;
import com.github.jnoee.xo.auth.server.AuthServerRealm;
import com.github.jnoee.xo.auth.server.AuthUserService;

/**
 * 组件配置。
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnBean({AuthUserService.class})
public class AuthServerAutoConfiguration implements ShiroConfigurationAware {
  /**
   * 配置AuthRealm组件。
   * 
   * @return 返回AuthRealm组件。
   */
  @Override
  @Bean
  public AuthServerRealm authRealm() {
    return new AuthServerRealm();
  }
}
