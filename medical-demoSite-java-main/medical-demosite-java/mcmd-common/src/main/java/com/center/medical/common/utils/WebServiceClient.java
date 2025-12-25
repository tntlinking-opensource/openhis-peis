package com.center.medical.common.utils;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
/**
 * @author: 路飞船长
 * @date: 2025/6/19 14:21
 * @description:
 */
public class WebServiceClient {

    /**
     * 发送 SOAP WebService 请求（支持自定义命名空间）
     *
     * @param url             WebService 接口地址
     * @param soapAction      SOAPAction 值（如 http://tempuri.org/AgainReportBack）
     * @param methodName      方法名（如 AgainReportBack）
     * @param paramName       参数名（如 CID_Data）
     * @param paramXml        参数内容字符串（如 <Request><OrderID>123</OrderID></Request>）
     * @param namespacePrefix 命名空间前缀（如 tem）
     * @param namespaceURI    命名空间 URI（如 http://tempuri.org/）
     * @return 响应的完整 XML 字符串
     * @throws Exception 异常
     */
    public static String callWebService(String url,
                                        String soapAction,
                                        String methodName,
                                        String paramName,
                                        String paramXml,
                                        String namespacePrefix,
                                        String namespaceURI) throws Exception {

        // 1. 创建连接
        SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnFactory.createConnection();

        // 2. 创建 SOAP 消息
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPPart soapPart = message.getSOAPPart();

        // 3. 构建 Envelope 和 Body
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespacePrefix, namespaceURI);
        SOAPBody body = envelope.getBody();

        // 4. 添加方法和参数
        SOAPElement methodElement = body.addChildElement(methodName, namespacePrefix);
        SOAPElement paramElement = methodElement.addChildElement(paramName, namespacePrefix);
        paramElement.addTextNode(paramXml);

        // 5. 添加 SOAP Header
        MimeHeaders headers = message.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);
        headers.addHeader("Content-Type", "text/xml; charset=utf-8");

        message.saveChanges();

        // 6. 打印请求内容（可选）
        System.out.println("Request SOAP Message:");
        ByteArrayOutputStream requestOut = new ByteArrayOutputStream();
        message.writeTo(requestOut);
        System.out.println(requestOut.toString("UTF-8"));

        // 7. 发送并接收响应
        SOAPMessage response = connection.call(message, url);
        connection.close();

        // 8. 读取响应内容
        ByteArrayOutputStream responseOut = new ByteArrayOutputStream();
        response.writeTo(responseOut);
        return responseOut.toString("UTF-8");
    }
}
