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
 * ZJ复查项目表(ReviewProject)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:24
 */
@Data
@TableName("REVIEW_PROJECT")
@ApiModel(value = "ReviewProject", description = "ZJ复查项目表实体类")
public class ReviewProject extends Model<ReviewProject> implements Serializable {
    private static final long serialVersionUID = -73984204005707208L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String itemsId;

    @ApiModelProperty(value = "收费项目名称")
    private String itemsName;

    @ApiModelProperty(value = "复查表ID")
    private String reviewId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
