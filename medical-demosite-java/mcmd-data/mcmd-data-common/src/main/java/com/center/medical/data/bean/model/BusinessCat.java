package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务类型(BusinessCat)表实体类
 *
 * @author 路飞船长
 * @since 2023-02-28 15:40:49
 */
@Data
@TableName("md_business_cat")
@ApiModel(value = "BusinessCat", description = "业务类型实体类")
public class BusinessCat extends Model<BusinessCat> implements Serializable {
    private static final long serialVersionUID = 514988081320167920L;

    @Excel(name = "类别id")
    @TableId(value = "type_id")
    @ApiModelProperty(value = "类型ID")
    private Long typeId;

    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @Excel(name = "类别名称")
    @ApiModelProperty(value = "名称")
    private String typeName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "介绍")
    private String tip;

    @ApiModelProperty(value = "排序")
    private Integer seq;

    @ApiModelProperty(value = "状态：-1.删除 0.下线 1.正常")
    private Integer status;

    @ApiModelProperty(value = "层级")
    private Integer grade;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "记录时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
