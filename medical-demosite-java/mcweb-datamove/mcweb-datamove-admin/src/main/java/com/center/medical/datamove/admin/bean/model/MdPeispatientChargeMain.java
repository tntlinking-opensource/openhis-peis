package com.center.medical.datamove.admin.bean.model;


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
 * 体检者费用主表(MdPeispatientChargeMain)表实体类
 *
 * @author ay
 * @since 2023-08-12 11:53:33
 */
@Data
@TableName("md_peispatient_charge_main")
@ApiModel(value = "MdPeispatientChargeMain", description = "体检者费用主表实体类")
public class MdPeispatientChargeMain extends Model<MdPeispatientChargeMain> implements Serializable {
    private static final long serialVersionUID = 211726017039363406L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
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
}
