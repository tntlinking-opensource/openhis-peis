package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者线上信息(PeisOl)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peis_ol")
@ApiModel(value = "PeisOl", description = "体检者线上信息实体类")
public class PeisOl extends Model<PeisOl> implements Serializable {
    private static final long serialVersionUID = 158961256184356873L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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
