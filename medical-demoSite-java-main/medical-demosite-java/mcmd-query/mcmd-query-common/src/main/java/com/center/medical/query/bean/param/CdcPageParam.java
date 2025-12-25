package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CDC职业病直报数据查询 分页参数
 */
@Data
public class CdcPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -6135623269761079352L;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
