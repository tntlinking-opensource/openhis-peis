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
 * JC短信信息表(MdShortmessage)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:42
 */
@Data
@TableName("md_shortmessage")
@ApiModel(value = "MdShortmessage", description = "JC短信信息表实体类")
public class MdShortmessage extends Model<MdShortmessage> implements Serializable {
    private static final long serialVersionUID = -52790815563542547L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "短消息类型ID， 详见数据表：md_short_message_type")
    private String messageType;

    @ApiModelProperty(value = "短消息名称")
    private String messageName;

    @ApiModelProperty(value = "短消息正文")
    private String messageText;

    @ApiModelProperty(value = "输入码")
    private String inputcode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "网上模板ID")
    private String templateId;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "APPID")
    private String appid;
}
