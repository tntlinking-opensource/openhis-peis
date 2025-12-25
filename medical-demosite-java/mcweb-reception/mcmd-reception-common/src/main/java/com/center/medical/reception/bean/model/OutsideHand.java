package com.center.medical.reception.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS外送手动结果表(OutsideHand)表实体类
 *
 * @author ay
 * @since 2022-12-07 15:11:14
 */
@Data
@TableName("md_outside_hand")
@ApiModel(value = "OutsideHand", description = "KS外送手动结果表实体类")
public class OutsideHand extends Model<OutsideHand> implements Serializable {
    private static final long serialVersionUID = 353799296626937965L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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
