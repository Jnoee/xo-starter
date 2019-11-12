package com.github.jnoee.xo.ienum.usertype;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import com.github.jnoee.xo.ienum.IEnum;
import com.github.jnoee.xo.ienum.IEnumManager;
import com.github.jnoee.xo.jpa.usertype.AbstractListUserType;
import com.github.jnoee.xo.utils.BeanUtils;
import com.github.jnoee.xo.utils.CollectionUtils;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * IEnum枚举列表自定义类型。
 */
public class IEnumListUserType extends AbstractListUserType {
  @SuppressWarnings("unchecked")
  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session,
      Object owner) throws SQLException {
    try {
      String value = getValue(rs, names[0], session);
      if (value != null) {
        Field field = getField(rs, names[0], owner);
        Class<? extends IEnum> enumClass =
            (Class<? extends IEnum>) BeanUtils.getGenericFieldType(field);
        List<IEnum> enums = new ArrayList<>();
        for (String enumValue : value.split(",")) {
          enums.add(IEnumManager.getIEnumByValue(enumClass, enumValue));
        }
        return enums;
      } else {
        return new ArrayList<>();
      }
    } catch (Exception e) {
      throw new HibernateException("转换IEnum枚举类型列表时发生异常。", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index,
      SharedSessionContractImplementor session) throws SQLException {
    try {
      List<IEnum> values = (ArrayList<IEnum>) value;
      if (CollectionUtils.isNotEmpty(values)) {
        List<String> enumValues = new ArrayList<>();
        for (IEnum ienum : values) {
          enumValues.add(ienum.getValue());
        }
        setValue(st, StringUtils.join(enumValues, ","), index, session);
      } else {
        setValue(st, null, index, session);
      }
    } catch (Exception e) {
      throw new HibernateException("转换IEnum枚举类型列表时发生异常。", e);
    }
  }
}
