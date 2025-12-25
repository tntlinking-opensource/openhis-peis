package com.center.medical.outside.bean.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 博英心电信息管理系统-病人信息查询接口出参
 * @author xhp
 * @since 2025-05-23 10:58
 */
@XmlRootElement(name="DATA")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPatientInfoDATA", propOrder = {
        "patientID", "orderID", "inPatientNo", "cardID", "name", "sex",
        "birthday", "address", "idNumber", "phone", "testName", "testCode",
        "openDpmt", "openDpmtCode", "execDpmt", "execDpmtCode", "sickbed",
        "openDoc", "jybs", "lczd", "openDate", "execStatus", "source"
})
@Data
public class BoyingGetPatientInfoData {

    @XmlElement(name = "PatientID")
    private String patientID;

    @XmlElement(name = "OrderID")
    private String orderID;

    @XmlElement(name = "InPatientNo")
    private String inPatientNo;

    @XmlElement(name = "CardID")
    private String cardID;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Sex")
    private String sex;

    @XmlElement(name = "Birthday")
    private String birthday;

    @XmlElement(name = "Address")
    private String address;

    @XmlElement(name = "IDNumber")
    private String idNumber;

    @XmlElement(name = "Phone")
    private String phone;

    @XmlElement(name = "TestName")
    private String testName;

    @XmlElement(name = "TestCode")
    private String testCode;

    @XmlElement(name = "OpenDpmt")
    private String openDpmt;

    @XmlElement(name = "OpenDpmtCode")
    private String openDpmtCode;

    @XmlElement(name = "ExecDpmt")
    private String execDpmt;

    @XmlElement(name = "ExecDpmtCode")
    private String execDpmtCode;

    @XmlElement(name = "Sickbed")
    private String sickbed;

    @XmlElement(name = "OpenDoc")
    private String openDoc;

    @XmlElement(name = "JYBS")
    private String jybs;

    @XmlElement(name = "LCZD")
    private String lczd;

    @XmlElement(name = "OpenDate")
    private String openDate;

    @XmlElement(name = "ExecStatus")
    private String execStatus;

    @XmlElement(name = "Source")
    private String source;


}
