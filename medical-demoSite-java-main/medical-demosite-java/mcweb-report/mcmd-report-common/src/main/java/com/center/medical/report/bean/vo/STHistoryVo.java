package com.center.medical.report.bean.vo;

import com.center.medical.bean.enums.MedicalType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-12-09 14:31
 * @description: 总检历史记录数据
 */
@Data
public class STHistoryVo implements Serializable {
    private static final long serialVersionUID = 6651287934634280770L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "总检时间")
    private String totalTime;

    @ApiModelProperty(value = "综述")
    private String summarize;

    @ApiModelProperty(value = "健康总检：健康建议；职业总检：职业结论及建议 OFFER")
    private String offer;

    @ApiModelProperty(value = "阳性结果")
    private String posistive;

    /**
     * @see MedicalType
     */
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "是否已生成报告：0.否 1.是")
    private Integer fpdf;

    @ApiModelProperty(value = "团体名称")
    private String urlPdf;

    @ApiModelProperty(value = "是否是老系统报告")
    private Integer isOld;

    @ApiModelProperty(value = "全路径")
    private String fullPath;
}
