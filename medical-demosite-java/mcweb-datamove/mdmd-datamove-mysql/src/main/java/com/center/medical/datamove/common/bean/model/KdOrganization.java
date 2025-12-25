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
 * 金蝶中的组织信息（kingdeeorganization）(KdOrganization)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
@Data
@TableName("kd_organization")
@ApiModel(value = "KdOrganization", description = "金蝶中的组织信息（kingdeeorganization）实体类")
public class KdOrganization extends Model<KdOrganization> implements Serializable {
    private static final long serialVersionUID = -75050364281140729L;

    @TableId(value = "org_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "org_id")
    private String orgId;

    @ApiModelProperty(value = "org_number")
    private String orgNumber;

    @ApiModelProperty(value = "org_name")
    private String orgName;

    @ApiModelProperty(value = "parent_id")
    private String parentId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
