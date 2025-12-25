package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(StencilMaintain)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_stencil_maintain")
@ApiModel(value = "StencilMaintain", description = "模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)实体类")
public class StencilMaintain extends Model<StencilMaintain> implements Serializable {
    private static final long serialVersionUID = 420914467394372640L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "模板名称")
    private String modelName;

    @ApiModelProperty(value = "模板输入码")
    private String modelInputcode;

    @ApiModelProperty(value = "模板类型，0科室(个检),1团检,2对比,3单科室头模板")
    private Integer modelType;

    @ApiModelProperty(value = "分中心")
    private String centerName;

    @ApiModelProperty(value = "分中心ID")
    private String centerId;

    @ApiModelProperty(value = "是否为默认：0是 1否；")
    private Integer isDefault;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "模板存放地址（可存放多个，用“；”分割）")
    private String modelUrls;

    @ApiModelProperty(value = "适用于：0健康 1职业")
    private Integer suitableType;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否为头模板 0： 是；1：不是")
    private Integer isHead;
}
