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
 * 职业团检分析-危害因素(FxHarm)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:51
 */
@Data
@TableName("FX_HARM")
@ApiModel(value = "FxHarm", description = "职业团检分析-危害因素实体类")
public class FxHarm extends Model<FxHarm> implements Serializable {
    private static final long serialVersionUID = 565339260167813409L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "危害因素id")
    private String harmId;

    @ApiModelProperty(value = "样本id")
    private String sampleId;

    @ApiModelProperty(value = "${column.comment}")
    private String personNum;

    @ApiModelProperty(value = "${column.comment}")
    private String harmName;
}
