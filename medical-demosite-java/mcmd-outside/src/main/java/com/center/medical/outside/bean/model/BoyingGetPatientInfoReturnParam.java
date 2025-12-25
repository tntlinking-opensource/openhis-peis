package com.center.medical.outside.bean.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 博英心电信息管理系统-病人信息查询接口出参
 * @author xhp
 * @since 2025-05-23 10:55
 */
@XmlRootElement(name="PARAM")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPatientInfoPARAM", propOrder = {
        "patCd", "hisCd", "outpatientCd"
})
@Data
public class BoyingGetPatientInfoReturnParam {

    //病人编号
    @XmlElement(name="PatCd")
    private String patCd;

    //住院号
    @XmlElement(name="HisCd")
    private String hisCd;

    //门诊号
    @XmlElement(name="OutpatientCd")
    private String outpatientCd;
}
