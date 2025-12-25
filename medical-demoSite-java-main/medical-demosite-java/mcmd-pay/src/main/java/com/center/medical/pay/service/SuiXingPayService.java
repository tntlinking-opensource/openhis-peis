package com.center.medical.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.SuiXingReverseScanParam;
import com.center.medical.bean.param.SuiXingTradeQueryParam;
import com.center.medical.bean.param.SuiXingTradeRefundParam;

import java.util.Map;

/**
 * 随行支付支付接口
 *
 * @author ay
 * @since 2024-03-21 09:40:47
 */
public interface SuiXingPayService extends IService<Peispatient> {

    /**
     * 扫码支付
     * @param param
     * @return
     */
    Map<String, Object> scanPay(SuiXingReverseScanParam param);


    /**
     * 正交易（主扫，被扫，聚合支付）查询
     * @param param
     * @return
     */
    Map<String, Object> tradeQuery(SuiXingTradeQueryParam param);



    /**
     * 随行支付交易退款
     * @param param
     * @return
     */
    Map<String, Object> TradeRefund(SuiXingTradeRefundParam param);

}

