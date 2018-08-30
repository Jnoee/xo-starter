package com.github.jnoee.xo.excel;

import java.io.FileOutputStream;

import org.junit.Test;

import com.github.jnoee.xo.excel.model.Department;

public class SheetsSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    ExcelData data = new ExcelData();
    data.setTemplateName("sheets");
    data.setSheetModels(Department.multi(3));
    data.setSheetModelName("department");

    Excel excel = excelFactory.create(data);
    excel.writeTo(new FileOutputStream(outputDir + "/sheets_export.xlsx"));
  }
}
