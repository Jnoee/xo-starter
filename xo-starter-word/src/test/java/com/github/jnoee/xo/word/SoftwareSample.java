package com.github.jnoee.xo.word;

import java.io.FileOutputStream;

import org.junit.Test;

import com.github.jnoee.xo.word.Word;
import com.github.jnoee.xo.word.model.Software;

public class SoftwareSample extends AbstractSample {
  @Test
  public void test() throws Exception {
    Word excel = wordFactory.create("software", new Software());
    excel.writeTo(new FileOutputStream(outputDir + "/software_export.docx"));
  }
}
