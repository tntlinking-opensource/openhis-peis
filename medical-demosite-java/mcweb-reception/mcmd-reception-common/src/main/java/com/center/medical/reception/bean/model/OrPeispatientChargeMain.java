package com.center.medical.reception.bean.model;


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
 * (PeispatientChargeMain)表实体类
 *
 * @author ay
 * @since 2023-08-12 11:55:02
 */
@Data
@TableName("PEISPATIENT_CHARGE_MAIN")
@ApiModel(value = "PeispatientChargeMain", description = "$tableInfo.comment实体类")
public class OrPeispatientChargeMain extends Model<OrPeispatientChargeMain> implements Serializable {
    private static final long serialVersionUID = 105438097478919296L;

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
