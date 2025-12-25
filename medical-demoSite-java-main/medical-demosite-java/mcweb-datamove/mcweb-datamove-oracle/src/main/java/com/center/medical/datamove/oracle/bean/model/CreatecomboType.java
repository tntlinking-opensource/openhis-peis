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
 * 最小套餐分类(CreatecomboType)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:56
 */
@Data
@TableName("CREATECOMBO_TYPE")
@ApiModel(value = "CreatecomboType", description = "最小套餐分类实体类")
public class CreatecomboType extends Model<CreatecomboType> implements Serializable {
    private static final long serialVersionUID = 934655886918886106L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String typeIndex;

    @ApiModelProperty(value = "${column.comment}")
    private Integer typeState;
}
