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
 * 自助机-报告打印 操作记录(MdShReport)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:39
 */
@Data
@TableName("md_sh_report")
@ApiModel(value = "MdShReport", description = "自助机-报告打印 操作记录实体类")
public class MdShReport extends Model<MdShReport> implements Serializable {
    private static final long serialVersionUID = 413255014185613458L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "0单科室报告 1既往报告")
    private Integer reportType;

    @ApiModelProperty(value = "科室ID（单科室 报告）")
    private String depId;

    @ApiModelProperty(value = "0未收费 1已收费")
    private Integer isCharged;

    @ApiModelProperty(value = "0未打印 1已打印")
    private Integer isPrinted;

    @ApiModelProperty(value = "打印失败时的错误信息")
    private String errorMsg;

    @ApiModelProperty(value = "打印时间")
    private Date printTime;
}
