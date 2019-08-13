package com.github.jnoee.xo.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.jnoee.xo.exception.SysException;
import com.github.jnoee.xo.model.Page;

/**
 * VO、PO工具类。
 */
public class VoUtils {
  /**
   * 将源对象复制成目标对象，忽略源对象中为null的Field。
   * 
   * @param source 源对象
   * @param targetClass 目标对象类
   * @return 返回目标对象。
   */
  public static <T> T copy(Object source, Class<T> targetClass) {
    try {
      T target = targetClass.newInstance();
      BeanUtils.copyFields(source, target);
      return target;
    } catch (Exception e) {
      throw new SysException("复制对象时发生异常。", e);
    }
  }

  /**
   * 将源对象复制成目标对象，忽略源对象中为null的Field。
   * 
   * @param source 源对象
   * @param targetClass 目标对象类
   * @param includeFieldNames 要复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象。
   */
  public static <T> T copyInclude(Object source, Class<T> targetClass, String includeFieldNames) {
    return copyInclude(source, targetClass, includeFieldNames, null);
  }

  /**
   * 将源对象复制成目标对象，忽略源对象中为null的Field。如果指定了要复制null值的Field，则为null时该Field也复制。
   * 
   * @param source 源对象
   * @param targetClass 目标对象类
   * @param includeFieldNames 要复制的Field的名称，多个名称之间用“,”分割
   * @param copyNullFieldNames 要复制null值的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象。
   */
  public static <T> T copyInclude(Object source, Class<T> targetClass, String includeFieldNames,
      String copyNullFieldNames) {
    try {
      T target = targetClass.newInstance();
      BeanUtils.copyFieldsInclude(source, target, includeFieldNames, copyNullFieldNames);
      return target;
    } catch (Exception e) {
      throw new SysException("复制对象时发生异常。", e);
    }
  }

  /**
   * 将源对象复制成目标对象，忽略源对象中为null的Field。
   * 
   * @param source 源对象
   * @param targetClass 目标对象类
   * @param excludeFieldNames 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象。
   */
  public static <T> T copyExclude(Object source, Class<T> targetClass, String excludeFieldNames) {
    return copyExclude(source, targetClass, excludeFieldNames, null);
  }

  /**
   * 将源对象复制成目标对象，忽略源对象中为null的Field。如果指定了要复制null值的Field，则为null时该Field也复制。
   * 
   * @param source 源对象
   * @param targetClass 目标对象类
   * @param excludeFieldNames 不复制的Field的名称，多个名称之间用“,”分割
   * @param copyNullFieldNames 要复制null值的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象。
   */
  public static <T> T copyExclude(Object source, Class<T> targetClass, String excludeFieldNames,
      String copyNullFieldNames) {
    try {
      T target = targetClass.newInstance();
      BeanUtils.copyFieldsExclude(source, target, excludeFieldNames, copyNullFieldNames);
      return target;
    } catch (Exception e) {
      throw new SysException("复制对象时发生异常。", e);
    }
  }

