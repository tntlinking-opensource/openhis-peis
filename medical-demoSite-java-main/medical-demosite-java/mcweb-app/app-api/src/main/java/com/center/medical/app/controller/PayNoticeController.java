/*
 * Copyright (c) 2022-2999 青岛易筑科技有限公司 All rights reserved.
 *
 * https://www.jixieguanjia.com.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.controller;

import com.alipay.api.AlipayApiException;
import com.center.medical.app.bean.bo.PayInfoBo;
import com.center.medical.app.common.enums.PayType;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.service.PayService;
import com.github.binarywang.wxpay.exception.WxPayException;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 支付回调通知接口
 *
 * @author ay
 * @since 2022-08-05 17:48:28
 */
@Slf4j
@ApiIgnore
@RestController
@RequestMapping("/notice/pay")
@Api(tags = "支付-支付回调通知地址")
@AllArgsConstructor
public class PayNoticeController {

    private final PayService payService;

    /**
     * 支付异步回调
     */
    @RequestMapping("/business/order/{payType}")
    public ResponseEntity<String> bsPayNotice(HttpServletRequest request, @PathVariable Integer payType, @RequestBody(required = false) String xmlData) throws WxPayException, UnsupportedEncodingException, AlipayApiException {
        log.info("业务订单支付回调bsPayNotice: {}、 {}", payType, xmlData);
        PayInfoBo payInfoBo = payService.validateAndGetPayInfo(request, PayType.instance(payType), xmlData);
        log.info("业务订单支付回调bsPayNotice.payInfoBo: {}", Json.toJsonString(payInfoBo));
        if (!payInfoBo.getIsPaySuccess()) {
            return ResponseEntity.ok(null);
        }
        // 根据内部订单号更新order settlement
        payService.paySuccess(payInfoBo.getPayNo(), payInfoBo.getBizPayNo(), payType);

        return ResponseEntity.ok(payInfoBo.getSuccessString());
    }



    /**
     * 微信退款结果回调
     * @param
     * @throws Exception
     */
    @PostMapping("/refundNotify")
    void refundNotify(@RequestBody String xmlData) throws Exception{
        log.info("微信退款结果回调: {}", xmlData);
        payService.refundNotify(xmlData);
    }

}
