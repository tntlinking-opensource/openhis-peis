package com.center.medical.outside.bean.param;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @author: 路飞船长
 * @date: 2025/6/16 13:45
 * @description: 登录反参
 */
@XmlRootElement(name="PARAM")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutsideLoginPARAM", propOrder = {
        "userName", "password", "sourceId"
})
@Data
public class WSLoginReturnParam {

    @XmlElement(name = "UserName")
    private String userName;

    @XmlElement(name = "Password")
    private String password;

    @XmlElement(name = "SourceId")
    private String sourceId;
}
