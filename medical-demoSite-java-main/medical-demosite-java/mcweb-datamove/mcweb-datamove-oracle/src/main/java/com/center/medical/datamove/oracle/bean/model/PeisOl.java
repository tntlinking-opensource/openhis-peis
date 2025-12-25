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
 * 体检者线上信息(PeisOl)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:57
 */
@Data
@TableName("PEIS_OL")
@ApiModel(value = "PeisOl", description = "体检者线上信息实体类")
public class PeisOl extends Model<PeisOl> implements Serializable {
    private static final long serialVersionUID = -63200329625314221L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientbizno;

    @ApiModelProperty(value = "app订单类型 0活动套餐，1团检套餐 2个性套餐         3自我检测4自主定制")
    private String orgDepartsubc;

    @ApiModelProperty(value = "微信商城核销码")
    private String orgDepartsubd;

    @ApiModelProperty(value = "是否已在APP保存")
    private String orgDepartsube;

    @ApiModelProperty(value = "微信商城套餐价格")
    private String ageofreal;

    @ApiModelProperty(value = "app预约号")
    private String idDoctorconclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String wechatCode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isWechatNoticed;

    @ApiModelProperty(value = "${column.comment}")
    private String wechatNoticeType;

    @ApiModelProperty(value = "${column.comment}")
    private Date wechatNoticeTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer wechatNoticeFailedCount;
}
