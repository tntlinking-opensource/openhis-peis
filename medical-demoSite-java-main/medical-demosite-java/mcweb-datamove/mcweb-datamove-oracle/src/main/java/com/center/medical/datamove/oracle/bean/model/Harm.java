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
 * JC危害因素(Harm)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:10
 */
@Data
@TableName("HARM")
@ApiModel(value = "Harm", description = "JC危害因素实体类")
public class Harm extends Model<Harm> implements Serializable {
    private static final long serialVersionUID = 650360746335591712L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID号")
    private String id;

    @ApiModelProperty(value = "危害因素代码")
    private String harmCode;

    @ApiModelProperty(value = "危害因素名字")
    private String harmName;

    @ApiModelProperty(value = "种类名字")
    private String harmClass;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "KEYWORD")
    private String keyword;

    @ApiModelProperty(value = "DIAGNOSIS_FROM")
    private String diagnosisFrom;

    @ApiModelProperty(value = "MBJB_ZYB")
    private String mbjbZyb;

    @ApiModelProperty(value = "MBJB_JJZ")
    private String mbjbJjz;

    @ApiModelProperty(value = "影响")
    private String influence;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "诊断依据")
    private String diagnosis;

    @ApiModelProperty(value = "职业病")
    private String industrialDisease;

    @ApiModelProperty(value = "禁忌症")
    private String contraindication;

    @ApiModelProperty(value = "对健康影响")
    private String affect;

    @ApiModelProperty(value = "危害因素种类ID")
    private String classId;

    @ApiModelProperty(value = "假删标识")
    private Double isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "是否是公共的")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "${column.comment}")
    private String creater;

    @ApiModelProperty(value = "${column.comment}")
    private String createFzx;

    @ApiModelProperty(value = "${column.comment}")
    private String pid;

    @ApiModelProperty(value = "${column.comment}")
    private Integer lv;
}
