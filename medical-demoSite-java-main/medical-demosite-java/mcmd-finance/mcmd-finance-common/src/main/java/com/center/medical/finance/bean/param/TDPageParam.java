package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记账管理-记账结算明细 分页参数
 */
@Data
public class TDPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -8587896543267025882L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名拼音码")
    private String name;

    @ApiModelProperty(value = "团体id")
    private String khdwmcid;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;


}
