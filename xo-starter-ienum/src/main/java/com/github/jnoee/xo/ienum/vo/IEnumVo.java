package com.github.jnoee.xo.ienum.vo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.jnoee.xo.ienum.IEnum;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IEnumVo {
  private String name;
  private String des;
  private Map<String, String> items = new LinkedHashMap<>();

  public IEnumVo(Class<?> ienumClass) {
    if (ienumClass.isAnnotationPresent(ApiModel.class)) {
      ApiModel anno = ienumClass.getAnnotation(ApiModel.class);
      name = anno.value();
      des = anno.description();
    } else {
      name = ienumClass.getSimpleName();
      des = ienumClass.getSimpleName();
    }
    for (IEnum ienum : (IEnum[]) ienumClass.getEnumConstants()) {
      items.put(ienum.getValue(), ienum.getText());
    }
  }
}
