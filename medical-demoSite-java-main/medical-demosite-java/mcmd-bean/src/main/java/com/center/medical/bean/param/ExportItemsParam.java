package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出体检者收费项目列表参数
 */
@Data
public class ExportItemsParam implements Serializable {
    private static final long serialVersionUID = 8587581635315455051L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientCode;
}
