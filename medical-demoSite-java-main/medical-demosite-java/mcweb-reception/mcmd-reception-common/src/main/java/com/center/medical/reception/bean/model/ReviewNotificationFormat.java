package com.center.medical.reception.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC复查通知格式(ReviewNotificationFormat)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:27
 */
@Data
@TableName("md_review_notification_format")
@ApiModel(value = "ReviewNotificationFormat", description = "JC复查通知格式实体类")
public class ReviewNotificationFormat extends Model<ReviewNotificationFormat> implements Serializable {
    private static final long serialVersionUID = 770359469107514392L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "通知格式名称")
    private String formatName;

    @ApiModelProperty(value = "拼音码")
    private String formatCode;

    @ApiModelProperty(value = "存放位置")
    private String formatFile;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
