package com.github.jnoee.xo.shiro.config;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Shiro配置。
 */
@Data
@ConfigurationProperties("su.shiro")
public class ShiroProperties {
  /** 加密算法 */
  private String algorithmName = Sha256Hash.ALGORITHM_NAME;
  /** 密码是否用HEX编码，默认是BASE64编码 */
  private Boolean hexEncode = false;
  /** 是否加盐值 */
  private Boolean salted = true;
  /** 盐值 */
  private String salt = Sha256Hash.ALGORITHM_NAME;
}
