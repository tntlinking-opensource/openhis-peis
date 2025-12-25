package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * C13读卡查询数据
 */
@Data
public class GetC13dataDto implements Serializable {
    private static final long serialVersionUID = 2543526019288230109L;

    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "检查项目名称")
    private String jcxmmc;

    @ApiModelProperty(value = "收费项目id")
    private String sfxmId;

    @ApiModelProperty(value = "体检项目id")
    private String tjxmId;

    @ApiModelProperty(value = "体征词id")
    private String tzcId;

    @ApiModelProperty(value = "结论词id")
    private String jlcId;

    @ApiModelProperty(value = "检查描述")
    private String jcmc;

    @ApiModelProperty(value = "结论词名称")
    private String jlcmc;

    @ApiModelProperty(value = "是否默认选中")
    private String mrxz;

    @ApiModelProperty(value = "检查项目名称进小结：0或null.否 1.是")
    private String jcxmjxj;

    @ApiModelProperty(value = "体征词进小结：0代表进，1代表不进")
    private String tzcjxj;

    @ApiModelProperty(value = "互斥分组")
    private String hcfz;

    @ApiModelProperty(value = "描述进小结：0或null.否 1.是")
    private String isDesc;

    @ApiModelProperty(value = "仅自由输入")
    private String fEntryonly;

    @ApiModelProperty(value = "重症级")
    private String intensiveLevel;

    @ApiModelProperty(value = "名称")
    private String sName;

    @ApiModelProperty(value = "${column.comment}")
    private Integer addUnit;

    @ApiModelProperty(value = "值单位")
    private String valueunit;
}
