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
 * KS问诊——最近检查人(MdWzLatestRummager)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:24
 */
@Data
@TableName("md_wz_latest_rummager")
@ApiModel(value = "MdWzLatestRummager", description = "KS问诊——最近检查人实体类")
public class MdWzLatestRummager extends Model<MdWzLatestRummager> implements Serializable {
    private static final long serialVersionUID = -21539648630109170L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;

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
}
