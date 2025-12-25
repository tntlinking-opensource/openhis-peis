package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC短信信息表(Shortmessage)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_shortmessage")
@ApiModel(value = "Shortmessage", description = "JC短信信息表实体类")
public class Shortmessage extends Model<Shortmessage> implements Serializable {
    private static final long serialVersionUID = -51901214039697960L;

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

    public String getInputcode() {
        if (StringUtils.isEmpty(inputcode)) {
            return null;
        }
        return inputcode.toUpperCase();
    }

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

    @TableField(exist = false)
    @ApiModelProperty(value = "短消息类型名称")
    private String typeName;
}
