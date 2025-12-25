package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 个检体检概况
 * @author xhp
 * @since 2023-06-06 8:28
 */
@Data
public class PlatformDataPersonPatientPageVo {
    @ApiModelProperty(value = "体检套餐")
    private String examsuiteName;
    @ApiModelProperty(value = "体检人")
    private String patientname;
    @ApiModelProperty(value = "金额")
    private Double amount;
    @ApiModelProperty(value = "收费方式")
    private String payway;
    @ApiModelProperty(value = "销售员")
    private String doctorapply;
}
