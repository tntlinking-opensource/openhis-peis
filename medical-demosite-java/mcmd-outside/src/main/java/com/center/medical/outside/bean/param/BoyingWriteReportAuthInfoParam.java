package com.center.medical.outside.bean.param;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * WebService
 *
 */
@Data
@XmlRootElement(name = "AuthInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WriteReportAuthInfo", propOrder = {"code", "flag", "signature"})
public class BoyingWriteReportAuthInfoParam {

    @XmlElement(name = "Code")
    private String code;

    @XmlElement(name = "Flag")
    private String flag;

    @XmlElement(name = "Signature")
    private String signature;

}
