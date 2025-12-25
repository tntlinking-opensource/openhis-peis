package com.center.medical.pay.controller;

import com.alibaba.fastjson.JSON;
import com.center.medical.pay.bean.model.PayRequestBody;
import com.center.medical.pay.service.TongLianProxySevice;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ay
 * @date: 2023-08-10 17:04
 * @description: 自助登记机通联支付接口
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "自助登记机通联支付接口")
@RequestMapping("/open/api/tongLianPay")
public class TongLianProxyController {


    private final TongLianProxySevice tongLianService;
    private PayRequestBody requestBody = new PayRequestBody();

    @GetMapping("/createOrder")
    public Object createOrder(String patientCode, String tempIds, String machine_id) {
        try {
            requestBody = tongLianService.createOrder(patientCode, tempIds, machine_id);
            return JSON.toJSON(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("e = " + e);
            return "error";
        }
    }


    @GetMapping("/test")
    public void test() throws Exception {

//        PrivateKey privateKey1 = SmUtil.privKeySM2FromBase64Str(SybConstants.SYB_SM2PPRIVATEKEY);
//        System.out.println("解密信息privateKey1：" + JSONUtil.toJsonStr(privateKey1));
//        testPay();//统一下单，异步类交易
//		testScanPay();//统一扫码，被扫交易
//		testCancel();//撤销
//		testRefund();//退款
//		testQuery();//查询
    }


//    public static void testScanPay() throws Exception {
//        // TODO Auto-generated method stub
//        SybPayService service = new SybPayService();
//        String reqsn = String.valueOf(System.currentTimeMillis());
//        Map<String, String> map = service.scanPay(1, reqsn, "标题", "备注", "134775931316089668", "", "", "", "");
//        print(map);
//    }
//
//    public static void testQuery() throws Exception {
//        SybPayService service = new SybPayService();
//        Map<String, String> map = service.query("", "112094120001088317");
//        print(map);
//    }
//
//    public static void testRefund() throws Exception {
//        SybPayService service = new SybPayService();
//        String reqsn = String.valueOf(System.currentTimeMillis());
//        Map<String, String> map = service.refund(1, reqsn, "", "20160712167578.2547");
//        print(map);
//    }
//
//    public static void testCancel() throws Exception {
//        SybPayService service = new SybPayService();
//        String reqsn = String.valueOf(System.currentTimeMillis());
//        Map<String, String> map = service.cancel(1, reqsn, "112094120001088316", "");
//        print(map);
//    }
//
//    public static void testPay() throws Exception {
//        SybPayService service = new SybPayService();
//        String reqsn = String.valueOf(System.currentTimeMillis());
//        Map<String, String> map = service.pay(1, reqsn, "W01", "标题", "备注", "", "123", "https://test.allinpaygd.com/JWeb/NotifyServlet", "", "", "", "", "", "", "", "", "", "", "", "");
//        print(map);
//    }
//
//    public static void print(Map<String, String> map) {
//        System.out.println("返回数据如下:");
//        if (map != null) {
//            for (String key : map.keySet()) {
//                System.out.println(key + ";" + map.get(key));
//            }
//        }
//    }


}
