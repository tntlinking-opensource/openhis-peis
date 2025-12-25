package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取所有复查模板上所需要的数据
 */
@Data
public class AllReviewDataDto implements Serializable {
    private static final long serialVersionUID = 5144111187285256651L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "公司名称或团体名称")
    private String orgName;

    @ApiModelProperty(value = "阳性结果")
    private String posistive;

    @ApiModelProperty(value = "复查时间止")
    private Date dateTo;

    @ApiModelProperty(value = "复查收费项目")
    private String itemsName;

    @ApiModelProperty(value = "注意事项")
    private String noticeOfProceedingText;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "职业总检时间")
    private Date totalDate;

    @ApiModelProperty(value = "团体名称")
    private String oo;

}
