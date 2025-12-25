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
 * 环信客服人员(AppCustomerService)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:26
 */
@Data
@TableName("APP_CUSTOMER_SERVICE")
@ApiModel(value = "AppCustomerService", description = "环信客服人员实体类")
public class AppCustomerService extends Model<AppCustomerService> implements Serializable {
    private static final long serialVersionUID = 382331554973933971L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String mobile;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String nickName;

    @ApiModelProperty(value = "${column.comment}")
    private String email;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;

    @ApiModelProperty(value = "${column.comment}")
    private String depName;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private String qxUserId;

    @ApiModelProperty(value = "${column.comment}")
    private String picture;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;
}
