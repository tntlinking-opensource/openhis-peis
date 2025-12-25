package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ZJ总检结论词表(TotalVerdict)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:36
 */
@Data
@TableName("TOTAL_VERDICT")
@ApiModel(value = "TotalVerdict", description = "ZJ总检结论词表实体类")
public class TotalVerdict extends Model<TotalVerdict> implements Serializable {
    private static final long serialVersionUID = 268457576239816027L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;

    @ApiModelProperty(value = "总检主表ID")
    private String totalId;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @ApiModelProperty(value = "0:健康 1:职业")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "${column.comment}")
    private Integer flag;

    @ApiModelProperty(value = "${column.comment}")
    private String totalAdvice;

    @ApiModelProperty(value = "${column.comment}")
    private String basconclusionName;

    @ApiModelProperty(value = "${column.comment}")
    private String mergeId;

    @ApiModelProperty(value = "${column.comment}")
    private String mergeName;

    @ApiModelProperty(value = "${column.comment}")
    private Double verdictSort;

    @ApiModelProperty(value = "${column.comment}")
    private String suggestiongroup;
}
