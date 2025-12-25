package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 肺功能 审核 小结数据
 */
@Data
public class DLDataDto implements Serializable {
    private static final long serialVersionUID = -3754327636353457392L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "是否已审核")
    private Integer isAudit;

    @ApiModelProperty(value = "检查人ID")
    private String rummagerId;

    @ApiModelProperty(value = "检查时间")
    private Date rummagerTime;

    @ApiModelProperty(value = "录入人ID")
    private String writeId;

    @ApiModelProperty(value = "录入时间")
    private Date writeTime;

    @ApiModelProperty(value = "小结")
    private String conclusions;

}
