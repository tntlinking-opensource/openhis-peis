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
 * JC短信信息分类表(MdShortMessageType)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:40
 */
@Data
@TableName("md_short_message_type")
@ApiModel(value = "MdShortMessageType", description = "JC短信信息分类表实体类")
public class MdShortMessageType extends Model<MdShortMessageType> implements Serializable {
    private static final long serialVersionUID = 450488356645812431L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "短消息类型名称")
    private String typeName;

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

    @ApiModelProperty(value = "可使用的参数，逗号分隔")
    private String params;
}
