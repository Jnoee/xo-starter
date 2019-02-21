package com.github.jnoee.xo.id.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 分布式主键配置属性。
 */
@Getter
@Setter
@ConfigurationProperties("xo.id")
public class IdProperties {
  /** workerId生成器类型，目前支持根据IP和主机名，分别对应类型值为 ip 和 host。 */
  private String type = "ip";
}
