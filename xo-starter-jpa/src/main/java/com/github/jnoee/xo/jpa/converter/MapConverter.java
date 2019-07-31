package com.github.jnoee.xo.jpa.converter;

import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.github.jnoee.xo.utils.StringUtils;

@Converter(autoApply = true)
public class MapConverter implements AttributeConverter<Map<String, String>, String> {
  @Override
  public String convertToDatabaseColumn(Map<String, String> attribute) {
    return StringUtils.mapToString(attribute);
  }

  @Override
  public Map<String, String> convertToEntityAttribute(String dbData) {
    return StringUtils.stringToMap(dbData);
  }
}
