package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 每笔银行汇款结算详情(KdReser)表实体类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:44
 */
@Data
@TableName("kd_reser")
@ApiModel(value = "KdReser", description = "每笔银行汇款结算详情实体类")
public class KdReser extends Model<KdReser> implements Serializable {
    private static final long serialVersionUID = 145634165266941940L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "银行汇款流水号")
    private String idRemitter;

    @ApiModelProperty(value = "结算方式的ID")
    private String idRemittanceway;

    @ApiModelProperty(value = "客户名称")
    private String customername;

    @ApiModelProperty(value = "客户id：团体号/体检号/卡号一类的")
    private String idCustomer;

    @ApiModelProperty(value = "销售经理ID")
    private String idFeecharger;

    @ApiModelProperty(value = "创建人ID")
    private String idCreator;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "备注")
    private String note;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "审核人ID")
    private String idAudit;

    @ApiModelProperty(value = "是否同步：0.未同步 1.已同步")
    private String isUpdate;

    @ApiModelProperty(value = "是否审核：0.未审核 1.已审核")
    private String isAudit;

    @ApiModelProperty(value = "审核日期")
    private Date auditdate;

    @ApiModelProperty(value = "修改人")
    private String idChange;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @TableField(exist = false)
    @ApiModelProperty(value = "销售经理名称")
    private String xsjl;

    @TableField(exist = false)
    @ApiModelProperty(value = "支付方式名称")
    private String payWayName;

    @TableField(exist = false)
    @ApiModelProperty(value = "创建人姓名")
    private String creatorName;

    @TableField(exist = false)
    @ApiModelProperty(value = "修改人姓名")
    private String changeName;


    @TableField(exist = false)
    @ApiModelProperty(value = "审核人姓名")
    private String auditName;
}
