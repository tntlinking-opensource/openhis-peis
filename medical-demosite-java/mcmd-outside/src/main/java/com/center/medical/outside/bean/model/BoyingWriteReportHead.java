package com.center.medical.outside.bean.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 博英心电信息管理系统-执行接口与报告接口出参
 * @author xhp
 * @since 2025-05-23 14:38
 */
@XmlRootElement(name="HEAD")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WriteReportHEAD", propOrder = {
        "serviceName","param","isSuccess","messCount"
})
@Data
public class BoyingWriteReportHead {
    @XmlElement(name="SERVICE_NAME")
    private String serviceName;

    @XmlElement(name="PARAM")
    private BoyingWriteReportReturnParam param;

    @XmlElement(name="ISSUCCESS")
    private boolean isSuccess;

    @XmlElement(name="MESS_COUNT")
    private String messCount;
}
