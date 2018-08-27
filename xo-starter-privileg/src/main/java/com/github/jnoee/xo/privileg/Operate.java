package com.github.jnoee.xo.privileg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

/**
 * 操作。
 */
@Data
public class Operate {
  @JacksonXmlProperty(isAttribute = true)
  private String code;
  @JacksonXmlProperty(isAttribute = true)
  private String name;
}
