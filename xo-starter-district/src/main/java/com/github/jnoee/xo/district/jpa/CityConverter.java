package com.github.jnoee.xo.district.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.github.jnoee.xo.district.City;
import com.github.jnoee.xo.district.DistrictHelper;

@Converter(autoApply = true)
public class CityConverter implements AttributeConverter<City, String> {
  @Override
  public String convertToDatabaseColumn(City attribute) {
    return attribute.getCode();
  }

  @Override
  public City convertToEntityAttribute(String dbData) {
    return DistrictHelper.getCity(dbData);
  }
}
