package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS一般检查(Tjreg)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:17
 */
@Data
@TableName("md_tjreg")
@ApiModel(value = "Tjreg", description = "KS一般检查实体类")
public class Tjreg extends Model<Tjreg> implements Serializable {
    private static final long serialVersionUID = -82073390138378456L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检编号")
    private String tjbh;

    @ApiModelProperty(value = "体检日期")
    private Date tjrq;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "身高")
    private String sg;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "体重")
    private String tz;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "心率")
    private String xl;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "血压")
    private String xy;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "小结")
    private String xj;

    @ApiModelProperty(value = "体检状态")
    private String tjzt;

    @ApiModelProperty(value = "登记ID")
    private Integer djid;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "体重指数")
    private Double bmi;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "收缩压")
    private Double ssy;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "舒张压")
    private Double szy;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "腰围")
    private Double yw;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "脉搏")
    private Double mb;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "营养状况")
    private String commonState;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "呼吸频率")
    private Double respiratoryRate;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "血压文字描述")
    private String xyms;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "体重文字描述")
    private String bmims;

    @ApiModelProperty(value = "上传标志  0未上传,1已上传")
    private Integer scbz;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "体温测量")
    private Double temperature;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "waist")
    private Double waist;


    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "胸围")
    private Double bust;


    @TableField(exist = false)
    @ApiModelProperty(value = "危急值(用于判断是否插入高危人员)")
    private String isDanger;

    @TableField(exist = false)
    @ApiModelProperty(value = "弃检")
    private String isUnchecked;


}
