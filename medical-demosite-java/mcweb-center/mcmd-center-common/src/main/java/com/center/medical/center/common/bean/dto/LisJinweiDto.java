package com.center.medical.center.common.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 金卫lis数据库视图结果
 * @author xhp
 * @since 2024-05-06 10:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LisJinweiDto {
    private String technician;//检验师+检查人
    private String testDate; //检测日期,
    private String itemNo;//检查项目CODE
    private String reportDesc;//结果
    private String reportStatus;//状态
    private String refRange;//参考范围
    private String unit; //项目单位
    private String sampleno;//lis代码
    private String rechkUsername;//审核人姓名
    private String rechkDt;//审核日期
    private String reportUser;//检查人代码
}
