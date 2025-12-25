package com.center.medical.reservation.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 团体预约记录(ReservationGroup)表实体类
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
@Data
@TableName("md_reservation_group")
@ApiModel(value = "ReservationGroup", description = "团体预约记录实体类")
public class ReservationGroup extends Model<ReservationGroup> implements Serializable {
    private static final long serialVersionUID = 588661281845096044L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "团体预约id")
    private String id;

    @ApiModelProperty(value = "分中心ID", required = true)
    @NotBlank(message = "请先选择预约的分中心!")
    private String branchId;

    @ApiModelProperty(value = "预约类型ID", required = true)
    @NotNull(message = "请先选择预约类型!")
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称", required = true)
    private String levelName;

    @ApiModelProperty(value = "预约时间", required = true)
    @NotNull(message = "请先选择预约时间!")
    private Date reservationDate;

    @ApiModelProperty(value = "团体ID(团检预约需要)", required = true)
    private String idOrg;

    @ApiModelProperty(value = "订单号", required = true)
    @NotBlank(message = "请先选择预约订单号!")
    private String orderNum;

    @ApiModelProperty(value = "订单名称", required = true)
    private String orderName;

    @ApiModelProperty(value = "套餐ID")
    private String comboId;

    @ApiModelProperty(value = "套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "销售经理ID", required = true)
    @NotBlank(message = "销售经理不能为空!")
    private String xsjlId;

    @ApiModelProperty(value = "销售经理", required = true)
    private String xsjlName;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检", required = true)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "预约人数(上午)", required = true)
    @NotNull(message = "预约人数不能为空!")
    private Integer countAm;

    @ApiModelProperty(value = "预约人数(下午)")
    private Integer countPm;

    @ApiModelProperty(value = "来捡人数(上午)")
    private Integer finishedAm;

    @ApiModelProperty(value = "来捡人数(下午)")
    private Integer finishedPm;

    @ApiModelProperty(value = "备注", required = true)
    private String remark;

    @ApiModelProperty(value = "来捡状态：1.暂无来捡 2.已部分来捡 3.全部来捡")
    private Integer status;

    @ApiModelProperty(value = "是否已删除：0.否 1.是")
    private Integer isDelete;

    @ApiModelProperty(value = "预约人id")
    private String creatorId;

    @ApiModelProperty(value = "预约人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "更新者id")
    private String modifier;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;
}
