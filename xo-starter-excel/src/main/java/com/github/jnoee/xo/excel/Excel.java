package com.github.jnoee.xo.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;

import com.github.jnoee.xo.exception.SysException;

import lombok.Getter;

/**
 * Excel组件。
 */
public class Excel {
  @Getter
  private Workbook workbook;

  public Excel(Workbook workbook) {
    this.workbook = workbook;
  }

  /**
   * 转换成Excel文件输出流。
   * 
   * @return 返回Excel文件输出流。
   */
  public ByteArrayOutputStream toOutputStream() {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      workbook.write(out);
      out.flush();
      return out;
    } catch (Exception e) {
      throw new SysException("转换Excel文件输出流时发生异常。", e);
    }
  }

  /**
   * 转换成Excel文件输入流。
   * 
   * @return 返回Excel文件输入流。
   */
  public ByteArrayInputStream toInputStream() {
    try {
      ByteArrayOutputStream out = toOutputStream();
      ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
      out.close();
      return in;
    } catch (Exception e) {
      throw new SysException("转换Excel文件输入流时发生异常。", e);
    }
  }

  /**
   * 将Excel文件写入到输出流。
   * 
   * @param out 输出流
   */
  public void writeTo(OutputStream out) {
    try {
      workbook.write(out);
      out.flush();
      out.close();
    } catch (Exception e) {
      throw new SysException("将Excel文件写入到输出流时发生异常。", e);
    }
  }
}
