package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信发送记录(SmsRecord)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_sms_record")
@ApiModel(value = "SmsRecord", description = "短信发送记录实体类")
public class SmsRecord extends Model<SmsRecord> implements Serializable {
    private static final long serialVersionUID = -87569302709839663L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "系统模板ID")
    private String idTemplate;

    @ApiModelProperty(value = "通知类型， 详见数据表：md_short_message_type")
    private String notifyType;

    @ApiModelProperty(value = "通知结果状态：0.通知失败 1.已取消 2.等待通知 3.已通知")
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



    public SmsRecord(String notifyType, String patientcode) {
        super();
        this.notifyType = notifyType;
        this.patientcode = patientcode;
    }

    public SmsRecord() {}
}
