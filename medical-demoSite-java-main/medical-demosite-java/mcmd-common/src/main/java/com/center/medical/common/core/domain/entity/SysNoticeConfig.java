package com.center.medical.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知配置表 sys_notice_config
 *
 */
@Data
@TableName("sys_notice_config")
@ApiModel(value = "通知配置表", description = "通知配置表实体类")
public class SysNoticeConfig extends Model<SysNoticeConfig> implements Serializable {

    private static final long serialVersionUID = -952752700044563720L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "通知名称")
    private String noticeName;

    @ApiModelProperty(value = "通知正文")
    private String noticeText;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "说明")
    private String note;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "网上模板ID")
    private String templateId;

    @ApiModelProperty(value = "APPID")
    private String appid;

    @ApiModelProperty(value = "角色id，可以存多个")
    private String roleId;

    @ApiModelProperty(value = "操作人")
    private String operator;

}
