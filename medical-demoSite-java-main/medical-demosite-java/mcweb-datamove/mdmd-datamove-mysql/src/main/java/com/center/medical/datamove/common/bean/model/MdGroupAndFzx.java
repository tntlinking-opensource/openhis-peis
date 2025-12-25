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
 * 分组分中心(MdGroupAndFzx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
@Data
@TableName("md_group_and_fzx")
@ApiModel(value = "MdGroupAndFzx", description = "分组分中心实体类")
public class MdGroupAndFzx extends Model<MdGroupAndFzx> implements Serializable {
    private static final long serialVersionUID = -56627239906075881L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "分组ID")
    private String groupId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "下载状态：0.未下载 1.已下载")
    private Integer xzzt;
}
