package com.github.jnoee.xo.privileg;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

/**
 * 模块，用于对权限配置展示的页面进行分组。
 */
@Data
public class Module {
  @JacksonXmlProperty(isAttribute = true)
  private String name;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "resource")
  private List<Resource> resources = new ArrayList<>();
}
