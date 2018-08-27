package com.github.jnoee.xo.excel;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.jnoee.xo.excel.Excel;

public class ChartSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("employees", genEmployees(6, null));

    Excel excel = excelFactory.create("chart", model);
    excel.writeTo(new FileOutputStream(outputDir + "/chart_export.xlsx"));
  }
}
