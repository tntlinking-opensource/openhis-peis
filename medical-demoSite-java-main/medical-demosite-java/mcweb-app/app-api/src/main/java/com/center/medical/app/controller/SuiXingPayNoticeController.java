

package com.center.medical.app.controller;

import com.center.medical.app.bean.bo.PayInfoBo;
import com.center.medical.app.common.enums.PayType;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.service.PayService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付回调通知接口
 *
 * @author ay
 * @since 2022-08-05 17:48:28
 */
@Slf4j
@ApiIgnore
@RestController
@RequestMapping("/notice/suiXingPay")
@Api(tags = "随行支付-支付回调通知地址")
@AllArgsConstructor
public class SuiXingPayNoticeController {

    private final PayService payService;

    /**
     * 支付异步回调
     */
    @RequestMapping("/business/order/{payType}")
    public ResponseEntity<String> bsPayNotice(HttpServletRequest request, @PathVariable Integer payType, @RequestBody(required = false) String xmlData) throws Exception {
        log.info("随行支付回调bsPayNotice: {}、 {}", payType, xmlData);
        PayInfoBo payInfoBo = payService.validateAndGetPayInfo(request, PayType.instance(payType), xmlData);
        log.info("随行支付回调bsPayNotice.payInfoBo: {}", Json.toJsonString(payInfoBo));
        if (!payInfoBo.getIsPaySuccess()) {
            return ResponseEntity.ok(null);
        }
        // 根据内部订单号更新order settlement
        payService.paySuccess(payInfoBo.getPayNo(), payInfoBo.getBizPayNo(), payType);

        return ResponseEntity.ok(payInfoBo.getSuccessString());
    }





}
