package com.github.jnoee.xo.ienum.usertype;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import com.github.jnoee.xo.ienum.IEnum;
import com.github.jnoee.xo.ienum.IEnumManager;
import com.github.jnoee.xo.jpa.usertype.AbstractUserType;

/**
 * IEnum枚举自定义类型。
 */
public class IEnumUserType extends AbstractUserType {
  @SuppressWarnings("unchecked")
  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session,
      Object owner) throws SQLException {
    try {
      String value = getValue(rs, names[0], session);

      if (value != null) {
        Field enumField = getField(rs, names[0], owner);
        Class<? extends IEnum> enumClass = (Class<? extends IEnum>) enumField.getType();
        return IEnumManager.getIEnumByValue(enumClass, value);
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new HibernateException("转换IEnum枚举类型时发生异常。", e);
    }
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index,
      SharedSessionContractImplementor session) throws SQLException {
    try {
      if (value != null) {
        setValue(st, ((IEnum) value).getValue(), index, session);
      } else {
        setValue(st, null, index, session);
      }
    } catch (Exception e) {
      throw new HibernateException("转换IEnum枚举类型时发生异常。", e);
    }
  }

  @Override
  public Class<?> returnedClass() {
    return IEnum.class;
  }
}
