package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目列表-结果-手动输入结果模块项目展示数据
 */
@Data
public class AllOutDataVo implements Serializable {

    private static final long serialVersionUID = -4657593508080094602L;

    @ApiModelProperty(value = "收费项目名称")
    private String idFee;

    @ApiModelProperty(value = "收费项目ID")
    private String idCharge;

    @ApiModelProperty(value = "检查项目名称")
    private String bcheck;

    @ApiModelProperty(value = "检查项目ID")
    private String idCheck;

    @ApiModelProperty(value = "参考低值")
    private String ckdz;

    @ApiModelProperty(value = "参考高值")
    private String ckgx;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}
