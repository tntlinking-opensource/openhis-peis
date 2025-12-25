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
 * 合并结伦词中间表(MdBasMergeConclusion)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
@Data
@TableName("md_bas_merge_conclusion")
@ApiModel(value = "MdBasMergeConclusion", description = "合并结伦词中间表实体类")
public class MdBasMergeConclusion extends Model<MdBasMergeConclusion> implements Serializable {
    private static final long serialVersionUID = -44627648093743756L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "结伦词ID")
    private String conclusionId;

    @ApiModelProperty(value = "合并结伦词ID")
    private String mergeId;
}
