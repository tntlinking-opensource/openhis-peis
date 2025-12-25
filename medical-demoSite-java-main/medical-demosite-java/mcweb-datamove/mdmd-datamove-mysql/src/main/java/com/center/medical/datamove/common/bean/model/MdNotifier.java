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
 * BG报告领取通知(MdNotifier)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
@Data
@TableName("md_notifier")
@ApiModel(value = "MdNotifier", description = "BG报告领取通知实体类")
public class MdNotifier extends Model<MdNotifier> implements Serializable {
    private static final long serialVersionUID = 963771900221140994L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "报告主表ID")
    private String reportId;

    @ApiModelProperty(value = "通知人ID")
    private String notifierId;

    @ApiModelProperty(value = "通知方式ID")
    private String notificationMode;

    @ApiModelProperty(value = "通知内容")
    private String notificationDetails;

    @ApiModelProperty(value = "通知时间")
    private Date notificationTime;

    @ApiModelProperty(value = "通知结果")
    private String notificationResult;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String memo;
}
