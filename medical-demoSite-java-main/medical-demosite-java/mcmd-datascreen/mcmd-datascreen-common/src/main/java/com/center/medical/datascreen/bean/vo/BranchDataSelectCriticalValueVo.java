package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 危急值
 * @author xhp
 * @since 2023-06-13 10:08
 */
@Data
public class BranchDataSelectCriticalValueVo {
    @ApiModelProperty("职业禁忌症")
    private int occupationalContraindication;
    @ApiModelProperty("疑似职业病")
    private int occupationalDiseases;
    @ApiModelProperty("健康体检(高危)")
    private int hignRisk;
}
