package com.center.medical.bean.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 报告领取 搜索输入码
 */
@Data
public class ReportSearchCodeParam implements Serializable {
    private static final long serialVersionUID = -3544654240914659240L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "姓名")
    private String patientName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @TableField(exist = false)
    @ApiModelProperty(value = "体检类型 0健康，1职业")
    private Integer tjlx;

}
