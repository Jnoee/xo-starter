package com.github.jnoee.xo.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Excel数据。
 */
@Data
public class ExcelData {
  /** 模版名称 */
  private String templateName;
  /** 工作表数据模型列表 */
  private List<?> sheetModels;
  /** 工作表名称列表 */
  private List<String> sheetNames = new ArrayList<>();
  /** 工作表名称字段 */
  private String sheetNameField = "name";
  /** 工作表数据模型名称 */
  private String sheetModelName;
  /** 除工作表模型外的数据模型 */
  private Map<String, ?> otherModel;
  /** 多工作表开始位置 */
  private Integer startSheetNum = 0;
}
