package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合并结伦词中间表(BasMergeConclusion)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:54
 */
@Data
@TableName("md_bas_merge_conclusion")
@ApiModel(value = "BasMergeConclusion", description = "合并结伦词中间表实体类")
public class BasMergeConclusion extends Model<BasMergeConclusion> implements Serializable {
    private static final long serialVersionUID = -28643495005007180L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "结伦词ID")
    private String conclusionId;

    @ApiModelProperty(value = "合并结伦词ID")
    private String mergeId;
}
