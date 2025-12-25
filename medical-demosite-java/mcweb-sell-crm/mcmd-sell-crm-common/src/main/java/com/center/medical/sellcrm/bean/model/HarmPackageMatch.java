package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 危害因素-套餐匹配表(HarmPackageMatch)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_harm_package_match")
@ApiModel(value = "HarmPackageMatch", description = "危害因素-套餐匹配表实体类")
public class HarmPackageMatch extends Model<HarmPackageMatch> implements Serializable {
    private static final long serialVersionUID = -51224967422797622L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "接害因素id")
    private String harmId;

    @ApiModelProperty(value = "上级接害因素id")
    private String pharmId;
}
