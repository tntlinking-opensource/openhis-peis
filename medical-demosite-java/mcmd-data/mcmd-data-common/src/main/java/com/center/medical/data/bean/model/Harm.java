package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC危害因素(Harm)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_harm")
@ApiModel(value = "Harm", description = "JC危害因素实体类")
public class Harm extends Model<Harm> implements Serializable {
    private static final long serialVersionUID = 707465722854451749L;

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

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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

    @TableField(updateStrategy = FieldStrategy.IGNORED)
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

    @TableField(exist = false)
    @ApiModelProperty(value = "种类名字(文字版)")
    private String mzhcHarmClass;

    @TableField(exist = false)
    @ApiModelProperty(value = "1为公共，其他为空")
    private String fzx;

}
