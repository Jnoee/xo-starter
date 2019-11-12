package com.github.jnoee.xo.ienum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jnoee.xo.exception.SysException;
import com.github.jnoee.xo.ienum.vo.IEnumVo;
import com.github.jnoee.xo.utils.StringUtils;

import lombok.Getter;

/**
 * IEnum枚举管理组件。
 */
public class IEnumManager {
  @Getter
  private static Map<Class<?>, IEnumVo> ienums = new HashMap<>();

  public static void add(Class<?> ienumClass) {
    ienums.put(ienumClass, new IEnumVo(ienumClass));
  }

  /**
   * 根据值获取对应的枚举值。
   * 
   * @param <T> 枚举类型
   * 
   * @param enumClass 枚举类
   * @param value 值
   * @return 返回值对应的枚举值。
   */
  @SuppressWarnings("unchecked")
  public static <T extends IEnum> T getIEnumByValue(Class<T> enumClass, String value) {
    try {
      Method method = enumClass.getMethod("values");
      for (T item : (T[]) method.invoke(enumClass)) {
        if (item.getValue().equals(value)) {
          return item;
        }
      }
    } catch (Exception e) {
      throw new SysException("获取枚举值时发生异常。", e);
    }
    return null;
  }

  /**
   * 根据文本获取对应的枚举值。
   * 
   * @param <T> 枚举类型
   * @param enumClass 枚举类
   * @param text 文本
   * @return 返回文本对应的枚举值。
   */
  @SuppressWarnings("unchecked")
  public static <T extends IEnum> T getIEnumByText(Class<T> enumClass, String text) {
    try {
      Method method = enumClass.getMethod("values");
      for (T item : (T[]) method.invoke(enumClass)) {
        if (item.getText().equals(text)) {
          return item;
        }
      }
    } catch (Exception e) {
      throw new SysException("获取枚举值时发生异常。", e);
    }
    return null;
  }

  /**
   * 获取枚举集合的值字符串。
   * 
   * @param ienums 枚举集合
   * @return 返回枚举集合的值字符串。
   */
  public static String getIEnumValues(Collection<? extends IEnum> ienums) {
    List<String> values = new ArrayList<>();
    for (IEnum ienum : ienums) {
      values.add(ienum.getValue());
    }
    return StringUtils.join(values, ",");
  }

  /**
   * 获取枚举集合的文本字符串。
   * 
   * @param ienums 枚举集合
   * @return 返回枚举集合的文本字符串。
   */
  public static String getIEnumTexts(Collection<? extends IEnum> ienums) {
    List<String> values = new ArrayList<>();
    for (IEnum ienum : ienums) {
      values.add(ienum.getText());
    }
    return StringUtils.join(values, ",");
  }

  private IEnumManager() {}
}
