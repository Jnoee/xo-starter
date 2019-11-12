package com.github.jnoee.xo.district;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 行政区划抽象基类。
 */
@Getter
@Setter
@EqualsAndHashCode(of = "code")
@ToString(of = "code")
public abstract class District implements Comparable<District> {
  /** 编码 */
  @JacksonXmlProperty(isAttribute = true)
  private String code;
  /** 名称 */
  @JacksonXmlProperty(isAttribute = true)
  private String name;

  @Override
  public int compareTo(District o) {
    return code.compareTo(o.getCode());
  }
}
