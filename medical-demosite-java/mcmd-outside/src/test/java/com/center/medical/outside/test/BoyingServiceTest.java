package com.center.medical.outside.test;

import cn.hutool.json.JSONUtil;
import com.center.medical.common.utils.WebServiceClient;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.outside.bean.dto.BoyingGetPatientInfoDto;
import com.center.medical.outside.bean.param.BoyingGetPatientInfoAuthInfoParam;
import com.center.medical.outside.bean.param.BoyingGetPatientInfoParam;
import com.center.medical.outside.service.BoyingService;
import org.apache.cxf.headers.Header;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BoyingServiceTest {

    public static void main(String[] args)  throws Exception {
        getPatientInfo();
//        String soapResponse = againReportBack();
//        AgainReportBackDto data = XmlUtils.parseXmlToObject(soapResponse, AgainReportBackDto.class, true);
//        System.out.println(data);
    }


    /**
     * 重新触发报告回传
     * @throws Exception
     */
    public static String againReportBack() throws Exception {
        try {
            String url = "http://121.42.142.245:8085/WSInterface.asmx";
            String soapAction = "http://tempuri.org/AgainReportBack";
            String methodName = "AgainReportBack";
            String paramName = "CID_Data";
            String paramXml = "<Request><OrderID>310231449283</OrderID></Request>";
            String namespacePrefix = "tem";
            String namespaceURI = "http://tempuri.org/";

            String result = WebServiceClient.callWebService(
                    url, soapAction, methodName, paramName, paramXml, namespacePrefix, namespaceURI
            );

            System.out.println("响应内容：\n" + result);
            return result;
        } catch (Exception e) {
            return "调用失败：" + e.getMessage();
        }
    }


    private static void getPatientInfo() throws Exception{
        // WSDL地址
        URL wsdlUrl = new URL("http://113.44.218.181:8081/open/api/v4/boying?wsdl"); //113.44.218.181:8081   localhost:8091

        // 命名空间和服务名称（需与服务端注解一致）
        QName SERVICE_NAME = new QName("http://service.outside.medical.world.com", "BoyingServiceImplService");

        // 创建服务和代理客户端
        Service service = Service.create(wsdlUrl, SERVICE_NAME);
        BoyingService client = service.getPort(BoyingService.class);

        // 设置SOAP Header
        setSoapHeaders((BindingProvider) client);


        // 构建业务参数
        BoyingGetPatientInfoParam param = new BoyingGetPatientInfoParam();
        param.setPatCd("010231452833");
        param.setHisCd("HIS789012");
        param.setOutpatientCd("OUT123456");
//        param.setAuthInfo(authInfo);

        // 调用接口
        BoyingGetPatientInfoDto response = client.getPatientInfo(param);

        // 打印结果
        System.out.println("获取患者信息结果：");
        System.out.println(response != null ? response.toString() : "未获取到有效数据。");
    }
    /**
     * 设置 SOAP 请求头信息，包括 Authorization 和 authInfo
     */
    private static void setSoapHeaders(BindingProvider bindingProvider) throws Exception {
        List<Header> headers = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        // 创建 Authorization Header
        Element authHeader = document.createElementNS("http://service.outside.medical.world.com", "Authorization");
        authHeader.setTextContent("VbrO//JXLUEsAjF3zj4g5W0AmM2TUFxm5ZAjfA4hq7fFAivVojfxobNv21l5ACRn"); // 替换为实际 token
        headers.add(new Header(new QName("http://service.outside.medical.world.com", "Authorization"), authHeader));

        // 创建 authInfo Header
        Element authInfoHeader = document.createElementNS("http://service.outside.medical.world.com", "AuthInfo");
        // 构建授权信息
        BoyingGetPatientInfoAuthInfoParam authInfo = new BoyingGetPatientInfoAuthInfoParam();
        authInfo.setCode("31985AB30C6062B5");
        authInfo.setFlag("COO_CUSTOMER");
        // 获取当前时间的时间戳（毫秒级）
        long timestamp = System.currentTimeMillis();
        System.out.println("timestamp:" + timestamp);
        String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDATYTZA3+6TfkKiuS/JtQ6KfCHlmwkz1FliEBCl+RapLNwC1He5fYzT5xSxix/5qY21IYlYR1VS68MXHKSfVU4wSLyZRerJqsUQMUYgs1mcu3xD4dtWUYaT5zkfQIZfv1QO9LIrGa4DCeTtySQZXbq/FkHHqmuaQ0G6bMTeqFMwIDAQAB";
        String signature = RSAUtil.publicEncrypt(authInfo.getCode() + authInfo.getFlag()+ timestamp, key);
        authInfo.setSignature(signature);
        authInfoHeader.setTextContent(JSONUtil.toJsonStr(authInfo)); // 替换为实际 authInfo
        headers.add(new Header(new QName("http://service.outside.medical.world.com", "AuthInfo"), authInfoHeader));

        // 设置 Headers
        bindingProvider.getRequestContext().put(Header.HEADER_LIST, headers);
    }
}
