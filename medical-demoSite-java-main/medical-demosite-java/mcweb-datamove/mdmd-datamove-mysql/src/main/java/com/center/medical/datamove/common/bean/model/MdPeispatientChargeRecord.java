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
 * 收费记录(MdPeispatientChargeRecord)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
@Data
@TableName("md_peispatient_charge_record")
@ApiModel(value = "MdPeispatientChargeRecord", description = "收费记录实体类")
public class MdPeispatientChargeRecord extends Model<MdPeispatientChargeRecord> implements Serializable {
    private static final long serialVersionUID = 712852609575750168L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "方法")
    private String method;

    @ApiModelProperty(value = "版本号")
    private Long version;

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

    @ApiModelProperty(value = "备注")
    private String note;
}
