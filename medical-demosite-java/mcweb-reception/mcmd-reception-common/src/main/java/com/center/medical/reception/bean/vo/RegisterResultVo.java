package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-03-15 19:04
 * @description: 登记结果
 */
@Data
public class RegisterResultVo implements Serializable {
    private static final long serialVersionUID = -990181789820852697L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "中间库状态：-1.保存、0.第一次登记、1.重新登记、2.增项")
    private Integer sataus;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "收费检查项目操作结果：true.操作成功、false.操作失败")
    private Boolean itemResult;

    @ApiModelProperty(value = "收费检查项目操作失败提示")
    private String itemText;
}
