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
 * JC职业病检查结论(ZySummary)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:31:01
 */
@Data
@TableName("ZY_SUMMARY")
@ApiModel(value = "ZySummary", description = "JC职业病检查结论实体类")
public class ZySummary extends Model<ZySummary> implements Serializable {
    private static final long serialVersionUID = -60318311902425645L;

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

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "假删状态")
    private Double isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String sort;
}
