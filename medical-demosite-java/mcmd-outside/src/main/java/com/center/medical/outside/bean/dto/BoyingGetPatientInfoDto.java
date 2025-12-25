package com.center.medical.outside.bean.dto;

import com.center.medical.outside.bean.model.BoyingGetPatientInfoHead;
import com.center.medical.outside.bean.model.BoyingGetPatientInfoMessage;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 博英心电信息管理系统-病人信息查询接口出参
 * @author xhp
 * @since 2025-05-23 9:53
 */
@XmlRootElement(name="HIS")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPatientInfoHIS", propOrder = {
        "head","message"
})
@Data
public class BoyingGetPatientInfoDto {

    @XmlElement(name="HEAD")
    private BoyingGetPatientInfoHead head;

    @XmlElement(name="MESSAGE")
    private BoyingGetPatientInfoMessage message;


}
