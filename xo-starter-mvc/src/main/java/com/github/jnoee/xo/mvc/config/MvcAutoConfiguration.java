package com.github.jnoee.xo.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.jnoee.xo.mvc.handler.GenericWebServerFactoryCustomizer;

@Configuration
public class MvcAutoConfiguration implements WebMvcConfigurer {
  @Bean("com.github.jnoee.xo.mvc.config.FreeMarkerSettings")
  FreeMarkerSettings freemarkerSettings() {
    return new FreeMarkerSettings();
  }

  @Bean
  GenericWebServerFactoryCustomizer webServerFactoryCustomizer() {
    return new GenericWebServerFactoryCustomizer();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/mvc/**")
        .addResourceLocations("classpath:/META-INF/xo/mvc/static/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/index");

    registry.addViewController("/500").setViewName("/500");
    registry.addViewController("/404").setViewName("/404");
    registry.addViewController("/403").setViewName("/403");
  }
}
