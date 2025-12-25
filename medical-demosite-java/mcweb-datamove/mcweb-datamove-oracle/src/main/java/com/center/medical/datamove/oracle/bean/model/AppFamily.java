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
 * 家人档案(不含本人)(AppFamily)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:33
 */
@Data
@TableName("APP_FAMILY")
@ApiModel(value = "AppFamily", description = "家人档案(不含本人)实体类")
public class AppFamily extends Model<AppFamily> implements Serializable {
    private static final long serialVersionUID = -70780431716235169L;

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
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String sex;

    @ApiModelProperty(value = "${column.comment}")
    private String idcard;

    @ApiModelProperty(value = "${column.comment}")
    private String tel;

    @ApiModelProperty(value = "${column.comment}")
    private String idMarriage;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String archiveId;

    @ApiModelProperty(value = "${column.comment}")
    private String birthday;
}
