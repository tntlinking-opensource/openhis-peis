package com.center.medical.pay.service;

import com.center.medical.pay.bean.model.PayRequestBody;

/**
 * 自助登记机通联支付接口
 *
 * @param
 * @return
 */
public interface TongLianProxySevice {


    /**
     * 自助登记机通联支付接口
     *
     * @param patientCode
     * @param tempIds
     * @param machine_id
     * @return
     */
    PayRequestBody createOrder(String patientCode, String tempIds, String machine_id) throws Exception;
}
