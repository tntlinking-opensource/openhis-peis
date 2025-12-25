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
 * 十周年卡复查金额记录表(MdCardRecheckRecord)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
@Data
@TableName("md_card_recheck_record")
@ApiModel(value = "MdCardRecheckRecord", description = "十周年卡复查金额记录表实体类")
public class MdCardRecheckRecord extends Model<MdCardRecheckRecord> implements Serializable {
    private static final long serialVersionUID = -88879789143177540L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建世家")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "操作起始金额")
    private Double startMoney;

    @ApiModelProperty(value = "操作结束金额")
    private Double endMoney;

    @ApiModelProperty(value = "金额(消费负数，充值正数)")
    private Double money;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "体检号（消费）")
    private String patientcode;

    @ApiModelProperty(value = "操作时间")
    private Date chargeTime;

    @ApiModelProperty(value = "操作类型：0.消费 1.充值")
    private Integer chargeType;

    @ApiModelProperty(value = "操作人用户名")
    private String username;
}
