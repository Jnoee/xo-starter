package com.github.jnoee.xo.mvc.config;

import com.github.jnoee.xo.freemarker.AbstractFreeMarkerSettings;
import com.github.jnoee.xo.utils.DateUtils;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * FreeMarker配置组件。
 */
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/META-INF/xo/mvc/macro/");
    addTemplatePath("classpath:/META-INF/xo/mvc/template/");
    addAutoImport("s", "mvc.ftl");
    addStaticClass(StringUtils.class, DateUtils.class);
  }
}
