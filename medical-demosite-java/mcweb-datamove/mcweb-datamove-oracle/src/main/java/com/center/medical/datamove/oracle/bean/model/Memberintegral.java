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
 * 会员卡积分明细表(Memberintegral)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:06
 */
@Data
@TableName("MEMBERINTEGRAL")
@ApiModel(value = "Memberintegral", description = "会员卡积分明细表实体类")
public class Memberintegral extends Model<Memberintegral> implements Serializable {
    private static final long serialVersionUID = -56031543834859135L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "会员卡号")
    private String cardId;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @ApiModelProperty(value = "记录增加或减少的积分")
    private Double limit;

    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "操作人ID")
    private String operationId;

    @ApiModelProperty(value = "是否为增加")
    private Double isAdd;

    @ApiModelProperty(value = "操作日期")
    private Date operationTime;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "付款方式ID")
    private String payMode;

    @ApiModelProperty(value = "操作后积分")
    private Double handleIntegral;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "假删")
    private Double isDelete;

    @ApiModelProperty(value = "会员姓名")
    private String memberName;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;
}
