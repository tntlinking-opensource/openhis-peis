package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS问诊——最近检查人(WzLatestRummager)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_wz_latest_rummager")
@ApiModel(value = "WzLatestRummager", description = "KS问诊——最近检查人实体类")
public class WzLatestRummager extends Model<WzLatestRummager> implements Serializable {
    private static final long serialVersionUID = -73228033172538238L;

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
