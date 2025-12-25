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
 * 每日排检上限(MdSortexamLimit)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:45
 */
@Data
@TableName("md_sortexam_limit")
@ApiModel(value = "MdSortexamLimit", description = "每日排检上限实体类")
public class MdSortexamLimit extends Model<MdSortexamLimit> implements Serializable {
    private static final long serialVersionUID = -60829279903754170L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiModelProperty(value = "排检日期")
    private Date sortDate;

    @ApiModelProperty(value = "VIP人数上限")
    private Integer vip;

    @ApiModelProperty(value = "VVIP人数上限")
    private Integer vvip;

    @ApiModelProperty(value = "普通上午人数上限")
    private Integer am;

    @ApiModelProperty(value = "普通下午人数上限")
    private Integer pm;
}
