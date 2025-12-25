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
 * 国民经济行业分类GB/T 4754—2017(BaseIndustry)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:35
 */
@Data
@TableName("BASE_INDUSTRY")
@ApiModel(value = "BaseIndustry", description = "国民经济行业分类GB/T 4754—2017实体类")
public class BaseIndustry extends Model<BaseIndustry> implements Serializable {
    private static final long serialVersionUID = 676531638655350363L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "门类代码")
    private String category;

    @ApiModelProperty(value = "大类代码")
    private String major;

    @ApiModelProperty(value = "中类代码")
    private String middle;

    @ApiModelProperty(value = "小类代码")
    private String sub;

    @ApiModelProperty(value = "1门类 2大类 3中类 4小类")
    private Integer arrangement;

    @ApiModelProperty(value = "济南市职业卫生综合管理平台代码")
    private String interfaceCode;

    @ApiModelProperty(value = "1已删除  0未删除")
    private String isDelete;

    @ApiModelProperty(value = "类别名称")
    private String industryName;

    @ApiModelProperty(value = "说明")
    private String illustration;

    @ApiModelProperty(value = "完整编码")
    private String industryCode;

    @ApiModelProperty(value = "序号（有但没用）")
    private String idx;
}
