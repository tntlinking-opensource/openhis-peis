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
 * (ProfessionCustomer)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:39
 */
@Data
@TableName("PROFESSION_CUSTOMER")
@ApiModel(value = "ProfessionCustomer", description = "$tableInfo.comment实体类")
public class ProfessionCustomer extends Model<ProfessionCustomer> implements Serializable {
    private static final long serialVersionUID = -53229950186449616L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "地址")
    private String empOrgAddress;

    @ApiModelProperty(value = "行政区域")
    private String empOrgAreaName;

    @ApiModelProperty(value = "组织机构代码")
    private String empOrgBizCode;

    @ApiModelProperty(value = "经济行业")
    private String empOrgEconSectorName;

    @ApiModelProperty(value = "经济类型")
    private String empOrgEconTypeName;

    @ApiModelProperty(value = "用人单位名称")
    private String empOrgName;

    @ApiModelProperty(value = "联系电话")
    private String empOrgPhone;

    @ApiModelProperty(value = "邮编")
    private String empOrgPostcode;

    @ApiModelProperty(value = "二级代码")
    private String empOrgSecBizCode;

    @ApiModelProperty(value = "企业规模")
    private String empOrgSizeName;
}
