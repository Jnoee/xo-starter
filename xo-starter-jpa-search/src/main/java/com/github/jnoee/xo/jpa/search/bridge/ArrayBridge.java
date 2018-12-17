package com.github.jnoee.xo.jpa.search.bridge;

import org.hibernate.search.bridge.StringBridge;

import com.github.jnoee.xo.utils.CollectionUtils;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * 字符串数组字段全文索引桥接器。
 */
public class ArrayBridge implements StringBridge {
  @Override
  public String objectToString(Object object) {
    String[] strs = (String[]) object;
    if (CollectionUtils.isEmpty(strs)) {
      return "";
    }
    return StringUtils.join(strs, " ");
  }
}
