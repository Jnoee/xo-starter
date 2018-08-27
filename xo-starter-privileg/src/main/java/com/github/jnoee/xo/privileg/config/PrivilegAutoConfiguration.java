package com.github.jnoee.xo.privileg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.privileg.PrivilegManager;

/**
 * 组件配置。
 */
@Configuration
public class PrivilegAutoConfiguration {
  /**
   * 配置权限管理组件。
   * 
   * @return 返回权限管理组件。
   */
  @Bean
  public PrivilegManager privilegManager() {
    return new PrivilegManager();
  }
}
