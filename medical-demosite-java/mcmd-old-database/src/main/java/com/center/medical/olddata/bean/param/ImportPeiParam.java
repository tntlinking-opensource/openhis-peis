package com.center.medical.olddata.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导入体检者参数
 */
@Data
public class ImportPeiParam implements Serializable {
    private static final long serialVersionUID = -742604126307638720L;

    @ApiModelProperty(value = "体检号集合")
    private List<String> patientcodes;
}
