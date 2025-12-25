package com.center.medical.app.bean.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 立即预约参数
 */
@Data
public class AppointmentNowDto implements Serializable {
    private static final long serialVersionUID = -5982605443117448720L;


    @ApiModelProperty(value = "订单类型：0.个检 1.团检", required = true, position = 0)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "分中心ID", required = true, position = 1)
    private String branchId;

    @ApiModelProperty(value = "预约类型ID", required = true, position = 2)
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称", required = true, position = 9)
    private String levelName;

    @ApiModelProperty(value = "预约时间", required = true, position = 3)
    private String reservationDate;

    @ApiModelProperty(value = "预约时间段ID", required = true, position = 4)
    private String timeId;

    @ApiModelProperty(value = "姓名", required = true, position = 5)
    private String realName;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用", required = true, position = 4)
    private Integer sex;

    @ApiModelProperty(value = "身份证号", required = true, position = 6)
    private String idcard;

    @ApiModelProperty(value = "预约手机号", required = true, position = 7)
    private String mobile;

    @ApiModelProperty(value = "分中心名称", position = 8)
    private String branchName;

    @ApiModelProperty(value = "订单号", position = 10)
    private String numorgresv;

    @ApiModelProperty(value = "体检系统套餐ID", position = 11)
    private String comboId;

    @ApiModelProperty(value = "体检系统套餐名称", position = 12)
    private String comboName;

    @ApiModelProperty(value = "体检号", position = 13)
    private String patientcode;

    @ApiModelProperty(value = "团体ID", position = 14)
    private String idOrg;

    @ApiModelProperty(value = "用户ID", position = 15)
    private String userId;

    @ApiModelProperty(value = "第三方系统ID：本地传0")
    private String systemId;

    @ApiModelProperty(value = "预约备注")
    private String remark;


}
