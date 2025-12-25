package com.center.medical.reservation.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-21 14:46
 * @description: 预约列表查询参数
 */
@Data
@ApiModel(value = "预约列表查询参数")
public class ReservationParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 5799177891826250482L;

    @ApiModelProperty(value = "预约手机号")
    private String mobile;

    @ApiModelProperty(value = "预约来源ID")
    private String systemId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.预约结束 ")
    private Integer status;

    @ApiModelProperty(value = "预约时间")
    private Date reservationDate;

    @ApiModelProperty(value = "客户类型id")
    private Integer levelId;

    @ApiModelProperty(value = "销售经理id")
    private String xsjl;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "不展示虚拟 1是0否")
    private Integer notDisplayVirtual;

}
