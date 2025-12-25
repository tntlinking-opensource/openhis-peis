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
 * JC职业病检查结论(MdZySummary)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
@Data
@TableName("md_zy_summary")
@ApiModel(value = "MdZySummary", description = "JC职业病检查结论实体类")
public class MdZySummary extends Model<MdZySummary> implements Serializable {
    private static final long serialVersionUID = -43584229714531621L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "序列号")
    private String serialNo;

    @ApiModelProperty(value = "结论代码")
    private String occupationSummaryCode;

    @ApiModelProperty(value = "结论")
    private String occupationSummary;

    @ApiModelProperty(value = "结论简称")
    private String printForShort;

    @ApiModelProperty(value = "备注")
    private String occupationSummaryExplain;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
