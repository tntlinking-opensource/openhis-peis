

package com.center.medical.app.controller;


import com.center.medical.app.bean.dto.PayInfoDto;
import com.center.medical.app.bean.param.PayParam;
import com.center.medical.app.bean.param.RefundParam;
import com.center.medical.app.common.enums.PayType;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.security.common.service.AppConnectService;
import com.center.medical.app.security.model.YamiUser;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户信息
 *
 * @author LGH
 */
@RestController
@RequestMapping("/api/v1/pay")
@Api(tags = "支付接口")
@AllArgsConstructor
public class PayController {


    private final PayService payService;

    private final AppConnectService appConnectService;



    @PostMapping("/pay")
    @ApiOperation(value = "支付", notes = "支付")
    @SneakyThrows
    public ResponseEntity<?> pay( @Valid @RequestBody PayParam payParam) {
        //2025-03-17 预约小程序暂时关闭购买渠道
        throw new AppBindException("暂不支持线上套餐购买，请咨询线下门店");

//        YamiUser user = SecurityUtils.getUser();
//        if (StringUtils.isEmpty(user.getBizUserId())){
//            throw new AppBindException("请退出登录后重新登录！");
//        }
//        //生成待支付订单
//        PayInfoDto payInfo = payService.createOrder(user.getUserId(),payParam);
//        payInfo.setBizUserId(user.getBizUserId());
//        payInfo.setPayType(payParam.getPayType());
//        payInfo.setReturnUrl(payParam.getReturnUrl());
//        payInfo.setFzxId(payParam.getFzxId());
//        //余额支付
//        if (payInfo.getPayType().equals(PayType.BALANCE.value())) {
//            payInfo.setUserId(user.getUserId());
//        }
//
//        //支付
//        Object wxPayMpOrderResult =  payService.pay(payInfo);
//        if(wxPayMpOrderResult == null){
//            throw new AppBindException("创建订单异常");
//        }else{
////            payService.paySuccess(payInfo.getPayNo(), "", payInfo.getPayType());//测试回调时打开
//            return ResponseEntity.ok(wxPayMpOrderResult);
//        }


    }




    /**
     * <pre>
     * 微信支付-申请退款
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
     * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
     * </pre>
     *
     * @param param 请求对象
     * @return 退款操作结果
     */

    @PostMapping("/refund")
    @ApiOperation(value = "退款", notes = "退款")
    public ResponseEntity refund(@RequestBody RefundParam param) throws Exception {
        //如果不传就默认为小程序
        if (ObjectUtils.isEmpty(param.getPayType())){
            param.setPayType(1);
        }
        return ResponseEntity.ok(payService.refund(param));
    }







}
