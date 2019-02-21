package com.github.jnoee.xo.jpa.usertype;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import com.github.jnoee.xo.jpa.entity.IdEntity;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * IdEntity列表自定义类型。
 */
public class IdEntityListUserType extends AbstractListUserType {
  @Override
  @SuppressWarnings("unchecked")
  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session,
      Object owner) throws SQLException {
    try {
      String value = getValue(rs, names[0], session);

      if (StringUtils.isNotBlank(value)) {
        Field idEntityListField = getField(rs, names[0], owner);
        ParameterizedType parameterizedType =
            (ParameterizedType) idEntityListField.getGenericType();
        Class<? extends IdEntity> idEntityClass =
            (Class<? extends IdEntity>) parameterizedType.getActualTypeArguments()[0];
        List<Object> entities = new ArrayList<>();
        for (String entityId : value.split(",")) {
          entities.add(((Session) session).get(idEntityClass, Long.parseLong(entityId)));
        }
        return entities;
      } else {
        return new ArrayList<IdEntity>();
      }
    } catch (Exception e) {
      throw new HibernateException("转换IdEntity列表类型时发生异常。", e);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void nullSafeSet(PreparedStatement st, Object value, int index,
      SharedSessionContractImplementor session) throws SQLException {
    try {
      if (value != null) {
        List<IdEntity> entities = (List<IdEntity>) value;
        List<String> idEntityIds = new ArrayList<>();
        for (IdEntity entity : entities) {
          idEntityIds.add(entity.getId().toString());
        }
        if (!idEntityIds.isEmpty()) {
          setValue(st, StringUtils.join(idEntityIds, ","), index, session);
        } else {
          setValue(st, null, index, session);
        }
      } else {
        setValue(st, null, index, session);
      }
    } catch (Exception e) {
      throw new HibernateException("转换IdEntity列表类型时发生异常。", e);
    }
  }
}
