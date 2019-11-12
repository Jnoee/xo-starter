package com.github.jnoee.xo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于分页/全文检索的查询条件模型。<br>
 * 更多条件的查询条件模型可继承该类进行定义。
 */
@Getter
@Setter
@ApiModel
public class PageQuery {
  @ApiModelProperty(value = "当前页码", example = "1", position = 1)
  @NotNull
  @Min(1)
  protected Integer pageNum = 1;
  @ApiModelProperty(value = "每页记录数", example = "20", position = 2)
  @NotNull
  @Min(1)
  protected Integer pageSize = 20;
  @ApiModelProperty(value = "排序字段", position = 3)
  protected String orderBy;
  @ApiModelProperty(value = "排序顺序", position = 4)
  protected String sort;
  @ApiModelProperty(value = "模糊搜索关键字", position = 5)
  protected String keyword;
}
