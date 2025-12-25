package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS一般检查(Tjreg)表实体类
 *
 * @author ay
 * @since 2024-06-05 15:37:36
 */
@Data
@TableName("TJREG")
@ApiModel(value = "Tjreg", description = "KS一般检查实体类")
public class OrTjreg extends Model<OrTjreg> implements Serializable {
    private static final long serialVersionUID = -81623849965193986L;

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
    private Integer ssy;


    @ApiModelProperty(value = "舒张压")
    private Integer szy;


    @ApiModelProperty(value = "腰围")
    private Double yw;


    @ApiModelProperty(value = "登记流水")
    private String djls;


    @ApiModelProperty(value = "脉搏")
    private Integer mb;


    @ApiModelProperty(value = "创建人")
    private String createId;


    @ApiModelProperty(value = "更新人")
    private String modifyId;


    @ApiModelProperty(value = "一般状况")
    private String commonState;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


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


    @ApiModelProperty(value = "${column.comment}")
    private Integer scbz;


    @ApiModelProperty(value = "${column.comment}")
    private Double temperature;


    @ApiModelProperty(value = "${column.comment}")
    private Double waist;


    @ApiModelProperty(value = "${column.comment}")
    private Integer bust;

}
