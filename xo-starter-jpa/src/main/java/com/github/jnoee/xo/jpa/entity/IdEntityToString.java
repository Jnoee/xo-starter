package com.github.jnoee.xo.jpa.entity;

import org.springframework.core.convert.converter.Converter;

/**
 * IdEntity转换为字符串转换器。
 */
public class IdEntityToString implements Converter<IdEntity, String> {
  @Override
  public String convert(IdEntity source) {
    return source.getId().toString();
  }
}
