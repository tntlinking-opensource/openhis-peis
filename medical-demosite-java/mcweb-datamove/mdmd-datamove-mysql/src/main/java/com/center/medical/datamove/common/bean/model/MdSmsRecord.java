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
 * 短信发送记录(MdSmsRecord)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:43
 */
@Data
@TableName("md_sms_record")
@ApiModel(value = "MdSmsRecord", description = "短信发送记录实体类")
public class MdSmsRecord extends Model<MdSmsRecord> implements Serializable {
    private static final long serialVersionUID = -88101078357861896L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "系统模板ID")
    private String idTemplate;

    @ApiModelProperty(value = "通知类型， 详见数据表：md_short_message_type")
    private String notifyType;

    @ApiModelProperty(value = "通知结果状态， 详见：com.world.center.bean.enums.SmsNotifyResultType")
    private Integer notifyResult;

    @ApiModelProperty(value = "通知时间")
    private Date notifyTime;

    @ApiModelProperty(value = "操作人")
    private String creater;

    @ApiModelProperty(value = "是否立即发送：0.否 1.是")
    private Integer isImmediately;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "通知内容")
    private String notifyContent;

    @ApiModelProperty(value = "档案ID")
    private String archiveId;
}
