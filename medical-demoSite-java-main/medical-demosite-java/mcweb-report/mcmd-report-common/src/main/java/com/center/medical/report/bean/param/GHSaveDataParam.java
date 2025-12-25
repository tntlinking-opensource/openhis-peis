package com.center.medical.report.bean.param;

import com.center.medical.report.bean.dto.GHGridData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 职业健康团检样本保存参数
 */
@Data
public class GHSaveDataParam implements Serializable {
    private static final long serialVersionUID = 4904926179627146593L;

    @ApiModelProperty(value = "组ID")
    private String groupId;

    @ApiModelProperty(value = "报告ID")
    private String reportId;

    @ApiModelProperty(value = "数据")
    private List<GHGridData> griddata;
}
