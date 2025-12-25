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
 * KS问诊——体检单位信息(WzTjdwxx)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:51
 */
@Data
@TableName("WZ_TJDWXX")
@ApiModel(value = "WzTjdwxx", description = "KS问诊——体检单位信息实体类")
public class WzTjdwxx extends Model<WzTjdwxx> implements Serializable {
    private static final long serialVersionUID = 678464585152776043L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "单位编码")
    private String corpCode;

    @ApiModelProperty(value = "单位名称")
    private String corpName;

    @ApiModelProperty(value = "地址")
    private String corpAddress;

    @ApiModelProperty(value = "邮编")
    private String post;

    @ApiModelProperty(value = "ECONOMY_TYPE")
    private String economyType;

    @ApiModelProperty(value = "CALLING")
    private String calling;

    @ApiModelProperty(value = "CALLING_SPACE")
    private String callingSpace;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "联系人")
    private String dbUser;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "LXR")
    private String lxr;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;
}
