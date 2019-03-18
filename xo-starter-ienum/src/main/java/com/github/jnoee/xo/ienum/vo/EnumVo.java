package com.github.jnoee.xo.ienum.vo;

import com.github.jnoee.xo.ienum.IEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 枚举vo
 */
@Data
@AllArgsConstructor
public class EnumVo {

  /** 显示文本 */
  @ApiModelProperty(value = "显示文本")
  private String text;

  /** 枚举值 */
  @ApiModelProperty(value = "值")
  private String value;

  /**
   * 构造值对象
   *
   * @param iEnum ienum
   */
  public EnumVo(IEnum iEnum) {
    setText(iEnum.getText());
    setValue(iEnum.getValue());
  }
}
