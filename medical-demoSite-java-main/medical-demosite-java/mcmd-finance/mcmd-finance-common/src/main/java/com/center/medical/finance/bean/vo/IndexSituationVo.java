package com.center.medical.finance.bean.vo;

import com.center.medical.finance.bean.dto.OutputValueDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 指标情况返回数据
 */
@Data
public class IndexSituationVo implements Serializable {
    private static final long serialVersionUID = -3243994656503707398L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "分中心名称")
    private List<String> name;

    @ApiModelProperty(value = "数据")
    private List<OutputValueDto> list;
}
