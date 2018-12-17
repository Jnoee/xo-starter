package com.github.jnoee.xo.ienum.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.github.jnoee.xo.ienum.IEnum;
import com.github.jnoee.xo.ienum.vo.IEnumVo;

import lombok.Getter;
import lombok.Setter;

/**
 * IEnum枚举管理组件。
 */
public class IEnumManager {
  @Setter
  private List<Class<? extends IEnum>> ienumClasses;
  @Getter
  private List<IEnumVo> ienums = new ArrayList<>();

  @PostConstruct
  protected void init() {
    for (Class<? extends IEnum> ienumClass : ienumClasses) {
      ienums.add(new IEnumVo(ienumClass));
    }
  }
}
