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
 * 通知方式（领取方式）表(MdNotificationmethod)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
@Data
@TableName("md_notificationmethod")
@ApiModel(value = "MdNotificationmethod", description = "通知方式（领取方式）表实体类")
public class MdNotificationmethod extends Model<MdNotificationmethod> implements Serializable {
    private static final long serialVersionUID = 320758906218674488L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "通知方式")
    private String methodName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否发送通知：0.否 1.是")
    private String isSendNotice;
}
