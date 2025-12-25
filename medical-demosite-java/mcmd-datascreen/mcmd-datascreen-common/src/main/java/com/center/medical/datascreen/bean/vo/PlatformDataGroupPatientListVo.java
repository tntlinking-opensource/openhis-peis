package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团检体检概况
 * @author xhp
 * @since 2023-06-02 9:24
 */
@Data
public class PlatformDataGroupPatientListVo {
    @ApiModelProperty(value = "体检套餐")
    private String examsuiteName;
    @ApiModelProperty(value = "体检团体")
    private String orgName;
    @ApiModelProperty(value = "体检人")
    private String patientname;
    @ApiModelProperty(value = "金额")
    private Double amount;
    @ApiModelProperty(value = "折扣率")
    private String discountRate;
    @ApiModelProperty(value = "收费方式")
    private String payway;
    @ApiModelProperty(value = "销售员")
    private String doctorapply;
}
