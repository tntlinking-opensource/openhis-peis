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
 * KS问诊——最近检查人(WzLatestRummager)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:50
 */
@Data
@TableName("WZ_LATEST_RUMMAGER")
@ApiModel(value = "WzLatestRummager", description = "KS问诊——最近检查人实体类")
public class WzLatestRummager extends Model<WzLatestRummager> implements Serializable {
    private static final long serialVersionUID = -60830318421893780L;

    @ApiModelProperty(value = "分中心ID")
    private String cid;

    @ApiModelProperty(value = "科室ID")
    private String ksId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "检查人ID")
    private String rummagerId;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;
}
