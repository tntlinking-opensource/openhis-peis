package com.center.medical.member.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员卡积分明细表(Memberintegral)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:57
 */
@Data
@TableName("md_memberintegral")
@ApiModel(value = "Memberintegral", description = "会员卡积分明细表实体类")
public class Memberintegral extends Model<Memberintegral> implements Serializable {
    private static final long serialVersionUID = 920108585467717217L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "会员卡号")
    @ApiModelProperty(value = "会员卡号")
    private String cardId;


    @Excel(name = "会员卡类型")
    @TableField(exist = false)
    @ApiModelProperty(value = "会员卡类型")
    private String typeName;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "会员姓名")
    private String memberName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @Excel(name = "剩余积分")
    @ApiModelProperty(value = "操作后积分")
    private Integer handleIntegral;

    @Excel(name = "积分类型" ,readConverterExp = "0=充值,1=消费")
    @ApiModelProperty(value = "积分类型：0.充值 1.消费")
    private Integer isAdd;

    @Excel(name = "分值")
    @TableField(value="`limit`")
    @ApiModelProperty(value = "记录增加或减少的积分")
    private Integer limit;

    @Excel(name = "操作人")
    @ApiModelProperty(value = "操作人ID")
    private String operationId;

    @Excel(name = "操作时间")
    @ApiModelProperty(value = "操作日期")
    private Date operationTime;

    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "付款方式ID")
    private String payMode;


    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;




}
