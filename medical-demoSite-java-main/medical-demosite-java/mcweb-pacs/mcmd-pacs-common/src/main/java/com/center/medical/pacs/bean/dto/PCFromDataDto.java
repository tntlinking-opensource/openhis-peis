package com.center.medical.pacs.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  保存formdata参数
 */
@Data
public class PCFromDataDto implements Serializable {
    private static final long serialVersionUID = -2058966726733556674L;

    @ApiModelProperty(value = "检查人姓名")
    private String rummagerName;

    @ApiModelProperty(value = "检查人ID")
    private String rummagerId;

    @ApiModelProperty(value = "录入人")
    private String writeName;

    @ApiModelProperty(value = "录入人ID")
    private String writeId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "PACS体检者收费项目ID")
    private String feeitemId;

    @ApiModelProperty(value = "pacs")
    private String pacs;

    @ApiModelProperty(value = "type")
    private String type;

    @ApiModelProperty(value = "检查时间")
    private String rummagerTime;

    @ApiModelProperty(value = "录入时间")
    private String writeTime;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "小结")
    private String conclusions;

    @ApiModelProperty(value = "图片")
    private String imgs;

}
