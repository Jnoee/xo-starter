package com.github.jnoee.xo.district;

import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JacksonXmlRootElement(localName = "provinces")
public class Districts {
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "province")
  private Set<Province> provinces = new TreeSet<>();
}
