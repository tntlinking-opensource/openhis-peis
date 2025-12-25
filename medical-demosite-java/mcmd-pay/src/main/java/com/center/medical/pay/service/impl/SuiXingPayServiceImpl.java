package com.center.medical.pay.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.SuiXingReverseScanParam;
import com.center.medical.bean.param.SuiXingTradeQueryParam;
import com.center.medical.bean.param.SuiXingTradeRefundParam;
import com.center.medical.common.config.SuiXingConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.pay.dao.TongLianPayMapper;
import com.center.medical.pay.service.SuiXingPayService;
import com.center.medical.pay.utils.ApiRequestBean;
import com.center.medical.pay.utils.HttpUtils;
import com.center.medical.pay.utils.RSASignature;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 随行支付支付接口
 * 详见 https://paas.tianquetech.com/
 * @author ay
 * @since 2024-07-05 09:40:47
 */
@Slf4j
@Service("suiXingPayService")
@RequiredArgsConstructor
public class SuiXingPayServiceImpl extends ServiceImpl<TongLianPayMapper, Peispatient> implements SuiXingPayService {

    private final ISysConfigService iSysConfigService;
    private final Snowflake snowflake;


    private static final String sxfPublic = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjo1+KBcvwDSIo+nMYLeOJ19Ju4ii0xH66ZxFd869EWFWk/EJa3xIA2+4qGf/Ic7m7zi/NHuCnfUtUDmUdP0JfaZiYwn+1Ek7tYAOc1+1GxhzcexSJLyJlR2JLMfEM+rZooW4Ei7q3a8jdTWUNoak/bVPXnLEVLrbIguXABERQ0Ze0X9Fs0y/zkQFg8UjxUN88g2CRfMC6LldHm7UBo+d+WlpOYH7u0OTzoLLiP/04N1cfTgjjtqTBI7qkOGxYs6aBZHG1DJ6WdP+5w+ho91sBTVajsCxAaMoExWQM2ipf/1qGdsWmkZScPflBqg7m0olOD87ymAVP/3Tcbvi34bDfwIDAQAB";

    /**
     * 扫码支付
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> scanPay(SuiXingReverseScanParam param) {

        SuiXingConfig suiXingConfig = iSysConfigService.getSysConfigObject(Constants.SUIXING_CONFIG, SuiXingConfig.class);
        if (ObjectUtils.isEmpty(suiXingConfig)){
            throw new ServiceException("请先维护随行支付配置！");
        }
        String ordNo = param.getOrdNo();
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
            reqData.put("mno", suiXingConfig.getMno()); //商户编号
            //reqData.put("subMechId", ""); //子商户号
            //reqData.put("subAppid", ""); //微信 subAppId
            reqData.put("ordNo", ordNo); //商户订单号
            reqData.put("authCode", param.getAuthCode()); //授权码
            reqData.put("amt", param.getAmt()); //订单总金额
            //reqData.put("discountAmt", ""); //参与优惠金额
            //reqData.put("unDiscountAmt", ""); //不参与优惠金额
            reqData.put("payType", identify(param.getAuthCode())); //支付渠道
            reqData.put("scene", "1"); //支付场景，1： 刷卡 2：声波 3：刷脸   不上传默认为 1
            reqData.put("subject", "体检支付"); //订单标题
            reqData.put("tradeSource", "02"); //交易来源 01服务商，02收银台，03硬件
            reqData.put("trmIp", "127.0.0.1");
            //reqData.put("limitPay", "00"); //限制卡类型: 00-全部 01-限定不能使 用信用卡支付 默认值 00
            //reqData.put("hbFqNum", "6"); //花呗分期数,仅可上送 6 或 12
            //reqData.put("hbFqPercent", "0"); //卖家承担分期 服务费比例,仅支持上送 0 或 100
            //reqData.put("goodsTag", "00"); //订单优惠标识 00：是，01： 否

            //reqData.put("couponDetail", ""); //优惠详情信息，见下面三个字段
            //reqData.put("costPrice", "200"); //订单原价保留两 位小数；微信 独有
            //reqData.put("receiptId ", "123456789"); //商品小票

            //reqData.put("goodsDetail", "123456789"); //单品优惠信息使用 json 数组格式提交
            //reqData.put("goodsId", "200"); //商品编码
            //reqData.put("thirdGoodsId", "12345678"); //微信/支付宝侧商品码
            //reqData.put("goodsName", "苹果电脑"); //商品名称
            //reqData.put("quantity", "1"); //商品数量
            //reqData.put("price", "1.01"); //商品单价
            //reqData.put("goodsCategory", ""); //商品类目；支 付宝独有
            //reqData.put("categoriesTree", "124868003|126232002|126252004"); //商品类目树
            //reqData.put("goodsDesc", ""); //商品描述；支 付宝独有
            //reqData.put("showUrl", ""); //商品展示地址 url；支付宝独有

            //reqData.put("needReceipt", "00"); //电子发票功能 微信开具电子 发票使用
            //reqData.put("ledgerAccountFlag", "00"); //是否做分账 分账交易使 用；00：做； 01：不做；不传默认为不做分账
            //reqData.put("ledgerAccountEffectTime", "00"); //分账有效时间 单位为天；是 否做分账选择 00 时该字段必传
//            reqData.put("notifyUrl", "https://medicalappapi.world.com/notice/suiXingPay/business/order/11"); //回调地址
            //reqData.put("ylTrmNo", ""); //银联终端号
            //reqData.put("terminalId", ""); //TQ机具编号
            //reqData.put("deviceNo ", ""); //设备号
            //reqData.put("identityFlag", ""); //是否是实名支付
            //reqData.put("buyerIdType", "IDCARD"); //证件类型
            //reqData.put("buyerIdNo", "410523198701054018"); //证件号
            //reqData.put("buyerName", "张三"); //买家姓名
            //reqData.put("mobileNum", ""); //手机号
            //reqData.put("extend", ""); //备用

            reqBean.setReqData(reqData);
            String req = JSONObject.toJSONString(reqBean);
            //请求数据json字符串示例
            //String req= "{"orgId":"26680846","reqData":{"ordNo":"145957903824647204","authCode":"28763400000664394","payType":"WECHAT","subject":"B扫C测试","amt":"0.01","tradeSource":"02","trmIp":"172.16.2.1","mno":"399190910000387","scene":"3"},"reqId":"1336c88a4efd40a7b4c4afb691e5ec43","signType":"RSA","timestamp":"2020-04-03 18:05:12","version":"1.0"}";
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
            String url = "https://openapi.tianquetech.com/order/reverseScan";


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
                    return map;
                }else {
                    throw new ServiceException("支付验签失败！");
                }

            }else {
                throw new ServiceException("随行支付失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("随行支付失败！");
        }
    }



    /**
     * 付款码判断逻辑
     * 支付宝：25 ~ 30开头，长度为16 ~ 24位的纯数字，实际字符串长度以开发者获取的付款码长度为准
     * 微信：10 ~ 19开头，长度为18 ~ 19位的纯数字
     * 银联二维码：62开头，长度为19位的纯数字
     * 数字人民币：01开头
     *
     * @param code
     * @return
     */
    public String identify(String code) {
        /**
         * WECHAT 微信
         * ALIPAY 支付宝
         * UNIONPAY 银联
         * DCEP 数字人民币
         * YZF 翼支付
         */
        code = code.trim(); // 去除空格

        if (code.matches("^25\\d{14,}$|^26\\d{14,}$|^27\\d{14,}$|^28\\d{14,}$|^29\\d{14,}$|^30\\d{14,}$")) {
            return "ALIPAY";
        } else if (code.matches("^10\\d{16,}$|^11\\d{16,}$|^12\\d{16,}$|^13\\d{16,}$|^14\\d{16,}$|^15\\d{16,}$|^16\\d{16,}$|^17\\d{16,}$|^18\\d{16,}$|^19\\d{16,}$")) {
            return "WECHAT";
        } else if (code.matches("^62\\d{17,18}$")) {
            return "UNIONPAY";
        } else if (code.startsWith("01") && code.length() == 19) {
            return "DCEP";
        }
        throw new ServiceException("未知的付款码！");
    }

