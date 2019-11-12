package com.github.jnoee.xo.district.api;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.district.City;
import com.github.jnoee.xo.district.County;
import com.github.jnoee.xo.district.DistrictHelper;
import com.github.jnoee.xo.district.Province;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "行政区划")
public class DistrictApi {
  @ApiOperation(value = "获取省份列表")
  @GetMapping("provinces")
  public Set<Province> listProvinces() {
    return DistrictHelper.getProvinces();
  }

  @ApiOperation(value = "获取省份")
  @GetMapping("provinces/{code}")
  public Province getProvince(@PathVariable("code") Province province) {
    return province;
  }

  @ApiOperation(value = "获取城市")
  @GetMapping("cities/{code}")
  public City getCity(@PathVariable("code") City city) {
    return city;
  }

  @ApiOperation(value = "获取辖区")
  @GetMapping("counties/{code}")
  public County getCity(@PathVariable("code") County county) {
    return county;
  }
}
