package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.MedicalType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用于判断职业小结(Comboexamitem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_comboexamitem")
@ApiModel(value = "Comboexamitem", description = "用于判断职业小结实体类")
public class Comboexamitem extends Model<Comboexamitem> implements Serializable {
    private static final long serialVersionUID = -22575100356725972L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "危害因素Id")
    private String harmId;

    @ApiModelProperty(value = "检查项目Id")
    private String examId;

    @ApiModelProperty(value = "收费项目Id")
    private String itemId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "男性上限")
    private Double scopeUpper;

    @ApiModelProperty(value = "男性下限")
    private Double scoperFloor;

    @ApiModelProperty(value = "女性上限")
    private Double gscopeupper;

    @ApiModelProperty(value = "女性下限")
    private Double gscoperfloor;

    @ApiModelProperty(value = "最小套餐ID")
    private String comboId;

    /**
     * @see MedicalType
     */
    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicalType;

    @ApiModelProperty(value = "ks_id")
    private String ksId;
}
