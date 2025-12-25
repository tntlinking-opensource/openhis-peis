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
 * 最小套餐与分中心关联表(MdComboandfzx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:16
 */
@Data
@TableName("md_comboandfzx")
@ApiModel(value = "MdComboandfzx", description = "最小套餐与分中心关联表实体类")
public class MdComboandfzx extends Model<MdComboandfzx> implements Serializable {
    private static final long serialVersionUID = 849883458711505718L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "同步状态：0.未同步 1.同步 2.更新")
    private Integer tbzt;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}
