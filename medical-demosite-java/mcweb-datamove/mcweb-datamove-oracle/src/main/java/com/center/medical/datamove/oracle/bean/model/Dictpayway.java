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
 * JC支付方式表(Dictpayway)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:14
 */
@Data
@TableName("DICTPAYWAY")
@ApiModel(value = "Dictpayway", description = "JC支付方式表实体类")
public class Dictpayway extends Model<Dictpayway> implements Serializable {
    private static final long serialVersionUID = 293445904122579038L;

    @ApiModelProperty(value = "序号")
    private String idPayway;

    @ApiModelProperty(value = "KEYPAYWAY")
    private String keypayway;

    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @ApiModelProperty(value = "支付方式代码, HIS接口标识")
    private String paywayCode;

    @ApiModelProperty(value = "PAYWAY_CODE2")
    private String paywayCode2;

    @ApiModelProperty(value = "PAYWAY_CODE3")
    private String paywayCode3;

    @ApiModelProperty(value = "PAYWAY_CODEHM")
    private String paywayCodehm;

    @ApiModelProperty(value = "导出代码")
    private String paywaycodex;

    @ApiModelProperty(value = "KEYPAYWAY2")
    private String keypayway2;

    @ApiModelProperty(value = "KEYPAYWAY3")
    private String keypayway3;

    @ApiModelProperty(value = "ID_PAYWAY2")
    private String idPayway2;

    @ApiModelProperty(value = "ID_PAYWAY3")
    private String idPayway3;

    @ApiModelProperty(value = "PAYWAYHIS_CODE")
    private String paywayhisCode;

    @ApiModelProperty(value = "PAYWAYHIS_NAME")
    private String paywayhisName;

    @ApiModelProperty(value = "F_HISINTTRANSDISABLED")
    private Integer fHisinttransdisabled;

    @ApiModelProperty(value = "ID_MEDINSURANCE")
    private String idMedinsurance;

    @ApiModelProperty(value = "ID_MEMBERCARD")
    private String idMembercard;

    @ApiModelProperty(value = "现金")
    private Integer fIscash;

    @ApiModelProperty(value = "离线")
    private Integer fIsoffline;

    @ApiModelProperty(value = "F_DISCOUNTORFREE")
    private Integer fDiscountorfree;

    @ApiModelProperty(value = "F_DELAYED")
    private Integer fDelayed;

    @ApiModelProperty(value = "财务专用")
    private Integer fApplytoaccountonly;

    @ApiModelProperty(value = "用于门诊")
    private Integer fApplytoOutp;

    @ApiModelProperty(value = "用于住院")
    private Integer fApplytoInp;

    @ApiModelProperty(value = "用于团检")
    private Integer fApplytoOrg;

    @ApiModelProperty(value = "用于个人")
    private Integer fApplytoPerson;

    @ApiModelProperty(value = "可退现金")
    private Integer fReturntocash;

    @ApiModelProperty(value = "内置")
    private Integer fBuiltin;

    @ApiModelProperty(value = "禁用")
    private Integer fDisabled;

    @ApiModelProperty(value = "团体发票ID")
    private String idReceipttypeOrg;

    @ApiModelProperty(value = "个人发票ID")
    private String idReceipttypePerson;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "F_HIDEONDAILYREPORT是否计算提成")
    private Integer fHideondailyreport;

    @ApiModelProperty(value = "F_AUTOINPUTBOX")
    private Integer fAutoinputbox;

    @ApiModelProperty(value = "排列顺序")
    private String plsx;

    @ApiModelProperty(value = "HIS不发送")
    private Integer hisbfs;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "删除")
    private Double isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Double isChange;

    @ApiModelProperty(value = "${column.comment}")
    private String thingKingdeeNumber;

    @ApiModelProperty(value = "${column.comment}")
    private String thingKingdeePaywayname;

    @ApiModelProperty(value = "${column.comment}")
    private String thingKingdeeUseStatus;

    @ApiModelProperty(value = "${column.comment}")
    private String groupKingdeeNumber;

    @ApiModelProperty(value = "${column.comment}")
    private String groupKingdeePaywayname;

    @ApiModelProperty(value = "${column.comment}")
    private String groupKingdeeUseStatus;

    @ApiModelProperty(value = "${column.comment}")
    private String posKingdeeNumber;

    @ApiModelProperty(value = "${column.comment}")
    private String posKingdeePaywayname;

    @ApiModelProperty(value = "${column.comment}")
    private String posKingdeeUseStatus;

    @ApiModelProperty(value = "${column.comment}")
    private String kingdeeCompany;

    @ApiModelProperty(value = "${column.comment}")
    private String kingdeeSaleer;

    @ApiModelProperty(value = "${column.comment}")
    private String vaccine;
}
