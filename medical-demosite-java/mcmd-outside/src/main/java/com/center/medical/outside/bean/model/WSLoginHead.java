package com.center.medical.outside.bean.model;

import com.center.medical.outside.bean.param.WSLoginReturnParam;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 第三方登录接口出参
 * @author 路飞
 * @since 2025-06-10 10:52
 */
@XmlRootElement(name="HEAD")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutsideLoginHEAD", propOrder = {
        "serviceName","param","isSuccess","messCount"
})
@Data
public class WSLoginHead {
    @XmlElement(name="SERVICE_NAME")
    private String serviceName;

    @XmlElement(name="PARAM", nillable = true)
    private WSLoginReturnParam param;

    @XmlElement(name="ISSUCCESS")
    private boolean isSuccess;

    @XmlElement(name="MESS_COUNT")
    private String messCount;
}
