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
 * 金碟账户(KdCustomer)表实体类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:42
 */
@Data
@TableName("kd_customer")
@ApiModel(value = "KdCustomer", description = "金碟账户实体类")
public class KdCustomer extends Model<KdCustomer> implements Serializable {
    private static final long serialVersionUID = -54839340297497256L;

    @TableId(value = "account_no", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "账户号")
    private String accountNo;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "使用状态id")
    private String useStatusId;

    @ApiModelProperty(value = "ct_date")
    private Date ctDate;

    @ApiModelProperty(value = "lt_date")
    private Date ltDate;

    @ApiModelProperty(value = "centerorgname")
    private String centerorgname;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
