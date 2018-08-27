package com.github.jnoee.xo.excel;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.jnoee.xo.excel.Excel;

public class GroupingSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("departments", genDepartments(3));

    Excel excel = excelFactory.create("grouping", model);
    excel.writeTo(new FileOutputStream(outputDir + "/grouping_export.xlsx"));
  }
}
