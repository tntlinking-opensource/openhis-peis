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
 * KS一般检查(MdTjreg)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:08
 */
@Data
@TableName("md_tjreg")
@ApiModel(value = "MdTjreg", description = "KS一般检查实体类")
public class MdTjreg extends Model<MdTjreg> implements Serializable {
    private static final long serialVersionUID = -22391769700902372L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

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
    private Integer djid;

    @ApiModelProperty(value = "体重指数")
    private Double bmi;

    @ApiModelProperty(value = "收缩压")
    private Object ssy;

    @ApiModelProperty(value = "舒张压")
    private Object szy;

    @ApiModelProperty(value = "腰围")
    private Double yw;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "脉搏")
    private Object mb;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "一般状况")
    private String commonState;

    @ApiModelProperty(value = "呼吸频率")
    private Double respiratoryRate;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "血压文字描述")
    private String xyms;

    @ApiModelProperty(value = "体重文字描述")
    private String bmims;

    @ApiModelProperty(value = "上传标志  0未上传,1已上传")
    private Integer scbz;

    @ApiModelProperty(value = "体温测量")
    private Double temperature;

    @ApiModelProperty(value = "waist")
    private Double waist;

    @ApiModelProperty(value = "bust")
    private Object bust;
}
