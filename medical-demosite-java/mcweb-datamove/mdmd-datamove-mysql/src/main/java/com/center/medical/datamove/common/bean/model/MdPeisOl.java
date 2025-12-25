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
 * 体检者线上信息(MdPeisOl)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
@Data
@TableName("md_peis_ol")
@ApiModel(value = "MdPeisOl", description = "体检者线上信息实体类")
public class MdPeisOl extends Model<MdPeisOl> implements Serializable {
    private static final long serialVersionUID = -30407200400493025L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "patientbizno")
    private String patientbizno;

    @ApiModelProperty(value = "app订单类型 0活动套餐，1团检套餐 2个性套餐         3自我检测4自主定制")
    private String orgDepartsubc;

    @ApiModelProperty(value = "微信商城核销码")
    private String orgDepartsubd;

    @ApiModelProperty(value = "是否已在APP保存")
    private String orgDepartsube;

    @ApiModelProperty(value = "微信商城套餐价格")
    private Double ageofreal;

    @ApiModelProperty(value = "app预约号")
    private Integer idDoctorconclusion;

    @ApiModelProperty(value = "微信沃德小程序体检码")
    private String wechatCode;

    @ApiModelProperty(value = "微信沃德小程序是否已通知")
    private Integer isWechatNoticed;

    @ApiModelProperty(value = "微信沃德小程序通知类型  1短信 2微信")
    private String wechatNoticeType;

    @ApiModelProperty(value = "微信沃德小程序通知时间")
    private Date wechatNoticeTime;

    @ApiModelProperty(value = "通知失败次数")
    private Integer wechatNoticeFailedCount;
}
