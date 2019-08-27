package com.github.jnoee.xo.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

/**
 * 参数设置处理抽象类，方便设置第三方组件的默认参数配置。
 */
public abstract class AbstractEnvironmentPostProcessor implements EnvironmentPostProcessor {
  protected Map<String, Object> defaultProperties = new HashMap<>();
  protected Map<String, Object> overrideProperties = new HashMap<>();

  /**
   * 增加默认配置。
   * 
   * @param key 配置名
   * @param value 配置值
   */
  public void addDefaultProperty(String key, Object value) {
    defaultProperties.put(key, value);
  }

  /**
   * 增加覆盖配置。
   * 
   * @param key 配置名
   * @param value 配置值
   */
  public void addOverrideProperties(String key, Object value) {
    overrideProperties.put(key, value);
  }

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {
    PropertiesPropertySource propertiesPropertySource =
        new PropertiesPropertySource(getClass().getName(), getProperties(environment));
    environment.getPropertySources().addLast(propertiesPropertySource);
  }

  /**
   * 获取配置。
   * 
   * @param environment 配置环境对象
   * @return 返回配置。
   */
  private Properties getProperties(ConfigurableEnvironment environment) {
    // 对于默认配置，如果环境中已有该配置，则使用已有的配置，否则使用默认配置。
    Map<String, Object> props = defaultProperties.entrySet().stream()
        .filter(map -> environment.getProperty(map.getKey()) == null)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    // 对于覆盖配置，不管环境中是否有该配置，都直接使用覆盖配置。
    props.putAll(overrideProperties);
    Properties properties = new Properties();
    properties.putAll(props);
    return properties;
  }
}
