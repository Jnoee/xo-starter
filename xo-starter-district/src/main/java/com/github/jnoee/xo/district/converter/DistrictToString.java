package com.github.jnoee.xo.district.converter;

import org.springframework.core.convert.converter.Converter;

import com.github.jnoee.xo.district.District;

public class DistrictToString implements Converter<District, String> {
  @Override
  public String convert(District source) {
    return source.getCode();
  }
}
