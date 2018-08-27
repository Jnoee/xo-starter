package com.github.jnoee.xo.privileg;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

/**
 * 权限配置。
 */
@Data
@JacksonXmlRootElement(localName = "privilegs")
public class Privilegs {
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "module")
  private List<Module> modules = new ArrayList<>();

  public List<Module> getModules() {
    if (modules == null) {
      modules = new ArrayList<>();
    }
    return modules;
  }
}
