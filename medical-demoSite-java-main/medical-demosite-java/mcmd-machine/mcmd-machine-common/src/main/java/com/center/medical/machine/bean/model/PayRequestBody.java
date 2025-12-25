package com.center.medical.machine.bean.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

/**
 * @program: mecreg
 * @description:
 * @author: yuane
 * @create: 2020-07-08 14:16
 */
public class PayRequestBody {
    public static final String PRODUCTIVE_URL = "https://syb.allinpay.com/sappweb/usertrans/cuspay";
    public static final String PRODUCTIVE_QUERY_URL = "https://vsp.allinpay.com/apiweb/tranx/queryorder";
    private final String resendnotify = "1";
    private String appid;
    private String c;
    private String oid;
    private String amt;
    private String returl;
    private String trxreserve;
    private String signtype = "MD5";
    private String sign;
    private String product;
    private String key;
    private String img;
    private String ids;
    private String cusid;
    private String orderid;
    private String randomstr;
    private String price;

    private void yuanToFen() {
        if (NumberUtils.isNumber(amt)) {
            this.price = String.valueOf(new BigDecimal(amt).multiply(new BigDecimal(100)).intValue());
        } else {
            throw new IllegalArgumentException("错误类型，不是数字");
        }
    }

    private void randomstr() {
        this.randomstr = RandomStringUtils.randomAlphabetic(5);
    }

    private void md5PaySign() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("appid", appid);
        treeMap.put("c", c);
        treeMap.put("oid", oid);
        treeMap.put("amt", price);
//        treeMap.put("trxreserve", "trxreserve");
//        treeMap.put("trxreserve", "");
        treeMap.put("returl", returl);
        treeMap.put("key", key);
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        String signSrc = stringBuilder.substring(0, stringBuilder.lastIndexOf("&"));
        System.out.println("signSrc = " + signSrc);
        String sign = DigestUtils.md5Hex(signSrc).toUpperCase();
        System.out.println("sign = " + sign);
        this.sign = sign;
    }

    private void md5QuerySign() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("appid", appid);
        treeMap.put("cusid", cusid);
        treeMap.put("orderid", orderid);
        treeMap.put("randomstr", randomstr);
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        String signSrc = stringBuilder.substring(0, stringBuilder.lastIndexOf("&"));
        System.out.println("signSrc = " + signSrc);
        String sign = DigestUtils.md5Hex(signSrc).toUpperCase();
        System.out.println("sign = " + sign);
        this.sign = sign;
    }

    public void trxreserveCreate() {
        this.trxreserve = "业务类型(05)|Q7#" + product;
    }


    private String getPayUrlEncode() throws UnsupportedEncodingException {
        yuanToFen();
        trxreserveCreate();
        md5PaySign();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        stringBuilder.append("appid=" + appid + "&");
        stringBuilder.append("c=" + c + "&");
        stringBuilder.append("oid=" + oid + "&");
        stringBuilder.append("amt=" + price + "&");
        stringBuilder.append("returl=" + returl + "&");
//        stringBuilder.append("trxreserve=" + URLEncoder.encode(trxreserve, "GBK") + "&");
//        stringBuilder.append("trxreserve=" + "" + "&");
//        stringBuilder.append("signtype=" + signtype + "&");
        stringBuilder.append("sign=" + sign + "&");
        return stringBuilder.substring(0, stringBuilder.lastIndexOf("&"));
    }

    private String getQueryUrlEncode() throws UnsupportedEncodingException {
        randomstr();
        md5QuerySign();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        stringBuilder.append("cusid=" + cusid + "&");
        stringBuilder.append("appid=" + appid + "&");
        stringBuilder.append("orderid=" + orderid + "&");
        stringBuilder.append("randomstr=" + randomstr + "&");
        stringBuilder.append("signtype=" + signtype + "&");
        stringBuilder.append("sign=" + sign + "&");
        return stringBuilder.substring(0, stringBuilder.lastIndexOf("&"));
    }

    public String getPayProductiveUrl() {
        try {
            return PRODUCTIVE_URL + getPayUrlEncode();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getQueryProductiveUrl() {
        try {
            return PRODUCTIVE_QUERY_URL + getQueryUrlEncode();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getReturl() {
        return returl;
    }

    public void setReturl(String returl) {
        this.returl = returl;
    }

    public String getTrxreserve() {
        return trxreserve;
    }

    public void setTrxreserve(String trxreserve) {
        this.trxreserve = trxreserve;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    public String getSign() {
        return sign;
    }


    public void setSign(String sign) {
        this.sign = sign;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}