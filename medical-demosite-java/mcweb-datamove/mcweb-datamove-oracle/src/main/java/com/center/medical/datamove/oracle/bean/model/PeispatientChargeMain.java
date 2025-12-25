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
 * (PeispatientChargeMain)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:14
 */
@Data
@TableName("PEISPATIENT_CHARGE_MAIN")
@ApiModel(value = "PeispatientChargeMain", description = "$tableInfo.comment实体类")
public class PeispatientChargeMain extends Model<PeispatientChargeMain> implements Serializable {
    private static final long serialVersionUID = 722700786982811598L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private Double moneyamount;

    @ApiModelProperty(value = "${column.comment}")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String version;
}
