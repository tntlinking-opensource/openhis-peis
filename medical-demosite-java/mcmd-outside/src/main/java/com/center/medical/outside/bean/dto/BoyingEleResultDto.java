package com.center.medical.outside.bean.dto;

import com.center.medical.bean.model.Attachment;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2025/6/18 13:48
 * @description: 博英心电图推送结果
 */
@Data
public class BoyingEleResultDto implements Serializable {

    private static final long serialVersionUID = 158177244555246146L;

    private BoyingWriteReportParam param;

    private Attachment attachment;

    private String patientCode;
}
