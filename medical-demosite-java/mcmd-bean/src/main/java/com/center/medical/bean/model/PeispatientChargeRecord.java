package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收费记录(PeispatientChargeRecord)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peispatient_charge_record")
@ApiModel(value = "PeispatientChargeRecord", description = "收费记录实体类")
public class PeispatientChargeRecord extends Model<PeispatientChargeRecord> implements Serializable {
    private static final long serialVersionUID = -52375977504540027L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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


    public PeispatientChargeRecord() {
        super();
    }
    public PeispatientChargeRecord(String patientcode, Long version, String method,
                                   Double moneyamount,
                                   Double moneyamountpaid, Double moneyamountOld, Double moneyamountpaidOld
            , String username,String note) {
        super();
        this.patientcode = patientcode;
        this.version = version;
        this.method = method;
        this.moneyamount = moneyamount;
        this.moneyamountpaid = moneyamountpaid;
        this.moneyamountOld = moneyamountOld;
        this.moneyamountpaidOld = moneyamountpaidOld;
        this.username = username;
        this.note=note;
    }
}
