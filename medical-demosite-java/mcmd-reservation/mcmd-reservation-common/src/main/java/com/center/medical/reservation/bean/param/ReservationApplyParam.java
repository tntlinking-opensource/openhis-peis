package com.center.medical.reservation.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 合作单位开放接口预约申请参数
 * @author: 路飞
 * @date: 2024-11-12 14:46
 * @description: 合作单位开放接口预约申请参数
 */
@Data
@ApiModel(value = "合作单位开放接口预约申请参数")
public class ReservationApplyParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -2461252665445759556L;

    @ApiModelProperty(value = "分中心ID", required = true)
    private String branchId;

    @ApiModelProperty(value = "预约时间段ID", required = true)
    private String timeId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "订单号", required = true)
    private String orderNum;

    @ApiModelProperty(value = "第三方预约ID")
    private String bizId;

    @ApiModelProperty(value = "姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "证件类型：1.身份证 2.护照 6.军人证 7.港澳通行证/回乡证或台胞证", required = true)
    private Integer cardType;

    @ApiModelProperty(value = "证件号", required = true)
    private String idcard;

    @ApiModelProperty(value = "性别：0.男 1.女", required = true)
    private Integer sex;

    @ApiModelProperty(value = "体检号", required = true)
    private String patientcode;

    @ApiModelProperty(value = "预约备注")
    private String remark;

}
