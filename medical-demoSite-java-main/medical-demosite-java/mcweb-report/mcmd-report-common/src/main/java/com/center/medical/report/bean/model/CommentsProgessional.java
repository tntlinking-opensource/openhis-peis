package com.center.medical.report.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业处理意见(CommentsProgessional)实体类
 *
 * @author 路飞船长
 * @since 2022-12-08 19:27:07
 */
@Data
@TableName("md_comments_progessional")
@ApiModel(value = "CommentsProgessional", description = "职业处理意见实体类")
public class CommentsProgessional extends Model<CommentsProgessional> implements Serializable {
    private static final long serialVersionUID = 576954500306723789L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "职业处理意见ID（基础数据中的）")
    private String progessionalId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
