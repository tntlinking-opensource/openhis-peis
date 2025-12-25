package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * pacs 选择收费项目 参数
 */
@Data
public class PacsSearchParam implements Serializable {
    private static final long serialVersionUID = -6520835064087619313L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室id")
    private String ksID;

    @ApiModelProperty(value = "收费项目id")
    private String feeitemId;

    @ApiModelProperty(value = "类型")
    private Integer type;

}
