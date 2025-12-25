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
 * KS问诊—职业史—危害因素(MdWzZysWhys)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:26
 */
@Data
@TableName("md_wz_zys_whys")
@ApiModel(value = "MdWzZysWhys", description = "KS问诊—职业史—危害因素实体类")
public class MdWzZysWhys extends Model<MdWzZysWhys> implements Serializable {
    private static final long serialVersionUID = 892610108111469793L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检次数")
    private Integer amount;

    @ApiModelProperty(value = "流水号")
    private String occupationNo;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "危害因素")
    private String diathesis;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;
}
