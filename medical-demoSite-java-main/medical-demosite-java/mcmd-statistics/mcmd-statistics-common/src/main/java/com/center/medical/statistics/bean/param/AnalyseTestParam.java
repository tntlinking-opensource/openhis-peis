package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验科科室医生工作量统计 参数
 */
@Data
public class AnalyseTestParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -6983599347656495017L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "检查项目ID（检验科用）")
    private String examId;

    @ApiModelProperty(value = "检查项目ID（pacs用）")
    private String itemId;

    @ApiModelProperty(value = "科室id（其他科室用）")
    private String ks;
}
