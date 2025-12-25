package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * kingdeereserway(KdReserway)表实体类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:44
 */
@Data
@TableName("kd_reserway")
@ApiModel(value = "KdReserway", description = "kingdeereserway实体类")
public class KdReserway extends Model<KdReserway> implements Serializable {
    private static final long serialVersionUID = 226937227451775342L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "account_no")
    private String accountNo;

    @ApiModelProperty(value = "account_name")
    private String accountName;

    @ApiModelProperty(value = "use_status_id")
    private String useStatusId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
