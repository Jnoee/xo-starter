package com.github.jnoee.xo.district.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.github.jnoee.xo.district.County;
import com.github.jnoee.xo.district.DistrictHelper;

@Converter(autoApply = true)
public class CountyConverter implements AttributeConverter<County, String> {
  @Override
  public String convertToDatabaseColumn(County attribute) {
    return attribute.getCode();
  }

  @Override
  public County convertToEntityAttribute(String dbData) {
    return DistrictHelper.getCounty(dbData);
  }
}
