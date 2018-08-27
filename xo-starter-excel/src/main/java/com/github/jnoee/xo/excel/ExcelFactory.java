package com.github.jnoee.xo.excel;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.github.jnoee.xo.excel.config.ExcelProperties;
import com.github.jnoee.xo.exception.SysException;
import com.github.jnoee.xo.utils.BeanUtils;
import com.github.jnoee.xo.utils.ResourceUtils;
import com.github.jnoee.xo.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * Excel组件工厂。
 */
@Slf4j
public class ExcelFactory {
  @Autowired
  private ExcelProperties properties;
  private XLSTransformer transformer = new XLSTransformer();
  private Map<String, Resource> templates = new HashMap<>();

  @PostConstruct
  protected void init() {
    String resourcePath = properties.getTemplateDir() + "/**.xlsx";
    for (Resource resource : ResourceUtils.getResourcesByWildcard(resourcePath)) {
      String templateName = StringUtils.substringBefore(resource.getFilename(), ".xlsx");
      templates.put(templateName, resource);
      log.info("加载Excel模版[{}]。", templateName);
    }
  }

  /**
   * 创建一个单工作表的Excel组件。
   * 
   * @param templateName 模版名称
   * @param model 数据模型
   */
  public Excel create(String templateName, Map<String, ?> model) {
    try {
      InputStream in = templates.get(templateName).getInputStream();
      Excel excel = new Excel(transformer.transformXLS(in, model));
      in.close();
      return excel;
    } catch (Exception e) {
      throw new SysException("创建Excel组件时发生异常。", e);
    }
  }

  /**
   * 创建一个多工作表的Excel组件。
   * 
   * @param data Excel数据
   */
  public Excel create(ExcelData data) {
    try {
      // 如果没有设置工作表名称列表，则根据指定的工作表名称字段自动生成。
      List<String> sheetNames = data.getSheetNames();
      if (sheetNames.isEmpty()) {
        for (Object sheetModel : data.getSheetModels()) {
          sheetNames.add(BeanUtils.getField(sheetModel, data.getSheetNameField()).toString());
        }
      }
      InputStream in = templates.get(data.getTemplateName()).getInputStream();
      Excel excel = new Excel(transformer.transformMultipleSheetsList(in, data.getSheetModels(),
          sheetNames, data.getSheetModelName(), data.getOtherModel(), data.getStartSheetNum()));
      in.close();
      return excel;
    } catch (Exception e) {
      throw new SysException("创建Excel组件时发生异常。", e);
    }
  }
}
