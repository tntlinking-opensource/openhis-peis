package com.center.medical.app.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约记录(Reservation)表实体类
 *
 * @author ay
 * @since 2023-03-18 08:54:13
 */
@Data
@TableName("md_reservation")
@ApiModel(value = "Reservation", description = "体检预约记录实体类")
public class Reservation extends Model<Reservation> implements Serializable {
    private static final long serialVersionUID = -50368646853436886L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "预约id")
    private String id;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检", required = true, position = 0)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "预约类型ID", required = true)
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "预约时间", required = true)
    private Date reservationDate;

    @ApiModelProperty(value = "预约时间段ID", required = true)
    private String timeId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "体检系统订单号(团检客户)")
    private String orderNum;

    @ApiModelProperty(value = "团检预约ID")
    private String reserveGroupId;

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

    @ApiModelProperty(value = "姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用", required = true, position = 4)
    private Integer sex;

    @ApiModelProperty(value = "身份证号", required = true)
    private String idcard;

    @ApiModelProperty(value = "预约手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "第三方系统ID：本地传0", required = true)
    private String systemId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团体ID(团检预约需要)", position = 14)
    private String idOrg;

    @ApiModelProperty(value = "预约备注")
    private String remark;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.预约结束")
    private Integer status;

    @ApiModelProperty(value = "是否已删除：0.否 1.是")
    private Integer isDelete;

    @ApiModelProperty(value = "预约失败的原因")
    private String failReason;

    @ApiModelProperty(value = "预约人ID")
    private String operator;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "创建者id")
    private String creator;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "更新者id")
    private String modifier;

    @ApiModelProperty(value = "到检时间")
    private Date finishedTime;

    @ApiModelProperty(value = "分中心名称")
    @TableField(exist = false)
    private String branchName;
}
