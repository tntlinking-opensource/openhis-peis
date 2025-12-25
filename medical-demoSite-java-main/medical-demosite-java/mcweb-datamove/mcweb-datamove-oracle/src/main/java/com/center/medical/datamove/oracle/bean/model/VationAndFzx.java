package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (VationAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:51
 */
@Data
@TableName("VATION_AND_FZX")
@ApiModel(value = "VationAndFzx", description = "$tableInfo.comment实体类")
public class VationAndFzx extends Model<VationAndFzx> implements Serializable {
    private static final long serialVersionUID = 556935323869433895L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String vationId;

    @ApiModelProperty(value = "${column.comment}")
    private String fzxId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer xzzt;
}
