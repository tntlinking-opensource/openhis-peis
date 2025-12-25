package com.center.medical.system.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.webservice.SoapClient;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.KingdeeConstants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.exception.UtilException;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.soap.SOAPElement;
import javax.xml.xpath.XPathConstants;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 金蝶工具类
 *
 * @author xhp
 * @since 2023-05-10 14:20
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class KingdeeUtil {

    private final ISysConfigService iSysConfigService;

    /**
     * 构建相应SOAP请求 无参数
     */
    public String send(String methodName) {
        SoapClient client = createClient(methodName);
        log.info(client.getMsgStr(true));
        String xml = client.send();
        String xpathResult = getXpathResult(methodName);
        return getJsonResultFromXml(xml, xpathResult);
    }

    /**
     * 构建相应SOAP请求 一个参数
     */
    public String send(String methodName, String name, String value) {
        SoapClient client = createClient(methodName, name, value);
        log.info(client.getMsgStr(true));
        String xml = client.send();
        String xpathResult = getXpathResult(methodName);
        return getJsonResultFromXml(xml, xpathResult);
    }

    /**
     * 构建相应SOAP请求 多个参数
     */
    public String send(String methodName, Map<String, String> params) {
        SoapClient client = createClient(methodName, params);
        log.info(client.getMsgStr(true));
        String xml = client.send();
        String xpathResult = getXpathResult(methodName);
        return getJsonResultFromXml(xml, xpathResult);
    }

    /**
     * 构建相应SOAP请求 无参数
     */
    public SoapClient createClient(String methodName) {
        // 新建客户端
        SoapClient client = SoapClient.create(getEndPoint())
                // 设置要请求的方法，此接口方法前缀为web，传入对应的命名空间
                .setMethod(methodName, KingdeeConstants.NAMESPACE_URI);
        return client;
    }

    /**
     * 构建相应SOAP请求 一个参数
     */
    public SoapClient createClient(String methodName, String name, String value) {
        // 新建客户端
        SoapClient client = SoapClient.create(getEndPoint())
                // 设置要请求的方法，此接口方法前缀为web，传入对应的命名空间
                .setMethod(methodName, KingdeeConstants.NAMESPACE_URI)
                // 设置参数，此处自动添加方法的前缀：web
                .setParam(name, value);
        return client;
    }

    /**
     * 构建相应SOAP请求 多个参数
     */
    public SoapClient createClient(String methodName, Map<String, String> params) {
        try {
            SoapClient client = SoapClient.create(getEndPoint())
                    .setMethod(methodName, KingdeeConstants.NAMESPACE_URI);
            SOAPElement arg0 = client.getMethodEle();
            for (String name : params.keySet()) {
                arg0.addChildElement(name, "loc").setValue(params.get(name));
            }
            return client;
        } catch (Exception e) {
            String errorMsg = "创建web service客户端失败";
            log.error(errorMsg, methodName, params, e);
            throw new UtilException(errorMsg, e);
        }
    }

    /**
     * 从响应的xml中读取json结果
     */
    public String getJsonResultFromXml(String xml, String xpathResult) {
        log.info(xml);
        Document document = XmlUtil.parseXml(xml);
        /**
         *<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
         *    <soap:Body>
         *       <soap:Fault>
         *          <faultcode>soap:Server</faultcode>
         *          <faultstring>服务器无法处理请求。 ---> Unexpected character encountered while parsing value: ?. Line 1, position 1.</faultstring>
         *          <detail/>
         *       </soap:Fault>
         *    </soap:Body>
         * </soap:Envelope>
         */
        Node errorNode = (Node) XmlUtil.getByXPath(KingdeeConstants.XPATH_FAULT, document, XPathConstants.NODE);
        if (errorNode != null) {
            String errorXml = XmlUtil.toStr(errorNode);
            String errorMsg = "服务器无法处理请求，请联系金蝶公司技术人员。" + errorXml;
            throw new ServiceException(errorMsg);
        }
        Object obj = XmlUtil.getByXPath(KingdeeConstants.XPATH_BODY + xpathResult
                , document
                , XPathConstants.STRING);
        String result = obj == null ? "" : obj.toString();
        /**
         * 例：{"ResponseStatus":"Error","Reason":"组织不存在！"}
         * 上传银行流水可能存在Success？
         */
        if (
                result.contains(KingdeeConstants.KEY_RESPONSE_STATUS)
                        && !result.contains(KingdeeConstants.SUCCESS_MSG)
        ) {
            throw new ServiceException(result);
        }
        /**
         * 返回json结果
         */
        return result;
    }

    /**
     * 按方法名获取xpath
     */
    public String getXpathResult(String methodName) {
        String result = null;
        switch (methodName) {
            case KingdeeConstants.METHOD_GET_ORGANIZATION:
                result = KingdeeConstants.XPATH_GET_ORGANIZATION;
                break;
            case KingdeeConstants.METHOD_GET_DEPARTMENT:
                result = KingdeeConstants.XPATH_GET_DEPARTMENT;
                break;
            case KingdeeConstants.METHOD_SAVE_OTHER_PAYABLE:
                result = KingdeeConstants.XPATH_SAVE_OTHER_PAYABLE;
                break;
            case KingdeeConstants.METHOD_GET_RECEIVE_BILL:
                result = KingdeeConstants.XPATH_GET_RECEIVE_BILL;
                break;
            case KingdeeConstants.METHOD_UPDATE_FLAG:
                result = KingdeeConstants.XPATH_UPDATE_FLAG;
                break;
            case KingdeeConstants.METHOD_GET_CUSTOMER:
                result = KingdeeConstants.XPATH_GET_CUSTOMER;
                break;
            case KingdeeConstants.METHOD_GET_FYXM:
                result = KingdeeConstants.XPATH_GET_FYXM;
                break;
            case KingdeeConstants.METHOD_EMPINFO:
                result = KingdeeConstants.XPATH_EMPINFO;
                break;
            case KingdeeConstants.METHOD_RECEIVE:
                result = KingdeeConstants.XPATH_RECEIVE;
                break;
        }
        return result;
    }

    /**
     * 获取webservice地址
     *
     * @return
     */
    public String getEndPoint() {
        JSONObject jo = getConfig();
        String endPoint = jo.getStr("endPoint");
        return endPoint;
    }

    /**
     * 获取金蝶配置信息
     *
     * @return
     */
    public JSONObject getConfig() {
        String configJson = iSysConfigService.selectConfigByKey(Constants.KINGDEE_CONFIG);
        JSONObject jo = JSONUtil.parseObj(configJson);
        return jo;
    }

    /**
     * 使用hutool jsonutil getdate 获取yyyy-MM-dd'T'HH:mm:ss格式的日期时，时间会增加8小时
     *
     * @param jo
     * @param key
     * @return
     */
    public Date getDateFromJson(JSONObject jo, String key) {
        if (jo == null) return null;
        String str = jo.getStr(key);
        if (StrUtil.isEmpty(str)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 固定密钥，请勿修改
    final static String encryptKey = "swrj-yxh";
    // 偏移量
    final static byte[] rgbIV = {(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};

    public static String EncryptDES(String encryptString) {
        try {
            String endDes = encrypt(encryptString, encryptKey, rgbIV);
            return endDes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptString;
    }

    private static String encrypt(String message, String key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        //固定秘钥
        DESKeySpec desKeySpec = new DESKeySpec(key.substring(0, 8).getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        //偏移量
        IvParameterSpec ivParameter = new IvParameterSpec(iv);
        //// 用密钥和偏移量初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameter);
        //用base64对加密后的数据进行处理
        byte[] encryptionBase64Bytes = new Base64().encode(cipher.doFinal(message.getBytes("UTF-8")));
        // 转换为字符串返回
        return new String(encryptionBase64Bytes);
    }
}
