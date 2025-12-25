/*
 * Copyright (c) 2022-2999 青岛易筑科技有限公司 All rights reserved.
 *
 * https://www.jixieguanjia.com.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.bo.PayInfoBo;
import com.center.medical.app.bean.dto.PayInfoDto;
import com.center.medical.app.bean.enums.OrderOriginType;
import com.center.medical.app.bean.model.BsSettlement;
import com.center.medical.app.bean.model.SuixingPayConfig;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.*;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.enums.PayType;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.common.util.ApiRequestBean;
import com.center.medical.app.common.util.HttpUtils;
import com.center.medical.app.common.util.RSASignature;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.config.WxConfig;
import com.center.medical.app.dao.BsSettlementMapper;
import com.center.medical.app.service.PayService;
import com.center.medical.app.service.SuixingPayConfigService;
import com.center.medical.app.service.SysConfigService;
import com.center.medical.app.service.UserService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import jodd.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单支付相关的业务实现
 *
 * @author 路飞船长
 * @since 2022-08-05 17:48:28
 */
@Slf4j
@Service("payService")
@AllArgsConstructor
public class PayServiceImpl implements PayService {

    @Resource
    private Snowflake snowflake;
    @Resource
    private BsSettlementMapper settlementMapper;
    @Resource
    private ShopConfig shopConfig;
    @Resource
    private WxConfig wxConfig;
    private final OsZhongKangConfig osZhongKangConfig;
    private static final String WX_SUCCESS_XML = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    private final UserService userService;
    private final SysConfigService sysConfigService;
    private static final String sxfPublic = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjo1+KBcvwDSIo+nMYLeOJ19Ju4ii0xH66ZxFd869EWFWk/EJa3xIA2+4qGf/Ic7m7zi/NHuCnfUtUDmUdP0JfaZiYwn+1Ek7tYAOc1+1GxhzcexSJLyJlR2JLMfEM+rZooW4Ei7q3a8jdTWUNoak/bVPXnLEVLrbIguXABERQ0Ze0X9Fs0y/zkQFg8UjxUN88g2CRfMC6LldHm7UBo+d+WlpOYH7u0OTzoLLiP/04N1cfTgjjtqTBI7qkOGxYs6aBZHG1DJ6WdP+5w+ho91sBTVajsCxAaMoExWQM2ipf/1qGdsWmkZScPflBqg7m0olOD87ymAVP/3Tcbvi34bDfwIDAQAB";

