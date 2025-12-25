package com.center.medical.datamove.common.bean.model;


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
 * JC危害因素(MdHarm)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
@Data
@TableName("md_harm")
@ApiModel(value = "MdHarm", description = "JC危害因素实体类")
public class MdHarm extends Model<MdHarm> implements Serializable {
    private static final long serialVersionUID = -43800679353928551L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否是公共的：0或null.否 1.是")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "创建人用户名")
    private String creater;

    @ApiModelProperty(value = "初创中心")
    private String createFzx;

    @ApiModelProperty(value = "上级id")
    private String pid;

    @ApiModelProperty(value = "级别：1.级父级  2.级子级")
    private Integer lv;
}
