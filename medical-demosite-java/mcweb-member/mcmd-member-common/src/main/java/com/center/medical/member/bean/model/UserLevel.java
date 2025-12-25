package com.center.medical.member.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员等级表(UserLevel)表实体类
 *
 * @author 路飞船长
 * @since 2023-03-14 11:19:35
 */
@Data
@TableName("md_user_level")
@ApiModel(value = "UserLevel", description = "会员等级表实体类")
public class UserLevel extends Model<UserLevel> implements Serializable {
    private static final long serialVersionUID = 750070606652439347L;

    @TableId(value = "level_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "等级ID")
    private Integer levelId;

    @ApiModelProperty(value = "等级名称")
    private String levelName;

    @ApiModelProperty(value = "等级条件：0.普通会员 1.付费会员")
    private Integer levelType;

    @ApiModelProperty(value = "付费会员价格")
    private Double needAmount;

    @ApiModelProperty(value = "有效期（天）")
    private Integer term;

    @ApiModelProperty(value = "期数类型 1:日 2:周 3:月 4:季 5:年")
    private Integer termType;

    @ApiModelProperty(value = "背景图片")
    private String img;

    @ApiModelProperty(value = "折扣范围")
    private Integer discountRange;

    @ApiModelProperty(value = "折扣方式：0.打折 1.满减")
    private Integer discountType;

    @ApiModelProperty(value = "会员折扣")
    private Double discount;

    @ApiModelProperty(value = "赠送积分")
    private Integer presScore;

    @ApiModelProperty(value = "积分回馈倍率")
    private Double rateScore;

    @ApiModelProperty(value = "状态：-1.未更新(等级修改后，用户等级的更新) 0.停用 1.已更新")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