    private final SuixingPayConfigService suixingPayConfigService;
    private static final String address = "https://medicalappapi.world.com";
    /**
     * 生成待支付的订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayInfoDto createOrder(String userId, PayParam payParam) {
        // 支付单号
        String payNo = String.valueOf(snowflake.nextId());
        String orderId = payParam.getOrderId();

        //把之前的未支付的给删掉，再生成一条新的
        settlementMapper.delete(new LambdaQueryWrapper<BsSettlement>()
                .eq(BsSettlement::getUserId, userId)
                .eq(BsSettlement::getOrderId, orderId)
                .eq(BsSettlement::getOrderOrigin, payParam.getOrderOrigin())
                .eq(BsSettlement::getPayStatus, 0)
        );

        BsSettlement bsSettlement = new BsSettlement();
        bsSettlement.setUserId(userId);
        bsSettlement.setIsClearing(0);
        bsSettlement.setCreateTime(new Date());
        bsSettlement.setOrderId(orderId);
        bsSettlement.setPayAmount(payParam.getMoney());//价格
        bsSettlement.setPayStatus(0);
        bsSettlement.setVersion(0);
        bsSettlement.setPayScore((double) 0);
        bsSettlement.setPayNo(payNo);
        bsSettlement.setPayType(payParam.getPayType());
        bsSettlement.setPayTypeName(PayType.getNameByNum(payParam.getPayType()));
        bsSettlement.setFzxId(payParam.getFzxId());
        bsSettlement.setOrderOrigin(payParam.getOrderOrigin());
//        if (payParam.getOrderOrigin() == OrderOriginType.MEAL.value()) {
//            //购买套餐
//            bsSettlement.setOrderOrigin(OrderOriginType.MEAL.value());
//        }


        settlementMapper.insert(bsSettlement);


        PayInfoDto payInfoDto = new PayInfoDto();
        String[] bodyList = {"", "购买套餐"};
        payInfoDto.setBody(bodyList[payParam.getOrderOrigin()]);
        payInfoDto.setPayAmount(bsSettlement.getPayAmount());
        payInfoDto.setPayNo(payNo);
        return payInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(String payNo, String bizPayNo, Integer payType) {
        log.info("支付成功回调，payNo：{}、bizPayNo：{}、payType：{}", payNo, bizPayNo, payType);

        List<BsSettlement> bsSettlements = settlementMapper.selectList(new LambdaQueryWrapper<BsSettlement>().eq(BsSettlement::getPayNo, payNo));
        BsSettlement bsSettlement = bsSettlements.get(0);
        log.info("支付成功回调，bsSettlement：{}", bsSettlement);

        if (Objects.nonNull(bsSettlement.getPayNo())) {
            // 订单已支付
            if (bsSettlement.getPayStatus() == 1) {
                log.info("订单已支付，settlement.id：{}", bsSettlement.getSettlementId());

            } else {
                // 修改订单结算信息
                bsSettlement.setPayStatus(1);
                bsSettlement.setVersion(bsSettlement.getVersion() + 1);
                bsSettlement.setBizPayNo(bizPayNo);
                bsSettlement.setCallbackTime(new Date());
                settlementMapper.updateById(bsSettlement);

                if (bsSettlement.getOrderOrigin() == OrderOriginType.MEAL.value()) {
                    //购买套餐体检系统，生成对应的体检号
                    String code = generateCode(bsSettlement);
                    bsSettlement.setBizOrderNo(code);
                    settlementMapper.updateById(bsSettlement);
                }
            }

        } else {
            throw new AppBindException("支付单号不存在，支付失败");
        }
    }

    /**
     * 发送请求生成对应的体检号
     * @param bsSettlement
     */
    private String generateCode(BsSettlement bsSettlement) {
        User user = userService.getUserByUserId(bsSettlement.getUserId());
        GenerateCodeParam generateCodeParam = new GenerateCodeParam(user.getUserMobile(),bsSettlement.getOrderId(),bsSettlement.getFzxId());
        generateCodeParam.setBizPayNo(bsSettlement.getBizPayNo());
        generateCodeParam.setMoney(bsSettlement.getPayAmount() + bsSettlement.getPayScore());
        generateCodeParam.setSettlementId(bsSettlement.getSettlementId());
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(generateCodeParam));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_CREATEMEALAPP_GENERATECODE_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    String patientCode = String.valueOf(response.getData());
//                    //截取后8位
//                    String result = patientCode.substring(patientCode.length() - 8);
                    log.info("生成体检号成功，{}",patientCode);
                    return patientCode;
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }

    }


    /**
     * 支付
     * @param payInfo
     * @return
     */
    @Override
    public Object pay(PayInfoDto payInfo) throws WxPayException {
        //微信支付不能大于1000w
        if (payInfo.getPayAmount() < 0.01 || payInfo.getPayAmount() > 10000000.00) {
            // 订单金额有误，无法进行支付
            throw new AppBindException("订单金额有误，无法进行支付");
        }
        if (payInfo.getPayType().equals(PayType.WECHATPAY.value())){
            //小程序支付
            WxPayService wxPayService = wxConfig.getWxPayService(PayType.WECHATPAY);
            WxPayUnifiedOrderRequest.WxPayUnifiedOrderRequestBuilder builder = WxPayUnifiedOrderRequest.newBuilder();
            WxPayUnifiedOrderRequest wxOrder = builder
                    .body("购买套餐") //商品描述
                    .openid(payInfo.getBizUserId()) //用户openid
                    .outTradeNo(payInfo.getPayNo())  //商户订单号
                    .spbillCreateIp("0.0.0.0")  //终端IP
                    .totalFee((int) (payInfo.getPayAmount() * 100)) // 支付收款金额 100分  注意：单位（分）
                    .tradeType(WxPayConstants.TradeType.JSAPI).build();
            wxOrder.setSignType("MD5");
            wxOrder.setNotifyUrl(address+"/notice/pay/business/order/" + payInfo.getPayType());//微信支付成功回调地址
            return wxPayService.createOrder(wxOrder);
        }else if (payInfo.getPayType().equals(PayType.SUIXINGPAL.value())){
            // 随行支付
            payInfo.setApiNoticeUrl(address+"/notice/suiXingPay/business/order/" + payInfo.getPayType());
            SuiXingPayParam param = new SuiXingPayParam(payInfo.getPayNo(),payInfo.getPayAmount().toString(),payInfo.getBizUserId(),payInfo.getApiNoticeUrl(),payInfo.getFzxId());
            return suiXingPay(param);
        }else {
            throw new AppBindException("未知的付款方式!");
        }


    }

    /**
     * 随行支付
     * @param param
     * @return
     */
    private Object suiXingPay(SuiXingPayParam param) {
        String ordNo = param.getOrdNo();
        SuixingPayConfig suiXingPay = suixingPayConfigService.getInfoByFzx(param.getFzxId());
        if (ObjectUtils.isEmpty(suiXingPay)){
            throw new AppBindException("请先维护随行支付配置!");
        }
        try {
            //合作方私钥(替换成自己的)
            String privateKey = suiXingPay.getPrivateKey();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String dataStr = df.format(new Date());

            ApiRequestBean<JSONObject> reqBean = new ApiRequestBean<JSONObject>();

            reqBean.setOrgId(suiXingPay.getOrgId());
            reqBean.setReqId(UUID.randomUUID().toString().replaceAll("-", ""));
            reqBean.setSignType("RSA");
            reqBean.setTimestamp(dataStr);
            reqBean.setVersion("1.0");
            JSONObject reqData = new JSONObject();

            //业务参数
            reqData.put("ordNo", ordNo); //商户订单号
            reqData.put("mno", suiXingPay.getMno()); //商户编号
            reqData.put("amt", param.getAmt()); //订单总金额
            reqData.put("appletSource", "01"); //收银台类型，枚举值：00 小程序支付插件
            reqData.put("subject", "购买套餐"); //订单标题
            reqData.put("trmIp", "127.0.0.1");
            reqData.put("notifyUrl", param.getNotifyUrl()); //回调地址


            reqBean.setReqData(reqData);
            String req = JSONObject.toJSONString(reqBean);
            //请求数据json字符串示例
            //String req= "{\"orgId\":\"26680846\",\"reqData\":{\"ordNo\":\"35081904944040745\",\"payType\":\"WECHAT\",\"customerIp\":\"\",\"subject\":\"聚合支付测试\",\"amt\":\"0.01\",\"payWay\":\"02\",\"tradeSource\":\"02\",\"userId\":\"2088101117955611\",\"trmIp\":\"172.16.2.1\",\"mno\":\"399190910000387\"},\"reqId\":\"08794fa24dcb467992a06b126e542be4\",\"signType\":\"RSA\",\"timestamp\":\"2020-04-03 17:51:34\",\"version\":\"1.0\"}";
            System.out.println("req:" + req);
            //此处不要改变reqData里面值的顺序用LinkedHashMap
            HashMap reqMap = JSON.parseObject(req, LinkedHashMap.class, Feature.OrderedField);
            //组装加密串
            String signContent = RSASignature.getOrderContent(reqMap);
            System.out.println("拼接后的参数：" + signContent);
            //sign
            String sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            System.out.println("============签名:" + sign);
            reqMap.put("sign", sign);

            String reqStr = JSON.toJSONString(reqMap);
            System.out.println("请求参数:" + reqMap);
            System.out.println("请求参数:" + reqStr);
            String url = "https://openapi.tianquetech.com/order/appletScanPre";


            String resultJson = HttpUtils.connectPostUrl(url, reqStr);

            System.out.println("返回信息：" + resultJson);
            //不要对reqData排序 所以用LinkedHashMap
            HashMap<String, Object> result = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
            if ("0000".equals(result.get("code"))) {
                //验签
                String signResult = result.get("sign").toString();
                result.remove("sign");

                String resultStr = RSASignature.getOrderContent(result);
                System.out.println(resultStr);
                //sign
                String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
                System.out.println("resultSign:" + resultSign);
                //组装加密串
                if (RSASignature.doCheck(resultStr, signResult, sxfPublic)) {
                    System.out.println("===================验签成功==============");
                    return result;
                }else {
                    throw new AppBindException("验签失败");
                }
            }else {
                throw new AppBindException("支付失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppBindException("支付失败");
        }
    }

    /**
     * 校验支付结果，并返回支付单号
     * @param request
     * @param payType
     * @param xmlData
     * @return
     * @throws WxPayException
     */
    @Override
    public PayInfoBo validateAndGetPayInfo(HttpServletRequest request, PayType payType, String xmlData) throws WxPayException {
        PayInfoBo payInfoBo = new PayInfoBo();
        if (payType.value().equals(PayType.WECHATPAY.value())){
            // 小程序支付
            WxPayService wxPayService = wxConfig.getWxPayService(payType);
            WxPayOrderNotifyResult parseOrderNotifyResult = wxPayService.parseOrderNotifyResult(xmlData);
            payInfoBo.setPayNo(parseOrderNotifyResult.getOutTradeNo());
            payInfoBo.setBizPayNo(parseOrderNotifyResult.getTransactionId());
        }else if (payType.value().equals(PayType.SUIXINGPAL.value())){
            //随行支付
            HashMap<String, Object> result = JSON.parseObject(xmlData, LinkedHashMap.class, Feature.OrderedField);
            //验签
            String signResult = result.get("sign").toString();
            result.remove("sign");
            String resultStr = RSASignature.getOrderContent(result);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, sxfPublic)) {
                log.info("随行支付验签成功");
                payInfoBo.setPayNo(result.get("ordNo").toString());
                payInfoBo.setBizPayNo(result.get("sxfUuid").toString());
            }
        }

        payInfoBo.setIsPaySuccess(true);
        payInfoBo.setSuccessString(WX_SUCCESS_XML);


        return payInfoBo;
    }

    /**
     * 退款
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map refund(RefundParam param){
        BsSettlement bsSettlement = settlementMapper.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(bsSettlement)){
            throw new AppBindException("请确认id是否填写正确");
        }
        if (bsSettlement.getPayStatus() != 1){
            throw new AppBindException("该订单还未支付！");
        }
        if (bsSettlement.getIsClearing() == 1){
            throw new AppBindException("该订单已退款！");
        }
        //查询体检系统体检者状态
        String str = checkPeiStatus(bsSettlement.getBizOrderNo());
        log.info("查询体检者状态:{}",str);
        if (!"true".equals(str)){
            throw new AppBindException(str);
        }

        //执行退款
        Map<String, String> map = new HashMap<>();
        if (1 == param.getPayType()){
            //小程序支付
            map = rebackPay(bsSettlement.getBizPayNo(), bsSettlement.getPayNo(), (int) (bsSettlement.getPayAmount() * 100));

        }else if (11 == param.getPayType()){
            // 随行支付
            SuiXingTradeRefundParam payParam = new SuiXingTradeRefundParam();
            payParam.setAmt(bsSettlement.getPayAmount().toString());//单位是分
            payParam.setOrigOrderNo(bsSettlement.getPayNo());
            payParam.setFzxId(bsSettlement.getFzxId());
            tradeRefund(payParam);
            map.put("status", "SUCCESS");
            // 直接退款成功，不用回调
            bsSettlement.setIsClearing(1);
            bsSettlement.setClearingTime(new Date());
            settlementMapper.updateById(bsSettlement);
            //删除之前生成的体检者数据
            deleteCode(bsSettlement.getBizOrderNo());
        }else {
            throw new AppBindException("未知的付款方式!");
        }

        return map;
    }

    /**
     * 查询体检系统体检者状态
     * @param code
     * @return
     */
    private String checkPeiStatus(String code) {
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(code);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_CREATEMEALAPP_CHECKPEISTATUS_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    log.info("查询体检系统体检者状态，{}",response.getData());
                    return String.valueOf(response.getData());
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }

    }


    /**
     * 删除之前生成的体检者数据
     * @param bizOrderNo
     */
    private void deleteCode(String bizOrderNo) {
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(bizOrderNo);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_CREATEMEALAPP_DELETECODE_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    log.info("删除成功，{}",bizOrderNo);
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }


    }


    /**
     *  退款
     */
    public Map<String, String> rebackPay(String transactionId,String orderNo, Integer amount){
        Map<String, String> returnMap = new HashMap<>();
        //微信支付-申请退款请求参数
        WxPayRefundV3Request request = new WxPayRefundV3Request();
        WxPayRefundV3Request.Amount am = new WxPayRefundV3Request.Amount();
        am.setTotal(amount);//原订单金额
        //退款币种
        am.setCurrency("CNY");
        //退款金额 单位为分
        am.setRefund(amount);
        //金额信息
        request.setAmount(am);
        //transaction_id:微信支付订单号
        request.setTransactionId(transactionId);
        //商户订单号
        request.setOutRefundNo(orderNo);
        request.setNotifyUrl("https://medicalappapi.world.com" + "/notice/pay/refundNotify"); //设置回调地址


        //调用微信V3退款API
        WxPayRefundV3Result result = null;
        try {
            result = wxConfig.getWxPayService(PayType.WECHATPAY).refundV3(request);
        } catch (WxPayException e) {
            log.info(e.getMessage());
            throw new AppBindException(e.getMessage());
        }

        String status = result.getStatus();
        log.info("退款状态：{}",status);
        if ("SUCCESS".equals(status) || "PROCESSING".equals(status)){
            returnMap.put("status", status);
            return returnMap;
        }else {
            throw new AppBindException("退款失败！");
        }
    }

    /**
     * 微信退款结果回调
     * @param xmlData
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundNotify(String xmlData) {
        WxPayService wxPayService = wxConfig.getWxPayService(PayType.WECHATPAY);
        WxPayRefundNotifyV3Result notifyResult = null;
        try {
            notifyResult = wxPayService.parseRefundNotifyV3Result(xmlData,null);
        } catch (WxPayException e) {
            log.info("解析失败！");
            throw new RuntimeException(e);
        }
        if (null != notifyResult && notifyResult.getResult().getRefundStatus().equals("SUCCESS")) {
            System.out.println("微信退款结果回调结果==============>:" + notifyResult.getResult());
            //逻辑代码，修改微信支付订单状态以及其他操作
            WxPayRefundNotifyV3Result.DecryptNotifyResult result = notifyResult.getResult();
            String outTradeNo = result.getOutTradeNo();
            BsSettlement bsSettlement = settlementMapper.selectOne(new LambdaQueryWrapper<BsSettlement>().eq(BsSettlement::getPayNo, outTradeNo));
            if (bsSettlement.getIsClearing() == 1){
                log.info("已成功执行退款回调！");
            }else {
                //修改结算表
                bsSettlement.setIsClearing(1);
                bsSettlement.setClearingTime(new Date());
                settlementMapper.updateById(bsSettlement);

                //删除之前生成的体检者数据
                deleteCode(bsSettlement.getBizOrderNo());
            }
        }else {
            log.info("退款失败！");
        }
    }





    /**
     * 随行支付交易退款
     * @param param
     */
    public Map<String, Object> tradeRefund(SuiXingTradeRefundParam param) {
        SuixingPayConfig suiXingConfig = suixingPayConfigService.getInfoByFzx(param.getFzxId());
        if (ObjectUtils.isEmpty(suiXingConfig)){
            throw new AppBindException("请先维护随行支付配置!");
        }
        String ordNo = snowflake.nextIdStr();
        try {
            //合作方私钥(替换成自己的)
            String privateKey = suiXingConfig.getPrivateKey();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String dataStr = df.format(new Date());

            ApiRequestBean<JSONObject> reqBean = new ApiRequestBean<JSONObject>();

            reqBean.setOrgId(suiXingConfig.getOrgId());
            reqBean.setReqId(UUID.randomUUID().toString().replaceAll("-", ""));
            reqBean.setSignType("RSA");
            reqBean.setTimestamp(dataStr);
            reqBean.setVersion("1.0");
            JSONObject reqData = new JSONObject();

            //业务参数
            reqData.put("ordNo", ordNo); //商户订单号
            reqData.put("mno", suiXingConfig.getMno()); //商户编号
            //下面三个至少传一个
            if (StringUtils.isNotEmpty(param.getOrigOrderNo())){
                reqData.put("origOrderNo", param.getOrigOrderNo()); //原商户订单号
            }else if (StringUtils.isNotEmpty(param.getOrigUuid())){
                reqData.put("origUuid", param.getOrigUuid()); //原交易科技公司订单号
            }else if (StringUtils.isNotEmpty(param.getOrigSxfUuid())){
                reqData.put("origSxfUuid", param.getOrigSxfUuid()); //正交易落单号
            }

            reqData.put("amt", param.getAmt()); //退款金额
            //reqData.put("notifyUrl", ""); //回调推送地址，用来接收科技公司的异步推送
            //reqData.put("refundReason", "退货"); //退货原因
            //reqData.put("extend", ""); //备用

            reqBean.setReqData(reqData);
            String req = JSONObject.toJSONString(reqBean);
            System.out.println("req:" + req);
            //此处不要改变reqData里面值的顺序用LinkedHashMap
            HashMap reqMap = JSON.parseObject(req, LinkedHashMap.class, Feature.OrderedField);
            //组装加密串
            String signContent = RSASignature.getOrderContent(reqMap);
            System.out.println("拼接后的参数：" + signContent);
            //sign
            String sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            System.out.println("============签名:" + sign);
            reqMap.put("sign", sign);

            String reqStr = JSON.toJSONString(reqMap);
            System.out.println("请求参数:" + reqMap);
            System.out.println("请求参数:" + reqStr);
            String url = "https://openapi.tianquetech.com/order/refund";


            String resultJson = HttpUtils.connectPostUrl(url, reqStr);

            System.out.println("返回信息：" + resultJson);
            //不要对reqData排序 所以用LinkedHashMap
            HashMap<String, Object> result = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
            if ("0000".equals(result.get("code"))) {
                //验签
                String signResult = result.get("sign").toString();
                result.remove("sign");

                String resultStr = RSASignature.getOrderContent(result);
                System.out.println(resultStr);
                //sign
                String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
                System.out.println("resultSign:" + resultSign);
                //组装加密串
                if (RSASignature.doCheck(resultStr, signResult, sxfPublic)) {
                    System.out.println("===================验签成功==============");
                    HashMap<String, Object> map = JSON.parseObject(result.get("respData").toString(), LinkedHashMap.class, Feature.OrderedField);
                    /**
                     * REFUNDSUC 退款成功
                     * REFUNDFAIL 退款失败
                     * REFUNDING 退款中
                     * 除了退款失败，其他都认为是成功
                     */
                    if ("REFUNDFAIL".equals(map.get("tranSts"))){
                        log.warn("随行支付退款失败:{}",map.get("bizMsg").toString());
                        throw new AppBindException(map.get("bizMsg").toString());
                    }
                    return map;
                }else {
                    throw new AppBindException("验签失败！");
                }
            }else {
                throw new AppBindException("退款失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppBindException("退款失败！");
        }
    }

    /**
     * 获取跳转路径
     * @return
     */
    @Override
    public String getJumpUrl() {
        String result = null;
        try {
            // 接口调用凭证：accessToken
            String accessToken = wxConfig.getWxMaService().getAccessToken();
            String baseUrl = "https://api.weixin.qq.com/wxa/generate_urllink?access_token="+accessToken;
            HashMap<String, Object> requestParam = new HashMap<>();
            //跳转自己小程序的链接
            requestParam.put("path","");
            //带参 例如：id=1
            requestParam.put("query","");
            String jsonStr = JSONUtil.toJsonStr(requestParam);
            cn.hutool.http.HttpResponse response = HttpRequest.post(baseUrl)
                    .header(Header.CONTENT_ENCODING, "UTF-8")
                    .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                    .body(jsonStr)
                    .execute();
            if (response.getStatus() == HttpStatus.HTTP_OK) {
                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(response.body());
                String url_link = jsonObject.getString("url_link");
                result =url_link;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("生成的跳转小程序的连接是：{}",result);
        return result;
    }
}

