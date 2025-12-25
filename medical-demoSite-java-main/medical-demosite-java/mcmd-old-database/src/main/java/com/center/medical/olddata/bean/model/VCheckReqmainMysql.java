package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (VCheckReqmainMysql)表实体类
 *
 * @author ay
 * @since 2024-08-09 15:46:19
 */
@Data
@TableName("V_CHECK_REQMAIN_MYSQL")
@ApiModel(value = "VCheckReqmainMysql", description = "$tableInfo.comment实体类")
public class VCheckReqmainMysql extends Model<VCheckReqmainMysql> implements Serializable {
    private static final long serialVersionUID = 748913818642650025L;

    @ApiModelProperty(value = "${column.comment}")
    private Integer interfaceclass;


    @ApiModelProperty(value = "${column.comment}")
    private String checkregsn;


    @ApiModelProperty(value = "${column.comment}")
    private String checkregno;


    @ApiModelProperty(value = "${column.comment}")
    private String checkreqid;


    @ApiModelProperty(value = "${column.comment}")
    private String checkreqdate;


    @ApiModelProperty(value = "${column.comment}")
    private String checkreqno;


    @ApiModelProperty(value = "${column.comment}")
    private String patientname;


    @ApiModelProperty(value = "${column.comment}")
    private String sex;


    @ApiModelProperty(value = "${column.comment}")
    private String birthdate;


    @ApiModelProperty(value = "${column.comment}")
    private Double age;


    @ApiModelProperty(value = "${column.comment}")
    private String ageunit;


    @ApiModelProperty(value = "${column.comment}")
    private String idcardno;


    @ApiModelProperty(value = "${column.comment}")
    private String departcode;


    @ApiModelProperty(value = "${column.comment}")
    private String departname;


    @ApiModelProperty(value = "${column.comment}")
    private String doctorcode;


    @ApiModelProperty(value = "${column.comment}")
    private String doctorname;


    @ApiModelProperty(value = "${column.comment}")
    private String itemcode;


    @ApiModelProperty(value = "${column.comment}")
    private String itemname;


    @ApiModelProperty(value = "${column.comment}")
    private String simplecode;


    @ApiModelProperty(value = "${column.comment}")
    private String simplename;


    @ApiModelProperty(value = "${column.comment}")
    private String execdepartcode;


    @ApiModelProperty(value = "${column.comment}")
    private String execdepartname;


    @ApiModelProperty(value = "${column.comment}")
    private Integer amount;

}
