package com.center.medical.center.common.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 金卫pacs数据库视图结果
 * @author xhp
 * @since 2024-05-11 8:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacsJinweiDto {
    private String examfeeitemCode;//收费项目接口代码
    private String examDate;//审核日期 yyyy-MM-dd
    private String examTime;//审核时间 HH:mm:ss
    private String examDoctor;//检查师  检查人  审核人
    private String examResultDesc;//检查结果描述
    private String examResultSummary;//检查结果总结
    private String imageFullPath;//图片路径 多个用;分隔
    private String userName;//保存至pacsResult表userName字段
}
