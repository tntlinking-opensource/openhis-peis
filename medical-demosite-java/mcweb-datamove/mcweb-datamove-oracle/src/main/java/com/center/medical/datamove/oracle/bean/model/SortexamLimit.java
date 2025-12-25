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
 * 预约管理(SortexamLimit)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:02
 */
@Data
@TableName("SORTEXAM_LIMIT")
@ApiModel(value = "SortexamLimit", description = "预约管理实体类")
public class SortexamLimit extends Model<SortexamLimit> implements Serializable {
    private static final long serialVersionUID = -45949856299904901L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
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
    private String vip;

    @ApiModelProperty(value = "VVIP人数上限")
    private String vvip;

    @ApiModelProperty(value = "普通上午人数上限")
    private String am;

    @ApiModelProperty(value = "普通下午人数上限")
    private String pm;
}
