package com.github.jnoee.xo.excel;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.jnoee.xo.excel.Excel;

public class BasicSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("department", genDepartments(1).get(0));

    Excel excel = excelFactory.create("basic", model);
    excel.writeTo(new FileOutputStream(outputDir + "/basic_export.xlsx"));
  }
}
