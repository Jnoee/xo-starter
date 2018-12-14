package com.github.jnoee.xo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.utils.SpringUtils;

/**
 * 组件配置。
 */
@Configuration
public class BaseAutoConfiguration {
  /**
   * 配置信息组件。
   * 
   * @return 返回信息组件。
   */
  @Bean
  MessageSource messageSource() {
    return new MessageSource();
  }

  /**
   * 配置Spring工具组件。
   * 
   * @return 返回Spring工具组件。
   */
  @Bean
  SpringUtils springUtils() {
    return new SpringUtils();
  }
}