    /**
     * 正交易（主扫，被扫，聚合支付）查询
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> tradeQuery(SuiXingTradeQueryParam param) {
        SuiXingConfig suiXingConfig = iSysConfigService.getSysConfigObject(Constants.SUIXING_CONFIG, SuiXingConfig.class);
        if (ObjectUtils.isEmpty(suiXingConfig)){
            throw new ServiceException("请先维护随行支付配置！");
        }
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
            reqData.put("mno", suiXingConfig.getMno()); //商户编号
            //下面四个至少传一个
            if (StringUtils.isNotEmpty(param.getOrdNo())){
                reqData.put("ordNo", param.getOrdNo()); //商户订单号
            }else if (StringUtils.isNotEmpty(param.getUuid())) {
                reqData.put("uuid", param.getUuid()); //科技公司订单号
            }else if (StringUtils.isNotEmpty(param.getTransactionId())) {
                reqData.put("transactionId", param.getTransactionId()); //支付宝或微信单号
            }else if (StringUtils.isNotEmpty(param.getSxfUuid())) {
                reqData.put("sxfUuid", param.getSxfUuid()); //落单号
            }


            //reqData.put("terminalId", ""); //TQ 机具编号， 支付来源为硬 件时，该参数 为必传；
            //reqData.put("deviceNo", ""); //设备号

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
            String url = "https://openapi.tianquetech.com/query/tradeQuery";


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
                    throw new ServiceException("验签失败！");
                }
            }else {
                throw new ServiceException("结果返回失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询失败！");
        }
    }

    /**
     * 随行支付交易退款
     * @param param
     */
    @Override
    public Map<String, Object> TradeRefund(SuiXingTradeRefundParam param) {
        SuiXingConfig suiXingConfig = iSysConfigService.getSysConfigObject(Constants.SUIXING_CONFIG, SuiXingConfig.class);
        if (ObjectUtils.isEmpty(suiXingConfig)){
            throw new ServiceException("请先维护随行支付配置！");
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

            log.info("随行支付退款返回信息：" + resultJson);
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
                        throw new ServiceException(map.get("bizMsg").toString());
                    }
                    return map;
                }else {
                    throw new ServiceException("验签失败！");
                }
            }else {
                throw new ServiceException("退款失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("退款失败！");
        }
    }
}

