package com.github.jnoee.xo.district;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.jnoee.xo.exception.SysException;

import lombok.extern.slf4j.Slf4j;

/**
 * 行政区划辅助类。
 */
@Slf4j
public class DistrictHelper {
  private static Districts districts;

  static {
    try (InputStream in = DistrictHelper.class.getResourceAsStream("district.xml")) {
      XmlMapper xmlMapper = new XmlMapper();
      districts = xmlMapper.readValue(in, Districts.class);
      // 建立行政区划之间的关联关系
      for (Province province : districts.getProvinces()) {
        for (City city : province.getCities()) {
          city.setProvince(province);
          for (County county : city.getCounties()) {
            county.setCity(city);
          }
        }
      }
    } catch (Exception e) {
      log.error("加载行政区划配置文件时发生异常。", e);
    }
  }

  /**
   * 获取所有省份列表。
   * 
   * @return 返回所有省份列表。
   */
  public static Set<Province> getProvinces() {
    return new TreeSet<>(districts.getProvinces());
  }

  /**
   * 根据辖区编码列表获取省份列表。
   * 
   * @param countyCodes 辖区编码列表
   * @return 返回辖区编码所在的省份列表。
   */
  public static Set<Province> getProvinces(Set<String> countyCodes) {
    Set<Province> provinces = new TreeSet<>();
    for (String countyCode : countyCodes) {
      County county = getCounty(countyCode);
      Optional<Province> province =
          provinces.stream().filter(p -> p.equals(county.getCity().getProvince())).findFirst();
      if (province.isPresent()) {
        province.get().addCounty(county);
      } else {
        Province copyProvince = county.getCity().getProvince().copy();
        copyProvince.addCounty(county);
        provinces.add(copyProvince);
      }
    }
    return provinces;
  }

  /**
   * 获取行政区划编码对应的省份。
   * 
   * @param districtCode 行政区划编码
   * @return 返回对应的省份。
   */
  public static Province getProvince(String districtCode) {
    String provinceCode = districtCode.substring(0, 2) + "0000";
    Optional<Province> provice =
        districts.getProvinces().stream().filter(p -> provinceCode.equals(p.getCode())).findFirst();
    if (provice.isPresent()) {
      return provice.get();
    }
    throw new SysException("未找到行政区划编码[" + districtCode + "]对应的省份。");
  }

  /**
   * 获取行政区划编码对应的城市。
   * 
   * @param districtCode 行政区划编码
   * @return 返回对应的城市。
   */
  public static City getCity(String districtCode) {
    Province province = getProvince(districtCode);
    String cityCode = districtCode.substring(0, 4) + "00";
    Optional<City> city =
        province.getCities().stream().filter(c -> cityCode.equals(c.getCode())).findFirst();
    if (city.isPresent()) {
      return city.get();
    }
    throw new SysException("未找到行政区划编码[" + districtCode + "]对应的城市。");
  }

  /**
   * 获取行政区划编码对应的辖区。
   * 
   * @param districtCode 行政区划编码
   * @return 返回对应的辖区。
   */
  public static County getCounty(String districtCode) {
    City city = getCity(districtCode);
    Optional<County> county =
        city.getCounties().stream().filter(c -> districtCode.equals(c.getCode())).findFirst();
    if (county.isPresent()) {
      return county.get();
    }
    throw new SysException("未找到行政区划编码[" + districtCode + "]对应的辖区。");
  }

  /**
   * 判断行政区划编码是否省份。
   * 
   * @param districtCode 行政区划编码
   * @return 如果是省份返回true，否则返回false。
   */
  public static Boolean isProvince(String districtCode) {
    return districtCode.endsWith("0000");
  }

  /**
   * 判断行政区划编码是否城市。
   * 
   * @param districtCode 行政区划编码
   * @return 如果是城市返回true，否则返回false。
   */
  public static Boolean isCity(String districtCode) {
    return !isProvince(districtCode) && districtCode.endsWith("00");
  }

  /**
   * 判断行政区划编码是否辖区。
   * 
   * @param districtCode 行政区划编码
   * @return 如果是辖区返回true，否则返回false。
   */
  public static Boolean isCounty(String districtCode) {
    return !isProvince(districtCode) && !isCity(districtCode);
  }

  private DistrictHelper() {}
}
