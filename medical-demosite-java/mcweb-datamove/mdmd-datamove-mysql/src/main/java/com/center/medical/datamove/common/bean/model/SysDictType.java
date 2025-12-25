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
 * 字典类型表(SysDictType)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
@Data
@TableName("sys_dict_type")
@ApiModel(value = "SysDictType", description = "字典类型表实体类")
public class SysDictType extends Model<SysDictType> implements Serializable {
    private static final long serialVersionUID = -73646728698329783L;

    @TableId(value = "dict_id")
    @ApiModelProperty(value = "字典主键")
    private Long dictId;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

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
