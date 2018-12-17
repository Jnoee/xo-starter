package com.github.jnoee.xo.ienum.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.Module;
import com.github.jnoee.xo.ienum.jackson.IEnumModule;

/**
 * 枚举增强总配置。
 */
@Configuration
@Import({IEnumJpaAutoConfiguration.class, IEnumWebAutoConfiguration.class})
public class IEnumAutoConfiguration {
  /**
   * 配置IEnum Jackson转换组件。
   * 
   * @return 返回IEnum Jackson转换组件。
   */
  @Bean
  @ConditionalOnClass(Module.class)
  public IEnumModule ienumModule() {
    return new IEnumModule();
  }
}
