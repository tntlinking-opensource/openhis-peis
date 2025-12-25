package com.center.medical.outside.bean.param;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 博英心电信息管理系统-病人信息查询接口入参
 * @author xhp
 * @since 2025-05-23 9:38
 */
@XmlRootElement(name="Request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPatientInfoRequest", propOrder = {
        "patCd", "hisCd", "outpatientCd"
})
@Data
public class BoyingGetPatientInfoParam {

    //病人编号
    @XmlElement(name="PatCd")
    private String patCd;

    //住院号
    @XmlElement(name="HisCd")
    private String hisCd;

    //门诊号
    @XmlElement(name="OutpatientCd")
    private String outpatientCd;

    //授权信息
//    @XmlElement(name="AuthInfo")
//    private BoyingGetPatientInfoAuthInfoParam authInfo;

}
