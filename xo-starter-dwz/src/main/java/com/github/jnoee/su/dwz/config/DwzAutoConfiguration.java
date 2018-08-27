package com.github.jnoee.su.dwz.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.jnoee.su.dwz.handler.DwzErrorController;
import com.github.jnoee.su.dwz.login.AuthCounter;

/**
 * 组件配置。
 */
@Configuration
public class DwzAutoConfiguration implements WebMvcConfigurer {
  @Bean
  @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AuthCounter authCounter() {
    return new AuthCounter();
  }

  @Bean("com.cntest.su.dwz.config.FreeMarkerSettings")
  public FreeMarkerSettings freemarkerSettings() {
    return new FreeMarkerSettings();
  }

  @Bean
  public DwzErrorController dwzErrorController() {
    return new DwzErrorController();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/dwz/**")
        .addResourceLocations("classpath:/META-INF/su/dwz/static/");
  }
}
