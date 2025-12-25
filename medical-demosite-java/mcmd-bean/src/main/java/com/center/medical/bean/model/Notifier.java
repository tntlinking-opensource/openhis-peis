package com.center.medical.bean.model;

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
 * BG报告领取通知(Notifier)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_notifier")
@ApiModel(value = "Notifier", description = "BG报告领取通知实体类")
public class Notifier extends Model<Notifier> implements Serializable {
    private static final long serialVersionUID = 693252267601379933L;

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
