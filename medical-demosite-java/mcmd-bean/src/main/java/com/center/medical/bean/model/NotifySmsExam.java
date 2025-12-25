package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 来检短信提醒表(NotifySmsExam)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_notify_sms_exam")
@ApiModel(value = "NotifySmsExam", description = "来检短信提醒表实体类")
public class NotifySmsExam extends Model<NotifySmsExam> implements Serializable {
    private static final long serialVersionUID = 263581762628052697L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String idOrder;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "联系人")
    private String idContact;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "操作人")
    private String createer;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
