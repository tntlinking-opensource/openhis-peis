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
 * 危害因素-套餐匹配表(MdHarmPackageMatch)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
@Data
@TableName("md_harm_package_match")
@ApiModel(value = "MdHarmPackageMatch", description = "危害因素-套餐匹配表实体类")
public class MdHarmPackageMatch extends Model<MdHarmPackageMatch> implements Serializable {
    private static final long serialVersionUID = 960618312334250122L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "接害因素id")
    private String harmId;

    @ApiModelProperty(value = "上级接害因素id")
    private String pharmId;
}
