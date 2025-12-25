package com.center.medical.outside.test;

import com.center.medical.outside.bean.dto.WSLoginDto;
import com.center.medical.outside.bean.param.WSLoginParam;
import com.center.medical.outside.service.WSLoginService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class WSLoginServiceTest {

    /**
     * WS登录接口测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // WebService WSDL 地址
        URL wsdlUrl = new URL("http://113.44.218.181:8081/open/api/v4/login?wsdl");

        // 命名空间与服务名称（与 @WebService 注解保持一致）
        QName SERVICE_NAME = new QName("http://service.outside.medical.world.com", "WSLoginServiceImplService");

        // 创建服务代理
        Service service = Service.create(wsdlUrl, SERVICE_NAME);

        // 获取接口实现类代理
        WSLoginService client = service.getPort(WSLoginService.class);

        // 构建请求参数
        WSLoginParam loginParam = new WSLoginParam();
        loginParam.setUserName("boying");
        loginParam.setPassword("by@0616");
        loginParam.setSourceId("BY20250616");

        // 发起请求
        WSLoginDto response = client.outsideLogin(loginParam);

        // 打印结果
        System.out.println("登录结果：");
        System.out.println("是否成功：" + response.getMessage().getSuccess());
        System.out.println("AccessToken：" + response.getMessage().getAccessToken());
        System.out.println("ExpiresIn：" + response.getMessage().getExpiresIn());
        System.out.println("ServiceName：" + response.getHead().getServiceName());
    }
}
