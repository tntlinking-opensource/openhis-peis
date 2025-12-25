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
 * 科室临时加项表(MdTempFeeitem)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:55
 */
@Data
@TableName("md_temp_feeitem")
@ApiModel(value = "MdTempFeeitem", description = "科室临时加项表实体类")
public class MdTempFeeitem extends Model<MdTempFeeitem> implements Serializable {
    private static final long serialVersionUID = 169836069940528436L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

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
