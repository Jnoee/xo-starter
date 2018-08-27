package com.github.jnoee.xo.privileg;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

/**
 * 资源。
 */
@Data
public class Resource {
  @JacksonXmlProperty(isAttribute = true)
  private String code;
  @JacksonXmlProperty(isAttribute = true)
  private String name;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "operate")
  private List<Operate> operates = new ArrayList<>();

  public void setOperates(List<Operate> operates) {
    for (Operate operate : operates) {
      operate.setCode(getCode() + ":" + operate.getCode());
    }
    this.operates = operates;
  }
}
