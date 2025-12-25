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
 * XS销售目标(Selltarget)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:56
 */
@Data
@TableName("SELLTARGET")
@ApiModel(value = "Selltarget", description = "XS销售目标实体类")
public class Selltarget extends Model<Selltarget> implements Serializable {
    private static final long serialVersionUID = 932545998986158048L;

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
    private Double qnmb;

    @ApiModelProperty(value = "全年回款额")
    private Double qnhke;

    @ApiModelProperty(value = "回款比率")
    private Double hkbl;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String bz;
}
