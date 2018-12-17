package com.github.jnoee.xo.excel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("xo.excel")
public class ExcelProperties {
  /** 模版路径 */
  private String templateDir = "classpath*:/META-INF/xo/excel";
}
