package com.center.medical.app.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约(Appointment)表实体类
 *
 * @author 路飞船长
 * @since 2023-04-07 13:55:03
 */
@Data
@TableName("md_appointment")
@ApiModel(value = "Appointment", description = "体检预约实体类")
public class Appointment extends Model<Appointment> implements Serializable {
    private static final long serialVersionUID = 259577863115517318L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "预约id")
    private String id;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检", required = true, position = 0)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "预约类型ID")
    private Integer levelId;

    @ApiModelProperty(value = "预约时间")
    private String reservationDate;

    @ApiModelProperty(value = "预约时间段ID")
    private String timeId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "体检系统套餐ID")
    private String comboId;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用", required = true, position = 4)
    private Integer sex;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "预约手机号")
    private String mobile;

    @ApiModelProperty(value = "预约备注")
    private String remark;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.申请改期中 4.改期成功 5.申请改期被驳回 6.预约结束 7.取消提交中 8.已取消")
    private Integer status;

    @ApiModelProperty(value = "预约失败原因")
    private String failReasion;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "创建者id")
    private Date creator;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "更新者id")
    private Date modifier;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "体检系统套餐名称")
    private String comboName;

    @ApiModelProperty(value = "删除状态：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;
}
