package com.center.medical.member.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员当前的等级(UserLevelBind)表实体类
 *
 * @author 路飞船长
 * @since 2023-03-14 11:19:36
 */
@Data
@TableName("md_user_level_bind")
@ApiModel(value = "UserLevelBind", description = "会员当前的等级实体类")
public class UserLevelBind extends Model<UserLevelBind> implements Serializable {
    private static final long serialVersionUID = 842437997759103904L;

    @TableId(value = "user_level_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private Long userLevelId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "等级")
    private Integer levelId;

    @ApiModelProperty(value = "等级名称")
    private String levelName;

    @ApiModelProperty(value = "会员来源：1.根据消费金额变动 2.手动申请")
    private Integer fromType;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "消费金额")
    private Double amount;

    @ApiModelProperty(value = "申请记录id")
    private Long applyId;

    @ApiModelProperty(value = "有效期（天）")
    private Integer term;

    @ApiModelProperty(value = "期数类型 1:日 2:周 3:月 4:季 5:年")
    private Integer termType;

    @ApiModelProperty(value = "背景图片")
    private String img;

    @ApiModelProperty(value = "折扣方式：0.打折 1.满减")
    private Integer discountType;

    @ApiModelProperty(value = "会员折扣")
    private Double discount;

    @ApiModelProperty(value = "赠送积分")
    private Integer presScore;

    @ApiModelProperty(value = "积分回馈倍率")
    private Double rateScore;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
