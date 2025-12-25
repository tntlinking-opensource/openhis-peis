package com.center.medical.outside.bean.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 博英心电信息管理系统-病人信息查询接口出参
 * @author xhp
 * @since 2025-05-23 10:58
 */
@XmlRootElement(name="MESSAGE")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPatientInfoMESSAGE", propOrder = {
        "data"
})
@Data
public class BoyingGetPatientInfoMessage {
    @XmlElement(name="DATA")
    private List<BoyingGetPatientInfoData> data;
}
