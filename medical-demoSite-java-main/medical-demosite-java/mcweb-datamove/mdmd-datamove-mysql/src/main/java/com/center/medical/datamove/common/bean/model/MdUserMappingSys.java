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
 * 用户映射系统(MdUserMappingSys)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:23
 */
@Data
@TableName("md_user_mapping_sys")
@ApiModel(value = "MdUserMappingSys", description = "用户映射系统实体类")
public class MdUserMappingSys extends Model<MdUserMappingSys> implements Serializable {
    private static final long serialVersionUID = -88713966506386491L;

    @TableId(value = "system_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "系统ID")
    private String systemId;

    @ApiModelProperty(value = "系统名称")
    private String name;

    @ApiModelProperty(value = "系统备注")
    private String ramark;

    @ApiModelProperty(value = "公钥")
    private String publicKey;

    @ApiModelProperty(value = "私钥")
    private String privateKey;

    @ApiModelProperty(value = "状态：-1删除 1.正常开放 2.暂停开放")
    private Integer status;

    @ApiModelProperty(value = "建立时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
