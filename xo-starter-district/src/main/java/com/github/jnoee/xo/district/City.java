package com.github.jnoee.xo.district;

import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 城市。
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class City extends District {
  /** 关联省份 */
  @JsonIgnore
  private Province province;
  /** 关联辖区 */
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "county")
  private Set<County> counties = new TreeSet<>();

  public City copy() {
    City city = new City();
    city.setCode(getCode());
    city.setName(getName());
    return city;
  }

  public void addCounty(County county) {
    county.setCity(this);
    counties.add(county);
  }

  /**
   * 获取包含省份的完整名称。
   * 
   * @return 返回包含省份的完整名称。
   */
  @JsonIgnore
  public String getFullName() {
    return province.getName() + "-" + getName();
  }
}
