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
 * 团体任务分中心（不会被同步）(MdVationAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:28
 */
@Data
@TableName("md_vation_and_fzx")
@ApiModel(value = "MdVationAndFzx", description = "团体任务分中心（不会被同步）实体类")
public class MdVationAndFzx extends Model<MdVationAndFzx> implements Serializable {
    private static final long serialVersionUID = 342709139136873573L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "团体任务ID")
    private String vationId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "下载状态：0未下载 1已下载")
    private Integer xzzt;
}
