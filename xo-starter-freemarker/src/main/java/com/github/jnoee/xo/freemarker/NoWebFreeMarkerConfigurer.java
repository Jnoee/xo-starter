package com.github.jnoee.xo.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.Getter;

/**
 * 非web环境下的FreeMarker配置组件。
 */
public class NoWebFreeMarkerConfigurer extends FreeMarkerConfigurationFactoryBean
    implements GenericFreeMarkerConfigurer {
  @Getter
  private ApplicationContext context;
  @Getter
  private List<AbstractFreeMarkerSettings> settings = new ArrayList<>();

  @Override
  public Configuration getConfiguration() {
    return getObject();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    context = applicationContext;
    Map<String, AbstractFreeMarkerSettings> freemarkerSettingsMap =
        context.getBeansOfType(AbstractFreeMarkerSettings.class);
    settings.addAll(freemarkerSettingsMap.values());
    Collections.sort(settings);
  }

  @Override
  public void afterPropertiesSet() throws IOException, TemplateException {
    super.afterPropertiesSet();
    init();
  }

  @Override
  protected void postProcessTemplateLoaders(List<TemplateLoader> templateLoaders) {
    super.postProcessTemplateLoaders(templateLoaders);
    for (String templatePath : getTemplatePaths()) {
      templateLoaders.add(getTemplateLoaderForPath(templatePath));
    }
  }
}
