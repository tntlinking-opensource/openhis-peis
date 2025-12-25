package com.center.medical.workflow.bean.model;


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
 * 工作流-预约会员类型(WorkflowReservationType)表实体类
 *
 * @author ay
 * @since 2024-03-07 13:36:10
 */
@Data
@TableName("md_workflow_reservation_type")
@ApiModel(value = "WorkflowReservationType", description = "工作流-预约会员类型实体类")
public class WorkflowReservationType extends Model<WorkflowReservationType> implements Serializable {
    private static final long serialVersionUID = 671135882071662400L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "工作流实例记录ID")
    private String caseId;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;


    @ApiModelProperty(value = "应付金额")
    private Double moneyamount;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "创建人")
    private String creator;


    @ApiModelProperty(value = "订单id")
    private String orderId;
}
