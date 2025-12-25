package com.center.medical.pay.controller;

import com.center.medical.bean.param.MicropayParam;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.pay.service.Payservice;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 路飞
 * @date: 2023-04-10 12:15
 * @description: 支付接口
 */
@Slf4j
@RestController("/pay")
@Api(tags = "支付接口")
@RequiredArgsConstructor
public class PayController {

    private final Payservice payservice;

    @RequestMapping("/dopay")
    public WxPayMicropayResult micropay(MicropayParam param) {
        try {
            return payservice.micropay(param);
        } catch (WxPayException e) {
            e.printStackTrace();
            throw new GlobalException("支付失败！");
        }
    }

    @ApiOperation("生成二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeType", value = "类型", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "parameterValue", value = "参数值", dataType = "String", required = true, paramType = "query")
    })
    @PostMapping(value = "/createQrCode")
    public R<String> createQrCode(MicropayParam param) throws WxPayException {
        return R.ok(payservice.createQrCode(param));
    }
}
