package com.github.jnoee.xo.xss.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jnoee.xo.xss.util.XssUtils;

public class XssModule extends SimpleModule {
  private static final long serialVersionUID = -1163964145819125663L;

  public XssModule() {
    super("jackson-datatype-xss");
    addDeserializer(String.class, new XssDeserializer());
    addSerializer(String.class, new XssSerializer());
  }

  class XssSerializer extends JsonSerializer<String> {
    @Override
    public Class<String> handledType() {
      return String.class;
    }

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider arg2)
        throws IOException {
      if (value != null) {
        String encodedValue = XssUtils.clear(value);
        jgen.writeString(encodedValue);
      }
    }
  }

  class XssDeserializer extends StdScalarDeserializer<String> {
    private static final long serialVersionUID = -8377341848196463423L;

    public XssDeserializer() {
      super(String.class);
    }

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      String text = jp.getValueAsString();
      if (text != null) {
        text = XssUtils.clear(text);
      }
      return text;
    }
  }
}
