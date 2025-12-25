package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室临时加项表(TempFeeitem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_temp_feeitem")
@ApiModel(value = "TempFeeitem", description = "科室临时加项表实体类")
public class TempFeeitem extends Model<TempFeeitem> implements Serializable {
    private static final long serialVersionUID = 897066316460448170L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "收费项目基础表id")
    private String itemId;

    @ApiModelProperty(value = "加项医生")
    private String doctorUsername;

    @ApiModelProperty(value = "优惠价")
    private Double price;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "体检者收费项目id")
    private String feeitemId;
}
