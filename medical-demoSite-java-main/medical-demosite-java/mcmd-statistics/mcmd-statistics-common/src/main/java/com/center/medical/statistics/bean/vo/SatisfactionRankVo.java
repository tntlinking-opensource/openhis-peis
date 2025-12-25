package com.center.medical.statistics.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 科室满意度排名 返回数据
 */
@Data
public class SatisfactionRankVo implements Serializable {
    private static final long serialVersionUID = -364828507793944717L;

    @ApiModelProperty(value = "录入人用户名")
    private String doctorname;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "总分")
    private String points;

    @ApiModelProperty(value = "排名")
    private String rowid;


}
