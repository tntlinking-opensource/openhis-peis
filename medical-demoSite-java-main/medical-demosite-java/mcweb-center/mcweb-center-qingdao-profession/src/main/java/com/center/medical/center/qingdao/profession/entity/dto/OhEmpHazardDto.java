package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OhEmpHazardDto implements Serializable {
    private static final long serialVersionUID = -2912266716861178211L;

    @ApiModelProperty(value = "危害因素代码")
    private String hazardCode;

    @ApiModelProperty(value = "是否位主要粉尘")
    private String isMain;

    @ApiModelProperty(value = "监护种类代码")
    private String careTypeCode;

    @ApiModelProperty(value = "体检结论代码")
    private String resultOptionCode;

    @ApiModelProperty(value = "职业危害监测种类代码")
    private String monitorTypeCode;

    @ApiModelProperty(value = "职业病代码")
    private String occDiseaseCode;

    @ApiModelProperty(value = "其他疾病或异常")
    private String otherContent;

    @ApiModelProperty(value = "单位名称")
    private String enterpriseName;

    @ApiModelProperty(value = "统一社会信用代码")
    private String enterpriseBizCode;

    @ApiModelProperty(value = "开始接害日期")
    private String hazardStartDate;

    @ApiModelProperty(value = "实际接害工龄-年")
    private String hazardYear;

    @ApiModelProperty(value = "实际接害工龄-月")
    private String hazardMonth;

    @ApiModelProperty(value = "实际接害工龄-日")
    private String hazardDay;
}
