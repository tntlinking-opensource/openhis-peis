package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 新老系统集成预约接口-申请预约参数
 * @author xhp
 * @since 2024-01-04 15:01
 */
@Data
public class IntegratedReservationApplyParam {
    @ApiModelProperty(value = "订单类型：0.个检 1.团检", required = true, position = 0)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "机构门店ID", required = true)
    private String hospitalSubId;

    @ApiModelProperty(value = "预约时间段ID", required = true, position = 4)
    private String timeId;

    @ApiModelProperty(value = "预约时间", required = true, position = 3)
    private Date reservationDate;

    @ApiModelProperty(value = "姓名", required = true, position = 5)
    private String realName;

    @ApiModelProperty(value = "身份证号", required = true, position = 6)
    private String idcard;

    @ApiModelProperty(value = "预约手机号", required = true, position = 7)
    private String mobile;

    @ApiModelProperty(value = "第三方套餐ID")
    private String bizComboId;

    @ApiModelProperty(value = "第三方订单ID")
    private String bizOrderNum;

    @ApiModelProperty(value = "第三方系统ID：本地传0", required = true)
    private String systemId;

    @ApiModelProperty(value = "预约备注")
    private String remark;

    @ApiModelProperty(value = "婚姻状态 0未婚，1已婚")
    private Integer idMarriage;

    @ApiModelProperty(value = "部门")
    private String orgDepart;
}
