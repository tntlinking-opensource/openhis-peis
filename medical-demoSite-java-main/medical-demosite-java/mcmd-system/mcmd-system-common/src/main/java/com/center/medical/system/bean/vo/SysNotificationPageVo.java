package com.center.medical.system.bean.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知记录表分页返回数据
 */
@Data
public class SysNotificationPageVo implements Serializable {
    private static final long serialVersionUID = 5229388637480029856L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "消息类型")
    private String noticeConfigId;

    @ApiModelProperty(value = "通知名称")
    private String noticeName;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "是否已读")
    private Integer isRead;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;


}
