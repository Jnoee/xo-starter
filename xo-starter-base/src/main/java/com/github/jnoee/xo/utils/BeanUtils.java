package com.github.jnoee.xo.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.github.jnoee.xo.exception.SysException;

/**
 * Bean工具类。用于直接操作类、对象的属性或方法。
 */
public class BeanUtils {
  /** 类属性缓存 */
  private static Map<Class<?>, Map<String, Field>> fieldsCache = new ConcurrentHashMap<>();

  /**
   * 获取类中指定名称的属性，支持多层级。
   * 
   * @param targetClass 类
   * @param fieldName 属性名
   * @return 返回对应的属性，如果没找到返回null。
   */
  public static Field findField(Class<?> targetClass, String fieldName) {
    if (fieldName.contains(".")) {
      return findNestedField(targetClass, fieldName);
    } else {
      return findDirectField(targetClass, fieldName);
    }
  }

  /**
   * 获取类中注有指定标注的属性集合。
   * 
   * @param targetClass 类
   * @param annotationClassOnField 标注
   * @return 返回注有指定标注的属性集合。
   */
  public static List<Field> findField(Class<?> targetClass,
      Class<? extends Annotation> annotationClassOnField) {
    List<Field> fields = new ArrayList<>();
    for (Field field : getDeclaredFields(targetClass).values()) {
      if (field.isAnnotationPresent(annotationClassOnField)) {
        fields.add(field);
      }
    }
    return fields;
  }

  /**
   * 获取对象中指定属性的值。
   * 
   * @param target 对象
   * @param field 属性
   * @return 返回对象中指定属性的值。
   */
  public static Object getField(Object target, Field field) {
    if (target == null || field == null) {
      throw new SysException("对象或属性不能为null。");
    }
    try {
      boolean accessible = field.isAccessible();
      field.setAccessible(true);
      Object result = field.get(target);
      field.setAccessible(accessible);
      return processHibernateLazyObject(result);
    } catch (Exception e) {
      throw new SysException("获取对象的属性[" + field.getName() + "]值失败", e);
    }
  }

  /**
   * 获取对象中指定属性的值，支持多层级。
   * 
   * @param target 对象
   * @param fieldName 属性名
   * @return 返回对象中指定属性的值。
   */
  public static Object getField(Object target, String fieldName) {
    if (fieldName.contains(".")) {
      return getNestedField(target, fieldName);
    } else {
      return getDirectField(target, fieldName);
    }
  }

  /**
   * 设置对象中指定属性的值。
   * 
   * @param target 对象
   * @param field 属性
   * @param value 值
   */
  public static void setField(Object target, Field field, Object value) {
    try {
      boolean accessible = field.isAccessible();
      field.setAccessible(true);
      field.set(target, value);
      field.setAccessible(accessible);
    } catch (Exception e) {
      throw new SysException("设置对象的属性[" + field.getName() + "]值失败", e);
    }
  }

  /**
   * 设置对象中指定属性的值，支持多层级。
   * 
   * @param target 对象
   * @param fieldName 属性名
   * @param value 值
   */
  public static void setField(Object target, String fieldName, Object value) {
    if (fieldName.contains(".")) {
      setNestedField(target, fieldName, value);
    } else {
      setDirectField(target, fieldName, value);
    }
  }

  /**
   * 获取指定类所有的公共属性集合。
   * 
   * @param targetClass 目标类
   * @return 返回指定类所有的公共属性集合。
   */
  public static Map<String, Field> getAllDeclaredFields(Class<?> targetClass) {
    if (fieldsCache.containsKey(targetClass)) {
      return fieldsCache.get(targetClass);
    }
    Map<String, Field> fields = new LinkedHashMap<>();
    for (Field field : targetClass.getDeclaredFields()) {
      fields.put(field.getName(), field);
    }
    Class<?> parentClass = targetClass.getSuperclass();
    if (parentClass != Object.class) {
      fields.putAll(getAllDeclaredFields(parentClass));
    }
    fieldsCache.put(targetClass, fields);
    return fields;
  }

