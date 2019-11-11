package com.github.jnoee.xo.dfs.oss;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.github.jnoee.xo.dfs.DfsClient;
import com.github.jnoee.xo.exception.SysException;
import com.github.jnoee.xo.utils.CollectionUtils;
import com.github.jnoee.xo.utils.StringUtils;

public class OssDfsClient implements DfsClient {
  @Autowired
  private OssProperties props;
  private OSSClient ossClient;

  @PostConstruct
  public void init() {
    CredentialsProvider credentialsProvider =
        new DefaultCredentialProvider(props.getAccessKeyId(), props.getAccessKeySecret());
    ossClient = new OSSClient(props.getEndpoint(), credentialsProvider, null);
  }

  @PreDestroy
  public void destroy() {
    ossClient.shutdown();
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
    ossClient.putObject(props.getDefaultBucketName(), fileName, file, metadata);
    return fileName;
  }

  @Override
  public String upload(MultipartFile file) {
    return upload(file, null);
  }

  @Override
  public String upload(String path, MultipartFile file) {
    return upload(path, file, null);
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
      ossClient.putObject(props.getDefaultBucketName(), fileName, in, metadata);
      return fileName;
    } catch (IOException e) {
      throw new SysException("上传文件时发生异常。", e);
    }
  }

  @Override
  public byte[] download(String fileName) {
    OSSObject ossObject = ossClient.getObject(props.getDefaultBucketName(), fileName);
    return com.github.jnoee.xo.utils.FileUtils.toByteArray(ossObject.getObjectContent());
  }

  @Override
  public File downloadToFile(String fileName) {
    String tmpFileName = FileUtils.getTempDirectoryPath() + File.separator + fileName;
    File tmpFile = FileUtils.getFile(tmpFileName);
    ossClient.getObject(new GetObjectRequest(props.getDefaultBucketName(), fileName), tmpFile);
    return tmpFile;
  }

  @Override
  public void delete(String fileName) {
    ossClient.deleteObject(props.getDefaultBucketName(), fileName);
  }

  @Override
  public Map<String, String> getMetadata(String fileName) {
    ObjectMetadata metadata = ossClient.getObjectMetadata(props.getDefaultBucketName(), fileName);
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
    metadata.getUserMetadata().forEach(metadataMap::put);
    return metadataMap;
  }
}
