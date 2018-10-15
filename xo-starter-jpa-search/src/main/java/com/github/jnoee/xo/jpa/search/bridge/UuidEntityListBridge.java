package com.github.jnoee.xo.jpa.search.bridge;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.bridge.StringBridge;

import com.github.jnoee.xo.jpa.entity.UuidEntity;
import com.github.jnoee.xo.utils.CollectionUtils;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * UuidEntity列表字段属性全文索引桥接器。
 */
public class UuidEntityListBridge implements StringBridge {
  @Override
  public String objectToString(Object object) {
    @SuppressWarnings("unchecked")
    List<UuidEntity> entities = (List<UuidEntity>) object;
    if (CollectionUtils.isEmpty(entities)) {
      return "";
    }
    List<String> enumValues = new ArrayList<>();
    for (UuidEntity entity : entities) {
      enumValues.add(entity.getId());
    }
    return StringUtils.join(enumValues, " ");
  }
}
