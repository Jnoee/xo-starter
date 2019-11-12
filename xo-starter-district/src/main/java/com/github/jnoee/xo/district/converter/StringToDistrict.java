package com.github.jnoee.xo.district.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.github.jnoee.xo.district.City;
import com.github.jnoee.xo.district.County;
import com.github.jnoee.xo.district.District;
import com.github.jnoee.xo.district.DistrictHelper;
import com.github.jnoee.xo.district.Province;
import com.github.jnoee.xo.utils.StringUtils;

public class StringToDistrict implements ConverterFactory<String, District> {
  @Override
  public <T extends District> Converter<String, T> getConverter(Class<T> targetType) {
    return new StringToDistrictConverter<>(targetType);
  }

  private class StringToDistrictConverter<T extends District> implements Converter<String, T> {
    private final Class<T> toClass;

    public StringToDistrictConverter(Class<T> toClass) {
      this.toClass = toClass;
    }

    @SuppressWarnings("unchecked")
    public T convert(String source) {
      if (StringUtils.isBlank(source)) {
        return null;
      }
      if (toClass == Province.class) {
        return (T) DistrictHelper.getProvince(source);
      }
      if (toClass == City.class) {
        return (T) DistrictHelper.getCity(source);
      }
      if (toClass == County.class) {
        return (T) DistrictHelper.getCounty(source);
      }
      return null;
    }
  }
}
