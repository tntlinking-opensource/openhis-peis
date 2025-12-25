package com.center.medical.reservation.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 合作单位开放接口预约详情参数
 * @author: 路飞
 * @date: 2024-11-12 14:46
 * @description: 合作单位开放接口预约详情参数
 */
@Data
@ApiModel(value = "合作单位开放接口预约详情参数")
public class CCReservationInfoParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 2991911702866730522L;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "手机号")
    private String phone;

}
