package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单与分中心关联表(MdOrderandfzx)表实体类
 *
 * @author ay
 * @since 2023-07-25 21:09:36
 */
@Data
@TableName("md_orderandfzx")
@ApiModel(value = "MdOrderandfzx", description = "订单与分中心关联表实体类")
public class MdOrderandfzx extends Model<MdOrderandfzx> implements Serializable {
    private static final long serialVersionUID = 172310150999086262L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String ddid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "同步状态：0.未同步 1.同步 2.更新")
    private Integer tbzt;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "备单日期")
    private Date bdrq;

    @ApiModelProperty(value = "下载状态：Null或其他.未下载 1.已下载")
    private Integer xzzt;

    @ApiModelProperty(value = "下载时间")
    private Date xzsj;

    @ApiModelProperty(value = "下载状态；0未下载、1已下载、null不需要下载")
    private Integer ddxzzt;

    @ApiModelProperty(value = "线上变更前台须知后的下载状态。0未下载、1已下载、null不需要下载")
    private Integer xzxzzt;

    @ApiModelProperty(value = "隐藏展示下载状态  0未下载 1已下载 null不需要下载")
    private Integer ycxzzt;

    @ApiModelProperty(value = "开单助理下载状态  0未下载 1已下载 null不需要下载")
    private Integer kdzlzt;


    public MdOrderandfzx(String ddid, String fzxid) {
        this.ddid = ddid;
        this.fzxid = fzxid;
    }


    public MdOrderandfzx() {
    }
}
