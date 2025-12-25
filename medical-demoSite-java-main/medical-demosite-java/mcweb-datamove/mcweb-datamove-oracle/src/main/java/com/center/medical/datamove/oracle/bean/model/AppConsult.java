package com.center.medical.datamove.oracle.bean.model;


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
 * 微信小程序医生咨询(AppConsult)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:24
 */
@Data
@TableName("APP_CONSULT")
@ApiModel(value = "AppConsult", description = "微信小程序医生咨询实体类")
public class AppConsult extends Model<AppConsult> implements Serializable {
    private static final long serialVersionUID = 751289615056028862L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorId;

    @ApiModelProperty(value = "${column.comment}")
    private String remarks;

    @ApiModelProperty(value = "${column.comment}")
    private Date consultTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date operateTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isPaid;

    @ApiModelProperty(value = "${column.comment}")
    private String price;

    @ApiModelProperty(value = "${column.comment}")
    private String operator;

    @ApiModelProperty(value = "${column.comment}")
    private String branchId;

    @ApiModelProperty(value = "${column.comment}")
    private Date payTime;
}
