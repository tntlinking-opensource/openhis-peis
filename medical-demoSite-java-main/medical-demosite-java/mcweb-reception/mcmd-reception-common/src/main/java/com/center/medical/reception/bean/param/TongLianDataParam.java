package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通联数据参数
 */
@Data
public class TongLianDataParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -1338577266625292893L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "0收入，1退费")
    private Integer fIsreturn;

    @ApiModelProperty(value = "开单医生")
    private String idOpendoctor;

}
