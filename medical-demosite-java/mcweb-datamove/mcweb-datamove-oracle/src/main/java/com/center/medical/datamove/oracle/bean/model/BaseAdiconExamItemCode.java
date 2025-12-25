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
 * (BaseAdiconExamItemCode)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:25
 */
@Data
@TableName("BASE_ADICON_EXAM_ITEM_CODE")
@ApiModel(value = "BaseAdiconExamItemCode", description = "$tableInfo.comment实体类")
public class BaseAdiconExamItemCode extends Model<BaseAdiconExamItemCode> implements Serializable {
    private static final long serialVersionUID = -36474034876746085L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String itemCode;

    @ApiModelProperty(value = "${column.comment}")
    private String itemName;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;
}
