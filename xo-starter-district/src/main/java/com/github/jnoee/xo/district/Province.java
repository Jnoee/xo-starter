package com.github.jnoee.xo.district;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 省份。
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Province extends District {
  /** 关联城市 */
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "city")
  private Set<City> cities = new TreeSet<>();

  public Province copy() {
    Province province = new Province();
    province.setCode(getCode());
    province.setName(getName());
    return province;
  }

  public void addCity(City city) {
    city.setProvince(this);
    cities.add(city);
  }

  public void addCounty(County county) {
    Optional<City> city = cities.stream().filter(c -> c.equals(county.getCity())).findFirst();
    if (city.isPresent()) {
      city.get().getCounties().add(county);
    } else {
      City copyCity = county.getCity().copy();
      copyCity.getCounties().add(county);
      cities.add(copyCity);
    }
  }
}
