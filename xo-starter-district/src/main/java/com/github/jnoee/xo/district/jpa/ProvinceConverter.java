package com.github.jnoee.xo.district.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.github.jnoee.xo.district.DistrictHelper;
import com.github.jnoee.xo.district.Province;

@Converter(autoApply = true)
public class ProvinceConverter implements AttributeConverter<Province, String> {
  @Override
  public String convertToDatabaseColumn(Province attribute) {
    return attribute.getCode();
  }

  @Override
  public Province convertToEntityAttribute(String dbData) {
    return DistrictHelper.getProvince(dbData);
  }
}
