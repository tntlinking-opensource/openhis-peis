package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * XS销售目标(Selltarget)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:23
 */
@Data
@TableName("crm_selltarget")
@ApiModel(value = "Selltarget", description = "XS销售目标实体类")
public class Selltarget extends Model<Selltarget> implements Serializable {
    private static final long serialVersionUID = -20335207428921461L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String bz;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户id")
    private String userid;


}
