package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * pacs科室工作量统计分页参数
 */
@Data
public class PacsQueryParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8583006485126740047L;

    @ApiModelProperty(value = "科室id")
    private String ks;

    @ApiModelProperty(value = "医师（姓名）")
    private String name;

    @ApiModelProperty(value = "录入人（姓名）")
    private String writename;

    @ApiModelProperty(value = " Pacs登记人员 1是")
    private String isPacs;

    @ApiModelProperty(value = "审核人")
    private String auditName;
}
