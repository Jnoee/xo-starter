package com.github.jnoee.xo.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.format.datetime.joda.JodaDateTimeFormatAnnotationFormatterFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jnoee.xo.utils.DateUtils;
import com.github.jnoee.xo.web.handler.ErrorView;
import com.github.jnoee.xo.web.handler.WebErrorAttributes;
import com.github.jnoee.xo.web.handler.WebErrorController;
import com.github.jnoee.xo.web.jackson.LongModule;

/**
 * 组件配置。
 */
@Configuration
@ConditionalOnWebApplication
public class WebAutoConfiguration implements WebMvcConfigurer {
  /**
   * 配置Long类型Jackson转换组件。
   * 
   * @return 返回Long类型Jackson转换组件。
   */
  @Bean
  @ConditionalOnClass(Module.class)
  public LongModule longModule() {
    return new LongModule();
  }

  /**
   * 配置ErrorAttributes组件。
   * 
   * @return 返回ErrorAttributes组件。
   */
  @Bean
  ErrorAttributes errorAttributes() {
    return new WebErrorAttributes();
  }

  /**
   * 配置异常处理组件。
   * 
   * @return 返回异常处理组件。
   */
  @Bean
  WebErrorController webErrorController() {
    return new WebErrorController();
  }

  /**
   * 配置异常视图。
   * 
   * @param objectMapper ObjectMapper
   * @param errorAttributes ErrorAttributes
   * @return 返回异常视图。
   */
  @Bean(name = "error")
  View errorView(ObjectMapper objectMapper, ErrorAttributes errorAttributes) {
    return new ErrorView(objectMapper, errorAttributes);
  }

  /**
   * 配置跨域支持组件。
   * 
   * @return 返回跨域支持组件。
   */
  @Bean
  CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addExposedHeader("x-auth-token");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("HEAD");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    // 添加默认java.util.Date转换器
    registry.addFormatter(new DateFormatter(DateUtils.DAY));
    // 添加DateTimeFormat注解的日期时间转换器
    registry.addFormatterForFieldAnnotation(new DateTimeFormatAnnotationFormatterFactory());
    registry.addFormatterForFieldAnnotation(new JodaDateTimeFormatAnnotationFormatterFactory());
  }
}
