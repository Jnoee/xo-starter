package com.github.jnoee.xo.excel;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.github.jnoee.xo.constant.Encoding;
import com.github.jnoee.xo.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Excel视图。
 */
@Slf4j
public class ExcelView extends AbstractXlsxView {
  private String fileName;
  private Excel excel;

  public ExcelView(String fileName, Excel excel) {
    this.fileName = fileName;
    this.excel = excel;
  }

  @Override
  protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
    return excel.getWorkbook();
  }

  @Override
  protected void renderWorkbook(Workbook workbook, HttpServletResponse response)
      throws IOException {
    try {
      ServletOutputStream out = response.getOutputStream();
      workbook.write(out);
    } catch (OpenXML4JRuntimeException e) {
      log.debug("这里触发了POI一个未解决的Bug，不影响导出。");
    }
  }

  @Override
  protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    String excelName = fileName + ".xlsx";
    response.setHeader("content-disposition", "attachment;filename=" + HttpUtils.encode(excelName));
    response.setContentType("application/ms-excel; charset=" + Encoding.UTF8);
    response.setCharacterEncoding(Encoding.UTF8);
  }
}
