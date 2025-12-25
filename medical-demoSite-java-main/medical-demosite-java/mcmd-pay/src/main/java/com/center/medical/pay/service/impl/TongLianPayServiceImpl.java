package com.center.medical.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.Kkfs;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.config.TongLianConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.pay.bean.lib.HttpConnectionUtil;
import com.center.medical.pay.bean.lib.SybUtil;
import com.center.medical.pay.dao.TongLianPayMapper;
import com.center.medical.pay.service.TongLianPayService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * 通联支付接口
 *  详见https://aipboss.allinpay.com/know/devhelp/main.php?pid=15#mid=88
 * @author ay
 * @since 2024-03-21 09:40:47
 */
@Slf4j
@Service("tongLianPayService")
@RequiredArgsConstructor
public class TongLianPayServiceImpl extends ServiceImpl<TongLianPayMapper, Peispatient> implements TongLianPayService {

    private final ISysConfigService iSysConfigService;


    /**
     *
     * @param trxamt
     * @param reqsn
     * @param paytype
     * @param body
     * @param remark
     * @param acct
     * @param validtime
     * @param notify_url
     * @param limit_pay
     * @param idno
     * @param truename
     * @param asinfo
     * @param sub_appid
     * @param goods_tag  单品优惠信息
     * @param chnlstoreid
     * @param subbranch
     * @param cusip   限云闪付JS支付业务
     * @param fqnum   限支付宝分期业务
     * @return
     * @throws Exception
     */
    public Map<String,String> pay(long trxamt, String reqsn, String paytype, String body, String remark, String acct, String validtime, String notify_url, String limit_pay,
                                  String idno, String truename, String asinfo, String sub_appid, String goods_tag, String benefitdetail, String chnlstoreid, String subbranch, String extendparams, String cusip, String fqnum) throws Exception{
        TongLianConfig sybConstants = iSysConfigService.getSysConfigObject(Constants.TONGLIAN_CONFIG, TongLianConfig.class);
        HttpConnectionUtil http = new HttpConnectionUtil(sybConstants.getSYB_APIURL()+"/pay");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        if(!SybUtil.isEmpty(sybConstants.getSYB_ORGID()))
            params.put("orgid", sybConstants.getSYB_ORGID());
        params.put("cusid", sybConstants.getSYB_CUSID());
        params.put("appid", sybConstants.getSYB_APPID());
        params.put("version", "11");
        params.put("trxamt", String.valueOf(trxamt));
        params.put("reqsn", reqsn);
        params.put("paytype", paytype);
        params.put("randomstr", SybUtil.getValidatecode(8));
        params.put("body", body);
        params.put("remark", remark);
        params.put("validtime", validtime);
        params.put("acct", acct);
        params.put("notify_url", notify_url);
        params.put("limit_pay", limit_pay);
        params.put("sub_appid", sub_appid);
        params.put("goods_tag", goods_tag);
        params.put("benefitdetail", benefitdetail);
        params.put("chnlstoreid", chnlstoreid);
        params.put("subbranch", subbranch);
        params.put("extendparams", extendparams);
        params.put("cusip", cusip);
        params.put("fqnum", fqnum);
        params.put("idno", idno);
        params.put("truename", truename);
        params.put("asinfo", asinfo);
        params.put("signtype", sybConstants.getSIGN_TYPE());
        String appkey = "";
        if(sybConstants.getSIGN_TYPE().equals("RSA"))
            appkey = sybConstants.getSYB_RSACUSPRIKEY();
        else if(sybConstants.getSIGN_TYPE().equals("SM2"))
            appkey = sybConstants.getSYB_SM2PPRIVATEKEY();
        else
            appkey = sybConstants.getSYB_MD5_APPKEY();
        params.put("sign", SybUtil.unionSign(params,appkey,sybConstants.getSIGN_TYPE()));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result,sybConstants);
        return map;

    }

    public Map<String,String> cancel(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
        TongLianConfig sybConstants = iSysConfigService.getSysConfigObject(Constants.TONGLIAN_CONFIG, TongLianConfig.class);
        HttpConnectionUtil http = new HttpConnectionUtil(sybConstants.getSYB_APIURL()+"/cancel");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        if(!SybUtil.isEmpty(sybConstants.getSYB_ORGID()))
            params.put("orgid", sybConstants.getSYB_ORGID());
        params.put("cusid", sybConstants.getSYB_CUSID());
        params.put("appid", sybConstants.getSYB_APPID());
        params.put("version", "11");
        params.put("trxamt", String.valueOf(trxamt));
        params.put("reqsn", reqsn);
        params.put("oldtrxid", oldtrxid);
        params.put("oldreqsn", oldreqsn);
        params.put("randomstr", SybUtil.getValidatecode(8));
        params.put("signtype", sybConstants.getSIGN_TYPE());
        String appkey = "";
        if(sybConstants.getSIGN_TYPE().equals("RSA"))
            appkey = sybConstants.getSYB_RSACUSPRIKEY();
        else if(sybConstants.getSIGN_TYPE().equals("SM2"))
            appkey = sybConstants.getSYB_SM2PPRIVATEKEY();
        else
            appkey = sybConstants.getSYB_MD5_APPKEY();
        params.put("sign", SybUtil.unionSign(params,appkey,sybConstants.getSIGN_TYPE()));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result,sybConstants);
        return map;
    }

    public Map<String,String> refund(long trxamt,String reqsn,String oldtrxid,String oldreqsn,Integer type) throws Exception{
        if (ObjectUtils.isEmpty(type) || (type != 9 && type != 11)) throw new Exception("未知的通联支付方式！");
        TongLianConfig sybConstants = iSysConfigService.getSysConfigObject(type == 9 ? Constants.TONGLIAN_CONFIG : Constants.TONGLIAN2_CONFIG, TongLianConfig.class);
        HttpConnectionUtil http = new HttpConnectionUtil(sybConstants.getSYB_APIURL()+"/refund");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        if(!SybUtil.isEmpty(sybConstants.getSYB_ORGID()))
            params.put("orgid", sybConstants.getSYB_ORGID());
        params.put("cusid", sybConstants.getSYB_CUSID());
        params.put("appid", sybConstants.getSYB_APPID());
        params.put("version", "11");
        params.put("trxamt", String.valueOf(trxamt));
        params.put("reqsn", reqsn);
        params.put("oldreqsn", oldreqsn);
        params.put("oldtrxid", oldtrxid);
        params.put("randomstr", SybUtil.getValidatecode(8));
        params.put("signtype", sybConstants.getSIGN_TYPE());
        String appkey = "";
        if(sybConstants.getSIGN_TYPE().equals("RSA"))
            appkey = sybConstants.getSYB_RSACUSPRIKEY();
        else if(sybConstants.getSIGN_TYPE().equals("SM2"))
            appkey = sybConstants.getSYB_SM2PPRIVATEKEY();
        else
            appkey = sybConstants.getSYB_MD5_APPKEY();
        params.put("sign", SybUtil.unionSign(params,appkey,sybConstants.getSIGN_TYPE()));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result,sybConstants);
        return map;
    }

    public Map<String,String> query(String reqsn,String trxid,Integer type) throws Exception{
        if (ObjectUtils.isEmpty(type)) {
            type = Kkfs.TONGLIAN.value();
        }
        if ((type != 9 && type != 11)){throw new Exception("未知的通联支付方式！");}
        TongLianConfig sybConstants = iSysConfigService.getSysConfigObject(type == 9 ? Constants.TONGLIAN_CONFIG : Constants.TONGLIAN2_CONFIG, TongLianConfig.class);
        HttpConnectionUtil http = new HttpConnectionUtil(sybConstants.getSYB_APIURL()+"/query");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        if(!SybUtil.isEmpty(sybConstants.getSYB_ORGID()))
            params.put("orgid", sybConstants.getSYB_ORGID());
        params.put("cusid", sybConstants.getSYB_CUSID());
        params.put("appid", sybConstants.getSYB_APPID());
        params.put("version", "11");
        params.put("reqsn", reqsn);
        params.put("trxid", trxid);
        params.put("randomstr", SybUtil.getValidatecode(8));
        params.put("signtype", sybConstants.getSIGN_TYPE());
        String appkey = "";
        if(sybConstants.getSIGN_TYPE().equals("RSA"))
            appkey = sybConstants.getSYB_RSACUSPRIKEY();
        else if(sybConstants.getSIGN_TYPE().equals("SM2"))
            appkey = sybConstants.getSYB_SM2PPRIVATEKEY();
        else
            appkey = sybConstants.getSYB_MD5_APPKEY();
        params.put("sign", SybUtil.unionSign(params,appkey,sybConstants.getSIGN_TYPE()));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result,sybConstants);
        return map;
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String,String> handleResult(String result,TongLianConfig sybConstants) throws Exception{
        System.out.println("ret:"+result);
        Map map = SybUtil.json2Obj(result, Map.class);
        if(map == null){
            throw new Exception("返回数据错误");
        }
        if("SUCCESS".equals(map.get("retcode"))){
            TreeMap tmap = new TreeMap();
            tmap.putAll(map);
            String appkey = "";
            if(sybConstants.getSIGN_TYPE().equals("RSA"))
                appkey = sybConstants.getSYB_RSATLPUBKEY();
            else if(sybConstants.getSIGN_TYPE().equals("SM2"))
                appkey = sybConstants.getSYB_SM2TLPUBKEY();
            else
                appkey = sybConstants.getSYB_MD5_APPKEY();
            if(SybUtil.validSign(tmap, appkey, sybConstants.getSIGN_TYPE())){
                System.out.println("签名成功");
                return map;
            }else{
                throw new Exception("验证签名失败");
            }

        }else{
            throw new Exception(map.get("retmsg").toString());
        }
    }

    public Map<String, String> scanPay(long trxamt,String reqsn,String body,String remark,String authcode,String limit_pay,String idno,String truename,String asinfo,Integer type) throws Exception{
        // TODO Auto-generated method stub
        if (ObjectUtils.isEmpty(type) || (type != 9 && type != 11)){throw new Exception("未知的通联支付方式！");}
        TongLianConfig sybConstants = iSysConfigService.getSysConfigObject(type == 9 ? Constants.TONGLIAN_CONFIG : Constants.TONGLIAN2_CONFIG, TongLianConfig.class);
        HttpConnectionUtil http = new HttpConnectionUtil(sybConstants.getSYB_APIURL()+"/scanqrpay");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        if(!SybUtil.isEmpty(sybConstants.getSYB_ORGID()))
            params.put("orgid", sybConstants.getSYB_ORGID());
        params.put("cusid", sybConstants.getSYB_CUSID());
        params.put("appid", sybConstants.getSYB_APPID());
        params.put("version", "11");
        params.put("trxamt", String.valueOf(trxamt));
        params.put("reqsn", reqsn);
        params.put("randomstr", SybUtil.getValidatecode(8));
        params.put("body", body);
        params.put("remark", remark);
        params.put("authcode", authcode);
        params.put("limit_pay", limit_pay);
        params.put("asinfo", asinfo);
        params.put("signtype", sybConstants.getSIGN_TYPE());
        params.put("terminfo", sybConstants.getTERMINFO());
        String appkey = "";
        if(sybConstants.getSIGN_TYPE().equals("RSA"))
            appkey = sybConstants.getSYB_RSACUSPRIKEY();
        else if(sybConstants.getSIGN_TYPE().equals("SM2"))
            appkey = sybConstants.getSYB_SM2PPRIVATEKEY();
        else
            appkey = sybConstants.getSYB_MD5_APPKEY();
        params.put("sign", SybUtil.unionSign(params,appkey,sybConstants.getSIGN_TYPE()));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result,sybConstants);
        return map;
    }



    public Map<String, String> reporting(String termno,String devicetype,String operation,String termstate,String termaddress) throws Exception{
        //报备设备 终端信息采集接口
        //https://aipboss.allinpay.com/know/devhelp/main.php?pid=15#mid=948
        TongLianConfig sybConstants = iSysConfigService.getSysConfigObject(Constants.TONGLIAN_CONFIG, TongLianConfig.class);
        HttpConnectionUtil http = new HttpConnectionUtil("https://vsp.allinpay.com/cusapi/merchantapi/addterm");

        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("cusid", sybConstants.getSYB_CUSID());
        params.put("appid", sybConstants.getSYB_APPID());
        params.put("termno", termno);//终端号
        params.put("devicetype", devicetype);//设备类型
        params.put("operation", operation);//操作类型
        params.put("termstate", termstate);//终端状态
        params.put("termaddress", termaddress);//终端地址



        params.put("signtype", sybConstants.getSIGN_TYPE());
        String appkey = "";
        if(sybConstants.getSIGN_TYPE().equals("RSA"))
            appkey = sybConstants.getSYB_RSACUSPRIKEY();
        else if(sybConstants.getSIGN_TYPE().equals("SM2"))
            appkey = sybConstants.getSYB_SM2PPRIVATEKEY();
        else
            appkey = sybConstants.getSYB_MD5_APPKEY();
        params.put("sign", SybUtil.unionSign(params,appkey,sybConstants.getSIGN_TYPE()));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result,sybConstants);
        return map;
    }

}

