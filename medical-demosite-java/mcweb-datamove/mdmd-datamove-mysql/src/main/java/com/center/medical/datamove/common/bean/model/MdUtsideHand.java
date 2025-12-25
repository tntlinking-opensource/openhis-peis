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
 * KS外送手动结果表(MdUtsideHand)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:27
 */
@Data
@TableName("md_utside_hand")
@ApiModel(value = "MdUtsideHand", description = "KS外送手动结果表实体类")
public class MdUtsideHand extends Model<MdUtsideHand> implements Serializable {
    private static final long serialVersionUID = -58220636253196573L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "检查项目ID")
    private String idCheck;

    @ApiModelProperty(value = "结果值")
    private String resultHand;
}
