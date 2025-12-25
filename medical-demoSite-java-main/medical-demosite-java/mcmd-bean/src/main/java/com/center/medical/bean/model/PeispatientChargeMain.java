package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者费用主表(PeispatientChargeMain)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peispatient_charge_main")
@ApiModel(value = "PeispatientChargeMain", description = "体检者费用主表实体类")
public class PeispatientChargeMain extends Model<PeispatientChargeMain> implements Serializable {
    private static final long serialVersionUID = 583984451847883850L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "note")
    private String note;

    @ApiModelProperty(value = "应付")
    private Double moneyamount;

    @ApiModelProperty(value = "实付")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "版本号")
    private Long version;


    public PeispatientChargeMain(String note, Double moneyamount,Double moneyamountpaid,String patientcode) {
        super();
        this.note = note;
        this.moneyamount = moneyamount;
        this.moneyamountpaid = moneyamountpaid;
        this.patientcode=patientcode;
        this.version=new Date().getTime();
    }


    public PeispatientChargeMain() {
    }
}
