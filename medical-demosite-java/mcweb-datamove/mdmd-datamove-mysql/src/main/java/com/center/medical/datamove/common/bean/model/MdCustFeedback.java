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
 * 故障反馈(MdCustFeedback)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
@Data
@TableName("md_cust_feedback")
@ApiModel(value = "MdCustFeedback", description = "故障反馈实体类")
public class MdCustFeedback extends Model<MdCustFeedback> implements Serializable {
    private static final long serialVersionUID = 737288549159771790L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "提交时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "故障名称")
    private String name;

    @ApiModelProperty(value = "故障类型")
    private String type;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "故障详细")
    private String detail;

    @ApiModelProperty(value = "故障时间")
    private Date faultdate;

    @ApiModelProperty(value = "处理备注")
    private String submited;

    @ApiModelProperty(value = "状态：0或null.未处理 1.已处理")
    private String state;

    @ApiModelProperty(value = "处理日期")
    private Date submitDate;

    @ApiModelProperty(value = "处理人Id")
    private String submitId;

    @ApiModelProperty(value = "处理人姓名")
    private String submitName;
}
