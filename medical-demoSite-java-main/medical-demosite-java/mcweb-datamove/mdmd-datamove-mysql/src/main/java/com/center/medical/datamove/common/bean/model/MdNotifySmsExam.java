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
 * 来检短信提醒表(MdNotifySmsExam)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
@Data
@TableName("md_notify_sms_exam")
@ApiModel(value = "MdNotifySmsExam", description = "来检短信提醒表实体类")
public class MdNotifySmsExam extends Model<MdNotifySmsExam> implements Serializable {
    private static final long serialVersionUID = -95478664791068841L;

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

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
