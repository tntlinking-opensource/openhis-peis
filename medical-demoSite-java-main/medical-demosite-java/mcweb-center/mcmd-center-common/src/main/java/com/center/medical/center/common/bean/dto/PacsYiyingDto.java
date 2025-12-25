package com.center.medical.center.common.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 易影pacs结果
 * @author xhp
 * @since 2025-02-25 15:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacsYiyingDto {
    private String feeitemId;//体检者收费项目id
    private Date examdatetime;//检查时间
    private String examresultdesc;//检查结果描述
    private String examresultsummary;//检查结果总结
    private String reportDoctor;//报告医生
    private String auditDoctor;//审核医生
}
