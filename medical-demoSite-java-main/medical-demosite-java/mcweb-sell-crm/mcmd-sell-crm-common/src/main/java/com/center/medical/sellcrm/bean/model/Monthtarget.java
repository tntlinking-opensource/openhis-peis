package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售月度计划(Monthtarget)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_monthtarget")
@ApiModel(value = "Monthtarget", description = "销售月度计划实体类")
public class Monthtarget extends Model<Monthtarget> implements Serializable {
    private static final long serialVersionUID = -12464966917578075L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "销售经理id")
    private String xsjlid;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "分中心id")
    private String fzxid;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "target1")
    private Double target1;

    @ApiModelProperty(value = "target2")
    private Double target2;

    @ApiModelProperty(value = "target3")
    private Double target3;

    @ApiModelProperty(value = "target4")
    private Double target4;

    @ApiModelProperty(value = "target5")
    private Double target5;

    @ApiModelProperty(value = "target6")
    private Double target6;

    @ApiModelProperty(value = "target7")
    private Double target7;

    @ApiModelProperty(value = "target8")
    private Double target8;

    @ApiModelProperty(value = "target9")
    private Double target9;

    @ApiModelProperty(value = "target10")
    private Double target10;

    @ApiModelProperty(value = "target11")
    private Double target11;

    @ApiModelProperty(value = "target12")
    private Double target12;
}
