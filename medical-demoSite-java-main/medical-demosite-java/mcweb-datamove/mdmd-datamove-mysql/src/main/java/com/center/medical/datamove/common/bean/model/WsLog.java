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
 * 网站日志(WsLog)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
@Data
@TableName("ws_log")
@ApiModel(value = "WsLog", description = "网站日志实体类")
public class WsLog extends Model<WsLog> implements Serializable {
    private static final long serialVersionUID = 618278331034104525L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "操作用户id")
    private String czUser;

    @ApiModelProperty(value = "操作时间")
    private Date czDate;

    @ApiModelProperty(value = "点击类型")
    private String djType;

    @ApiModelProperty(value = "操作类型")
    private String czType;

    @ApiModelProperty(value = "操作对象")
    private String czObject;

    @ApiModelProperty(value = "IP")
    private String ipDz;

    @ApiModelProperty(value = "网站名称")
    private String pcName;

    @ApiModelProperty(value = "备注")
    private String rmk;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "czdj")
    private String czdj;

    @ApiModelProperty(value = "分中心id")
    private String fzx;
}
