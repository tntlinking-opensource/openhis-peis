package com.center.medical.outside.interceptor;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.dto.CooCustomerInfo;
import com.center.medical.common.enums.BsFlag;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.outside.bean.param.BoyingGetPatientInfoAuthInfoParam;
import com.center.medical.outside.config.Constants;
import com.center.medical.system.bean.model.CodeConfig;
import com.center.medical.system.service.CodeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: 路飞船长
 * @date: 2025/6/16 09:36
 * @description:
 */
@Slf4j
@Component
public class OpenApiV4Interceptor extends AbstractSoapInterceptor {
    @Autowired
    private CodeConfigService codeConfigService;

    public OpenApiV4Interceptor() {
        // 在解析协议头之后进行拦截
        super(Phase.PRE_LOGICAL);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

        // 1. 获取当前调用方法名
//        BindingOperationInfo operationInfo = message.getExchange().get(BindingOperationInfo.class);
//        if (operationInfo != null) {
//            String operationName = operationInfo.getName().getLocalPart();
//            log.error("当前调用方法：" + operationName);
//            if ("OutsideLogin".equalsIgnoreCase(operationName)) {
//                // 登录接口跳过校验
//                return;
//            }
//        }

        List<Header> headers = message.getHeaders();
        String token = null;
        String authInfoHeaderStr = null;
        // 查找名为 "Authorization" 的 Header
        for (Header header : headers) {
            if (header.getName().getLocalPart().equalsIgnoreCase("Authorization")) {
                Element element = (Element) header.getObject();
                token = element.getTextContent();
                if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(authInfoHeaderStr)) break;
            }
            if (header.getName().getLocalPart().equalsIgnoreCase("AuthInfo")) {
                Element element = (Element) header.getObject();
                authInfoHeaderStr = element.getTextContent();
                if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(authInfoHeaderStr)) break;
            }
        }
        log.info("7、携带的token={}", token);

        log.info("8、携带的authInfoHeaderStr={}", authInfoHeaderStr);
        BoyingGetPatientInfoAuthInfoParam authInfoHeader = JSONUtil.toBean(authInfoHeaderStr, BoyingGetPatientInfoAuthInfoParam.class);
//        if (StringUtils.isNotBlank(token)) {
//            return;
//        }

        String sourceId = "";
        //  验证签名合法性
        try {

            String authCode = authInfoHeader.getCode();
            String flag = authInfoHeader.getFlag();
            String signature = authInfoHeader.getSignature();

            log.info("1.0、原始请求参数：{}、{}, {}", authCode, flag, signature);

            if (StringUtils.isBlank(signature)) {
                log.warn("1.1、签名参数不能为空！");
                throw createSoapFault("签名参数不能为空！");
            }
            if (StringUtils.isBlank(authCode)) {
                log.warn("1.2、授权码不能为空，authCode={}", authCode);
                throw createSoapFault("授权码不能为空！");
            }
            if (StringUtils.isBlank(flag)) {
                log.warn("1.3、业务标识码不能为空，flag={}", flag);
                throw createSoapFault("业务标识码不能为空！");
            }
            //验证授权码是否有效
            CodeConfig authCodeItem = codeConfigService.getOne(
                    new LambdaQueryWrapper<CodeConfig>()
                            .eq(CodeConfig::getAuthCode, authCode)
                            .eq(CodeConfig::getBsFlag, flag)
                            .eq(CodeConfig::getStatus, 1)
                            .gt(CodeConfig::getExpiryDate, new Date()));
            if (Objects.isNull(authCodeItem)) {
                log.warn("2、授权码查询结果为空！");
                throw createSoapFault("授权已过期！");
            }
            sourceId = authCodeItem.getSourceId();
            log.warn("2.1、验证签名:{}", authCodeItem);
            // TODO: 验证签名
            boolean valid = validateSignature(authCodeItem, signature);
            if (!valid) {
                throw createSoapFault("签名验证失败！");
            }

        } catch (Exception e) {
            log.error("2.2、验证签名异常:{}", e);
            throw new Fault(e);
        }


        //登录令牌验证

        try {
            if (StringUtils.isNotBlank(token)) {
                // 在这里可以对获取到的authorizationHeader进行后续处理，比如验证等操作
                //判断授权是否有效
                CooCustomerInfo authInfo = RedisUtil.get(BsFlag.COO_CUSTOMER + "_ACCESS_TOKEN:" + token);
                log.info("7.1、redis中的的authInfo={}", JSONUtil.toJsonStr(authInfo));
                if (Objects.isNull(authInfo)){
                    log.warn("7.2、token已过期！");
                    throw createSoapFault("token已过期！");
                }
                // 判断授权码是否是登录用户的授权码
                if (!sourceId.equals(authInfo.getSourceId())){
                    log.warn("7.3、授权码不是当前登录用户的授权码！");
                    throw createSoapFault("授权码无效！");
                }
                // 刷新token的有效期
                RedisUtil.set(BsFlag.COO_CUSTOMER+ "_ACCESS_TOKEN:" + token, authInfo, Constants.ACCESS_TOKEN_EXPIRES_TIME);

            } else {
                log.warn("7.4、需授权接口，尚未登录！");
                throw createSoapFault("尚未登录！");
            }
        } catch (SOAPException e) {
            log.error("7.5、SOAP鉴权异常：{}", e);
            throw new RuntimeException("SOAP鉴权异常", e);
        }

    }

    private String getTextContent(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        return (list.getLength() > 0) ? list.item(0).getTextContent() : null;
    }

    private boolean validateSignature(CodeConfig authCodeItem, String signature) {
        // RSA解密：signature=RSAUtil.publicEncrypt(authCode+flag+timeStamp, authCodeItem.getKeyText())
        log.info("3、解密及数据清洗rsaConfig：{}", JSONUtil.toJsonStr(authCodeItem));
        try {
            String decryptData = RSAUtil.privateDecrypt(signature, authCodeItem.getValueText());
            log.info("4、解密及数据清洗，请求参数decryptData：{}", decryptData);
            // 判断decryptData是否以authCode+flag开头，如果是则截取authCode+flag后面的内容赋值给dataStr，然后将dataStr这个时间戳字符串转换为long类型，判断是否在3分钟之内，如果是则返回true，否则返回false
            String prefix = authCodeItem.getAuthCode() + authCodeItem.getBsFlag();
            if (!decryptData.startsWith(prefix)) {
                log.error("4.1、签名不合法，prefix：{}", prefix);
                return false;
            }

            String dataStr = decryptData.substring(prefix.length());
            long timestamp;
            try {
                timestamp = Long.parseLong(dataStr);
                log.info("4.2、时间戳解析成功timestamp：{}", timestamp);
            } catch (NumberFormatException e) {
                log.error("4.3、签名不合法，时间戳解析失败dataStr：{}", dataStr);
                return false;
            }

            long now = System.currentTimeMillis();
            //判断是否时间戳是否在3分钟之内 3分钟 = 180000 毫秒
            if (Math.abs(timestamp - now) <= 180000){
                log.info("5.1、签名正确且有效：{}", timestamp - now);
                return true;
            }else {
                log.error("5.2、签名超时失效：{}", timestamp - now);
                return false;
            }

        }catch (Exception e){
            log.error("6、解密失败：{}", e);
            return false;
        }
    }

    private SOAPFaultException createSoapFault(String message) throws SOAPException {
        SOAPFault fault = SOAPFactory.newInstance().createFault(message, new javax.xml.namespace.QName("Client.Auth"));
        return new SOAPFaultException(fault);
    }
}
