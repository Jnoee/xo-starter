package com.github.jnoee.xo.mvc.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.github.jnoee.xo.mvc.freemarker.GenericFreeMarkerConfigurer;
import com.github.jnoee.xo.mvc.handler.GenericWebServerFactoryCustomizer;

@Configuration
@EnableConfigurationProperties({FreeMarkerProperties.class})
public class MvcAutoConfiguration implements WebMvcConfigurer {
  /**
   * 配置FreeMarker模版组件。
   * 
   * @param freemarkerProperties FreeMarker配置属性
   * @return 返回FreeMarker模版组件。
   */
  @Bean
  public FreeMarkerConfigurer freemarkerConfigurer(FreeMarkerProperties freemarkerProperties) {
    GenericFreeMarkerConfigurer configurer = new GenericFreeMarkerConfigurer();
    configurer.setFreemarkerSettings(freemarkerProperties.toProperties());
    return configurer;
  }

  @Bean("com.github.jnoee.xo.mvc.config.FreeMarkerSettings")
  public FreeMarkerSettings freemarkerSettings() {
    return new FreeMarkerSettings();
  }

  @Bean
  public GenericWebServerFactoryCustomizer webServerFactoryCustomizer() {
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
