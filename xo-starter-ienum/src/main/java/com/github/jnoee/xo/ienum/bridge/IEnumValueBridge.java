package com.github.jnoee.xo.ienum.bridge;

import org.hibernate.HibernateException;
import org.hibernate.search.bridge.StringBridge;

import com.github.jnoee.xo.ienum.IEnum;

/**
 * IEnum枚举类型字段对value属性进行全文索引的桥接器。
 */
public class IEnumValueBridge implements StringBridge {
  @Override
  public String objectToString(Object object) {
    if (object == null) {
      return null;
    }
    try {
      return ((IEnum) object).getValue();
    } catch (Exception e) {
      throw new HibernateException("生成枚举" + object.getClass() + "的全文索引时发生异常", e);
    }
  }
}
