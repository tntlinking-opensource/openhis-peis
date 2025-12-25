package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KF短信通知记录(SmsRecord)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:00
 */
@Data
@TableName("SMS_RECORD")
@ApiModel(value = "SmsRecord", description = "KF短信通知记录实体类")
public class SmsRecord extends Model<SmsRecord> implements Serializable {
    private static final long serialVersionUID = 472651239361935355L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "模板ID")
    private String idTemplate;

    @ApiModelProperty(value = "通知类型")
    private String notifyType;

    @ApiModelProperty(value = "通知状态 0:未通知 1:已通知 2:等待通知 3:通知失败")
    private Integer notifyResult;

    @ApiModelProperty(value = "通知时间 0:未通知 1:已通知 2:通知失败")
    private Date notifyTime;

    @ApiModelProperty(value = "操作人")
    private String creater;

    @ApiModelProperty(value = "立即发送(1是0否)")
    private Integer isImmediately;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "通知内容")
    private String notifyContent;

    @ApiModelProperty(value = "档案ID")
    private String archiveId;
}
