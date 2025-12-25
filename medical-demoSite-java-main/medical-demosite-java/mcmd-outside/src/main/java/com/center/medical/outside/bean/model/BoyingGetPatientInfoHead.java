package com.center.medical.outside.bean.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 博英心电信息管理系统-病人信息查询接口出参
 * @author xhp
 * @since 2025-05-23 10:52
 */
@XmlRootElement(name="HEAD")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPatientInfoHEAD", propOrder = {
        "serviceName","param","isSuccess","messCount"
})
@Data
public class BoyingGetPatientInfoHead {
    @XmlElement(name="SERVICE_NAME")
    private String serviceName;

    @XmlElement(name="PARAM")
    private BoyingGetPatientInfoReturnParam param;

    @XmlElement(name="ISSUCCESS")
    private boolean isSuccess;

    @XmlElement(name="MESS_COUNT")
    private String messCount;
}
