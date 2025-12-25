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
 * (HarmAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:12
 */
@Data
@TableName("HARM_AND_FZX")
@ApiModel(value = "HarmAndFzx", description = "$tableInfo.comment实体类")
public class HarmAndFzx extends Model<HarmAndFzx> implements Serializable {
    private static final long serialVersionUID = 934627473349175697L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String harmId;

    @ApiModelProperty(value = "${column.comment}")
    private String fzxId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tbzt;
}
