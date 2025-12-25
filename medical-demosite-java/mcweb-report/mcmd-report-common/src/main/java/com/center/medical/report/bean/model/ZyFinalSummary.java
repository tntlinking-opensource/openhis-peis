package com.center.medical.report.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业最终结论(ZyFinalSummary)表实体类
 *
 * @author makejava
 * @since 2024-12-18 09:30:14
 */
@Data
@TableName("md_zy_final_summary")
@ApiModel(value = "ZyFinalSummary", description = "职业最终结论")
public class ZyFinalSummary extends Model<ZyFinalSummary> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "zy_vs_summary.id")
    private String progessional;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @ApiModelProperty(value = "comments_progessional_id")
    private String commentsProgessionalId;

}

