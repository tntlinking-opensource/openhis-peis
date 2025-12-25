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
 * kingdeereserway(KdReserway)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
@Data
@TableName("kd_reserway")
@ApiModel(value = "KdReserway", description = "kingdeereserway实体类")
public class KdReserway extends Model<KdReserway> implements Serializable {
    private static final long serialVersionUID = -94967759517016241L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "account_no")
    private String accountNo;

    @ApiModelProperty(value = "account_name")
    private String accountName;

    @ApiModelProperty(value = "use_status_id")
    private String useStatusId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
