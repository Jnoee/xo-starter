package com.github.jnoee.xo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import com.github.jnoee.xo.constant.Measure;
import com.github.jnoee.xo.exception.SysException;

/**
 * 文件操作工具类。
 */
public class FileUtils {
  private static final int DEFAULT_BUFFER_SIZE = 4 * Measure.K;

  /**
   * 在已有文件名后增加一个以精确到毫秒的当前时间戳生成随机文件名。
   * 
   * @param fileName 已有文件名
   * @return 返回生成的随机文件名。
   */
  public static String getRandomFileName(String fileName) {
    return getFileName(fileName) + "-" + DateUtils.format(new Date(), DateUtils.MILLISECOND_N)
        + getFileType(fileName);
  }

  /**
   * 生成UUID文件名。
   * 
   * @param fileName 原文件名
   * @return 返回UUID文件名。
   */
  public static String getUuidFileName(String fileName) {
    return UUID.randomUUID() + getFileType(fileName);
  }

  /**
   * 转换文件路径。（${user.home}替换成系统目录，斜杠替换成反斜杠。）
   * 
   * @param filePath 文件路径
   * @return 返回转换后的文件路径。
   */
  public static String transformFilePath(String filePath) {
    String path = filePath.replaceAll("$\\{user.home\\}", System.getProperty("user.home"));
    path = path.replaceAll("\\\\", "/");
    return path;
  }

  /**
   * 从带有类型后缀的文件名中获取不带类型后缀的文件名。
   * 
   * @param fileName 带有类型后缀的文件名
   * @return 返回不带类型后缀的文件名。
   */
  public static String getFileName(String fileName) {
    return fileName.substring(0, fileName.lastIndexOf('.'));
  }

  /**
   * 从带有类型后缀的文件名中获取文件名的类型后缀。
   * 
   * @param fileName 带有类型后缀的文件名
   * @return 返回文件名的类型后缀。
   */
  public static String getFileType(String fileName) {
    return fileName.substring(fileName.lastIndexOf('.'));
  }

  /**
   * 从文件完整路径中截取完整的文件名。
   * 
   * @param filePath 文件完整路径
   * @return 返回从文件完整路径中截取完整的文件名。
   */
  public static String getFullFileName(String filePath) {
    String path = transformFilePath(filePath);
    if (path.indexOf('/') == -1) {
      return path;
    } else {
      return StringUtils.substringAfterLast(path, "/");
    }
  }

  /**
   * 从文件完整路径中截取完整的文件目录。
   * 
   * @param filePath 文件完整路径
   * @return 返回从文件完整路径中截取完整的文件目录。
   */
  public static String getFullFileDir(String filePath) {
    String path = transformFilePath(filePath);
    if (path.indexOf('/') == -1) {
      return "";
    } else {
      return StringUtils.substringBeforeLast(path, "/");
    }
  }

  /**
   * 新建文件对象，但不创建文件。
   * 
   * @param filePath 文件完整路径
   * @return 返回File文件对象。
   */
  public static File newFile(String filePath) {
    File fileDir = new File(getFullFileDir(filePath));
    if (!fileDir.exists()) {
      fileDir.mkdirs();
    }
    return new File(filePath);
  }

  /**
   * 根据文件的完整路径创建一个新文件。如果目录不存在时先创建目录再创建文件。
   * 
   * @param filePath 文件完整路径
   * @return 返回创建的File文件对象。
   */
  public static File createFile(String filePath) {
    try {
      File file = newFile(filePath);
      if (file.createNewFile()) {
        return file;
      } else {
        throw new SysException("创建文件时发生错误，可能是文件已存在。");
      }
    } catch (IOException e) {
      throw new SysException("创建文件时发生错误。", e);
    }
  }

  /**
   * 创建目录。
   * 
   * @param dirPath 目录路径
   * @return 返回目录。
   */
  public static File createDir(String dirPath) {
    File dir = new File(dirPath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir;
  }

  /**
   * 将输入流转换为字节数组。
   * 
   * @param in 输入流
   * @return 返回字节数组。
   */
  public static byte[] toByteArray(InputStream in) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      copyInToOut(in, out);
      byte[] bytes = out.toByteArray();
      in.close();
      out.close();
      return bytes;
    } catch (Exception e) {
      throw new SysException("将文件转换成字节数组时发生异常", e);
    }
  }

  /**
   * 将一个文件转化为字节数组
   * 
   * @param file 文件
   * @return 返回字节数组。
   */
  public static byte[] toByteArray(File file) {
    try {
      FileInputStream in = new FileInputStream(file);
      return toByteArray(in);
    } catch (FileNotFoundException e) {
      throw new SysException("将文件转换成字节数组时发生异常", e);
    }
  }

  /**
   * 将输入流复制到输出流。
   * 
   * @param input 输入流
   * @param output 输出流
   */
  public static void copyInToOut(InputStream input, OutputStream output) {
    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    int n = 0;
    try {
      while (-1 != (n = input.read(buffer))) {
        output.write(buffer, 0, n);
      }
    } catch (IOException e) {
      throw new SysException("从输入流复制到输出流时发生异常", e);
    }
  }

  /**
   * 获取文件MD5码。
   * 
   * @param file 文件
   * @return 返回文件MD5码。
   */
  public static String md5(File file) {
    try (FileInputStream in = new FileInputStream(file)) {
      return md5(in);
    } catch (Exception e) {
      throw new SysException("获取文件MD5码时发生异常。", e);
    }
  }

  /**
   * 获取输入流MD5码。
   * 
   * @param in 输入流
   * @return 返回输入流MD5码。
   */
  public static String md5(InputStream in) {
    try {
      return DigestUtils.md5Hex(in);
    } catch (Exception e) {
      throw new SysException("获取文件MD5码时发生异常。", e);
    }
  }

  /**
   * 获取系统临时目录路径。
   * 
   * @return 返回系统临时目录路径。
   */
  public static String getTmpDir() {
    return System.getProperty("java.io.tmpdir");
  }

  /**
   * 创建一个随机的UUID临时目录。
   * 
   * @return 返回随机的UUID临时目录。
   */
  public static File createTmpUuidDir() {
    String dirPath = getTmpDir() + File.separator + UUID.randomUUID();
    return createDir(dirPath);
  }

  /**
   * 创建一个随机的UUID临时文件。
   * 
   * @param fileType 文件类型
   * @return 返回随机的UUID临时文件。
   */
  public static File createTmpUuidFile(String fileType) {
    String fileName = getTmpDir() + File.separator + UUID.randomUUID() + "." + fileType;
    return createFile(fileName);
  }

  /**
   * 新建一个随机的UUID临时文件对象，但不创建文件。
   * 
   * @param fileType 文件类型
   * @return 返回随机的UUID临时文件对象。
   */
  public static File newTmpUuidFile(String fileType) {
    String fileName = getTmpDir() + File.separator + UUID.randomUUID() + "." + fileType;
    return newFile(fileName);
  }

  /**
   * 私有构造方法。
   */
  private FileUtils() {}
}
