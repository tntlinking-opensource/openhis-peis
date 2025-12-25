package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取加项收费项目数据
 */
@Data
public class GetTempFeeitemDto implements Serializable {
    private static final long serialVersionUID = -3733952113522449593L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String itemId;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "医生名称")
    private String doctorUsername;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "体检者收费项目备注")
    private String remarks;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "PACS体检者收费项目ID")
    private String feeitemId;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "所属科室")
    private String ssks;


    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

}
