package com.center.medical.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;

import java.util.Map;

/**
 * 通联支付接口
 *
 * @author ay
 * @since 2024-03-21 09:40:47
 */
public interface TongLianPayService extends IService<Peispatient> {


    /**
     * 支付
     * @return
     * @throws Exception
     */
    public Map<String,String> pay(long trxamt, String reqsn, String paytype, String body, String remark, String acct, String validtime, String notify_url, String limit_pay,
                                  String idno, String truename, String asinfo, String sub_appid, String goods_tag, String benefitdetail, String chnlstoreid, String subbranch, String extendparams, String cusip, String fqnum) throws Exception;


    /**
     * 取消
     * @param trxamt
     * @param reqsn
     * @param oldtrxid
     * @param oldreqsn
     * @return
     * @throws Exception
     */
    public Map<String,String> cancel(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception;


    /**
     * 退款
     * @param trxamt
     * @param reqsn
     * @param oldtrxid
     * @param oldreqsn
     * @return
     * @throws Exception
     */
    public Map<String,String> refund(long trxamt,String reqsn,String oldtrxid,String oldreqsn,Integer type) throws Exception;


    /**
     * 查询
     * @param reqsn
     * @param trxid
     * @return
     * @throws Exception
     */
    public Map<String,String> query(String reqsn,String trxid,Integer type) throws Exception;


    /**
     * 扫码支付
     * @param trxamt
     * @param reqsn
     * @param body
     * @param remark
     * @param authcode
     * @param limit_pay
     * @param idno
     * @param truename
     * @param asinfo
     * @return
     * @throws Exception
     */
    public Map<String, String> scanPay(long trxamt,String reqsn,String body,String remark,String authcode,String limit_pay,String idno,String truename,String asinfo,Integer type) throws Exception;


    /**
     * 报备设备
     * @param termno
     * @param devicetype
     * @param operation
     * @param termstate
     * @param termaddress
     * @return
     * @throws Exception
     */
    public Map<String, String> reporting(String termno,String devicetype,String operation,String termstate,String termaddress) throws Exception;

}

