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
 * 综合分析-项目參检（健康）(FxItemscheck)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fx_itemscheck")
@ApiModel(value = "FxItemscheck", description = "综合分析-项目參检（健康）实体类")
public class FxItemscheck extends Model<FxItemscheck> implements Serializable {
    private static final long serialVersionUID = -49010091210678740L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "收费项目名称")
    private String itemName;

    @ApiModelProperty(value = "收费项目ID")
    private String itemId;

    @ApiModelProperty(value = "该项检出人数-男")
    private Integer checkMale;

    @ApiModelProperty(value = "该项检出人数-女")
    private Integer checkFemale;

    @ApiModelProperty(value = "该项检出人数-合计")
    private Integer checkTotal;

    @ApiModelProperty(value = "总体參检人数-男")
    private Integer allMale;

    @ApiModelProperty(value = "总体參检人数-女")
    private Integer allFemale;

    @ApiModelProperty(value = "总体參检人数-合计")
    private Integer allTotal;

    @ApiModelProperty(value = "该项參检比例-男")
    private Double perMale;

    @ApiModelProperty(value = "该项參检比例-女")
    private Double perFemale;

    @ApiModelProperty(value = "该项參检比例-合计")
    private Double perTotal;

    @ApiModelProperty(value = "科室报告排序")
    private Integer reportSort;

    @ApiModelProperty(value = "行号")
    private Integer rowno;
}
