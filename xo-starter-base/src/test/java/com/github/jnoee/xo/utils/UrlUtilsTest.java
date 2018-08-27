package com.github.jnoee.xo.utils;

import org.junit.Assert;
import org.junit.Test;

import com.github.jnoee.xo.constant.Encoding;
import com.github.jnoee.xo.utils.UrlUtils;

public class UrlUtilsTest {
  private String origString = "测试编码";
  private String distString = "%E6%B5%8B%E8%AF%95%E7%BC%96%E7%A0%81";

  @Test
  public void testEncodeString() {
    Assert.assertEquals(distString, UrlUtils.encode(origString));
  }

  @Test
  public void testEncodeStringString() {
    Assert.assertEquals(distString, UrlUtils.encode(origString, Encoding.UTF8));
  }

  @Test
  public void testDecodeString() {
    Assert.assertEquals(origString, UrlUtils.decode(distString));
  }

  @Test
  public void testDecodeStringString() {
    Assert.assertEquals(origString, UrlUtils.decode(distString, Encoding.UTF8));
  }

}
