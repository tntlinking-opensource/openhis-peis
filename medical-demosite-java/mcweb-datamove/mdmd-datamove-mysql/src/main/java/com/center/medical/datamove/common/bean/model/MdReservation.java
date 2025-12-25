package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约记录(MdReservation)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
@Data
@TableName("md_reservation")
@ApiModel(value = "MdReservation", description = "体检预约记录实体类")
public class MdReservation extends Model<MdReservation> implements Serializable {
    private static final long serialVersionUID = -84400290828680061L;

    @TableId(value = "id")
    @ApiModelProperty(value = "预约id")
    private String id;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "预约类型ID")
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "预约时间")
    private Date reservationDate;

    @ApiModelProperty(value = "预约时间段ID")
    private String timeId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "体检系统套餐ID")
    private String comboId;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "第三方预约ID")
    private String bizId;

    @ApiModelProperty(value = "第三方套餐ID")
    private String bizComboId;

    @ApiModelProperty(value = "第三方订单ID")
    private String bizOrderNum;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "预约手机号")
    private String mobile;

    @ApiModelProperty(value = "第三方系统ID")
    private String systemId;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检")
    private String fUsecodehiden;

    @ApiModelProperty(value = "性别：0.男 1.女 2.未知")
    private Object sex;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "预约备注")
    private String remark;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.预约结束 ")
    private Integer status;

    @ApiModelProperty(value = "预约失败的原因")
    private String failReason;

    @ApiModelProperty(value = "是否已删除：0.否 1.是")
    private String isDelete;

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
}
