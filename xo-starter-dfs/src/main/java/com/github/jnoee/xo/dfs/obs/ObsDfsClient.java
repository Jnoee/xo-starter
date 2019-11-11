package com.github.jnoee.xo.dfs.obs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.github.jnoee.xo.dfs.DfsClient;
import com.github.jnoee.xo.exception.SysException;
import com.github.jnoee.xo.utils.CollectionUtils;
import com.github.jnoee.xo.utils.StringUtils;
import com.obs.services.ObsClient;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;

public class ObsDfsClient implements DfsClient {
  @Autowired
  private ObsProperties props;
  private ObsClient obsClient;

  @PostConstruct
  public void init() {
    obsClient =
        new ObsClient(props.getAccessKeyId(), props.getAccessKeySecret(), props.getEndpoint());
  }

  @PreDestroy
  public void destroy() {
    try {
      obsClient.close();
    } catch (IOException e) {
      throw new SysException("关闭Obs客户端时发生异常。", e);
    }
  }

  @Override
  public String upload(File file) {
    return upload(file, null);
  }

  @Override
  public String upload(String path, File file) {
    return upload(path, file, null);
  }

  @Override
  public String upload(File file, Map<String, String> metadataMap) {
    return upload(null, file, metadataMap);
  }

  @Override
  public String upload(String path, File file, Map<String, String> metadataMap) {
    String fileName = genUploadFileName(path, file.getName());
    ObjectMetadata metadata = map2metadata(metadataMap);
    try {
      String md5 = obsClient.base64Md5(new FileInputStream(file));
      metadata.setContentMd5(md5);
      PutObjectResult result =
          obsClient.putObject(props.getDefaultBucketName(), fileName, file, metadata);
      return result.getObjectKey();
    } catch (Exception e) {
      throw new SysException("上传文件时发生异常。", e);
    }
  }

  @Override
  public String upload(MultipartFile file) {
    return upload(file, null);
  }

  @Override
  public String upload(String dir, MultipartFile file) {
    return upload(dir, file, null);
  }

  @Override
  public String upload(MultipartFile file, Map<String, String> metadataMap) {
    return upload(null, file, metadataMap);
  }

  @Override
  public String upload(String path, MultipartFile file, Map<String, String> metadataMap) {
    String fileName = genUploadFileName(path, file.getOriginalFilename());
    ObjectMetadata metadata = map2metadata(metadataMap);
    try (InputStream in = file.getInputStream()) {
      byte[] fileBytes = com.github.jnoee.xo.utils.FileUtils.toByteArray(in);
      String md5 = obsClient.base64Md5(new ByteArrayInputStream(fileBytes));
      metadata.setContentMd5(md5);
      PutObjectResult result = obsClient.putObject(props.getDefaultBucketName(), fileName,
          new ByteArrayInputStream(fileBytes), metadata);
      return result.getObjectKey();
    } catch (Exception e) {
      throw new SysException("上传文件时发生异常。", e);
    }
  }

  @Override
  public byte[] download(String fileName) {
    try {
      ObsObject obsObject = obsClient.getObject(props.getDefaultBucketName(), fileName);
      InputStream in = obsObject.getObjectContent();
      byte[] fileBytes = com.github.jnoee.xo.utils.FileUtils.toByteArray(in);
      ObjectMetadata metadata = obsClient.getObjectMetadata(props.getDefaultBucketName(), fileName);
      String md5 = metadata.getContentMd5();
      String md5Download = obsClient.base64Md5(new ByteArrayInputStream(fileBytes));
      if (!md5.contentEquals(md5Download)) {
        throw new SysException("下载文件时检测MD5码不一致。");
      }
      return fileBytes;
    } catch (SysException e) {
      throw e;
    } catch (Exception e) {
      throw new SysException("下载文件时发生异常。", e);
    }
  }

  @Override
  public File downloadToFile(String fileName) {
    try {
      byte[] bytes = download(fileName);
      String tmpFileName = FileUtils.getTempDirectoryPath() + File.separator + fileName;
      File tmpFile = FileUtils.getFile(tmpFileName);
      FileUtils.writeByteArrayToFile(tmpFile, bytes);
      return tmpFile;
    } catch (SysException e) {
      throw e;
    } catch (IOException e) {
      throw new SysException("下载文件时发生异常。", e);
    }
  }

  @Override
  public void delete(String fileName) {
    obsClient.deleteObject(props.getDefaultBucketName(), fileName);
  }

  @Override
  public Map<String, String> getMetadata(String fileName) {
    ObjectMetadata metadata = obsClient.getObjectMetadata(props.getDefaultBucketName(), fileName);
    return metadata2map(metadata);
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
        fileName = path + "/" + fileName;
      }
    }
    return fileName;
  }

  /**
   * 将Map元数据转换成ObjectMetadata对象。
   * 
   * @param metadata Map元数据
   * @return 返回ObjectMetadata对象。
   */
  private ObjectMetadata map2metadata(Map<String, String> metadataMap) {
    ObjectMetadata metadata = new ObjectMetadata();
    if (CollectionUtils.isNotEmpty(metadataMap)) {
      metadataMap.forEach(metadata::addUserMetadata);
    }
    return metadata;
  }

  /**
   * 将ObjectMetadata对象转换成Map元数据。
   * 
   * @param metadata ObjectMetadata对象
   * @return 返回Map元数据。
   */
  private Map<String, String> metadata2map(ObjectMetadata metadata) {
    Map<String, String> metadataMap = new HashMap<>();
    metadata.getMetadata().forEach((k, v) -> metadataMap.put(k, v.toString()));
    return metadataMap;
  }
}
