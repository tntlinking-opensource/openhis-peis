package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * kingdeeorganization(KdOrganization)表实体类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
@Data
@TableName("kd_organization")
@ApiModel(value = "KdOrganization", description = "kingdeeorganization实体类")
public class KdOrganization extends Model<KdOrganization> implements Serializable {
    private static final long serialVersionUID = -15266378100642521L;

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
