package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售年度目标总结数据
 */
@Data
public class LTSummaryVo implements Serializable {
    private static final long serialVersionUID = -1960972557330467121L;


    @ApiModelProperty(value = "全年目标(合计)")
    private Double target;

    @ApiModelProperty(value = "实际完成额(合计)")
    private Double complete;
}
