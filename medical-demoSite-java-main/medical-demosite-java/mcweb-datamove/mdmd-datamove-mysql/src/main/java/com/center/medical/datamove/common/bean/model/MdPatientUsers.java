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
 * 体检用户账号(MdPatientUsers)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
@Data
@TableName("md_patient_users")
@ApiModel(value = "MdPatientUsers", description = "体检用户账号实体类")
public class MdPatientUsers extends Model<MdPatientUsers> implements Serializable {
    private static final long serialVersionUID = 923195062372728749L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "档案ID")
    private String archiveId;

    @ApiModelProperty(value = "激活状态")
    private String status;
}
