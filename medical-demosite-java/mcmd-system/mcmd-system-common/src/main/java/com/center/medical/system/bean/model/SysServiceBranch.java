package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统服务-分中心关联记录(SysServiceBranch)表实体类
 *
 * @author makejava
 * @since 2024-03-01 18:02:35
 */
@Data
@TableName("sys_service_branch")
@ApiModel(value = "SysServiceBranch", description = "系统服务-分中心关联记录实体类")
public class SysServiceBranch extends Model<SysServiceBranch> implements Serializable {
    private static final long serialVersionUID = -53745558170425869L;

    @TableId(value = "id")
    @ApiModelProperty(value = "关联ID")
    private Integer id;

    @ApiModelProperty(value = "系统服务ID（关联数据表sys_service_type的ID）")
    private Integer serviceId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "是否人工处理 ：0.否 1.是")
    private Integer isManual;

    @ApiModelProperty(value = "人工处理人员(关联sys_user表user_no)")
    private String executer;

    @ApiModelProperty(value = "默认执行时间")
    private Date executeTime;

    @ApiModelProperty(value = "状态：0.正常 1.停用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
