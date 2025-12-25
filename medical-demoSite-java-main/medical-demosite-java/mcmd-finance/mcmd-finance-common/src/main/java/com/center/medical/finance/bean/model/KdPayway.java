package com.center.medical.finance.bean.model;

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
 * kingdeepayway(KdPayway)表实体类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
@Data
@TableName("kd_payway")
@ApiModel(value = "KdPayway", description = "kingdeepayway实体类")
public class KdPayway extends Model<KdPayway> implements Serializable {
    private static final long serialVersionUID = 963479391460396874L;

    @TableId(value = "account_no", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "account_no")
    private String accountNo;

    @ApiModelProperty(value = "account_name")
    private String accountName;

    @ApiModelProperty(value = "use_status_id")
    private String useStatusId;

    @ApiModelProperty(value = "ct_date")
    private Date ctDate;

    @ApiModelProperty(value = "lt_date")
    private Date ltDate;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