  /**
   * 将源对象列表复制成目标对象列表，忽略源对象中为null的Field。
   * 
   * @param sourceList 源对象列表
   * @param targetClass 目标对象类
   * @return 返回目标对象列表。
   */
  public static <T> List<T> copy(List<?> sourceList, Class<T> targetClass) {
    List<T> targetList = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(sourceList)) {
      for (Object source : sourceList) {
        targetList.add(copy(source, targetClass));
      }
    }
    return targetList;
  }

  /**
   * 将源对象列表复制成目标对象列表，忽略源对象中为null的Field。
   * 
   * @param sourceList 源对象列表
   * @param targetClass 目标对象类
   * @param includeFieldNames 要复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象列表。
   */
  public static <T> List<T> copyInclude(List<?> sourceList, Class<T> targetClass,
      String includeFieldNames) {
    return copyInclude(sourceList, targetClass, includeFieldNames, null);
  }

  /**
   * 将源对象列表复制成目标对象列表，忽略源对象中为null的Field。如果指定了要复制null值的Field，则为null时该Field也复制。
   * 
   * @param sourceList 源对象列表
   * @param targetClass 目标对象类
   * @param includeFieldNames 要复制的Field的名称，多个名称之间用“,”分割
   * @param copyNullFieldNames 要复制null值的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象列表。
   */
  public static <T> List<T> copyInclude(List<?> sourceList, Class<T> targetClass,
      String includeFieldNames, String copyNullFieldNames) {
    List<T> targetList = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(sourceList)) {
      for (Object source : sourceList) {
        targetList.add(copyInclude(source, targetClass, includeFieldNames, copyNullFieldNames));
      }
    }
    return targetList;
  }

  /**
   * 将源对象列表复制成目标对象列表，忽略源对象中为null的Field。
   * 
   * @param sourceList 源对象列表
   * @param targetClass 目标对象类
   * @param excludeFieldNames 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象列表。
   */
  public static <T> List<T> copyExclude(List<?> sourceList, Class<T> targetClass,
      String excludeFieldNames) {
    return copyExclude(sourceList, targetClass, excludeFieldNames, null);
  }

  /**
   * 将源对象列表复制成目标对象列表，忽略源对象中为null的Field。如果指定了要复制null值的Field，则为null时该Field也复制。
   * 
   * @param sourceList 源对象列表
   * @param targetClass 目标对象类
   * @param excludeFieldNames 不复制的Field的名称，多个名称之间用“,”分割
   * @param copyNullFieldNames 要复制null值的Field的名称，多个名称之间用“,”分割
   * @return 返回目标对象列表。
   */
  public static <T> List<T> copyExclude(List<?> sourceList, Class<T> targetClass,
      String excludeFieldNames, String copyNullFieldNames) {
    List<T> targetList = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(sourceList)) {
      for (Object source : sourceList) {
        targetList.add(copyExclude(source, targetClass, excludeFieldNames, copyNullFieldNames));
      }
    }
    return targetList;
  }

  /**
   * 将源分页对象复制成目标分页对象，忽略源对象中为null的Field。
   * 
   * @param sourcePage 源分页对象
   * @param targetClass 目标对象类
   * @return 返回目标分页对象。
   */
  public static <T> Page<T> copy(Page<?> sourcePage, Class<T> targetClass) {
    Page<T> targetPage =
        new Page<>(sourcePage.getCount(), sourcePage.getNumber(), sourcePage.getSize());
    targetPage.setContents(copy(sourcePage.getContents(), targetClass));
    return targetPage;
  }

  /**
   * 将源分页对象复制成目标分页对象，忽略源对象中为null的Field。
   * 
   * @param sourcePage 源分页对象
   * @param targetClass 目标对象类
   * @param includeFieldNames 要复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标分页对象。
   */
  public static <T> Page<T> copyInclude(Page<?> sourcePage, Class<T> targetClass,
      String includeFieldNames) {
    return copyInclude(sourcePage, targetClass, includeFieldNames, null);
  }

  /**
   * 将源分页对象复制成目标分页对象，忽略源对象中为null的Field。如果指定了要复制null值的Field，则为null时该Field也复制。
   * 
   * @param sourcePage 源分页对象
   * @param targetClass 目标对象类
   * @param includeFieldNames 要复制的Field的名称，多个名称之间用“,”分割
   * @param copyNullFieldNames 要复制null值的Field的名称，多个名称之间用“,”分割
   * @return 返回目标分页对象。
   */
  public static <T> Page<T> copyInclude(Page<?> sourcePage, Class<T> targetClass,
      String includeFieldNames, String copyNullFieldNames) {
    Page<T> targetPage =
        new Page<>(sourcePage.getCount(), sourcePage.getNumber(), sourcePage.getSize());
    targetPage.setContents(
        copyInclude(sourcePage.getContents(), targetClass, includeFieldNames, copyNullFieldNames));
    return targetPage;
  }

  /**
   * 将源分页对象复制成目标分页对象，忽略源对象中为null的Field。
   * 
   * @param sourcePage 源分页对象
   * @param targetClass 目标对象类
   * @param excludeFieldNames 不复制的Field的名称，多个名称之间用“,”分割
   * @return 返回目标分页对象。
   */
  public static <T> Page<T> copyExclude(Page<?> sourcePage, Class<T> targetClass,
      String excludeFieldNames) {
    return copyExclude(sourcePage, targetClass, excludeFieldNames, null);
  }

  /**
   * 将源分页对象复制成目标分页对象，忽略源对象中为null的Field。如果指定了要复制null值的Field，则为null时该Field也复制。
   * 
   * @param sourcePage 源分页对象
   * @param targetClass 目标对象类
   * @param excludeFieldNames 不复制的Field的名称，多个名称之间用“,”分割
   * @param copyNullFieldNames 要复制null值的Field的名称，多个名称之间用“,”分割
   * @return 返回目标分页对象。
   */
  public static <T> Page<T> copyExclude(Page<?> sourcePage, Class<T> targetClass,
      String excludeFieldNames, String copyNullFieldNames) {
    Page<T> targetPage =
        new Page<>(sourcePage.getCount(), sourcePage.getNumber(), sourcePage.getSize());
    targetPage.setContents(
        copyExclude(sourcePage.getContents(), targetClass, excludeFieldNames, copyNullFieldNames));
    return targetPage;
  }

  private VoUtils() {}
}
