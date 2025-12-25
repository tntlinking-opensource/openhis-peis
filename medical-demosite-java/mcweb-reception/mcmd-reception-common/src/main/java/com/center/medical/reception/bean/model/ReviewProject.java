package com.center.medical.reception.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ZJ复查项目表(ReviewProject)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:28
 */
@Data
@TableName("md_review_project")
@ApiModel(value = "ReviewProject", description = "ZJ复查项目表实体类")
public class ReviewProject extends Model<ReviewProject> implements Serializable {
    private static final long serialVersionUID = 679923610211708359L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String itemsId;

    @ApiModelProperty(value = "收费项目名称")
    private String itemsName;

    @ApiModelProperty(value = "复查表ID")
    private String reviewId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
