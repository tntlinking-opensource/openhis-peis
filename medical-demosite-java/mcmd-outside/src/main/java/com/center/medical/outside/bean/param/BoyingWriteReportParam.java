package com.center.medical.outside.bean.param;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 博英心电信息管理系统-执行接口与报告接口入参
 * @author xhp
 * @since 2025-05-23 14:34
 */
@XmlRootElement(name="Request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WriteReportRequest", propOrder = {
        "patientID",  "orderId", "execStatus", "conclusion","reportUrl","reportDoc"
})
@Data
public class BoyingWriteReportParam implements Serializable {
    private static final long serialVersionUID = -8569189275796634068L;
    //体检号
    @XmlElement(name="PatientID")
    private String patientID;

    //医嘱号
    @XmlElement(name="OrderID")
    private String orderId;

    //执行状态 1:已执行、2:已报告
    @XmlElement(name="ExecStatus")
    private String execStatus;

    //文字报告
    @XmlElement(name="Conclusion")
    private String conclusion;

    //报告链接
    @XmlElement(name="ReportURL")
    private String reportUrl;

    //审核医师
    @XmlElement(name="ReportDoc")
    private String reportDoc;

    //授权信息
//    @XmlElement(name="AuthInfo")
//    private BoyingWriteReportAuthInfoParam authInfo;
}
