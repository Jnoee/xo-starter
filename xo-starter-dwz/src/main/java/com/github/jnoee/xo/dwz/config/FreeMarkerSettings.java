package com.github.jnoee.xo.dwz.config;

import org.apache.shiro.SecurityUtils;

import com.github.jnoee.xo.freemarker.AbstractFreeMarkerSettings;

/**
 * FreeMarker配置组件。
 */
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/META-INF/xo/dwz/template/");
    addTemplatePath("classpath:/META-INF/xo/dwz/macro/");
    addAutoImport("dwz", "dwz.ftl");

    addStaticClass(SecurityUtils.class);
    addTld("/META-INF/xo/tld/shiro.tld");
    addAutoInclude("shiro-include.ftl");
    addAutoImport("sec", "sec.ftl");
  }
}
