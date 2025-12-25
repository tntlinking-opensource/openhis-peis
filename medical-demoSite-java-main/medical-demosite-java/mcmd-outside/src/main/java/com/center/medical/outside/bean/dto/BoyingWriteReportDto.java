package com.center.medical.outside.bean.dto;

import com.center.medical.outside.bean.model.BoyingWriteReportHead;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 博英心电信息管理系统-执行接口与报告接口出参
 * @author xhp
 * @since 2025-05-23 14:37
 */
@XmlRootElement(name="HIS")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WriteReportHIS", propOrder = {
        "head","message"
})
@Data
public class BoyingWriteReportDto {

    @XmlElement(name="HEAD")
    private BoyingWriteReportHead head;

    @XmlElement(name="MESSAGE")
    private String message;
}
