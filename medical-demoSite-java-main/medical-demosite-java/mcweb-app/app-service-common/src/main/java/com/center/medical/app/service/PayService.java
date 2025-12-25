package com.center.medical.app.service;

import com.center.medical.app.bean.bo.PayInfoBo;
import com.center.medical.app.bean.dto.PayInfoDto;
import com.center.medical.app.bean.param.PayParam;
import com.center.medical.app.bean.param.RefundParam;
import com.center.medical.app.common.enums.PayType;
import com.github.binarywang.wxpay.exception.WxPayException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: 路飞船长
 * @date: 2022/8/17 17:35
 * @description: 订单支付业务逻辑接口
 */
public interface PayService {
    /**
     * 生成待支付的订单
     *
     * @param userId   用户id
     * @param payParam 支付参数
     * @return 支付信息
     */
    PayInfoDto createOrder(String userId, PayParam payParam);

    /**
     * 支付成功回调
     *
     * @param payNo    支付单号
     * @param bizPayNo 第三方支付单号
     * @param payType  支付方式
     * @return 订单信息
     */
    void paySuccess(String payNo, String bizPayNo, Integer payType);

    /**
     * 支付
     * @param payInfo
     * @return
     */
    Object pay(PayInfoDto payInfo) throws WxPayException;

    /**
     *  校验支付结果，并返回支付单号
     * @param request
     * @param instance
     * @param xmlData
     * @return
     */
    PayInfoBo validateAndGetPayInfo(HttpServletRequest request, PayType instance, String xmlData) throws WxPayException;

    /**
     * 退款
     * @param param
     * @return
     */
    Map refund(RefundParam param) throws Exception;

    /**
     * 微信退款结果回调
     * @param xmlData
     */
    void refundNotify(String xmlData);

    /**
     * 获取跳转路径
     * @return
     */
    String getJumpUrl();
}
