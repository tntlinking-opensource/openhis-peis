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
 * (Kingdeereser)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:30
 */
@Data
@TableName("KINGDEERESER")
@ApiModel(value = "Kingdeereser", description = "$tableInfo.comment实体类")
public class Kingdeereser extends Model<Kingdeereser> implements Serializable {
    private static final long serialVersionUID = 108619765960239240L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String idRemitter;

    @ApiModelProperty(value = "${column.comment}")
    private String idRemittanceway;

    @ApiModelProperty(value = "${column.comment}")
    private String customername;

    @ApiModelProperty(value = "${column.comment}")
    private String idCustomer;

    @ApiModelProperty(value = "${column.comment}")
    private String idFeecharger;

    @ApiModelProperty(value = "${column.comment}")
    private String idCreator;

    @ApiModelProperty(value = "${column.comment}")
    private String moneyamountpaid;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String idAudit;

    @ApiModelProperty(value = "${column.comment}")
    private String isUpdate;

    @ApiModelProperty(value = "${column.comment}")
    private String isAudit;

    @ApiModelProperty(value = "${column.comment}")
    private Date auditdate;

    @ApiModelProperty(value = "${column.comment}")
    private String idChange;
}
