package com.github.jnoee.xo.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 日期区间查询条件模型。
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class DateRangeQuery extends PageQuery {
  @ApiModelProperty(value = "起始日期")
  private Date startDate;
  @ApiModelProperty(value = "截止日期")
  private Date endDate;
}
