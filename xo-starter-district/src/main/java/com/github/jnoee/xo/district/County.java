package com.github.jnoee.xo.district;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 辖区。
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class County extends District {
  /** 关联城市 */
  @JsonIgnore
  private City city;

  /**
   * 获取包含省份城市的完整名称。
   * 
   * @return 返回包含省份城市的完整名称。
   */
  @JsonIgnore
  public String getFullName() {
    return city.getFullName() + "-" + getName();
  }
}
