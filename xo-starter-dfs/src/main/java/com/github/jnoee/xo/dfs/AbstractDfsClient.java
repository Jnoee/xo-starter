package com.github.jnoee.xo.dfs;

import com.github.jnoee.xo.utils.FileUtils;

public abstract class AbstractDfsClient implements DfsClient {
  protected String genUuidFileName(String fileName) {
    return FileUtils.getUuidFileName(fileName).replaceAll("-", "");
  }
}
