package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 通知公告表(SysNotice)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Data
@TableName("sys_notice")
@ApiModel(value = "SysNotice", description = "通知公告表实体类")
public class SysNotice extends Model<SysNotice> implements Serializable {
    private static final long serialVersionUID = -55373277364297321L;

    @TableId(value = "notice_id")
    @ApiModelProperty(value = "公告ID")
    private Integer noticeId;

    @ApiModelProperty(value = "公告标题")
    private String noticeTitle;

    @ApiModelProperty(value = "公告类型（1通知 2公告）")
    private String noticeType;

    @ApiModelProperty(value = "公告内容")
    private Object noticeContent;

    @ApiModelProperty(value = "公告状态（0正常 1关闭）")
    private String status;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
