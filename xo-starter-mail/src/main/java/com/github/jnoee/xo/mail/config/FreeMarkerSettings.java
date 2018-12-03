package com.github.jnoee.xo.mail.config;

import com.github.jnoee.xo.freemarker.AbstractFreeMarkerSettings;

/**
 * FreeMarker配置组件。
 */
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/META-INF/xo/mail/");
  }
}
