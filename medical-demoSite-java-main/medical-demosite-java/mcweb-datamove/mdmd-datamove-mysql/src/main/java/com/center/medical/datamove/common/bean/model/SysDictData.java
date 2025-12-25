package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 字典数据表(SysDictData)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Data
@TableName("sys_dict_data")
@ApiModel(value = "SysDictData", description = "字典数据表实体类")
public class SysDictData extends Model<SysDictData> implements Serializable {
    private static final long serialVersionUID = -98407030586764512L;

    @TableId(value = "dict_code")
    @ApiModelProperty(value = "字典编码")
    private Long dictCode;

    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "样式属性（其他样式扩展）")
    private String cssClass;

    @ApiModelProperty(value = "表格回显样式")
    private String listClass;

    @ApiModelProperty(value = "是否默认（Y是 N否）")
    private String isDefault;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
