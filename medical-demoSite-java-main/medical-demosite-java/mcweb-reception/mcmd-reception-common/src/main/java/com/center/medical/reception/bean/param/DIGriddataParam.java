package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验科获取收费项目表格数据
 */
@Data
public class DIGriddataParam implements Serializable {
    private static final long serialVersionUID = 157332387421915522L;

    @ApiModelProperty(value = "体检码")
    private String patientCode;

    @ApiModelProperty(value = "科室id")
    private String ksId;

    @ApiModelProperty(value = "是否补全，true是false否")
    private String autoFill;
}
