package com.github.jnoee.xo.web.config;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jnoee.xo.utils.DateUtils;
import com.github.jnoee.xo.web.converter.IEnumToString;
import com.github.jnoee.xo.web.converter.StringToIEnum;
import com.github.jnoee.xo.web.handler.ErrorView;
import com.github.jnoee.xo.web.handler.WebErrorAttributes;
import com.github.jnoee.xo.web.handler.WebErrorController;
import com.github.jnoee.xo.web.jackson.IEnumModule;

/**
 * 组件配置。
 */
@Configuration
@ConditionalOnWebApplication
public class WebAutoConfiguration implements WebMvcConfigurer {
  /**
   * 配置ErrorAttributes组件。
   * 
   * @return 返回ErrorAttributes组件。
   */
  @Bean
  public ErrorAttributes errorAttributes() {
    return new WebErrorAttributes();
  }

  /**
   * 配置异常处理组件。
   * 
   * @return 返回异常处理组件。
   */
  @Bean
  public WebErrorController webErrorController() {
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
  public View errorView(ObjectMapper objectMapper, ErrorAttributes errorAttributes) {
    return new ErrorView(objectMapper, errorAttributes);
  }

  /**
   * 配置IEnum Jackson转换组件。
   * 
   * @return 返回IEnum Jackson转换组件。
   */
  @Bean
  public IEnumModule ienumModule() {
    return new IEnumModule();
  }

  /**
   * 配置跨域支持组件。
   * 
   * @return 返回跨域支持组件。
   */
  @Bean
  public CorsFilter corsFilter() {
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

    registry.removeConvertible(String.class, Enum.class);
    registry.addConverterFactory(new StringToIEnum());
    registry.addConverter(new IEnumToString());
  }
}
