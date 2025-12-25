package com.center.medical.outside.bean.param;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * WebService 登录请求参数
 */
@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutsideLoginRequest", propOrder = {"userName", "password", "sourceId"})
public class WSLoginParam {

    @XmlElement(name = "UserName")
    private String userName;

    @XmlElement(name = "Password")
    private String password;

    @XmlElement(name = "SourceId")
    private String sourceId;

}
