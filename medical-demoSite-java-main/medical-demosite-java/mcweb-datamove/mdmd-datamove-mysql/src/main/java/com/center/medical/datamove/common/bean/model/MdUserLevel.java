package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 会员等级表(MdUserLevel)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:18
 */
@Data
@TableName("md_user_level")
@ApiModel(value = "MdUserLevel", description = "会员等级表实体类")
public class MdUserLevel extends Model<MdUserLevel> implements Serializable {
    private static final long serialVersionUID = 760716870045945670L;

    @TableId(value = "level_id")
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
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
