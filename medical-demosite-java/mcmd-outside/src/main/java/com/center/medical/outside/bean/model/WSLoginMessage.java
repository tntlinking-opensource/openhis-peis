package com.center.medical.outside.bean.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 第三方登录接口出参
 * @author 路飞
 * @since 2025-06-10 10:52
 */
@XmlRootElement(name="MESSAGE")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutsideLoginMESSAGE", propOrder = {
        "success", "accessToken", "expiresIn"
})
@Data
public class WSLoginMessage {

    @XmlElement(name = "Success")
    private Boolean success;

    @XmlElement(name = "AccessToken", nillable = true)
    private String accessToken;

    @XmlElement(name = "ExpiresIn", nillable = true)
    private Long expiresIn;
}
