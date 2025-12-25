package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 普通套餐与收费项目关联表(Mealanditem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-21 19:41:49
 */
@Data
@TableName("md_mealanditem")
@ApiModel(value = "Mealanditem", description = "普通套餐与收费项目关联表实体类")
public class Mealanditem extends Model<Mealanditem> implements Serializable {
    private static final long serialVersionUID = -73400792423259545L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "收费项目ID")
    private String sfxmid;

    @ApiModelProperty(value = "是否必选：0或null.否 1.是")
    private Integer sfbx;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否复制套餐：0或null.否 1.是")
    private Integer isSystem;

    @ApiModelProperty(value = "优惠价")
    private Double price;

    @ApiModelProperty(value = "排序")
    private Integer itemSort;

    @ApiModelProperty(value = "分组")
    private Integer itemGroup;

    @ApiModelProperty(value = "分组类型：0.组内选 1.组间选 2.组任选")
    private Integer groupType;


    @TableField(exist = false)
    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @TableField(exist = false)
    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @TableField(exist = false)
    @ApiModelProperty(value = "性别0男，1女，其他通用")
    private Integer xb;

    @TableField(exist = false)
    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @TableField(exist = false)
    @ApiModelProperty(value = "价格")
    private Double jg;

    @TableField(exist = false)
    @ApiModelProperty(value = "备注")
    private String bz;

    @TableField(exist = false)
    @ApiModelProperty(value = "体检类型 0健康，1职业，其他综合")
    private Integer tjlx;

    @TableField(exist = false)
    @ApiModelProperty(value = "所属科室名称")
    private String ssks;

    @TableField(exist = false)
    @ApiModelProperty(value = "xmzt")
    private String xmzt;

    @TableField(exist = false)
    @ApiModelProperty(value = "打印项目分类名称")
    private String printType;

    @TableField(exist = false)
    @ApiModelProperty(value = "顺序 实际使用此字段排序")
    private Integer printShunxu;
}
