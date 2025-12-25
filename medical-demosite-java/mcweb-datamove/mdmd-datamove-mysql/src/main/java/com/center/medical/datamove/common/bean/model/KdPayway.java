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
 * 金蝶中的收银方式（kingdeepayway）(KdPayway)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
@Data
@TableName("kd_payway")
@ApiModel(value = "KdPayway", description = "金蝶中的收银方式（kingdeepayway）实体类")
public class KdPayway extends Model<KdPayway> implements Serializable {
    private static final long serialVersionUID = 614115053945226271L;

    @TableId(value = "account_no", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "account_no")
    private String accountNo;

    @ApiModelProperty(value = "account_name")
    private String accountName;

    @ApiModelProperty(value = "use_status_id")
    private String useStatusId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "ct_date")
    private Date ctDate;

    @ApiModelProperty(value = "lt_date")
    private Date ltDate;
}
