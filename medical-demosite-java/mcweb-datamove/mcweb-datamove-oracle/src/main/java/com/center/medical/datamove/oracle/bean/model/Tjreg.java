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
 * KS一般检查(Tjreg)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:32
 */
@Data
@TableName("TJREG")
@ApiModel(value = "Tjreg", description = "KS一般检查实体类")
public class Tjreg extends Model<Tjreg> implements Serializable {
    private static final long serialVersionUID = -93795370629109834L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检编号")
    private String tjbh;

    @ApiModelProperty(value = "体检日期")
    private Date tjrq;

    @ApiModelProperty(value = "身高")
    private String sg;

    @ApiModelProperty(value = "体重")
    private String tz;

    @ApiModelProperty(value = "心率")
    private String xl;

    @ApiModelProperty(value = "血压")
    private String xy;

    @ApiModelProperty(value = "小结")
    private String xj;

    @ApiModelProperty(value = "体检状态")
    private String tjzt;

    @ApiModelProperty(value = "登记ID")
    private Double djid;

    @ApiModelProperty(value = "体重指数")
    private String bmi;

    @ApiModelProperty(value = "收缩压")
    private String ssy;

    @ApiModelProperty(value = "舒张压")
    private String szy;

    @ApiModelProperty(value = "腰围")
    private String yw;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "脉搏")
    private String mb;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "一般状况")
    private String commonState;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "呼吸频率")
    private String respiratoryRate;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "短号体检号")
    private String shortCode;

    @ApiModelProperty(value = "血压文字描述")
    private String xyms;

    @ApiModelProperty(value = "体重文字描述")
    private String bmims;

    @ApiModelProperty(value = "${column.comment}")
    private Integer scbz;

    @ApiModelProperty(value = "${column.comment}")
    private String temperature;

    @ApiModelProperty(value = "${column.comment}")
    private Double waist;

    @ApiModelProperty(value = "${column.comment}")
    private String bust;
}
