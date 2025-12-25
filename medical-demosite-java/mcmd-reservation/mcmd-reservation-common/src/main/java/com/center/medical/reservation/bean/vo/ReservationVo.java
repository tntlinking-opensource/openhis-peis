package com.center.medical.reservation.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-21 15:16
 * @description: 体检预约信息
 */
@Data
public class ReservationVo implements Serializable {

    private static final long serialVersionUID = 1142368432262227829L;

    @ApiModelProperty(value = "预约id")
    private String id;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    @ApiModelProperty(value = "预约类型ID")
    private Integer levelId;

    @ApiModelProperty(value = "预约类型")
    private String levelName;

    @ApiModelProperty(value = "预约时间")
    private Date reservationDate;

    @ApiModelProperty(value = "预约时间段ID")
    private String timeId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "体检系统套餐ID")
    private String comboId;

    @ApiModelProperty(value = "体检系统套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "第三方预约ID")
    private String bizId;

    @ApiModelProperty(value = "第三方套餐ID")
    private String bizComboId;

    @ApiModelProperty(value = "第三方订单ID")
    private String bizOrderNum;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用", required = true, position = 4)
    private Integer sex;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "预约手机号")
    private String mobile;

    @ApiModelProperty(value = "预约来源ID")
    private String systemId;

    @ApiModelProperty(value = "预约来源")
    private String typeName;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "预约备注")
    private String remark;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.预约结束 ")
    private Integer status;

    @ApiModelProperty(value = "预约失败的原因")
    private String failReason;

    @ApiModelProperty(value = "预约人ID")
    private String operator;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "创建者id")
    private String creator;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "更新者id")
    private String modifier;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位名称")
    private String xsjl;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "是否已答问卷，0否1是")
    private Integer questionnaire;
}
