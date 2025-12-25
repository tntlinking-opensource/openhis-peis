package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (Kingdeeorganization)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:27
 */
@Data
@TableName("KINGDEEORGANIZATION")
@ApiModel(value = "Kingdeeorganization", description = "$tableInfo.comment实体类")
public class Kingdeeorganization extends Model<Kingdeeorganization> implements Serializable {
    private static final long serialVersionUID = -38973771323927338L;

    @ApiModelProperty(value = "${column.comment}")
    private String orgId;

    @ApiModelProperty(value = "${column.comment}")
    private String orgNumber;

    @ApiModelProperty(value = "${column.comment}")
    private String orgName;

    @ApiModelProperty(value = "${column.comment}")
    private String parentId;
}
