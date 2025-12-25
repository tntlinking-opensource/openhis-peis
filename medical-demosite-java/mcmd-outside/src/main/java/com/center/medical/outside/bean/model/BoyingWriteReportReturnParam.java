package com.center.medical.outside.bean.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 博英心电信息管理系统-执行接口与报告接口出参
 * @author xhp
 * @since 2025-05-23 14:40
 */
@XmlRootElement(name="PARAM")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WriteReportPARAM", propOrder = {
        "orderId", "execStatus", "conclusion","reportUrl"
})
@Data
public class BoyingWriteReportReturnParam {

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

}
