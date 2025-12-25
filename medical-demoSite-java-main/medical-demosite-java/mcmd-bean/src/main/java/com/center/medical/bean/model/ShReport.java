package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 自助机-报告打印 操作记录(ShReport)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_sh_report")
@ApiModel(value = "ShReport", description = "自助机-报告打印 操作记录实体类")
public class ShReport extends Model<ShReport> implements Serializable {
    private static final long serialVersionUID = 150349735980962507L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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
