package com.github.jnoee.xo.dfs.local;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.github.jnoee.xo.dfs.DfsClient;
import com.github.jnoee.xo.exception.SysException;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * 本地文件服务客户端组件。
 */
public class LocalDfsClient implements DfsClient {
  @Autowired
  private LocalDfsProperties props;

  @Override
  public String upload(File file) {
    return upload(file, new HashMap<String, String>());
  }

  @Override
  public String upload(String path, File file) {
    return upload(path, file, new HashMap<String, String>());
  }

  @Override
  public String upload(File file, Map<String, String> metadata) {
    return upload(null, file, metadata);
  }

  @Override
  public String upload(String path, File file, Map<String, String> metadata) {
    try {
      String fileName = genUploadFileName(path, file.getName());
      File localFile = new File(getFilePath(fileName));
      FileUtils.copyFile(file, localFile);
      return fileName;
    } catch (IOException e) {
      throw new SysException("上传文件时发生异常。", e);
    }
  }

  @Override
  public String upload(MultipartFile file) {
    return upload(file, new HashMap<String, String>());
  }

  @Override
  public String upload(String path, MultipartFile file) {
    return upload(path, file, new HashMap<String, String>());
  }

  @Override
  public String upload(MultipartFile file, Map<String, String> metadata) {
    return upload(null, file, metadata);
  }

  @Override
  public String upload(String path, MultipartFile file, Map<String, String> metadata) {
    try {
      String fileName = genUploadFileName(path, file.getOriginalFilename());
      File localFile = new File(getFilePath(fileName));
      FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
      return fileName;
    } catch (IOException e) {
      throw new SysException("上传文件时发生异常。", e);
    }
  }

  @Override
  public byte[] download(String fileName) {
    try {
      File file = new File(getFilePath(fileName));
      return FileUtils.readFileToByteArray(file);
    } catch (IOException e) {
      throw new SysException("下载文件时发生异常。", e);
    }
  }

  @Override
  public File downloadToFile(String fileName) {
    return new File(getFilePath(fileName));
  }

  @Override
  public void delete(String fileName) {
    try {
      File file = new File(getFilePath(fileName));
      FileUtils.forceDelete(file);
    } catch (IOException e) {
      throw new SysException("删除文件时发生异常。", e);
    }
  }

  @Override
  public Map<String, String> getMetadata(String filePath) {
    return new HashMap<>();
  }

  /**
   * 生成上传文件名。
   * 
   * @param path 文件路径
   * @param origfileName 原文件名
   * @return 返回上传文件名。
   */
  private String genUploadFileName(String path, String origfileName) {
    String fileName = genUuidFileName(origfileName);
    if (StringUtils.isNotBlank(path)) {
      if (path.contains(".")) {
        fileName = path;
      } else {
        fileName = path + File.separator + fileName;
      }
    }
    return fileName;
  }

  /**
   * 获取文件完整路径。
   * 
   * @param fileName 文件名
   * @return 返回文件完整路径。
   */
  private String getFilePath(String fileName) {
    return props.getLocalDir() + File.separator + fileName;
  }
}
