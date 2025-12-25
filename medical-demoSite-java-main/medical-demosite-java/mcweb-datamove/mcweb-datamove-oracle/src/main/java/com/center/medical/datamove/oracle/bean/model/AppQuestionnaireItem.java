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
 * 微信小程序问卷项目(AppQuestionnaireItem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:50
 */
@Data
@TableName("APP_QUESTIONNAIRE_ITEM")
@ApiModel(value = "AppQuestionnaireItem", description = "微信小程序问卷项目实体类")
public class AppQuestionnaireItem extends Model<AppQuestionnaireItem> implements Serializable {
    private static final long serialVersionUID = 605744496895713536L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String appUserQuestionnaireId;

    @ApiModelProperty(value = "${column.comment}")
    private String itemId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isQuestionRecommend;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDoctorRecommend;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isPaid;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorReason;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isBase;
}
