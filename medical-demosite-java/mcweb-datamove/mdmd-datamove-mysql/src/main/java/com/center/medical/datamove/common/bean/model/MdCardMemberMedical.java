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
 * 会员卡体检卡关联表(MdCardMemberMedical)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
@Data
@TableName("md_card_member_medical")
@ApiModel(value = "MdCardMemberMedical", description = "会员卡体检卡关联表实体类")
public class MdCardMemberMedical extends Model<MdCardMemberMedical> implements Serializable {
    private static final long serialVersionUID = -14464656570730537L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "关联时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "会员卡id")
    private String memberId;

    @ApiModelProperty(value = "体检卡id")
    private String medicalId;
}
