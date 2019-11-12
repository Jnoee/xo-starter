package com.github.jnoee.xo.district;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jnoee.xo.district.DistrictHelper;
import com.github.jnoee.xo.district.Province;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistrictHelperTest {
  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testGetProvinces() throws Exception {
    Set<Province> provinces = DistrictHelper.getProvinces();
    log.debug("{}", mapper.writeValueAsString(provinces));
  }

  @Test
  public void testGetDistrict() throws Exception {
    log.debug("{}", mapper.writeValueAsString(DistrictHelper.getCounty("110101")));
  }

  @Test
  public void testGetProvincesListOfString() throws Exception {
    Set<String> codes = new TreeSet<>();
    codes.add("110101");
    codes.add("110102");
    codes.add("120101");
    log.debug("{}", mapper.writeValueAsString(DistrictHelper.getProvinces(codes)));
  }
}
