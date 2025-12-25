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
 * (FxReviewInfo)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:58
 */
@Data
@TableName("FX_REVIEW_INFO")
@ApiModel(value = "FxReviewInfo", description = "$tableInfo.comment实体类")
public class FxReviewInfo extends Model<FxReviewInfo> implements Serializable {
    private static final long serialVersionUID = 190533595484618563L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String harmId;

    @ApiModelProperty(value = "${column.comment}")
    private String harmName;

    @ApiModelProperty(value = "${column.comment}")
    private String diagnosis;

    @ApiModelProperty(value = "${column.comment}")
    private String num;

    @ApiModelProperty(value = "${column.comment}")
    private String summaryText;

    @ApiModelProperty(value = "${column.comment}")
    private String sampleId;
}
