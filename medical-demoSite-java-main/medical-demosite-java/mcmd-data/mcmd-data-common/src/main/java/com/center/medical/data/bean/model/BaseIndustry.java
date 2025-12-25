package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 国民经济行业分类GB/T 4754—2017(BaseIndustry)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_base_industry")
@ApiModel(value = "BaseIndustry", description = "国民经济行业分类GB/T 4754—2017实体类")
public class BaseIndustry extends Model<BaseIndustry> implements Serializable {
    private static final long serialVersionUID = 623552124970367186L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "类别名称")
    private String industryName;

    @ApiModelProperty(value = "说明")
    private String illustration;

    @ApiModelProperty(value = "完整编码")
    private String industryCode;

    @ApiModelProperty(value = "序号（有但没用）")
    private Integer idx;
}
