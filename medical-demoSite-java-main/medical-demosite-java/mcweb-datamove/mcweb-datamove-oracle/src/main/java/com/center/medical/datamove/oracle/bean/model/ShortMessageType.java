package com.center.medical.datamove.oracle.bean.model;


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
 * JC短信信息分类表(ShortMessageType)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:58
 */
@Data
@TableName("SHORT_MESSAGE_TYPE")
@ApiModel(value = "ShortMessageType", description = "JC短信信息分类表实体类")
public class ShortMessageType extends Model<ShortMessageType> implements Serializable {
    private static final long serialVersionUID = -55037526103591967L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @ApiModelProperty(value = "可使用的参数，逗号分隔")
    private String params;
}
