package com.center.medical.datamove.oracle.bean.model;


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
 * 来检短信提醒表(NotifySmsExam)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:12
 */
@Data
@TableName("NOTIFY_SMS_EXAM")
@ApiModel(value = "NotifySmsExam", description = "来检短信提醒表实体类")
public class NotifySmsExam extends Model<NotifySmsExam> implements Serializable {
    private static final long serialVersionUID = 357140226173100758L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
