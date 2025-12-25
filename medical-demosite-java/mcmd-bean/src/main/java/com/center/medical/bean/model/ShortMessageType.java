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
 * JC短信信息分类表(ShortMessageType)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_short_message_type")
@ApiModel(value = "ShortMessageType", description = "JC短信信息分类表实体类")
public class ShortMessageType extends Model<ShortMessageType> implements Serializable {
    private static final long serialVersionUID = 613342749626761542L;

    //检前通知   登记列表发送短信新增
    public static final String PREDETECTION ="7";
    //核酸采样短信
    public static final String NUCLEIN="6";

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
