package com.center.medical.platform.controller.common;

import com.center.medical.common.annotation.Excel;
import com.center.medical.common.xss.Xss;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/4/20 14:19
 * @description:
 */
@Data
public class SyncPatient implements Serializable {
    private static final long serialVersionUID = 4009843297718186191L;


    @ApiModelProperty(value = "体检号")
    @Xss(message = "体检号不能包含脚本字符")
    @Excel(name = "体检号")
    private String patientCode;
}
