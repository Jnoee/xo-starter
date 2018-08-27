package com.github.jnoee.xo.pay.yee;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.jnoee.xo.constant.Encoding;
import com.github.jnoee.xo.utils.HttpClientUtils;
import com.github.jnoee.xo.utils.HttpUtils;

/**
 * 易宝支付组件。
 */
public class YeePay {
  @Autowired
  private YeePayProperties properties;

  /**
   * 生成支付链接。
   * 
   * @param req 支付请求
   * @return 返回支付链接。
   */
  public String genPayUrl(PayReq req) {
    req.setMerId(properties.getAccout());
    req.setUrl(properties.getReturnUrl());
    req.setServerNotifyUrl(properties.getNotifyUrl());

    String payUrl = properties.getNodeUrl();
    payUrl += "?" + HttpUtils.genParamsStr(req.toSignMap(properties.getKey()), Encoding.GBK);
    return payUrl;
  }

  /**
   * 生成支付响应。
   * 
   * @param queryString 支付回调请求参数字符串
   * @return 返回支付响应。
   */
  public PayRes genPayRes(String queryString) {
    return new PayRes(queryString, properties.getKey());
  }

  /**
   * 查询单笔订单。
   * 
   * @param req 查询订单请求对象
   * @return 返回查询订单响应对象。
   */
  public OrderRes queryOrder(OrderReq req) {
    req.setMerId(properties.getAccout());
    String resStr =
        HttpClientUtils.doGet(properties.getCommandUrl(), req.toSignMap(properties.getKey()));
    return new OrderRes(resStr, properties.getKey());
  }
}
