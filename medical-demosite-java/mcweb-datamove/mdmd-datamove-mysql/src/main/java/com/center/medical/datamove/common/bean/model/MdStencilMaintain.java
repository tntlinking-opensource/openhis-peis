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
 * 模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(MdStencilMaintain)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:48
 */
@Data
@TableName("md_stencil_maintain")
@ApiModel(value = "MdStencilMaintain", description = "模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)实体类")
public class MdStencilMaintain extends Model<MdStencilMaintain> implements Serializable {
    private static final long serialVersionUID = 896520112972122048L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "模板名称")
    private String modelName;

    @ApiModelProperty(value = "模板输入码")
    private String modelInputcode;

    @ApiModelProperty(value = "模板类型，详见：com.world.center.bean.enums.ModelType")
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
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.已删除 1.未删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否为头模板：0或null.否 1.是")
    private Integer isHead;
}
