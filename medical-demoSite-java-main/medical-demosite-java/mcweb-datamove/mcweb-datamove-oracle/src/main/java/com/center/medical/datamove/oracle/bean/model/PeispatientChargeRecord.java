package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (PeispatientChargeRecord)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:17
 */
@Data
@TableName("PEISPATIENT_CHARGE_RECORD")
@ApiModel(value = "PeispatientChargeRecord", description = "$tableInfo.comment实体类")
public class PeispatientChargeRecord extends Model<PeispatientChargeRecord> implements Serializable {
    private static final long serialVersionUID = -86069907619922606L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "方法")
    private String method;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "应付")
    private Double moneyamount;

    @ApiModelProperty(value = "实付")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "原应付")
    private Double moneyamountOld;

    @ApiModelProperty(value = "原实付")
    private Double moneyamountpaidOld;

    @ApiModelProperty(value = "操作人用户名")
    private String username;

    @ApiModelProperty(value = "${column.comment}")
    private String note;
}