  /**
   * 获取指定类非static、final的公共属性列表。
   * 
   * @param targetClass 目标类
   * @return 返回指定类非static、final的公共属性列表。
   */
  public static Map<String, Field> getDeclaredFields(Class<?> targetClass) {
    Map<String, Field> fields = getAllDeclaredFields(targetClass);
    return fields.entrySet().stream()
        .filter(map -> !Modifier.isStatic(map.getValue().getModifiers())
            && !Modifier.isFinal(map.getValue().getModifiers()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * 复制两个对象相同Field的值，忽略源对象中为null的Field。
   * 
   * @param source 源对象
   * @param target 目标对象
   */
  public static void copyFields(Object source, Object target) {
    String includeFieldNames = StringUtils.join(getDeclaredFields(source.getClass()).keySet(), ",");
    copyFieldsInclude(source, target, includeFieldNames);
  }

  /**
   * 复制两个对象相同Field的值，忽略源对象中为null的Field。
   * 
   * @param source 源对象
   * @param target 目标对象
   * @param includeFieldNames 要复制的Field的名称，多个名称之间用“,”分割
   */
  public static void copyFieldsInclude(Object source, Object target, String includeFieldNames) {
    copyFieldsInclude(source, target, includeFieldNames, null);
  }

  /**
   * 复制两个对象相同Field的值，忽略源对象中为null的Field。如果指定了要复制null值的Field，则为null时该Field也复制。
   * 
   * @param source 源对象
   * @param target 目标对象
   * @param includeFieldNames 要复制的Field的名称，多个名称之间用“,”分割
   * @param copyNullFieldNames 要复制null值的Field的名称，多个名称之间用“,”分割
   */
  public static void copyFieldsInclude(Object source, Object target, String includeFieldNames,
      String copyNullFieldNames) {
    checkSourceAndTarget(source, target);
    // 如果源对象是懒加载对象，先处理成非懒加载
    source = processHibernateLazyObject(source);
    List<String> copyNullFields = stringToList(copyNullFieldNames);
    List<String> includeFields = stringToList(includeFieldNames);
    for (String fieldName : includeFields) {
      copyField(source, target, fieldName, copyNullFields.contains(fieldName));
    }
  }

  /**
   * 复制两个对象相同Field的值，忽略源对象中为null的Field。
   * 
   * @param source 源对象
   * @param target 目标对象
   * @param excludeFieldNames 不复制的Field的名称，多个名称之间用“,”分割
   */
  public static void copyFieldsExclude(Object source, Object target, String excludeFieldNames) {
    copyFieldsExclude(source, target, excludeFieldNames, null);
  }

  /**
   * 复制两个对象相同Field的值，忽略源对象中为null的Field。如果指定了要复制null值的Field，则为null时该Field也复制。
   * 
   * @param source 源对象
   * @param target 目标对象
   * @param excludeFieldNames 不复制的Field的名称，多个名称之间用“,”分割
   * @param copyNullFieldNames 要复制null值的Field的名称，多个名称之间用“,”分割
   */
  public static void copyFieldsExclude(Object source, Object target, String excludeFieldNames,
      String copyNullFieldNames) {
    List<String> excludeFields = stringToList(excludeFieldNames);
    String includeFieldNames = getDeclaredFields(source.getClass()).keySet().stream()
        .filter(k -> !excludeFields.contains(k)).collect(Collectors.joining(","));
    copyFieldsInclude(source, target, includeFieldNames, copyNullFieldNames);
  }

  /**
   * 获取Field的类型。
   * 
   * @param field Field
   * @return 返回Field的类型。
   */
  public static Class<?> getFieldType(Field field) {
    Class<?> fieldType = field.getType();
    if (Collection.class.isAssignableFrom(fieldType)) {
      Type fc = field.getGenericType();
      if (fc instanceof ParameterizedType) {
        ParameterizedType pt = (ParameterizedType) fc;
        fieldType = (Class<?>) pt.getActualTypeArguments()[0];
      }
    }
    return fieldType;
  }

  /**
   * 获取泛型Field的泛型类型。
   * 
   * @param field 泛型Field
   * @return 返回泛型Field的泛型类型。
   */
  public static Class<?> getGenericFieldType(Field field) {
    Type type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    if (type instanceof ParameterizedType) {
      return (Class<?>) ((ParameterizedType) type).getRawType();
    } else {
      return (Class<?>) type;
    }
  }

  /**
   * 属性名字符串转换成列表。
   * 
   * @param fieldNames 逗号分隔的属性名字符串
   * @return 返回属性名列表。
   */
  private static List<String> stringToList(String fieldNames) {
    List<String> fields = new ArrayList<>();
    if (!StringUtils.isBlank(fieldNames)) {
      Arrays.stream(fieldNames.split(",")).forEach(fieldName -> fields.add(fieldName.trim()));
    }
    return fields;
  }

  /**
   * 复制两个对象指定Field的值。
   * 
   * @param source 源对象
   * @param target 目标对象
   * @param fieldName Field的名称
   * @param containedNull 是否复制null值
   */
  @SuppressWarnings("unchecked")
  private static void copyField(Object source, Object target, String fieldName,
      Boolean containedNull) {
    Object sourceFieldValue = getField(source, fieldName);
    Boolean needCopy = isFieldNeedCopy(source, target, fieldName);
    if (sourceFieldValue == null && !containedNull) {
      needCopy = false;
    }
    if (needCopy) {
      // 处理Collection类型的属性
      if (sourceFieldValue != null
          && Collection.class.isAssignableFrom(sourceFieldValue.getClass())) {
        if (!((Collection<Object>) sourceFieldValue).isEmpty() || containedNull) {
          CollectionUtils.copy((Collection<Object>) sourceFieldValue,
              (Collection<Object>) getField(target, fieldName));
        }
      } else {
        setField(target, fieldName, sourceFieldValue);
      }
    }
  }

  /**
   * 判断指定的Field是否需要复制。
   * 
   * @param source 源对象
   * @param target 目标对象
   * @param fieldName Field的名称
   * @return 返回指定的Field是否需要复制。
   */
  private static Boolean isFieldNeedCopy(Object source, Object target, String fieldName) {
    try {
      Field sourceField = findField(source.getClass(), fieldName);
      Field targetField = findField(target.getClass(), fieldName);
      return targetField != null && sourceField != null
          && sourceField.getType() == targetField.getType()
          && !Modifier.isFinal(targetField.getModifiers())
          && !Modifier.isStatic(targetField.getModifiers());
    } catch (SysException e) {
      return false;
    }
  }

  /**
   * 获取类中指定名称的单层级属性。
   * 
   * @param targetClass 类
   * @param fieldName 属性名
   * @return 返回对应的属性，如果没找到抛出异常。
   */
  private static Field findDirectField(Class<?> targetClass, String fieldName) {
    Field field = getAllDeclaredFields(targetClass).get(fieldName);
    if (field == null) {
      throw new SysException("类[" + targetClass.getName() + "]中未找到属性[" + fieldName + "]。");
    }
    return field;
  }

  /**
   * 获取类中指定名称的多层级属性。
   * 
   * @param targetClass 类
   * @param fieldName 属性名
   * @return 返回对应的属性，如果没找到返回null。
   */
  private static Field findNestedField(Class<?> targetClass, String fieldName) {
    String[] nestedFieldNames = fieldName.split("\\.");
    Field field = null;
    for (String nestedFieldName : nestedFieldNames) {
      field = findDirectField(targetClass, nestedFieldName);
      targetClass = field.getType();
    }
    return field;
  }

  /**
   * 获取对象中指定单层级属性的值。
   * 
   * @param target 对象
   * @param fieldName 属性名
   * @return 返回对象中指定属性的值。
   */
  private static Object getDirectField(Object target, String fieldName) {
    Field field = findDirectField(target.getClass(), fieldName);
    return getField(target, field);
  }

  /**
   * 获取对象中指定多层级属性的值。
   * 
   * @param target 对象
   * @param fieldName 属性名
   * @return 返回对象中指定属性的值。
   */
  private static Object getNestedField(Object target, String fieldName) {
    String[] nestedFieldNames = fieldName.split("\\.");
    for (String nestedFieldName : nestedFieldNames) {
      target = getDirectField(target, nestedFieldName);
    }
    return target;
  }

  /**
   * 设置对象中指定单层级属性的值。
   * 
   * @param target 对象
   * @param fieldName 属性名
   * @param value 值
   */
  private static void setDirectField(Object target, String fieldName, Object value) {
    Field field = findDirectField(target.getClass(), fieldName);
    setField(target, field, value);
  }

  /**
   * 设置对象中指定多层级属性的值。
   * 
   * @param target 对象
   * @param fieldName 属性名
   * @param value 值
   */
  private static void setNestedField(Object target, String fieldName, Object value) {
    String[] nestedFieldNames = StringUtils.substringBeforeLast(fieldName, ".").split("\\.");
    for (String nestedFieldName : nestedFieldNames) {
      if (target != null) {
        target = getDirectField(target, nestedFieldName);
      } else {
        throw new SysException("未找到多层级属性：" + fieldName);
      }
    }
    setDirectField(target, StringUtils.substringAfterLast(fieldName, "."), value);
  }

  /**
   * 处理Hibernate懒加载对象。
   * 
   * @param lazyObject 懒加载对象
   * @return 如果是Hibernate懒加载对象则执行代理方法返回实际对象，否则直接返回。
   */
  private static Object processHibernateLazyObject(Object lazyObject) {
    try {
      Class<?> hibernateProxyClass = Class.forName("org.hibernate.proxy.HibernateProxy");
      if (hibernateProxyClass.isAssignableFrom(lazyObject.getClass())) {
        Method method = lazyObject.getClass().getMethod("getHibernateLazyInitializer");
        Object lazyInitializer = method.invoke(lazyObject);
        method = lazyInitializer.getClass().getMethod("getImplementation");
        return method.invoke(lazyInitializer);
      } else {
        return lazyObject;
      }
    } catch (Exception e) {
      return lazyObject;
    }
  }

  /**
   * 检查源对象和目标对象。
   * 
   * @param source 源对象
   * @param target 目标对象
   */
  private static void checkSourceAndTarget(Object source, Object target) {
    if (source == null || target == null) {
      throw new SysException("参与复制的源对象和目标对象都不能为null。");
    }
  }

  /**
   * 私有构造方法。
   */
  private BeanUtils() {}
}
