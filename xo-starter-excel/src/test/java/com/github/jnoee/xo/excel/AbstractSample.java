package com.github.jnoee.xo.excel;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.jnoee.xo.excel.config.ExcelAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ExcelAutoConfiguration.class)
public abstract class AbstractSample {
  protected String outputDir = "src/test/resources/META-INF/xo/export";
  @Autowired
  protected ExcelFactory excelFactory;
}
