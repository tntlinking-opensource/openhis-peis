package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 用于保存科室的模板（个检用）、团检的模板、对比模板(StencilMaintain)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:05
 */
@Data
@TableName("STENCIL_MAINTAIN")
@ApiModel(value = "StencilMaintain", description = "用于保存科室的模板（个检用）、团检的模板、对比模板实体类")
public class StencilMaintain extends Model<StencilMaintain> implements Serializable {
    private static final long serialVersionUID = 370901878253912825L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "模板名称")
    private String modelName;

    @ApiModelProperty(value = "模板输入码")
    private String modelInputcode;

    @ApiModelProperty(value = "科室(个检)、团检、对比")
    private Integer modelType;

    @ApiModelProperty(value = "分中心")
    private String centerName;

    @ApiModelProperty(value = "分中心ID")
    private String centerId;

    @ApiModelProperty(value = "0：是；1：否；")
    private Integer isDefault;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "模板存放地址（可存放多个，用“；”分割）")
    private String modelUrls;

    @ApiModelProperty(value = "0：健康；1：职业；")
    private Integer suitableType;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否为头模板")
    private Integer isHead;
}
