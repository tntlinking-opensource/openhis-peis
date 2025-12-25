package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放(CrmCustomerOperateHistory)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:57
 */
@Data
@TableName("crm_customer_operate_history")
@ApiModel(value = "CrmCustomerOperateHistory", description = "客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放实体类")
public class CrmCustomerOperateHistory extends Model<CrmCustomerOperateHistory> implements Serializable {
    private static final long serialVersionUID = 152963902323719456L;

    @ApiModelProperty(value = "操作人ID")
    private String operateId;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "操作时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "客户ID")
    private String khdwid;

    @ApiModelProperty(value = "客户名称")
    private String khdwmc;

    @ApiModelProperty(value = "操作类型(名称)")
    private String operateType;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "销售经理名称")
    private String xsjlmc;

    @ApiModelProperty(value = "操作人名称")
    private String operateName;

    @ApiModelProperty(value = "分配人ID")
    private String fprid;

    @ApiModelProperty(value = "分配人名称")
    private String fprmc;

    @ApiModelProperty(value = "转移（原销售经理id）")
    private String fromXsjlid;

    @ApiModelProperty(value = "转移（原销售经理用户名）")
    private String fromXsjlname;

    @ApiModelProperty(value = "转移类型：0/未离职 1.已离职")
    private Integer transType;
}
