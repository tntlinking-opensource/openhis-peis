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
 * 合并结伦词(MdBasMerge)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
@Data
@TableName("md_bas_merge")
@ApiModel(value = "MdBasMerge", description = "合并结伦词实体类")
public class MdBasMerge extends Model<MdBasMerge> implements Serializable {
    private static final long serialVersionUID = -32988747931836643L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "结论名称")
    private String mergeName;

    @ApiModelProperty(value = "总检建议")
    private String suggestion;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "团检建议")
    private String tjjy;

    @ApiModelProperty(value = "创建人")
    private String creater;
}
