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
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CrmCustomerTransfer)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:58
 */
@Data
@TableName("crm_customer_transfer")
@ApiModel(value = "CrmCustomerTransfer", description = "客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。实体类")
public class CrmCustomerTransfer extends Model<CrmCustomerTransfer> implements Serializable {
    private static final long serialVersionUID = -10772713002975102L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "转移时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "原销售经理id")
    private String fromXsjlid;

    @ApiModelProperty(value = "变更至销售经理id")
    private String toXsjlid;

    @ApiModelProperty(value = "变更至销售经理名称")
    private String toXsjl;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "下载状态：0.未下载 1.已下载")
    private Integer xzzt;

    @ApiModelProperty(value = "下载时间")
    private Date xzdate;

    @ApiModelProperty(value = "客户id")
    private String customerId;
}
