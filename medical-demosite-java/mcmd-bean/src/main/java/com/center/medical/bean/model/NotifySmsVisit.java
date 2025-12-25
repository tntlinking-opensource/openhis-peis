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
 * 短信回访表(NotifySmsVisit)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_notify_sms_visit")
@ApiModel(value = "NotifySmsVisit", description = "短信回访表实体类")
public class NotifySmsVisit extends Model<NotifySmsVisit> implements Serializable {
    private static final long serialVersionUID = 698851583922733287L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "回访关联表ID")
    private String idVisit;

    @ApiModelProperty(value = "模板ID")
    private String idTemplate;

    @ApiModelProperty(value = "通知状态：0.未通知 1.已通知 2.等待通知 3.通知失败")
    private Integer notifyResult;

    @ApiModelProperty(value = "通知时间：0.未通知 1.已通知 2.通知失败")
    private Date notifyTime;

    @ApiModelProperty(value = "操作人")
    private String createer;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
