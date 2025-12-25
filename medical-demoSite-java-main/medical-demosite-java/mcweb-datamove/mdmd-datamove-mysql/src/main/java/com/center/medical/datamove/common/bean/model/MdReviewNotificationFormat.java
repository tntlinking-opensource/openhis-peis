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
 * JC复查通知格式(MdReviewNotificationFormat)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:19
 */
@Data
@TableName("md_review_notification_format")
@ApiModel(value = "MdReviewNotificationFormat", description = "JC复查通知格式实体类")
public class MdReviewNotificationFormat extends Model<MdReviewNotificationFormat> implements Serializable {
    private static final long serialVersionUID = 158966122228571896L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}
