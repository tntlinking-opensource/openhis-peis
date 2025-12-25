package com.center.medical.datamove.oracle.bean.model;


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
 * KS外送手动结果表(OutsideHand)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:32
 */
@Data
@TableName("OUTSIDE_HAND")
@ApiModel(value = "OutsideHand", description = "KS外送手动结果表实体类")
public class OutsideHand extends Model<OutsideHand> implements Serializable {
    private static final long serialVersionUID = 944125713503444698L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "结果值")
    private String result;

    @ApiModelProperty(value = "收费项目id")
    private String idCharge;

    @ApiModelProperty(value = "${column.comment}")
    private String idCheck;

    @ApiModelProperty(value = "${column.comment}")
    private String resultHand;
}
