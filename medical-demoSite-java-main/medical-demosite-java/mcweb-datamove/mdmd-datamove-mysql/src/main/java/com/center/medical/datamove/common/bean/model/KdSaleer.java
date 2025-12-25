package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 金蝶销售员(KdSaleer)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
@Data
@TableName("kd_saleer")
@ApiModel(value = "KdSaleer", description = "金蝶销售员实体类")
public class KdSaleer extends Model<KdSaleer> implements Serializable {
    private static final long serialVersionUID = -87455092721175192L;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "账户号")
    private String accountNo;

    @ApiModelProperty(value = "状态")
    private String useStatusId;

    @ApiModelProperty(value = "ct_date")
    private Date ctDate;

    @ApiModelProperty(value = "lt_date")
    private Date ltDate;

    @ApiModelProperty(value = "centerorgname")
    private String centerorgname;

    @ApiModelProperty(value = "md5")
    private String md5;
}
