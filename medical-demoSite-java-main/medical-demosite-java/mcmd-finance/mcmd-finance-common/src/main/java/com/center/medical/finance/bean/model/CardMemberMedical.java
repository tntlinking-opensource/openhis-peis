package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员卡体检卡关联表(CardMemberMedical)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_card_member_medical")
@ApiModel(value = "CardMemberMedical", description = "会员卡体检卡关联表实体类")
public class CardMemberMedical extends Model<CardMemberMedical> implements Serializable {
    private static final long serialVersionUID = -70784246630266728L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "关联时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "会员卡id")
    private String memberId;

    @ApiModelProperty(value = "体检卡id")
    private String medicalId;


    public CardMemberMedical() {
    }


    public CardMemberMedical(String memberId, String medicalId) {
        super();
        this.memberId = memberId;
        this.medicalId = medicalId;
    }
}

