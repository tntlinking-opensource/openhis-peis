package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通知配置表 添加或更新参数
 */
@Data
public class NoticeConfigSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -7548110708898680969L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "通知名称")
    private String noticeName;

    @ApiModelProperty(value = "通知正文")
    private String noticeText;

    @ApiModelProperty(value = "说明")
    private String note;

    @ApiModelProperty(value = "网上模板ID")
    private String templateId;

    @ApiModelProperty(value = "APPID")
    private String appid;

    @ApiModelProperty(value = "角色id，可以存多个")
    private String roleId;




}
