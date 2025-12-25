package com.center.medical.app.bean.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约申请信息
 *
 * @author 路飞船长
 * @since 2023-03-27 18:52:45
 */
@Data
@ApiModel(value = "体检预约申请信息", description = "体检预约申请信息")
public class AppointmentDto implements Serializable {
    private static final long serialVersionUID = 728505440187070327L;

    @ApiModelProperty(value = "预约id")
    private String id;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

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

    @ApiModelProperty(value = "体检号(团检预约需要)", position = 13)
    private String patientcode;

    @ApiModelProperty(value = "团体ID(团检预约需要)", position = 14)
    private String idOrg;

    @ApiModelProperty(value = "用户ID", position = 15)
    private String userId;

    @ApiModelProperty(value = "第三方系统ID：本地传0")
    private String systemId;

    @ApiModelProperty(value = "预约备注")
    private String remark;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.预约结束")
    private Integer status;

    @ApiModelProperty(value = "预约失败原因")
    private String failReasion;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "客户证件类型：1.身份证 2.护照 6.军人证  7.港澳通行证/回乡证或台胞证")
    private Integer countreportoccupationxml;
}
