package com.github.jnoee.xo.word.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("xo.word")
public class WordProperties {
  /** 模版路径 */
  private String templateDir = "classpath*:/META-INF/xo/word";
}
