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
 * 问卷项目推荐原因(AppQuestionnaireItemReason)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:52
 */
@Data
@TableName("APP_QUESTIONNAIRE_ITEM_REASON")
@ApiModel(value = "AppQuestionnaireItemReason", description = "问卷项目推荐原因实体类")
public class AppQuestionnaireItemReason extends Model<AppQuestionnaireItemReason> implements Serializable {
    private static final long serialVersionUID = -95251570671382520L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String questionnaireItemId;

    @ApiModelProperty(value = "${column.comment}")
    private String comboId;

    @ApiModelProperty(value = "${column.comment}")
    private String reason;
}
