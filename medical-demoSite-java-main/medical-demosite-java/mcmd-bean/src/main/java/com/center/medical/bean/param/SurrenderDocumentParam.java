package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 交单记录表分页参数
 */
@Data
public class SurrenderDocumentParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5637409578992810813L;

    @ApiModelProperty(value = "提交人")
    private String presenter;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;
}
