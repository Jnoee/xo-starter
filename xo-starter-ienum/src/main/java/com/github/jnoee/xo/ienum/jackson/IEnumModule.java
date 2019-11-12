package com.github.jnoee.xo.ienum.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jnoee.xo.ienum.IEnum;
import com.github.jnoee.xo.ienum.IEnumManager;

/**
 * IEnum枚举转换组件。
 */
public class IEnumModule extends SimpleModule {
  private static final long serialVersionUID = 4364404372582339545L;

  /**
   * 构造方法。
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public IEnumModule() {
    super("jackson-datatype-ienum");
    IEnumManager.getIenums().forEach((k, v) -> {
      addSerializer(k, new IEnumSerializer(k));
      addDeserializer(k, new IEnumDeserializer(k));
    });
  }

  /**
   * IEnum枚举序列化。
   */
  class IEnumSerializer<I extends IEnum> extends JsonSerializer<I> {
    private Class<I> enumClass;

    public IEnumSerializer(Class<I> enumClass) {
      this.enumClass = enumClass;
    }

    @Override
    public Class<I> handledType() {
      return enumClass;
    }

    @Override
    public void serialize(I value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException {
      jgen.writeString(value.getValue());
    }
  }

  class IEnumDeserializer<I extends IEnum> extends JsonDeserializer<I> {
    private Class<I> enumClass;

    public IEnumDeserializer(Class<I> enumClass) {
      this.enumClass = enumClass;
    }

    @Override
    public I deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      return IEnumManager.getIEnumByValue(enumClass, p.getValueAsString());
    }
  }
}
