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
 * 领导目标表(Leadertarget)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:35
 */
@Data
@TableName("LEADERTARGET")
@ApiModel(value = "Leadertarget", description = "领导目标表实体类")
public class Leadertarget extends Model<Leadertarget> implements Serializable {
    private static final long serialVersionUID = -38484348695576363L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "第一季度目标")
    private Double dyjdmb;

    @ApiModelProperty(value = "第二季度目标")
    private Double dejdmb;

    @ApiModelProperty(value = "第三季度目标")
    private Double dsjdmb;

    @ApiModelProperty(value = "第四季度目标")
    private Double dijdmb;

    @ApiModelProperty(value = "全年目标")
    private Double ndmb;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}
