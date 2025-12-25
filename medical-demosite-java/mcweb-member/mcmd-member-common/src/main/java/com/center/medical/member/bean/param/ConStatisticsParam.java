package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 咨询与随访统计分页参数
 */
@Data
public class ConStatisticsParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5064880187749078932L;


    @ApiModelProperty(value = "咨询医生")
    private String doctorUsername;

    @ApiModelProperty(value = "类别 1.现场咨询 2.来电咨询 3.电话回访")
    private String consultType;


}
